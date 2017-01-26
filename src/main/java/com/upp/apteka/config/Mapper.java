package com.upp.apteka.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.controller.SwingController;

@Component
public class Mapper {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public void changeActivity(String name, Map<String, Object> params){
		SwingController swingController = applicationContext.getBean(name, SwingController.class);
		swingController.switchToActivity(params);
	}
}
