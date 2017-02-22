package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.service.DeliveryService;

@Component("viewDeliveryMedicines")
public class ViewDeliveryMedicinesController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private DeliveryService deliveryService;

	private static final int DELMEDS_PER_PAGE = 20;

	public void switchToActivity(Map<String, Object> params) {
		Activity allDeliveriesActivity = (Activity) appContext.getBean("viewDeliveryMedicinesActivity");

		Long deliveryId = (Long) params.get("id");

		List<DeliveryMedicine> medicines = deliveryService.getDeliveryMedicines(deliveryId, 0, DELMEDS_PER_PAGE);

		params.clear();

		params.put("medicines", medicines);
		params.put("id", deliveryId);

		allDeliveriesActivity.showActivity(params);

	}

}
