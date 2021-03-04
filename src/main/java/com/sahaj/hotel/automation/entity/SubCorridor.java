package com.sahaj.hotel.automation.entity;

import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_ON;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_OFF;

import java.util.UUID;

import com.sahaj.hotel.automation.entity.base.IAC;
import com.sahaj.hotel.automation.entity.base.ILight;
import com.sahaj.hotel.automation.entity.base.ISubCorridor;
import com.sahaj.hotel.automation.entity.constant.AC_STATE;
import com.sahaj.hotel.automation.entity.constant.LIGHT_STATE;

import lombok.NonNull;

public class SubCorridor implements ISubCorridor {

	@NonNull protected final String id;
	protected final int  number;
	protected final boolean isActive;
	@NonNull protected final ILight light;
	@NonNull protected final IAC ac;
	
	public SubCorridor(String id, Integer number, Boolean isActive, ILight light, IAC ac) {
		this.id = id == null? UUID.randomUUID().toString(): id;
		this.number = number;
		this.isActive = isActive == null? false: isActive;
		this.light = light == null? new Light(LIGHT_OFF): light;
		this.ac = ac == null? new AC(AC_ON): ac;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public LIGHT_STATE getLightState() {
		return light.getState();
	}
	
	@Override
	public AC_STATE getACState() {
		return ac.getState();
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public void turnOnTheLight() {
		light.switchOn();
	}

	@Override
	public void turnOffTheLight() {
		light.switchOff();
	}	
	
	@Override
	public void turnOnTheAC() {
		ac.switchOn();
	}

	@Override
	public void turnOffTheAC() {
		ac.switchOff();
	}	

	@Override
	public ISubCorridor setIsActive(boolean isActive) {
		return this.toBuilder()
			.isActive(isActive)
			.build();
	}
	
//	@Override
//	public void turnOffTheLightIfUnnecessary() {
//		// no motion for last 1 minute, turn off the lights
//		if(lastMotion.isPresent() && lastMotion.get().isBefore(LocalDateTime.now().minusMinutes(1l)))
//			this.turnOffTheLight();
//	}

	@Override
	public int hashCode() {
		return this.number;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == this) 
            return true; 
  
        if (!(obj instanceof SubCorridor)) 
            return false; 
          
        ISubCorridor corridor = (SubCorridor) obj; 
        return corridor.getNumber() == number; 
	}

	private SubCorridorBuilder toBuilder() {
		return new SubCorridorBuilder(id, number, isActive, light, ac);
	}

	public static  SubCorridorBuilder builder() {
 		return new SubCorridorBuilder();
 	}
 	
 	public static class SubCorridorBuilder {
 		
 		private String id;
 		private Integer number;
 		private Boolean isActive;
 		private ILight light;
 		private IAC ac;

		public SubCorridorBuilder(String id, Integer number, Boolean isActive, ILight light, IAC ac) {
			super();
			this.id = id;
			this.number = number;
			this.isActive = isActive;
			this.light = light;
			this.ac = ac;
		}

		private SubCorridorBuilder() {}
 		
 		public SubCorridorBuilder id(String id) {
 			this.id = id;
 			return this;
 		}
 		
 		public SubCorridorBuilder number(Integer number) {
 			this.number = number;
 			return this;
 		}
 		
 		public SubCorridorBuilder isActive(Boolean isActive) {
 			this.isActive = isActive;
 			return this;
 		}
 		
 		public SubCorridorBuilder light(ILight light) {
 			this.light = light;
 			return this;
 		}
 		
 		public SubCorridorBuilder ac(IAC ac) {
 			this.ac = ac;
 			return this;
 		}
 		
 		public SubCorridor build() {
 			return new SubCorridor(id, number, isActive, light, ac);
 		}
 	}

}
