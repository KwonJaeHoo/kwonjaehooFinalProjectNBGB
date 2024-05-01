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
public class EmailCheckDto 
{
    private String email;
    
	public static EmailCheckDto findUser(User user)
	{
		return EmailCheckDto.builder()		
				.email(user.getUserEmail())
				.build();
	}
	
	public static EmailCheckDto findInstructor(Instructors instructors)
	{
		return EmailCheckDto.builder()
				.email(instructors.getInstructorEmail())
				.build();
	}
}