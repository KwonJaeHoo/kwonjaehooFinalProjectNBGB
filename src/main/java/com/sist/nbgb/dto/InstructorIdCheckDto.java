package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Instructors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorIdCheckDto 
{
	private String instructorId;
	
	public InstructorIdCheckDto(Instructors instructors)
	{
		this.instructorId = instructors.getInstructorId();
	}
}
