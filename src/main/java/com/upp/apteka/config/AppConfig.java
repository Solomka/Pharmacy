package com.upp.apteka.config;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
		dispatcherFrame.setMinimumSize(new Dimension(800, 700));
		// dispatcherFrame.setResizable(false);
		dispatcherFrame.setLocationRelativeTo(null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		// add main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		dispatcherFrame.setContentPane(mainPanel);
		dispatcherFrame.setLayout(new BorderLayout());

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel infoLabel = new JLabel("Вітаємо у AIC аптеки \n " + pharmacyId().getName() + " (" + pharmacyId().getAddress() + ")");
		infoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		infoLabel.setHorizontalAlignment(JLabel.CENTER);

		infoPanel.add(infoLabel);
		mainPanel.add(infoPanel);

		// add main manu bar
		JMenuBar menuBar = new JMenuBar();

		// JManu for addition

		JMenu addMenu = new JMenu("Додавання");

		JMenuItem addDoctor = new JMenuItem("Додати лікаря");
		addDoctor.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addDoctor", new HashMap<String, Object>());

			}
		});

		JMenuItem addPatient = new JMenuItem("Додати пацієнта");
		addPatient.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPatient", new HashMap<String, Object>());

			}
		});

		JMenuItem addPrescription = new JMenuItem("Додати рецепт");
		addPrescription.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPrescription", new HashMap<String, Object>());

			}
		});

		JMenuItem addPurchase = new JMenuItem("Додати покупку");
		addPurchase.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("selectPrescription", new HashMap<String, Object>());

			}
		});

		JMenuItem addPharmacy = new JMenuItem("Додати аптеку");
		addPharmacy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPharmacy", new HashMap<String, Object>());

			}
		});

		JMenuItem addMedicine = new JMenuItem("Додати ліки");
		addMedicine.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addMedicine", new HashMap<String, Object>());

			}
		});

		/*
		 * JMenuItem addDelivery = new JMenuItem("Додати поставку");
		 * addPharmacy.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent e) {
		 * mapper.changeActivity("addDelivery", new HashMap<String, Object>());
		 * 
		 * } });
		 */

		// JManu for viewing

		JMenu viewMenu = new JMenu("Переглянути");

		JMenuItem allPatients = new JMenuItem("Переглянути пацієнтів");
		allPatients.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allPatients", params);

			}
		});

		JMenuItem allDoctors = new JMenuItem("Переглянути лікарів");
		allDoctors.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allDoctors", params);

			}
		});

		JMenuItem allPurchases = new JMenuItem("Переглянути покупки");
		allPurchases.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allPurchases", params);

			}
		});

		JMenuItem allPrescriptions = new JMenuItem("Переглянути рецепти");
		allPrescriptions.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent arg0) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allPrescriptions", params);

			}
		});

		JMenuItem allPharmacies = new JMenuItem("Переглянути аптеки");
		allPharmacies.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allPharmacies", params);

			}
		});

		JMenuItem allMedicines = new JMenuItem("Переглянути ліки");
		allMedicines.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allMedicines", params);

			}
		});

		JMenuItem allPharmacyMedicines = new JMenuItem("Переглянути ліки аптеки");
		allPharmacyMedicines.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("current", 1);

				mapper.changeActivity("allPharmacyMedicines", params);

			}
		});

		// JMenu for viewing
		viewMenu.add(allPharmacies);
		viewMenu.add(allMedicines);
		viewMenu.add(allPharmacyMedicines);
		viewMenu.add(allDoctors);
		viewMenu.add(allPatients);
		viewMenu.add(allPrescriptions);
		viewMenu.add(allPurchases);

		// JMenu for addition
		addMenu.add(addPharmacy);
		addMenu.add(addMedicine);
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
