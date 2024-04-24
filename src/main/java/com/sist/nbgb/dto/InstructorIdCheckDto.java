package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Instructors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructorIdCheckDto 
{
	private String instructorId;
	
	public static InstructorIdCheckDto of(Instructors instructors)
	{
		return InstructorIdCheckDto
				.builder()
				.instructorId(instructors.getInstructorId())
				.build();
	}
}
