package com.sist.nbgb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "REFRESH_TOKEN")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken 
{
	@Id
	@Column
	//user, instructor id
	private String key;
}
