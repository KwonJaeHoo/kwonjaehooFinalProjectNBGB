package com.sist.nbgb.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class InstructorsDto 
{
	@NotNull
	@Size(min = 4, max = 20)
	private String instructorId;
	
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Size(min = 4, max = 32)
	private String instructorPassword;
	
	@NotNull
	@Size(min = 2, max = 16)
	private String instructorName;

	@NotNull
	@Size(min = 2, max = 16)
	private String instructorNickname;
	
	@NotNull
	@Size(max = 50)
	private String instructorEmail;
	
	@NotNull
	private String instructorPhone;
	
	@NotNull
	private String instructorBank;
	
	@NotNull
	private String instructorAccount;
	
	@NotNull
	@Size(min = 2, max = 30)
	private String instructorCategory;
	
	public static InstructorsDto from(Instructors instructors)
	{
		if(instructors == null)
		{
			return null;
		}
		
		return InstructorsDto.builder()
				.instructorId(instructors.getInstructorId())
				.instructorName(instructors.getInstructorName())
				.instructorNickname(instructors.getInstructorNickname())
				.instructorEmail(instructors.getInstructorEmail())
				.instructorPhone(instructors.getInstructorPhone())
				.instructorBank(instructors.getInstructorBank())
				.instructorAccount(instructors.getInstructorAccount())
				.instructorCategory(instructors.getInstructorCategory())
				.build();
	}
}
