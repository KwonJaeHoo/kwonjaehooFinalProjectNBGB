package com.sist.nbgb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sist.nbgb.entity.Instructors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class InstructorInfoDto
{
	private String instructorId;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String instructorPassword;
	
	private String instructorName;

	private String instructorNickname;
	
	private String instructorEmail;
	
	private String instructorPhone;
	
	private String instructorBank;
	
	private String instructorAccount;
	
	private String instructorCategory;
	
	public static InstructorInfoDto infoInstructor(List<Instructors> instructors)
	{
		return InstructorInfoDto
				.builder()
				.instructorId(instructors.get(0).getInstructorId())
				.instructorPassword(instructors.get(0).getInstructorPassword())
				.instructorName(instructors.get(0).getInstructorName())
				.instructorNickname(instructors.get(0).getInstructorNickname())
				.instructorEmail(instructors.get(0).getInstructorEmail())
				.instructorPhone(instructors.get(0).getInstructorPhone())
				.instructorBank(instructors.get(0).getInstructorBank())
				.instructorAccount(instructors.get(0).getInstructorAccount())
				.instructorCategory(instructors.get(0).getInstructorCategory())
				.build();
	}
}
