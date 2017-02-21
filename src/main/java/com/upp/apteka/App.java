package com.upp.apteka;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.upp.apteka.config.AppConfig;

public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		final ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		JFrame dispatcherFrame = ctx.getBean(JFrame.class);
		dispatcherFrame.addWindowListener(new WindowListener() {

			// @Override
			public void windowOpened(WindowEvent e) {
			}

			// @Override
			public void windowIconified(WindowEvent e) {
			}

			// @Override
			public void windowDeiconified(WindowEvent e) {
			}

			// @Override
			public void windowDeactivated(WindowEvent e) {
			}

			// @Override
			public void windowClosing(WindowEvent e) {
			}

			// @Override
			public void windowClosed(WindowEvent e) {
				((AnnotationConfigApplicationContext) ctx).close();
			}

			// @Override
			public void windowActivated(WindowEvent e) {
			}
		});
		dispatcherFrame.setVisible(true);

		// Create a DecimalFormat that fits your requirements
/*
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// parse the string
		try {
			BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse("10,692,467,440,017.120");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
*/
	}
}
