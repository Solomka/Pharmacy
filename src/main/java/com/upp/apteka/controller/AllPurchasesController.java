package com.upp.apteka.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.service.PurchaseService;

@Component("allPurchases")
public class AllPurchasesController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PurchaseService purchaseService;

	private static final int PURCHASES_PER_PAGE = 20;

	//@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity allPurchasesActivity = (Activity) appContext.getBean("allPurchasesActivity");

		String query = (String) params.get("query");
		if (query == null)
			query = "";

		int page = (Integer) params.get("current");

		Long purchaseId = (Long) params.get("id");

		Date startDate = null;
		Date endDate = null;

		try {
			startDate = new Date(((java.util.Date) params.get("startDate")).getTime());
			endDate = new Date(((java.util.Date) params.get("endDate")).getTime());
		} catch(Exception e) {

		}

		List<Purchase> purchases = purchaseService.findByQuery(query, startDate, endDate,
				PURCHASES_PER_PAGE * (page - 1), PURCHASES_PER_PAGE, false, purchaseId);

		params.clear();

		int maxNumber = purchaseService.count(query, startDate, endDate, false, purchaseId);

		if (maxNumber % PURCHASES_PER_PAGE == 0  && maxNumber != 0)
			maxNumber = maxNumber / PURCHASES_PER_PAGE;
		else
			maxNumber = maxNumber / PURCHASES_PER_PAGE + 1;

		if (page > maxNumber)
			page = maxNumber;

		params.put("last", maxNumber);
		params.put("current", page);
		params.put("purchases", purchases);
		params.put("query", query);
		params.put("id", purchaseId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		allPurchasesActivity.showActivity(params);

	}

}
