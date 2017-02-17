package com.upp.apteka.activity;

import java.util.Map;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.service.PharmacyService;

@Component
@Scope("prototype")
public class PharmacyActivity implements Activity {

	// GridLayout
	// BorderLayout
	// GridBagLayout

	@Autowired
	private JFrame jFrame;

	@Autowired
	private PharmacyService pharmacyService;

	public void showActivity(Map<String, Object> params) {
		// TODO Auto-generated method stub

	}

}
