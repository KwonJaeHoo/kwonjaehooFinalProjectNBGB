package com.sist.nbgb.response;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.Instructors;
import com.sist.nbgb.enums.Role;
import com.sist.nbgb.enums.Status;

import lombok.Getter;

@Getter
public class InstructorsResponse
{
	private String instructorId;

	private String instructorPassword;
	
	private String instructorName;
	
	private String instructorNickname;
	
	private String instructorEmail;
	
	private String instructorPhone;
	
	private String instructorBank;
	
	private String instructorAccount;
	
	private Role Authority;
	
	private Status instructorStatus;
	
	private LocalDateTime instructorRegdate;
	
	private String instructorCategory;
	
	public InstructorsResponse(Instructors instructors)
	{
		this.instructorId = instructors.getInstructorId();

		this.instructorPassword = instructors.getInstructorPassword();
		
		this.instructorName = instructors.getInstructorName();
		
		this.instructorNickname = instructors.getInstructorNickname();
		
		this.instructorEmail = instructors.getInstructorEmail();
		
		this.instructorPhone = instructors.getInstructorPhone();
		
		this.instructorBank = instructors.getInstructorBank();
		
		this.instructorAccount = instructors.getInstructorAccount();
		
		this.Authority = instructors.getAuthority();
		
		this.instructorRegdate = instructors.getInstructorRegdate();
	
		this.instructorCategory = instructors.getInstructorCategory();
	}
}
