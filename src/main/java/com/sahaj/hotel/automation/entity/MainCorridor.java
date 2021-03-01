package com.sahaj.hotel.automation.entity;

import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_ON;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_ON;

import java.util.UUID;

import com.sahaj.hotel.automation.entity.base.IAC;
import com.sahaj.hotel.automation.entity.base.ILight;
import com.sahaj.hotel.automation.entity.base.IMainCorridor;
import com.sahaj.hotel.automation.entity.constant.AC_STATE;
import com.sahaj.hotel.automation.entity.constant.LIGHT_STATE;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public class MainCorridor implements IMainCorridor {

	@NonNull protected final ILight light;
	@NonNull protected final IAC ac;
	@NonNull protected final String id;
	protected final int  number;
	
	@Builder
	public MainCorridor(String id, int number) {
		this.id = id == null? UUID.randomUUID().toString(): id;
		this.number = number;
		this.light = new Light(LIGHT_ON);
		this.ac = new AC(AC_ON);
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
}
