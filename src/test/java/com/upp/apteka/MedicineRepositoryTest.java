package com.upp.apteka;

import  org.junit.Assert;

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
import com.upp.apteka.repository.MedicineRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class MedicineRepositoryTest {

	/*
	@Autowired
	@Qualifier("medicineRepository")
	private IRepository<Medicine, Long, HqlSpecification> medicineRepository;
	*/
	
	@Autowired
	private MedicineRepository medicineRepository;
	
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
	
	/*
	@Test
	public void createMedicine() {
		medicine = generateMedicineInstance();
		medicineRepository.create(medicine);
		assertNotNull(medicine.getId());
	}
	
	
	
	@Test
	public void getMedicine(){
		//System.out.println("Medicine before: " + medicine);
		medicine = medicineRepository.read(new Long("2"));
		//System.out.println("Medicine after: " + medicine);
		assertNotNull(medicine);
	}
	
	@Test 
	public void getAllMedicines(){
		List<Medicine> medicines = medicineRepository.getAll(0, 5);
		
		for(Medicine medicine: medicines)
			System.out.println("Medicine:" + medicine + "\n" );
		assertNotNull(medicines);
	}
	
	
	@Test 
	public void getPharmacyMedicines(){
		List<PharmacyMedicine> medicines = medicineRepository.getPharmacyMedicines(new Long("8"), 0, 5);
		
		for(PharmacyMedicine medicine: medicines)
			System.out.println("Medicine: name: " + medicine.getMedicine().getName() + ", packPrice= " + medicine.getPackPrice() + ", packQuantity= " + medicine.getPackQuantity()+ "\n" );
		Assert.assertNotNull(medicines);
	}
	
	@Test 
	public void getConcretePharmacyMedicine(){
		PharmacyMedicine medicine = medicineRepository.getPharmacyMedicine(new Long("8"), new Long("4"));		
		System.out.println("Medicine: name: " + medicine.getMedicine().getName() + ", packPrice= " + medicine.getPackPrice() + ", packQuantity= " + medicine.getPackQuantity()+ "\n" );
		Assert.assertNotNull(medicine);
	}
	*/
	@Test 
	public void searchMedicineInPharmacies(){
		List<PharmacyMedicine> medicines = medicineRepository.searchMedicineInPharmacies(new Long("4"), 0, 5);
		
		for(PharmacyMedicine medicine: medicines)
			System.out.println("Medicine: pharmacyName: " + medicine.getPharmacy().getName()+ ", pharmacyAddress: " + medicine.getPharmacy().getAddress()+ ", medicineName: "+ medicine.getMedicine().getName() + ", packPrice= " + medicine.getPackPrice() + ", packQuantity= " + medicine.getPackQuantity()+ "\n" );
		Assert.assertNotNull(medicines);
	}
	
	/*
	@Test
	public void updateMedicine(){	
				
		Long eight = new Long("2");
		medicine = medicineRepository.read(eight);
		medicine.setName("Travomel");
		medicineRepository.update(medicine);
		
		medicine = medicineRepository.read(eight);
		
		assertEquals(medicine.getName(), "Travomel");
		
	}	
	*/
	
	/*
	@Test
	public void delete(){
		assertEquals(medicineRepository.delete(new Long("1")), true);		
	}
*/

}
