package com.sahaj.hotel.automation.model;

import com.sahaj.hotel.automation.motion.model.base.IMotionRequestModel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class MotionRequestModel implements IMotionRequestModel {

	int floorNumber;
	int corridorNumber;

}
