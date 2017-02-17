package com.upp.apteka;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.upp.apteka.config.AppConfig;

public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		JFrame dispatcherFrame = ctx.getBean(JFrame.class);
		dispatcherFrame.setVisible(true);

	}
}
