package com.sahaj.hotel.automation.entity.base;

import com.sahaj.hotel.automation.entity.constant.AC_STATE;
import com.sahaj.hotel.automation.entity.constant.LIGHT_STATE;

public interface ICorridor extends IEntity {

	int getNumber();
	LIGHT_STATE getLightState();
	AC_STATE getACState();
}
