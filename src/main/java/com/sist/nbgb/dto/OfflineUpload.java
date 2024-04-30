package com.sist.nbgb.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class OfflineUpload 
{
	public String ckUpload(MultipartHttpServletRequest request) {
        MultipartFile uploadFile = request.getFile("upload");

        String fileName = getFileName(uploadFile); // 파일명만을 추출합니다.

        String realPath = getPath(request);

        String savePath = realPath + fileName;

        String uploadPath = "/" + fileName;

        uploadFile(savePath, uploadFile);

        return uploadPath;
    }

    private void uploadFile(String savePath, MultipartFile uploadFile) {
        File file = new File(savePath);
        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload the file", e);
        }
    }

    private String getFileName(MultipartFile uploadFile) {
        String originalFileName = uploadFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + ext; // UUID 앞부분을 제거하여 파일명만 반환합니다.
    }

    private String getPath(MultipartHttpServletRequest request) {
        // 실제 파일 저장 경로
        String realPath = "C:/project/sts4/SFPN/src/main/resources/static/images/offlineUpload/";
        Path directoryPath = Paths.get(realPath);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory", e);
            }
        }
        return realPath;
    }
    
    //온라인
    public String ckUploadOnline(MultipartHttpServletRequest request) {
        MultipartFile uploadFile = request.getFile("upload");

        String fileName = getFileName(uploadFile); // 파일명만을 추출합니다.

        String realPath = getPathOnline(request);

        String savePath = realPath + fileName;

        String uploadPath = "/" + fileName;

        uploadFile(savePath, uploadFile);

        return uploadPath;
    }
    
    private String getPathOnline(MultipartHttpServletRequest request) {
        // 실제 파일 저장 경로
        String realPath = "C:/project/sts4/SFPN/src/main/resources/static/images/onlineUpload/";
        Path directoryPath = Paths.get(realPath);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory", e);
            }
        }
        return realPath;
    }
}
