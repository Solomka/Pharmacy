package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.ByeActivity;

@Component("bye")
public class ByeController implements SwingController{
	
	@Autowired
	private ApplicationContext appContext;
	
	public void switchToActivity(Map<String, Object> params){
		ByeActivity byeActivity = appContext.getBean(ByeActivity.class, "BYE-BYE");
		byeActivity.showActivity();
	}
}
