package com.sist.nbgb.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class fileDTO {
	private String content;
	private MultipartFile files;
}
