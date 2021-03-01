package com.sahaj.hotel.automation.data;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sahaj.hotel.automation.entity.Floor;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.MainCorridor;
import com.sahaj.hotel.automation.entity.SubCorridor;
import com.sahaj.hotel.automation.entity.base.IFloor;
import com.sahaj.hotel.automation.entity.base.IHotel;
import com.sahaj.hotel.automation.entity.base.IMainCorridor;
import com.sahaj.hotel.automation.entity.base.ISubCorridor;

@Configuration
public class Config {

	@Bean
	IHotel hotel() {
		return Hotel.builder()
				.name("Sahej")
				.floors(getFloors())
				.build();
	}
	
	private Set<IFloor> getFloors() {
		Set<IFloor> floors = new HashSet<>();
		for(int i=1; i<=1; i++) {
			Set<ISubCorridor> subCorridors = getSubCorridors();
			IFloor floor = Floor.builder()
					.number(i)
					.subCorridors(subCorridors)
					.mainCorridors(getMainCorridors())
					.build();
			floors.add(floor);
		}
		return floors;
	}

	private Set<IMainCorridor> getMainCorridors() {
		Set<IMainCorridor> corridors = new HashSet<>();
		for(int i=1; i<=2; i++) {
			IMainCorridor subCorridor = MainCorridor
					.builder()
					.number(i)
					.build();			
			corridors.add(subCorridor);
		}

		return corridors;
	}
	
	private Set<ISubCorridor> getSubCorridors() {
		Set<ISubCorridor> corridors = new HashSet<>();
		for(int i=1; i<=4; i++) {
			ISubCorridor subCorridor = SubCorridor
					.builder()
					.number(i)
					.build();			
			corridors.add(subCorridor);
		}

		return corridors;
	}

}
