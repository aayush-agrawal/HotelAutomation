package com.sahaj.hotel.automation.model;

import com.sahaj.hotel.automation.motion.model.base.INoMotionRequestModel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class NoMotionRequestModel implements INoMotionRequestModel {

	int floorNumber;
	int corridorNumber;

}
