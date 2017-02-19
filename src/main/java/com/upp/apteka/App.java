package com.upp.apteka;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.upp.apteka.config.AppConfig;

public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		final ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		JFrame dispatcherFrame = ctx.getBean(JFrame.class);
		dispatcherFrame.addWindowListener(new WindowListener() {

			//@Override
			public void windowOpened(WindowEvent e) {
			}

			//@Override
			public void windowIconified(WindowEvent e) {
			}

			//@Override
			public void windowDeiconified(WindowEvent e) {
			}

			//@Override
			public void windowDeactivated(WindowEvent e) {
			}

			//@Override
			public void windowClosing(WindowEvent e) {
			}

			//@Override
			public void windowClosed(WindowEvent e) {
				((AnnotationConfigApplicationContext) ctx).close();
			}

			//@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		dispatcherFrame.setVisible(true);

	}
}
