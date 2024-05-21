package com.sist.nbgb.dto;

import com.sist.nbgb.entity.Admin;
import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.entity.User;
import com.sist.nbgb.enums.Provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeDto 
{
	private String nickname;
	
	private Provider Provider;
	
	public static AttributeDto userAttribute(User user)
	{
		return AttributeDto
				.builder()
				.nickname(user.getUserNickname())
				.Provider(user.getUserProvider())
				.build();
	}
	
	public static AttributeDto instructorAttribute(Instructors instructors)
	{
		return AttributeDto
				.builder()
				.nickname(instructors.getInstructorNickname())
				.build();
	}
	
	public static AttributeDto adminAttribute(Admin admin)
	{
		return AttributeDto
				.builder()
				.nickname(admin.getAdminName())
				.build();
	}
}
