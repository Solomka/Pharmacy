package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.SelectPrescriptionActivity;

@Component("selectPrescription")
public class SelectPrescriptionController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	public void switchToActivity(Map<String, Object> params) {
		SelectPrescriptionActivity spa = appContext.getBean(SelectPrescriptionActivity.class);
		spa.showActivity();
	}
}
