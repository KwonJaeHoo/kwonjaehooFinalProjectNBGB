package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OfflineClass;
import com.sist.nbgb.enums.Status;

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
public class OfflineUpdateDto {
    private String offlineClassTitle;
    private String offlineClassContent;
    private String offlineClassPlace;
    private String offlineClassCategory;
    private Long offlineClassPrice;
    private Long offlineClassLimitPeople;
    private String rejection;
    private Status onlineClassApprove;
    private LocalDateTime rejectionRegdate;

    public OfflineUpdateDto(OfflineClass offlineClass) {
    	this.offlineClassTitle = offlineClass.getOfflineClassTitle();
    	this.offlineClassContent = offlineClass.getOfflineClassContent();
    	this.offlineClassPlace = offlineClass.getOfflineClassPlace();
    	this.offlineClassCategory = offlineClass.getOfflineClassCategory();
    	this.offlineClassPrice = offlineClass.getOfflineClassPrice();
    	this.offlineClassLimitPeople = offlineClass.getOfflineClassLimitPeople();
    	this.rejection = offlineClass.getRejection();
    	this.onlineClassApprove = offlineClass.getOfflineClassApprove();
    	this.rejectionRegdate = offlineClass.getOfflineClassRegdate();
    }
}
