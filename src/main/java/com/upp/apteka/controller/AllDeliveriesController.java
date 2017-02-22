package com.upp.apteka.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Delivery;
import com.upp.apteka.service.DeliveryService;

@Component("allDeliveries")
public class AllDeliveriesController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private DeliveryService deliveryService;

	private static final int DELIVERIES_PER_PAGE = 20;

	public void switchToActivity(Map<String, Object> params) {
		Activity allDeliveriesActivity = (Activity) appContext.getBean("allDeliveriesActivity");

		String query = (String) params.get("query");
		if (query == null)
			query = "";
		
		System.out.println("Query: " + query);

		int page = (Integer) params.get("current");

		Long deliveryId = (Long) params.get("id");

		Date startDate = null;
		Date endDate = null;

		try {
			startDate = new Date(((java.util.Date) params.get("startDate")).getTime());
			endDate = new Date(((java.util.Date) params.get("endDate")).getTime());
		} catch (Exception e) {

		}

		List<Delivery> deliveries = deliveryService.findByQuery(query, startDate, endDate,
				DELIVERIES_PER_PAGE * (page - 1), DELIVERIES_PER_PAGE, false);

		params.clear();

		int maxNumber = deliveryService.count(query, startDate, endDate, false);

		if (maxNumber % DELIVERIES_PER_PAGE == 0 && maxNumber != 0)
			maxNumber = maxNumber / DELIVERIES_PER_PAGE;
		else
			maxNumber = maxNumber / DELIVERIES_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("deliveries", deliveries);
		params.put("query", query);
		params.put("id", deliveryId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		allDeliveriesActivity.showActivity(params);

	}

}
