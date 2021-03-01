package com.sahaj.hotel.automation.service.base;

import com.sahaj.hotel.automation.model.base.IModel;

public interface IService<P extends IModel, R extends IModel>{

	R execute(P p);
	
}
