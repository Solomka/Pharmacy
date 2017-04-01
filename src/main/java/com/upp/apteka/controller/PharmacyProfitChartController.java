package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;

@Component("pharmacyProfitChart")
public class PharmacyProfitChartController implements SwingController {

	@Autowired
	ApplicationContext appContext;

	@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity pharmacyProfitChartActivity = (Activity) appContext.getBean("pharmacyProfitChartActivity");
		pharmacyProfitChartActivity.showActivity(null);

	}

}
