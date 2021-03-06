package com.sahaj.hotel.automation.entity;

import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_OFF;
import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_ON;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_OFF;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_ON;
import static com.sahaj.hotel.automation.entity.constant.PowerConsumption.AC_CONSUMPTION;
import static com.sahaj.hotel.automation.entity.constant.PowerConsumption.LIGHT_CONSUMPTION;

import java.util.Set;
import java.util.UUID;

import com.sahaj.hotel.automation.entity.base.ICorridor;
import com.sahaj.hotel.automation.entity.base.IFloor;
import com.sahaj.hotel.automation.entity.base.IMainCorridor;
import com.sahaj.hotel.automation.entity.base.ISubCorridor;

import lombok.Getter;

@Getter
public class Floor implements IFloor {

	protected final String id;
	protected final int number;
	protected final int totalConsumption;
	protected final int allowedConsumption;
	protected final Set<IMainCorridor> mainCorridors;
	protected final Set<ISubCorridor> subCorridors;
	

	public Floor(String id, Integer number, Integer totalConsumption, Set<IMainCorridor> mainCorridors, Set<ISubCorridor> subCorridors) {

		this.id = id == null? UUID.randomUUID().toString(): id;
		this.number = number;
		this.mainCorridors = mainCorridors;
		this.subCorridors = subCorridors;
		this.allowedConsumption = (mainCorridors.size() * 15) + (subCorridors.size() * 10);

		if(totalConsumption == null)
			this.totalConsumption = getTotalConsumption(mainCorridors) + getTotalConsumption(subCorridors); 
		else
			this.totalConsumption = totalConsumption;

//		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(subCorridors.size());
//		
//		subCorridors.forEach(corridor -> {
//			executorService.scheduleAtFixedRate(() -> {
//				corridor.turnOffTheLightIfUnnecessary();
//			}, 1, 1, TimeUnit.SECONDS);
//		});
		
	}

	private int getTotalConsumption(Set<? extends ICorridor> corridors) {
		int consumption = 0;
		for (ICorridor corridor : corridors) {
			if(corridor.getACState().equals(AC_ON))
				consumption += AC_CONSUMPTION;
			if(corridor.getLightState().equals(LIGHT_ON))
				consumption += LIGHT_CONSUMPTION;
		} 
		return consumption;
	}

	@Override
	public Set<ISubCorridor> getSubCorridors() {
		return Set.copyOf(subCorridors);
	}

	@Override
	public IFloor setActive(int corridorNumber) {
		synchronized (this.id) {
			// turn the light on
			// update the total consumption
			ISubCorridor corridor = subCorridors.stream()
				.filter(el-> corridorNumber == el.getNumber())
				.findFirst()
				.get();
			
			if(corridor.isActive()) 
				return this;
			
			int newConsumption = totalConsumption;
			if(corridor.getLightState().equals(LIGHT_OFF)) {				
				corridor.turnOnTheLight();
				newConsumption += LIGHT_CONSUMPTION;
			}

			if(corridor.getACState().equals(AC_OFF)) {				
				corridor.turnOnTheAC();
				newConsumption += AC_CONSUMPTION;
			}
			corridor = corridor.setIsActive(true);

			addCorridor(corridor);
			
			
			return this.toBuilder()
				.totalConsumption(newConsumption)
				.build();			
		}
	}

	@Override
	public IFloor setNonActive(int corridorNumber) {
		synchronized (this.id) {
			// turn the light on
			// update the total consumption
			ISubCorridor corridor = subCorridors.stream()
				.filter(el-> corridorNumber == el.getNumber())
				.findFirst()
				.get();
			
			if(!corridor.isActive()) 
				return this;
			
			int newConsumption = totalConsumption;
			if(corridor.getLightState().equals(LIGHT_ON)) {				
				corridor.turnOffTheLight();
				newConsumption -= LIGHT_CONSUMPTION;
			}
			corridor = corridor.setIsActive(false);

			addCorridor(corridor);
			
			
			return this.toBuilder()
				.totalConsumption(newConsumption)
				.build();			
		}
	}

	public IFloor updateTotalConsumption(int totalConsumption) {
		synchronized (this.id) {						
			return this.toBuilder()
				.totalConsumption(totalConsumption)
				.build();			
		}
	}
	
	private void addCorridor(ISubCorridor corridor) {
		synchronized(this.id) {
			subCorridors.remove(corridor);
			subCorridors.add(corridor);			
		}
	}

	@Override
	public int hashCode() {
		return this.number;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == this) 
            return true; 
  
        if (!(obj instanceof Floor)) 
            return false; 
          
        IFloor floor = (Floor) obj; 
        return floor.getNumber() == number; 
	}
	

	private FloorBuilder toBuilder() {
		return new FloorBuilder(id, number, totalConsumption, mainCorridors, subCorridors);
	}

	public static  FloorBuilder builder() {
 		return new FloorBuilder();
 	}
 	
 	public static class FloorBuilder {
 		
 		String id;
 		Integer number;
 		Integer totalConsumption;
 		Set<IMainCorridor> mainCorridors;
 		Set<ISubCorridor> subCorridors;

		public FloorBuilder(String id, Integer number, Integer totalConsumption, Set<IMainCorridor> mainCorridors,
				Set<ISubCorridor> subCorridors) {
			super();
			this.id = id;
			this.number = number;
			this.totalConsumption = totalConsumption;
			this.mainCorridors = mainCorridors;
			this.subCorridors = subCorridors;
		}
 		

		private FloorBuilder() {}
 		
 		public FloorBuilder id(String id) {
 			this.id = id;
 			return this;
 		}
 		
 		public FloorBuilder number(Integer number) {
 			this.number = number;
 			return this;
 		}
 		
 		public FloorBuilder totalConsumption(Integer totalConsumption) {
 			this.totalConsumption = totalConsumption;
 			return this;
 		}
 		
 		public FloorBuilder mainCorridors(Set<IMainCorridor> mainCorridors) {
 			this.mainCorridors = mainCorridors;
 			return this;
 		}
 		
 		public FloorBuilder subCorridors(Set<ISubCorridor> subCorridors) {
 			this.subCorridors = subCorridors;
 			return this;
 		}

 		
 		public Floor build() {
 			return new Floor(id, number, totalConsumption, mainCorridors, subCorridors);
 		}

 	}

}
