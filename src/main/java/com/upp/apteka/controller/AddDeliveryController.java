package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;

@Component("addDelivery")
public class AddDeliveryController implements SwingController {

	@Autowired
	ApplicationContext appContext;

	public void switchToActivity(Map<String, Object> params) {
		Activity addDeliveryActivity = (Activity) appContext.getBean("addDeliveryActivity");
		addDeliveryActivity.showActivity(new HashMap<String, Object>());
	}

}
