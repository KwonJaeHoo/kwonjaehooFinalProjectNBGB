package com.sist.nbgb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountChangeDto 
{
	private String id;
	
	private String bank;
	
	private String account;
}
