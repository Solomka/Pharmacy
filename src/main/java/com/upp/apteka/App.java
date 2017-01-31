package com.upp.apteka;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.upp.apteka.config.AppConfig;
import com.upp.apteka.controller.SwingController;

public class App {
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		JFrame dispatcherFrame = ctx.getBean(JFrame.class);
		dispatcherFrame.setVisible(true);

		SwingController helloController = ctx.getBean("hello", SwingController.class);
		helloController.switchToActivity(null);		
		
	}
}
