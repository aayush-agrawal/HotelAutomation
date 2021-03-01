package com.sahaj.hotel.automation.entity;

import com.sahaj.hotel.automation.entity.base.IAC;
import com.sahaj.hotel.automation.entity.constant.AC_STATE;

import lombok.Builder;

@Builder
public class AC implements IAC {

	
	protected AC_STATE state; 
	
	@Override
	public void switchOn() {
		this.state = AC_STATE.AC_ON;
	}
	
	@Override
	public void switchOff() {
		this.state = AC_STATE.AC_OFF;
	}
	
	@Override
	public AC_STATE getState() {
		return state;
	}
	
	@Override
	public void setState(AC_STATE state) {
		this.state = state;
	}
	
}
