package com.upp.apteka.activity;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.service.PharmacyService;

@Component
@Scope("prototype")
public class PharmacyActivity {


	@Autowired
	private JFrame jFrame;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	//GridLayout
	//BorderLayout
	//GridBagLayout
	
	
	
}
