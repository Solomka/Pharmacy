package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;

@Component("addPharmacy")
public class AddPharmacyController implements SwingController {
	@Autowired
	private ApplicationContext appContext;

	public void switchToActivity(Map<String, Object> params) {
		Activity addPharmcyActivity = (Activity) appContext.getBean("addPharmacyActivity");
		addPharmcyActivity.showActivity(new HashMap<String, Object>());

	}

}
