package com.sahaj.hotel.automation.entity.base;

import java.util.Set;

public interface IFloor extends IEntity {
	
	int getNumber();
	Set<ISubCorridor> getSubCorridors();
	Set<IMainCorridor> getMainCorridors();
	int getTotalConsumption();
	int getAllowedConsumption();
	public IFloor setActive(int corridorNumber);
	public IFloor setNonActive(int corridorNumber);
	public IFloor updateTotalConsumption(int totalConsumption);
}
