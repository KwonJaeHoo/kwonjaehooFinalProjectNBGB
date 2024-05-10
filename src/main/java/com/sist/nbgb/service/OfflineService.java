package com.sist.nbgb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nbgb.dto.ClassLikeDTO;
import com.sist.nbgb.dto.OfflineApproveResponse;
import com.sist.nbgb.dto.OfflinePayBeforeDto;
import com.sist.nbgb.dto.OfflinePaymentApproveDto;
import com.sist.nbgb.dto.OfflinePopDto;
import com.sist.nbgb.dto.OfflinePostDto;
import com.sist.nbgb.dto.OfflineReviewLikeDto;
import com.sist.nbgb.dto.OfflineUpdateDto;
import com.sist.nbgb.entity.ClassId;
import com.sist.nbgb.entity.ClassLike;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.entity.OfflinePaymentApprove;
import com.sist.nbgb.entity.ReviewId;
import com.sist.nbgb.entity.ReviewLike;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;
import com.sist.nbgb.repository.InstructorsRepository;
import com.sist.nbgb.repository.OfflineLikeRepository;
import com.sist.nbgb.repository.OfflinePaymentApproveRepository;
import com.sist.nbgb.repository.OfflineRepository;
import com.sist.nbgb.repository.OfflineUserRepository;
import com.sist.nbgb.repository.UserRepository;
import com.sist.nbgb.response.UUIDUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OfflineService
{
	private final OfflineRepository offlineRepository;
	
	private final OfflineUserRepository userRepository;
	
	private final OfflineLikeRepository likeRepository;
	
	private final UserRepository userRepository2;
	
	private final InstructorsRepository instructorRepository;
	
	private final OfflinePaymentApproveRepository approveRepository;
	
	public List<OfflineClass> findAll()
	{	
		return offlineRepository.findAll();
	}
	
	public List<OfflineClass> findByUpload(Status approve)
	{
		return offlineRepository.findByUpload(approve);
	}
	
	public List<OfflineClass> findByCategory(String search, Status approve)
	{
		return offlineRepository.findByCategory(search, approve);
	}
	
	public List<OfflineClass> findByPalce(String search, Status approve)
	{
		return offlineRepository.findByPalce(search, approve);
	}
	
	public List<OfflineClass> findByTwoPlace(String search1, String search2, Status approve)
	{
		return offlineRepository.findByTwoPlace(search1, search2, approve);
	}
	
	public List<OfflineClass> findBySearch(String search, Status offlineClassApprove)
	{
		return offlineRepository.findBySearch(search, offlineClassApprove);
	}
	
	//중복검색
	public List<OfflineClass> findCateKeyword(String search1, String search2, Status offlineClassApprove)
	{
		return offlineRepository.findCateKeyword(search1, search2, offlineClassApprove);
	}
	
	public List<OfflineClass> findPlaceKeyword(String search1, String search2, Status offlineClassApprove)
	{
		return offlineRepository.findPlaceKeyword(search1, search2, offlineClassApprove);
	}
	
	public List<OfflineClass> findTwoPlaceKeyword(String search1, String search2, String search3, Status offlineClassApprove)
	{
		return offlineRepository.findTwoPlaceKeyword(search1, search2, search3, offlineClassApprove);
	}
	
	
	//뷰 페이지
	public OfflineClass findByView(Long offlineClassId)
	{
		return offlineRepository.findByView(offlineClassId);
	}
	
	public int updateViews(Long offlineClassId)
	{
		return offlineRepository.updateViews(offlineClassId);
	}
	
	//찜
	//찜 중복 검색
	public Long duplicationLike(Long offlineClassId, String userId)
	{		
		return likeRepository.countByClassId_classIdAndClassId_classIdenAndClassId_userId(offlineClassId, "OFF" , userId); 
	}
	
	//찜 등록
	@Transactional
	public ClassLikeDTO offlineLike(ClassLikeDTO likeDto)
	{
		ClassId classId = ClassId.builder()
				.classId(likeDto.getClassId())
				.classIden(likeDto.getClassIden())
				.build();
		
		User user = userRepository2.findFirstByUserId(likeDto.getUserId());
		
		ClassLike classLike = ClassLike.builder()
				.classId(classId)
				.userId(user)
				.build();
		
		return ClassLikeDTO.toDto(likeRepository.save(classLike));
	}
	
	//찜 갯수
	public Long countLike(Long offlineClassId)
	{
		return likeRepository.countByClassId_classIdAndClassId_classIden(offlineClassId, "OFF");
	}
	
	//찜 취소
	@Transactional
	public int deleteLike(Long classId, String userId)
	{
		if(duplicationLike(classId, userId) > 0)
		{
			return likeRepository.deleteByClassId_classIdAndClassId_classIdenAndClassId_userId(classId, "OFF", userId);
		}
		return 0;
	}
	
	//오프라인 등록
	@Transactional
	public OfflinePostDto offlinePost(OfflinePostDto offlinePostDto)
	{
		Instructors id = instructorRepository.findFirstByInstructorId(offlinePostDto.getInstructorId());
		
		
		OfflineClass offlineClass = OfflineClass.builder()
				.offlineClassTitle(offlinePostDto.getOfflineClassTitle())
				.offlineClassContent(offlinePostDto.getOfflineClassContent())
				.offlineClassRegdate(LocalDateTime.now())
				.offlineClassPlace(offlinePostDto.getOfflineClassPlace())
				.offlineClassCategory(offlinePostDto.getOfflineClassCategory())
				.instructorId(id)
				.offlineClassPrice(offlinePostDto.getOfflineClassPrice())
				.offlineClassApprove(Status.Y)
				.rejection(null)
				.offlineClassLimitPeople(offlinePostDto.getOfflineClassLimitPeople())
				.offlineClassViews((long) 0)
				.build();

		 // 저장 후에 offlineClassId 값을 가져옵니다.
	    OfflineClass savedOfflineClass = offlineRepository.save(offlineClass);
	    Long offlineClassId = savedOfflineClass.getOfflineClassId();

	    // 반환할 DTO에 offlineClassId 값을 설정한 후에 반환합니다.
	    offlinePostDto.setOfflineClassId(offlineClassId);
	    
	    return offlinePostDto;
	}
	
	//오프라인 공방 예약하기
	public Optional<User> findByUserId(String userId)
	{
		return userRepository.findByUserId(userId);
	}
	
	//강사 찾기
	public Optional<Instructors> findByInstructorId(String instructorId)
	{
		return instructorRepository.findById(instructorId);
	}
	
	
	//결제 저장
	@Transactional
	public OfflinePaymentApproveDto payUpload(OfflinePayBeforeDto payDto)
	{
		String orderId = UUIDUtil.uniqueValue();
		
		
		OfflinePaymentApprove offlinePaymentApprove = OfflinePaymentApprove.builder()
				.partnerOrderId(orderId)
				.partnerUserId(payDto.getUserId())
				.itemCode(payDto.getOfflineClassId())
				.itemName(payDto.getOfflineClassTitle())
				.bookingDate(payDto.getBookingDate())
				.bookingTime(payDto.getResTime())
				.point(Long.parseLong(payDto.getUsedPoint()))
				.totalAmount(Long.parseLong(payDto.getTotalAmount()))
				.status("S")
				.build();

		 // 저장 후에 offlineClassId 값을 가져옵니다.
		OfflinePaymentApprove savedOfflineClass = approveRepository.save(offlinePaymentApprove);
	    String partnerOrderId = savedOfflineClass.getPartnerOrderId();

	    // 반환할 DTO에 offlineClassId 값을 설정한 후에 반환합니다.
	    OfflinePaymentApproveDto approveDto = new OfflinePaymentApproveDto();
	    approveDto.setPartnerOrderId(partnerOrderId);
	    approveDto.setPartnerUserId(savedOfflineClass.getPartnerUserId());
	    approveDto.setItemCode(savedOfflineClass.getItemCode());
	    approveDto.setItemName(savedOfflineClass.getItemName());
	    approveDto.setBookingDate(savedOfflineClass.getBookingDate());
	    approveDto.setBookingTime(savedOfflineClass.getBookingTime());
	    approveDto.setPoint(savedOfflineClass.getPoint());
	    approveDto.setTotalAmount(savedOfflineClass.getTotalAmount());
	    
	    return approveDto;
	}
	
	//결제 오류시 삭제
	@Transactional
	public int deletePay(String orderId)
	{
		return approveRepository.deleteByPartnerOrderId(orderId);
	}
	
	//결제찾기
	public Optional<OfflinePaymentApprove> findbyPartnerOrderId(String orderId)
	{
		return approveRepository.findAllByPartnerOrderId(orderId);
	}
	
	//업데이트
	@Transactional
	public int updatePay(OfflineApproveResponse payDto)
	{
		return approveRepository.updatePay(payDto.getPartner_order_id(), payDto.getTid(), payDto.getCid());
	}
	
	@Transactional
	public int updatePoint(String userId, Long point)
	{
		return userRepository.updatePoint(userId, point);
	}
	
	//결제 갯수 조회
	public Long countPeople(String classId, String date, String time)
	{
		return approveRepository.countPeople(classId, date, time);
	}
	
	//오프라인 클래스 수정
	@Transactional
	public Long offlineUpdate(final Long id, final OfflineUpdateDto params)
	{
		OfflineClass entity = offlineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
		entity.update(params.getOfflineClassTitle(), params.getOfflineClassContent(), params.getOfflineClassCategory(), params.getOfflineClassLimitPeople(), params.getOfflineClassPrice(), params.getOfflineClassPlace());
		
		return id;
	}
}
