package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.AddDoctorActivity;

@Component("addDoctor")
public class AddDoctorController implements SwingController{
	
	@Autowired
	private ApplicationContext appContext;
	
	public void switchToActivity(Map<String, Object> params){
		AddDoctorActivity addDoctorActivity = appContext.getBean(AddDoctorActivity.class);
		addDoctorActivity.showActivity();

	}
}
