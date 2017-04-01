package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;

@Component("lossChart")
public class LossChartController implements SwingController{

	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public void switchToActivity(Map<String, Object> params) {
		Activity lossChartActivity = (Activity) appContext.getBean("lossChartActivity");
		lossChartActivity.showActivity(null);
		
	}

}
