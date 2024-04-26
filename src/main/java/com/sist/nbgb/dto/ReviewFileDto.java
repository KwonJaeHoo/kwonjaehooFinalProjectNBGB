package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Review;
import com.sist.nbgb.entity.ReviewFile;
import com.sist.nbgb.entity.ReviewFileId;

import lombok.Getter;

@Getter
public class ReviewFileDto {
	private final Review reviewId;
	private final ReviewFileId reviewFileId;
	private final String reviewFileOrgName;
	private final String reviewFileName;
	private final String reviewFileExt;
	private final Long reviewFileSize;
	
	public ReviewFileDto(ReviewFile reviewFile)
	{
		this.reviewId = reviewFile.getReviewId();
		this.reviewFileId = reviewFile.getReviewFileId();
		this.reviewFileOrgName = reviewFile.getReviewFileOrgName();
		this.reviewFileName = reviewFile.getReviewFileName();
		this.reviewFileExt = reviewFile.getReviewFileExt();
		this.reviewFileSize = reviewFile.getReviewFileSize();
	}
}

