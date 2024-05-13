package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClassFile;
import com.sist.nbgb.entity.OnlineClassLog;
import com.sist.nbgb.entity.OnlineClassLogId;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ReviewUpdateDTO {
	private String reviewContent;
	private Long reviewRating;
	private LocalDateTime reviewRegdate;
	
	public ReviewUpdateDTO(String reviewContent, Long reviewRating) {
		this.reviewContent = reviewContent;
		this.reviewRating = reviewRating;
		this.reviewRegdate = LocalDateTime.now().withNano(0);
	}
}
