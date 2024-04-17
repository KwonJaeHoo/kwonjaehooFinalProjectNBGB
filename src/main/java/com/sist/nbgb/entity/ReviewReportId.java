package com.sist.nbgb.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class ReviewReportId implements Serializable 
{

	private static final long serialVersionUID = 1L;
	private String userId;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
}