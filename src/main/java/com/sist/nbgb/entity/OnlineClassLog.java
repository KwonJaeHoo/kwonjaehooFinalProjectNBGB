package com.sist.nbgb.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="NBGB_ONLINE_CLASS_LOG")
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Builder
public class OnlineClassLog
{
	@EmbeddedId
	private OnlineClassLogId onlineClassLogId;

	@MapsId("userId")
	@Comment("회원 아이디")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId", columnDefinition = "VARCHAR2(20)")
	private User userId;
	
	@MapsId("OnlineClassFileId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns
	({
		@JoinColumn(name = "onlineClassId"),
		@JoinColumn(name = "onlineFileId"),
		@JoinColumn(name = "onlineFileLength")
	})
	
	private OnlineClassFile onlineClassFile;
	 
	@Column
	@Comment("영상 시청(마지막 시청 위치)")
	private Long onlineLogCurr;
	
	@CreatedDate
	@Comment("로그 날짜")
	private LocalDateTime onlineLogDate;

	@Column
	@Comment("상태")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public void update(Long onlineLogCurr, LocalDateTime onlineLogDate, Status status) {
		this.onlineLogCurr = onlineLogCurr;
		this.onlineLogDate = onlineLogDate;
		this.status = status;
	}
}