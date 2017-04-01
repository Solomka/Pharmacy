package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.upp.apteka.activity.Activity;

@Controller("profitChart")
public class ProfitChartController implements SwingController {

	@Autowired
	ApplicationContext appContext;

	@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity profitChartActivity = (Activity) appContext.getBean("profitChartActivity");
		profitChartActivity.showActivity(null);

	}

}
