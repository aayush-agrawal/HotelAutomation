package com.sahaj.hotel.automation.entity;

import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_OFF;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_ON;

import com.sahaj.hotel.automation.entity.base.ILight;
import com.sahaj.hotel.automation.entity.constant.LIGHT_STATE;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Light implements ILight {

	
	protected LIGHT_STATE state; 
	
	@Override
	public void switchOn() {
		this.state = LIGHT_ON;
	}
	
	@Override
	public void switchOff() {
		this.state = LIGHT_OFF;
	}
	
	@Override
	public void setState(LIGHT_STATE state) {
		this.state = state;
	}
	
	@Override
	public LIGHT_STATE getState() {
		return state;
	}
}
