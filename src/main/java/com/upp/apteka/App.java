package com.upp.apteka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.upp.apteka.config.AppConfig;
import com.upp.apteka.controller.SwingController;

public class App {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		@SuppressWarnings("resource")
		// AnnotationConfigApplicationContext ctx = new
		// AnnotationConfigApplicationContext(AppConfig.class);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		JFrame dispatcherFrame = ctx.getBean(JFrame.class);
		dispatcherFrame.setVisible(true);

		SwingController controller = ctx.getBean("addPurchase", SwingController.class);
		
		Map<String, Object> params = new HashMap<>();
		
		//TODO JUST FOR TESTING PURPOSE
		params.put("prescriptionId", 8L);
		params.put("pharmacyId", 1L);
		
		controller.switchToActivity(params);

	}
}
