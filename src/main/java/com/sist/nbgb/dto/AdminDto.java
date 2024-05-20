package com.sist.nbgb.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sist.nbgb.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto 
{
	@NotNull
	private String adminId;
	
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String adminPassword;
	
	@NotNull
	private String adminName;
	
	public static AdminDto admin(Admin admin)
	{
		return AdminDto
				.builder()
				.adminId(admin.getAdminId())
				.adminPassword(admin.getAdminPassword())
				.adminName(admin.getAdminName())
				.build();
	}
}
