package com.upp.apteka;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.service.MedicineService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class MedicineServiceTest {

	@Autowired
	private MedicineService medicineService;

	private Medicine medicine;

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

		medicine = null;
	}

	public static Medicine generateMedicineInstance() {

		Medicine medicine = new Medicine("Nervohel", "HP", new BigDecimal(300), 15);
		return medicine;
	}
/*
	@Test
	public void getAllMedicines() {

		List<Medicine> medicines = medicineService.getAllMedicines(0);

		for (Medicine medicine : medicines)
			System.out.println("Medicine:" + medicine + "\n");
		Assert.assertNotEquals(medicines.size(), 0);

	}

	@Test
	public void getMedicine() {

		medicine = medicineService.getMedicine(new Long("2"));
		System.out.println("Medicine: " + medicine);
		Assert.assertNotNull(medicine);
	}

	
	@Test
	public void addMedicine() {
		
		medicine = generateMedicineInstance();
		Assert.assertNotNull(medicineService.addMedicine(medicine));
	}

	@Test
	public void updateMedicine() {
		
		medicine = medicineService.getMedicine(new Long("6"));
		medicine.setProducer("Znahar");
		
		medicineService.updateMedicine(medicine);
		Assert.assertEquals(medicineService.getMedicine(new Long("6")).getProducer(), "Znahar");
	}

	@Test
	public void deleteMedicine() {		
		Assert.assertEquals(medicineService.deleteMedicine(new Long("6")), true);
	}

	
	@Test
	public void getPharmacyMedicines() {
		
		List<PharmacyMedicine> pMs = medicineService.getPharmacyMedicines(new Long("8"), 0);
		showPharmacyMedicines(pMs);
		Assert.assertNotEquals(pMs.size(), 0);
	}
*/
	
	@Test
	public void getPharmacyMedicine() {
		
		List<PharmacyMedicine> pMs = medicineService.getPharmacyMedicine(new Long("9"), "H", 0);
		showPharmacyMedicines(pMs);
		Assert.assertNotEquals(pMs.size(), 0);
	}

	
	@Test
	public void searchMedicineInPharmacies() {
		
		List<PharmacyMedicine> pMs = medicineService.searchMedicineInPharmacies("Hil", 0);
		showPharmacyMedicines(pMs);
		Assert.assertNotEquals(pMs.size(), 0);
	}

	public void showPharmacyMedicines(List<PharmacyMedicine> pharmacyMedicines) {
		for (PharmacyMedicine pharmacyMedicine : pharmacyMedicines) {
			System.out.println("PharmacyMedicine: " + pharmacyMedicine + ", medicineName = " + pharmacyMedicine.getMedicine().getName());
		}
	}

}
