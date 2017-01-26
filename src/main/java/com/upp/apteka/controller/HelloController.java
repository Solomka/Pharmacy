package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.HelloActivity;

@Component("hello")
public class HelloController implements SwingController{
	
	@Autowired
	private ApplicationContext appContext;
	
	public void switchToActivity(Map<String, Object> params){
		HelloActivity helloActivity = appContext.getBean(HelloActivity.class, "HELLO-HELLO");
		helloActivity.showActivity();

	}
}
