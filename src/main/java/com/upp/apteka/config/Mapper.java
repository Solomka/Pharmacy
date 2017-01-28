package com.upp.apteka.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.controller.SwingController;

/**
 * Changing activities mapper
 * 
 */
@Component
public class Mapper {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * method that returns appropriate view representation controller bean and invokes its 'showing activity class method'
	 * 
	 * 1 controller per 1 showing activity
	 * 
	 * @param name
	 * @param params
	 */
	public void changeActivity(String name, Map<String, Object> params){
		SwingController swingController = applicationContext.getBean(name, SwingController.class);
		swingController.switchToActivity(params);
	}
}
