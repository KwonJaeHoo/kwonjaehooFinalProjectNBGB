package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OfflineClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfflinePostDto {
    private Long offlineClassId; // 오프라인 클래스의 ID

    private String instructorId;
    private String offlineClassTitle;
    private String offlineClassContent;
    private String offlineClassPlace;
    private String offlineClassCategory;
    private Long offlineClassPrice;
    private Long offlineClassLimitPeople;

    public static OfflinePostDto toDto(OfflineClass offlineClass) {
        return OfflinePostDto.builder()
        		.instructorId(offlineClass.getInstructorId().getInstructorId())
                .offlineClassTitle(offlineClass.getOfflineClassTitle())
                .offlineClassContent(offlineClass.getOfflineClassContent())
                .offlineClassPlace(offlineClass.getOfflineClassPlace())
                .offlineClassCategory(offlineClass.getOfflineClassCategory())
                .offlineClassPrice(offlineClass.getOfflineClassPrice())
                .offlineClassLimitPeople(offlineClass.getOfflineClassLimitPeople())
                .build();
    }

	public void setOfflineClassId(Long offlineClassId) {
		this.offlineClassId = offlineClassId;
	}
}
