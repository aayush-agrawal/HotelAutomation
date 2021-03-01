package com.sahaj.hotel.automation.motion.controller;

import org.springframework.stereotype.Component;

import com.sahaj.hotel.automation.model.MotionRequestModel;
import com.sahaj.hotel.automation.model.NoMotionRequestModel;
import com.sahaj.hotel.automation.motion.service.base.IMotionService;
import com.sahaj.hotel.automation.motion.service.base.INoMotionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AutomationController {

	private final IMotionService motionService;
	private final INoMotionService noMotionService;
	
	public void motion(int floorNumber, int corridorNumber, boolean isMotion){
		if(isMotion)
			motionService.execute(MotionRequestModel.builder()
							.floorNumber(floorNumber)
							.corridorNumber(corridorNumber)
							.build());
		else
			noMotionService.execute(NoMotionRequestModel.builder()
					.floorNumber(floorNumber)
					.corridorNumber(corridorNumber)
					.build());

	}
}
