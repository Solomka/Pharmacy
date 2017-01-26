package com.upp.apteka.config;

import javax.swing.JFrame;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.upp")
@EnableAspectJAutoProxy
public class AppConfig {

	@Bean
	public JFrame getDispatcherFrame(){
		JFrame dispatcherFrame = new JFrame();
		
		dispatcherFrame.setTitle("Аптека");
		dispatcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispatcherFrame.setSize(500, 300);
		dispatcherFrame.setLocationRelativeTo(null);
		
		return dispatcherFrame;
	}
}
