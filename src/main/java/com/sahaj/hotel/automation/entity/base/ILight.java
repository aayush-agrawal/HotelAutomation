package com.sahaj.hotel.automation.entity.base;

import com.sahaj.hotel.automation.entity.constant.LIGHT_STATE;

public interface ILight {

	void switchOn();
	void switchOff();
	void setState(LIGHT_STATE state);
	LIGHT_STATE getState();
}
