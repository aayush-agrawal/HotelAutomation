package com.sahaj.hotel.automation.entity.base;

import java.util.Set;

public interface IHotel extends IEntity {
	
	String getName();
	Set<IFloor> getFloors();
	void updateFloor(IFloor floor);
	
}
