package com.sahaj.hotel.automation.entity.base;

public interface ISubCorridor extends ICorridor {

	void turnOnTheLight();
	void turnOffTheLight();
	void turnOnTheAC();
	void turnOffTheAC();
	boolean isActive();
	ISubCorridor setIsActive(boolean isActive);
	
}
