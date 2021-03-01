package com.sahaj.hotel.automation.motion.service;

import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Service;

import com.sahaj.hotel.automation.entity.base.IFloor;
import com.sahaj.hotel.automation.entity.base.IHotel;
import com.sahaj.hotel.automation.model.VoidModel;
import com.sahaj.hotel.automation.model.base.IVoidModel;
import com.sahaj.hotel.automation.motion.model.base.INoMotionRequestModel;
import com.sahaj.hotel.automation.motion.service.base.INoMotionService;
import com.sahaj.hotel.automation.motion.task.AutomationTask;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NoMotionService implements INoMotionService {

	private final IHotel hotel;
	private final ExecutorService automationExecutor;
		
	@Override
	public IVoidModel execute(INoMotionRequestModel model) {
		
		int floorNumber = model.getFloorNumber();
		int corridorNumber = model.getCorridorNumber();
		
		IFloor floor = hotel.getFloors().stream()
				.filter(el-> floorNumber == el.getNumber())
				.findFirst()
				.get();
			
			floor = floor.setNonActive(corridorNumber);
			hotel.updateFloor(floor);
			
//			automationExecutor.execute(new AutomationTask(floorNumber, corridorNumber, hotel));
			new AutomationTask(floorNumber, corridorNumber, hotel).run();

		return new VoidModel();
	}
}
