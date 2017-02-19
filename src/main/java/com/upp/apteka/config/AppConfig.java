package com.upp.apteka.config;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.service.PharmacyService;

@Configuration
@ComponentScan(basePackages = "com.upp.apteka")
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbc.properties")
@PropertySource("classpath:pharmacy.properties")
@Import({ HibernateConfig.class })
public class AppConfig {

	@Autowired
	private Environment environment;

	@Autowired
	private Mapper mapper;

	@Autowired
	private PharmacyService pharmacyService;

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.databaseurl"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;

	}

	@Bean
	public Pharmacy pharmacyId() {
		return pharmacyService.getPharmacy(Long.valueOf(environment.getRequiredProperty("pharmacy.id")));
	}

	@Bean
	public JFrame getDispatcherFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		JFrame dispatcherFrame = new JFrame();

		dispatcherFrame.setTitle("Аптека");
		dispatcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispatcherFrame.setMinimumSize(new Dimension(650, 700));
		// dispatcherFrame.setResizable(false);
		dispatcherFrame.setLocationRelativeTo(null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		JMenuBar menuBar = new JMenuBar();

		JMenu addMenu = new JMenu("Додавання");

		JMenuItem addDoctor = new JMenuItem("Додати лікаря");
		addDoctor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addDoctor", new HashMap<String, Object>());

			}
		});

		JMenuItem addPatient = new JMenuItem("Додати пацієнта");
		addPatient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPatient", new HashMap<String, Object>());

			}
		});

		JMenuItem addPrescription = new JMenuItem("Додати рецепт");
		addPrescription.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPrescription", new HashMap<String, Object>());

			}
		});

		JMenuItem addPurchase = new JMenuItem("Додати покупку");
		addPurchase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("selectPrescription", new HashMap<String, Object>());

			}
		});

		JMenu viewMenu = new JMenu("Переглянути");

		JMenuItem allPatients = new JMenuItem("Переглянути пацієнтів");
		allPatients.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Map<String, Object> params = new HashMap<>();
				params.put("current", 1);

				mapper.changeActivity("allPatients", params);

			}
		});
		
		viewMenu.add(allPatients);

		addMenu.add(addDoctor);
		addMenu.add(addPatient);
		addMenu.add(addPrescription);
		addMenu.add(addPurchase);

		menuBar.add(addMenu);
		menuBar.add(viewMenu);

		dispatcherFrame.setJMenuBar(menuBar);

		return dispatcherFrame;
	}
}
