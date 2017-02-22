package com.upp.apteka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Delivery;
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

		int page = (Integer) params.get("current");
		System.out.println("Page: " + page);

		Long deliveryId = (Long) params.get("id");

		List<DeliveryMedicine> medicines = deliveryService.getDeliveryMedicines(deliveryId, (page - 1) * DELMEDS_PER_PAGE, DELMEDS_PER_PAGE);
		
		System.out.println("Controller meds: " + medicines.size());
		Delivery delivery = deliveryService.getDelivery(deliveryId);

		params.clear();

		int maxNumber = deliveryService.countDM(deliveryId);

		if (maxNumber % DELMEDS_PER_PAGE == 0 && maxNumber != 0)
			maxNumber = maxNumber / DELMEDS_PER_PAGE;
		else
			maxNumber = maxNumber / DELMEDS_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("delivery", delivery);
		params.put("medicines", medicines);
		params.put("id", deliveryId);

		allDeliveriesActivity.showActivity(params);

	}

}
