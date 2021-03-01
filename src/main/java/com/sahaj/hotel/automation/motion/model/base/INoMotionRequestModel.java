package com.sahaj.hotel.automation.motion.model.base;

import com.sahaj.hotel.automation.model.base.IModel;

public interface INoMotionRequestModel extends IModel {

	int getFloorNumber();
	int getCorridorNumber();
}
