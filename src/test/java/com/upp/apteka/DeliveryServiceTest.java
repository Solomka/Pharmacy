package com.upp.apteka;

import java.sql.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.service.DeliveryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class DeliveryServiceTest {
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private MedicineRepository medicineRepository;
	
	private Delivery delivery;
	
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
	
	public Delivery generateDeliveryInstance() {

		//Timestamp deliveryDate = new Timestamp(System.currentTimeMillis());
		String str="2017-01-30";  
	    Date deliveryDate = Date.valueOf(str);
		Pharmacy pharmacy = pharmacyRepository.read(new Long("8"));

		Delivery delivery = new Delivery(deliveryDate, pharmacy);

		generateDeliveryMedicineList(delivery);

		return delivery;
	}

	private void generateDeliveryMedicineList(Delivery delivery) {

		DeliveryMedicine delM1 = new DeliveryMedicine(1);
		Medicine medicine = medicineRepository.read(new Long("4"));

		delM1.setMedicine(medicine);
		delivery.addToDeliveryMedicine(delM1);

		DeliveryMedicine delM2 = new DeliveryMedicine(2);
		Medicine medicine2 = medicineRepository.read(new Long("5"));

		delM2.setMedicine(medicine2);
		delivery.addToDeliveryMedicine(delM2);

	}

}
