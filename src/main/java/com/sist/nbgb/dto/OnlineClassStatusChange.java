package com.sist.nbgb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineClassStatusChange 
{
	private Long onlineClassId;
	private String rejection;
}
