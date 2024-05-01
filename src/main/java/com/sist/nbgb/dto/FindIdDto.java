package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindIdDto 
{
	private String name;
	
	private String email;
	
	public static FindIdDto findUser(User user)
	{
		return FindIdDto.builder()				
				.name(user.getUserName())
				.email(user.getUserEmail())
				.build();
	}
	
	public static FindIdDto findInstructor(Instructors instructors)
	{
		return FindIdDto.builder()
				.name(instructors.getInstructorName())
				.email(instructors.getInstructorEmail())
				.build();
	}
}
