package com.sahaj.hotel.automation.entity.base;

import com.sahaj.hotel.automation.entity.constant.AC_STATE;

public interface IAC {

	void switchOn();
	void switchOff();
	void setState(AC_STATE state);
	AC_STATE getState();
	
}
