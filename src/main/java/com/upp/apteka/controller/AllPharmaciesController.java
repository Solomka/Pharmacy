package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.service.PharmacyService;

@Component("allPharmacies")
public class AllPharmaciesController implements SwingController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private PharmacyService pharmacyService;

	private static final int PATIENTS_PER_PAGE = 20;

	public void switchToActivity(Map<String, Object> params) {
		// TODO Auto-generated method stub

	}

}
