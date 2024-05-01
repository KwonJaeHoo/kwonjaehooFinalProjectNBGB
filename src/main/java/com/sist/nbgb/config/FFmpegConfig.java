package com.sist.nbgb.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;

@Slf4j
@Configuration
public class FFmpegConfig {
 @Value("${ffmpeg.main.location}")
    private String ffmpegPath;

    @Value("${ffmpeg.probe.location}")
    private String ffprobePath;

    @Bean
    public FFmpeg ffMpeg() throws IOException {
        return new FFmpeg(ffmpegPath);
    }

    @Bean
    public FFprobe ffProbe() throws IOException {
        return new FFprobe(ffprobePath);
    }
}
