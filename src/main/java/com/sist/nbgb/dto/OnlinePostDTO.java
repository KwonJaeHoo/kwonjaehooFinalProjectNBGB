package com.sist.nbgb.dto;

import com.sist.nbgb.entity.OnlineClass;

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
public class OnlinePostDTO {
    private Long onlineClassId; // 온라인 클래스의 ID

    private String onlineClassTitle;
    private String onlineClassContent;
    private Long onlineCategoryId;
    private Long onlineClassPrice;
    private Long onlineClassPeriod;

    public static OnlinePostDTO toDto(OnlineClass onlineClass) {
        return OnlinePostDTO.builder()
                .onlineClassTitle(onlineClass.getOnlineClassTitle())
                .onlineClassContent(onlineClass.getOnlineClassContent())
                .onlineCategoryId(onlineClass.getOnlineCategoryId().getOnlineCategoryId())
                .onlineClassPrice(onlineClass.getOnlineClassPrice())
                .build();
    }

	public void setOnlineClassId(Long onlineClassId) {
		this.onlineClassId = onlineClassId;
	}
}