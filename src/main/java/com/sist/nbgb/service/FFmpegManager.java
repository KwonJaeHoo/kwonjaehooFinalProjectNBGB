package com.sist.nbgb.service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class FFmpegManager {

    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;

    private static final String THUMBNAIL_EXTENSION = ".png";
    private static final String DEFAULT_IMAGE_PATH = "src/main/resources/static/images/bg_1.png";


    public void getThumbnail(String sourcePath) {
    	// 썸네일 저장할 경로
        final String outputPath = sourcePath.split("\\.")[0] + THUMBNAIL_EXTENSION;

        try {
        	// ffmpeg cli 명령어 생성
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(sourcePath)
                    .overrideOutputFiles(true)
                    .addOutput(outputPath)
                    .setFormat("image2")
                    .setFrames(1)
                    .setVideoFrameRate(1)
                    .done();
				
            // 명령어 실행
            ffmpeg.run(builder);
        } catch (Exception e) {
        	// 썸네일 추출 실패시 기본 이미지 썸네일로 사용
            File thumbnail = new File(outputPath);
            File defaultImage = new File(DEFAULT_IMAGE_PATH);

            try {
                FileUtils.copyFile(defaultImage, thumbnail);
            } catch (Exception ex) {
                log.error("Thumbnail Extract Failed => {}", sourcePath, e);
            }
        }
    }
}