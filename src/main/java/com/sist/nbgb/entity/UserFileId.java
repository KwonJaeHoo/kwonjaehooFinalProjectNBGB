package com.sist.nbgb.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class UserFileId implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String userId;
}
