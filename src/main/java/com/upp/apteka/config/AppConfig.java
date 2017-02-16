package com.upp.apteka.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sql.DataSource;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = "com.upp.apteka")
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbc.properties")
@Import({ HibernateConfig.class })
public class AppConfig {

	@Autowired
	private Environment environment;

	@Autowired
	private Mapper mapper;

	@Bean
	DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.databaseurl"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;

	}

	@Bean
	public JFrame getDispatcherFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		JFrame dispatcherFrame = new JFrame();

		dispatcherFrame.setTitle("Аптека");
		dispatcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispatcherFrame.setSize(600, 600);
		dispatcherFrame.setResizable(false);
		dispatcherFrame.setLocationRelativeTo(null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		JMenuBar menuBar = new JMenuBar();

		JMenu test = new JMenu("Тестове меню");

		JMenuItem addDoctor = new JMenuItem("Додати лікаря");
		addDoctor.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addDoctor", null);

			}
		});

		JMenuItem addPatient = new JMenuItem("Додати пацієнта");
		addPatient.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPatient", null);

			}
		});

		JMenuItem addPrescription = new JMenuItem("Додати рецепт");
		addPrescription.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPrescription", null);

			}
		});

		test.add(addDoctor);
		test.add(addPatient);
		test.add(addPrescription);

		menuBar.add(test);

		dispatcherFrame.setJMenuBar(menuBar);

		return dispatcherFrame;
	}
}
