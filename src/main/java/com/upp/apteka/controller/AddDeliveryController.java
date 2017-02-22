package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.service.DeliveryService;

@Component("addDelivery")
public class AddDeliveryController implements SwingController {

	@Autowired
	ApplicationContext appContext;

	@Autowired
	DeliveryService deliveryService;

	public void switchToActivity(Map<String, Object> params) {
		Activity addDeliveryActivity = (Activity) appContext.getBean("addDeliveryActivity");
		Long deliveryId = (Long) params.get("id");

		//for editing, but not in my case, aahaahha =)
		if (deliveryId != null) {
			Map<String, Object> newParams = new HashMap<String, Object>();
			newParams.put("delivery", deliveryService.getDelivery(deliveryId));
			addDeliveryActivity.showActivity(newParams);
		} else {
			addDeliveryActivity.showActivity(params);
		}
	}

}
