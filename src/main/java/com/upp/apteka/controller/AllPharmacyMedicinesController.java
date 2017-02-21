package com.upp.apteka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.upp.apteka.service.MedicineService;

@Component("allPharmacyMedicines")
public class AllPharmacyMedicinesController implements SwingController {
	
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private MedicineService medicineService;

	public void switchToActivity(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

}
