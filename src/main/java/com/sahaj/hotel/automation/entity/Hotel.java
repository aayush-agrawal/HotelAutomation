package com.sahaj.hotel.automation.entity;

import java.util.Set;
import java.util.UUID;

import com.sahaj.hotel.automation.entity.base.IFloor;
import com.sahaj.hotel.automation.entity.base.IHotel;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Hotel implements IHotel {

	@NonNull protected final String id;
	@NonNull protected final String name;
	@NonNull protected final Set<IFloor> floors;  

	public Hotel(String id, String name, Set<IFloor> floors) {
		super();
		this.id = id == null? UUID.randomUUID().toString(): id;
		this.name = name;
		this.floors = floors;
	}
	
	@Override
	public void updateFloor(IFloor floor) {
		synchronized(this.id) {
			floors.remove(floor);
			floors.add(floor);			
		}
	}
	
}
