package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.AddPrescriptionActivity;

@Component("addPrescription")
public class AddPrescriptionController implements SwingController{
	
	@Autowired
	private ApplicationContext appContext;
	
	public void switchToActivity(Map<String, Object> params){
		AddPrescriptionActivity activity = appContext.getBean(AddPrescriptionActivity.class);
		activity.showActivity();

	}
}
