package com.sahaj.hotel.automation.motion.task;

import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_OFF;
import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_ON;
import static com.sahaj.hotel.automation.entity.constant.PowerConsumption.AC_CONSUMPTION;
import static java.util.function.Predicate.not;

import java.util.List;
import java.util.stream.Collectors;

import com.sahaj.hotel.automation.entity.base.IFloor;
import com.sahaj.hotel.automation.entity.base.IHotel;
import com.sahaj.hotel.automation.entity.base.ISubCorridor;
import com.sahaj.hotel.automation.entity.constant.PowerConsumption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class AutomationTask implements Runnable {

	private int floorNumber;
	private int corridorNumber;
	private IHotel hotel;
	
	@Override
	public void run() {
		
		IFloor floor = hotel.getFloors().stream()
				.filter(el-> floorNumber == el.getNumber())
				.findFirst()
				.get();

		synchronized (floor.getId()) {
			int totalConsumption = floor.getTotalConsumption();
			int allowedConsumption = floor.getAllowedConsumption(); 

			// check if total consumption > allowed consumption
			// turn the AC off for other sub-corridor where there is no motion
//			if(totalConsumption <= allowedConsumption) {
//				return;
//			}
			

			List<ISubCorridor> nonActiveCorridors = floor.getSubCorridors().stream()
//				.filter(el -> el.getNumber() != corridorNumber)
				.filter(not(ISubCorridor::isActive))
				.collect(Collectors.toList());
			
			if(!nonActiveCorridors.isEmpty()) {
				optimise(floor, nonActiveCorridors);
				
			} else {
				// TODO: what if all the other subCorridor has motion? how to optimise
				// if AC is kept ON, need to switch it off while turning OFF the light (no motion)	
			}			
		
		}
	
	}

	private void optimise(IFloor floor, List<ISubCorridor> nonActiveCorridors) {
		for (ISubCorridor nonActiveCorridor : nonActiveCorridors) {
			int totalConsumption = floor.getTotalConsumption();
			int allowedConsumption = floor.getAllowedConsumption(); 
			
			if(totalConsumption == allowedConsumption)
				return;
			
			if(totalConsumption > allowedConsumption) 
				floor = turnOffTheAC(floor, nonActiveCorridor, totalConsumption);				
			else if(totalConsumption + AC_CONSUMPTION <= allowedConsumption)
				floor = turnOnTheAC(floor, nonActiveCorridor, totalConsumption);				

		}
	}

	private IFloor turnOffTheAC(IFloor floor, ISubCorridor nonActiveCorridor, int totalConsumption) {
		if(nonActiveCorridor.getACState().equals(AC_OFF))
			return floor;
		
		nonActiveCorridor.turnOffTheAC();
		floor = floor.updateTotalConsumption(totalConsumption-AC_CONSUMPTION);	
		hotel.updateFloor(floor);
		return floor;
	}
	
	private IFloor turnOnTheAC(IFloor floor, ISubCorridor nonActiveCorridor, int totalConsumption) {
		if(nonActiveCorridor.getACState().equals(AC_ON))
			return floor;
		
		nonActiveCorridor.turnOnTheAC();
		floor = floor.updateTotalConsumption(totalConsumption+AC_CONSUMPTION);	
		hotel.updateFloor(floor);
		return floor;
	}
	
}
