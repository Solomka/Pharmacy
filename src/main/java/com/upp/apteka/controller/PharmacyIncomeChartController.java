package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;

@Component("pharmacyIncomeChart")
public class PharmacyIncomeChartController implements SwingController{

	@Autowired
	ApplicationContext appContect;
	
	@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity pharmacyInconeChartActivity = (Activity)appContect.getBean("pharmacyIncomeChartActivity");
		pharmacyInconeChartActivity.showActivity(null);
		
	}

}
