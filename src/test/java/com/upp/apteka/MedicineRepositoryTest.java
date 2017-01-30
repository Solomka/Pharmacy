package com.upp.apteka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.utils.repository.HibernateSpecification;
import com.upp.apteka.utils.repository.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class MedicineRepositoryTest {

	@Autowired
	@Qualifier("medicineRepository")
	private Repository<Medicine, Long, HibernateSpecification> medicineRepository;
	
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

		Medicine medicine = new Medicine("Vocara", "Znahar", new BigDecimal(100), 25);
		return medicine;
	}
	
/*	
	@Test
	public void createMedicine() {
		medicine = generateMedicineInstance();
		medicineRepository.create(medicine);
		assertNotNull(medicine.getId());
	}
	*/
	
	
	@Test
	public void getMedicine(){
		//System.out.println("Medicine before: " + medicine);
		medicine = medicineRepository.read(new Long("2"));
		//System.out.println("Medicine after: " + medicine);
		assertNotNull(medicine);
	}
	
	@Test 
	public void getAllMedicines(){
		List<Medicine> medicines = medicineRepository.getAll();
		
		for(Medicine medicine: medicines)
			System.out.println("Medicine:" + medicine + "\n" );
		assertNotNull(medicines);
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
