package com.upp.apteka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.activity.Activity;
import com.upp.apteka.service.DoctorService;

@Component("addDoctor")
public class AddDoctorController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private DoctorService doctorService;

	public void switchToActivity(Map<String, Object> params) {
		Activity helloActivity = (Activity) appContext.getBean("addDoctorActivity");

		Long doctorId = (Long) params.get("id");

		if (doctorId != null) {
			Map<String, Object> newParams = new HashMap<String, Object>();
			newParams.put("doctor", doctorService.read(doctorId));

			helloActivity.showActivity(newParams);
		} else
			helloActivity.showActivity(new HashMap<String, Object>());

	}
}
