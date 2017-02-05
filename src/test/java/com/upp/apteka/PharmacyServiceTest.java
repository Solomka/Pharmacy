package com.upp.apteka;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.service.PharmacyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class PharmacyServiceTest {

	private static final Logger LOGGER = Logger.getLogger(PharmacyServiceTest.class.getName());

	@Autowired
	private PharmacyService pharmacyService;

	private Pharmacy pharmacy;
	private List<Pharmacy> pharmacies;

	/**
	 * @BeforeClass: onceExecutedBeforeAll tests"
	 */

	@BeforeClass
	public static void prepare() {

		BasicConfigurator.configure(new ConsoleAppender(new SimpleLayout()));
	}

	/**
	 * @Before: executedBeforeEach test"
	 */
	@Before
	public void before() {

		pharmacy = null;
		pharmacies = new ArrayList<Pharmacy>();
	}

	public Pharmacy generatePharmacyInstance() {

		Pharmacy pharmacy = new Pharmacy("Social pharmacy", "I. Franka, 12 str.", 30);
		return pharmacy;
	}
/*
	@Test
	public void getAllPharmacies() {

		pharmacies = pharmacyService.getAllPharmacies(0);
		showPharmacies(pharmacies);
		Assert.assertNotEquals(pharmacies.size(), 0);

	}

	@Test
	public void getPharmacy() {

		pharmacy = pharmacyService.getPharmacy(new Long("9"));
		System.out.println("Pharmacy: " + pharmacy);
		Assert.assertNotNull(pharmacy);
	}
*/
	@Test
	public void findPharmacyByName() {
		pharmacies = pharmacyService.findPharmacyByName("Z");
		showPharmacies(pharmacies);
		Assert.assertNotEquals(pharmacies.size(), 0);
	}
	
/*
	@Test
	public void addPharmacy() {
		pharmacy = generatePharmacyInstance();
		Assert.assertNotNull(pharmacyService.addPharmacy(pharmacy));
	}
	
	@Test
	@Rollback
	public void updatePharmacy(){		
		pharmacy = pharmacyService.getPharmacy(new Long("9"));
		pharmacy.setName("Znahar");
		pharmacyService.updatePharmacy(pharmacy);
		
		Assert.assertEquals(pharmacy.getName(), "Znahar");		
	}	
	
	@Test
	public void deletePharmacy(){
		Assert.assertEquals(pharmacyService.deletePharmacy(new Long("10")), true);
	}
	*/
	private void showPharmacies(List<Pharmacy> pharmacies) {
		for (Pharmacy pharmacy : pharmacies)
			System.out.println("Pharmacy:" + pharmacy + "\n");
	}

}
