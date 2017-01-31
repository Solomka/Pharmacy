package com.upp.apteka;

import java.sql.Timestamp;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.repository.DeliveryRepository;
import com.upp.apteka.utils.repository.HqlSpecification;
import com.upp.apteka.utils.repository.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class DeliveryRepositoryTest {

	@Autowired
	@Qualifier("deliveryRepository")
	private Repository<Delivery, Long, HqlSpecification> deliveryRepository;

	@Autowired
	@Qualifier("pharmacyRepository")
	private Repository<Pharmacy, Long, HqlSpecification> pharmacyRepository;

	@Autowired
	@Qualifier("medicineRepository")
	private Repository<Medicine, Long, HqlSpecification> medicineRepository;

	private Delivery delivery;

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

		delivery = null;
	}
/*
	@Test
	public void createDelivary() {
		delivery = generateDeliveryInstance();
		deliveryRepository.create(delivery);
		System.out.println("Created delivery: " + delivery.toString() );
		
		Assert.assertNotNull(delivery.getId());
	}
*/
	@Test
	public void getAllDeliveries(){
		List<Delivery> deliveries = deliveryRepository.getAll();
		for(Delivery delivery: deliveries)
			System.out.println("delivery: " + delivery.toString() + "\n");
		Assert.assertNotNull(deliveries);
	}
	
	@Test
	@Transactional
	public void getDelivery(){
		delivery = deliveryRepository.read(new Long("9"));
		
		System.out.println("Delivery: " + delivery.toString());
		
		List<DeliveryMedicine> deliveryMedicines = delivery.getDeliveryMedicines();
		
		for(DeliveryMedicine med: deliveryMedicines)
			System.out.println("medecine: " + med.toString() + "\n");
		Assert.assertNotNull(delivery.getId());
	}
	/*
	@Test
	public void deleteDelivery(){
		Assert.assertEquals(deliveryRepository.delete(new Long("8")), true);
	}
*/	
	public Delivery generateDeliveryInstance() {

		Timestamp deliveryDate = new Timestamp(System.currentTimeMillis());
		Pharmacy pharmacy = pharmacyRepository.read(new Long("8"));

		Delivery delivery = new Delivery(deliveryDate, pharmacy);

		generateDeliveryMedicineList(delivery);

		return delivery;
	}

	private void generateDeliveryMedicineList(Delivery delivery) {

		DeliveryMedicine delM1 = new DeliveryMedicine(1);
		Medicine medicine = medicineRepository.read(new Long("2"));

		delM1.setMedicine(medicine);
		delivery.addToDeliveryMedicine(delM1);

		DeliveryMedicine delM2 = new DeliveryMedicine(2);
		Medicine medicine2 = medicineRepository.read(new Long("3"));

		delM2.setMedicine(medicine2);
		delivery.addToDeliveryMedicine(delM2);

	}

}
