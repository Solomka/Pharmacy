package com.upp.apteka;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
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
	private MedicineService medicineRepository;

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

		Medicine medicine = new Medicine("Hilac", "Znahar", new BigDecimal(200), 10);
		return medicine;
	}

	@Test
	public void getAllMedicines() {

	}

	@Test
	public void getMedicine() {

	}

	@Test
	public void addMedicine() {

	}

	@Test
	public void updateMedicine() {

	}

	@Test
	public void deleteMedicine() {

	}

	@Test
	public void getPharmacyMedicines() {

	}

	@Test
	public void getPharmacyMedicine() {

	}

	@Test
	public void searchMedicineInPharmacies() {

	}

	public void showPharmacyMedicines(List<PharmacyMedicine> pharmacyMedicines) {
		for (PharmacyMedicine pharmacyMedicine : pharmacyMedicines) {
			System.out.println("PharmacyMedicine: " + pharmacyMedicine);
		}
	}

}
