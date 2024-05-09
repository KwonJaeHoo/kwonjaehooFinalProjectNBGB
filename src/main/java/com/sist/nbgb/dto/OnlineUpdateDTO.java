package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClass;
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
public class OnlineUpdateDTO {
    private String onlineClassTitle;
    private String onlineClassContent;
    private Long onlineCategoryId;
    private Long onlineClassPrice;
    private Long onlineClassPeriod;
    private String rejection;
    private Status onlineClassApprove;
    private LocalDateTime rejectionRegdate;
    private String onlineFileContent;
    
    public OnlineUpdateDTO(OnlineClass onlineClass) {
        this.onlineClassTitle = onlineClass.getOnlineClassTitle();
        this.onlineClassContent = onlineClass.getOnlineClassContent();
        this.onlineCategoryId = onlineClass.getOnlineCategoryId().getOnlineCategoryId();
        this.onlineClassPrice = onlineClass.getOnlineClassPrice();
        this.rejection = onlineClass.getRejection();
        this.onlineClassApprove = onlineClass.getOnlineClassApprove();
        this.rejectionRegdate = onlineClass.getRejectionRegdate();
        this.onlineFileContent = onlineClass.getOnlineClassContent();
    }
    
}
