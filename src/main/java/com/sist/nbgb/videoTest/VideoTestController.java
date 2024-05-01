package com.sist.nbgb.videoTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.entity.OnlineClassFileId;
import com.sist.nbgb.service.OnlineClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VideoTestController {
	private final VideoTestService videoService;
	private final OnlineClassService onlineClassService;
	
	//페이지 이동
	@GetMapping("/videoTest")
	public String videoTest() {
		return "/onlineClass/onlineClassPlayTest";
	}
	
	//파일 업로드
	@ResponseBody
	@PostMapping("/videoTest/upload")
	public String videoUploadTest(@RequestParam(value="inputFile", required=false) MultipartFile insertFile,
			@RequestParam(value="comment", required=false) String comment) throws IOException{
		log.debug("[VideoTestController] videoUploadTest");
		Long onlineClassId = Long.valueOf(22);
		Long onlineFileId = Long.valueOf(8);
		OnlineClass onlineClass = onlineClassService.findById(onlineClassId);
		String path="C:\\project\\sts4\\SFPN\\src\\main\\resources\\static\\video";
		String fileOrgName = insertFile.getOriginalFilename().substring(0, insertFile.getOriginalFilename().lastIndexOf("."));
		String fileExt = insertFile.getOriginalFilename().substring(insertFile.getOriginalFilename().lastIndexOf("."));
		String fileSaveName = String.valueOf(onlineClassId) + "_" + String.valueOf(onlineFileId) + "강_" + insertFile.getOriginalFilename();
		Long fileSize = insertFile.getSize();
		
		
		OnlineClassFileTestDTO fileDto = OnlineClassFileTestDTO.builder()
				.onlineClassId(onlineClass)
				.onlineClassFileId(OnlineClassFileId
						.builder().onlineClassId(onlineClassId)
						.onlineFileId(onlineFileId).onlineFileLength(Long.valueOf(180)).build())
				.onlineFileOrgName(fileOrgName)
				.onlineFileName(fileSaveName)
				.onlineFileExt(fileExt)
				.onlineFileSize(fileSize)
				.onlineFileContent(comment)
				.build();
		
		videoService.saveFile(fileDto);
		
		try {
			File file = new File(path, fileSaveName);
			insertFile.transferTo(file);
			videoService.saveThumnail(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("================");
		System.out.println("파일 저장 경로 : " + path);
		System.out.println("원본파일명 : " + fileOrgName);
		System.out.println("확장자 : " + fileExt);
		System.out.println("저장파일명 : " + fileSaveName);
		System.out.println("comment : " + comment);
		System.out.println("================");
		
		return "redirect:/onlineClass/onlineClassPlayTest";
    }

	


}

