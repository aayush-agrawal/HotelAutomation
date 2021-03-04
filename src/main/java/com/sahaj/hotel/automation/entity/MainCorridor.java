package com.sahaj.hotel.automation.entity;

import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_ON;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_ON;

import java.util.UUID;

import com.sahaj.hotel.automation.entity.base.IAC;
import com.sahaj.hotel.automation.entity.base.ILight;
import com.sahaj.hotel.automation.entity.base.IMainCorridor;
import com.sahaj.hotel.automation.entity.constant.AC_STATE;
import com.sahaj.hotel.automation.entity.constant.LIGHT_STATE;

public class MainCorridor implements IMainCorridor {

	protected final String id;
	protected final int  number;
	protected final IAC ac;
	protected final ILight light;
	
	public MainCorridor(String id, Integer number, IAC ac, ILight light) {
		this.id = id == null? UUID.randomUUID().toString(): id;
		this.number = number;
		this.light = light == null? new Light(LIGHT_ON): light;
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
	public int hashCode() {
		return this.number;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == this) 
            return true; 
  
        if (!(obj instanceof MainCorridor)) 
            return false; 
          
        IMainCorridor corridor = (MainCorridor) obj; 
        return corridor.getNumber() == number; 
	}
	
	private MainCorridorBuilder toBuilder() {
		return new MainCorridorBuilder(id, number, light, ac);
	}

	public static  MainCorridorBuilder builder() {
 		return new MainCorridorBuilder();
 	}
 	
 	public static class MainCorridorBuilder {
 		
 		String id;
 		Integer  number;
 		IAC ac;
 		ILight light;

		public MainCorridorBuilder(String id, Integer number, ILight light, IAC ac) {
			super();
			this.id = id;
			this.number = number;
			this.light = light;
			this.ac = ac;
		}

		private MainCorridorBuilder() {}
 		
 		public MainCorridorBuilder id(String id) {
 			this.id = id;
 			return this;
 		}
 		
 		public MainCorridorBuilder number(Integer number) {
 			this.number = number;
 			return this;
 		}
 		 		
 		public MainCorridorBuilder light(ILight light) {
 			this.light = light;
 			return this;
 		}
 		
 		public MainCorridorBuilder ac(IAC ac) {
 			this.ac = ac;
 			return this;
 		}
 		
 		public MainCorridor build() {
 			return new MainCorridor(id, number, ac, light);
 		}
 	}
}
