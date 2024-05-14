package com.sist.nbgb.dto;

import java.time.LocalDateTime;

import com.sist.nbgb.entity.OnlineClass;
import com.sist.nbgb.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineClassDenyDTO {
    private Long onlineClassId;
    private String rejection;
    private Status onlineClassApprove;
    private LocalDateTime onlineClassRegdate;
    
    
    public OnlineClassDenyDTO(OnlineClass onlineClass)
    {
    	this.onlineClassId = onlineClass.getOnlineClassId();
    	this.rejection = onlineClass.getRejection();
    	this.onlineClassApprove = onlineClass.getOnlineClassApprove();
    	this.onlineClassRegdate = onlineClass.getOnlineClassRegdate();
    }
}