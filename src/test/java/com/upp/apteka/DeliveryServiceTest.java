package com.upp.apteka;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.junit.Assert;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.repository.PharmacyRepository;
import com.upp.apteka.service.DeliveryService;
import com.upp.apteka.service.PharmacyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class DeliveryServiceTest {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private MedicineRepository medicineRepository;
	
	private Delivery delivery;
	private List<Delivery> deliveries;
	
	
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
		deliveries = new ArrayList<Delivery>();
	}
	/*
	@Test
	public void addDelivery(){
		delivery = generateDeliveryInstance();
		deliveryService.addDelivery(delivery);
		Assert.assertNotNull(delivery.getId());
		
	}	
	
	@Test
	public void getAllDeliveries(){
		deliveries = deliveryService.getAllDeliveries(0);
		showDeliveries(deliveries);
		Assert.assertNotEquals(deliveries.size(), 0);
	}
	
	
	@Test
	public void getAllPharmacyDeliveries(){
		deliveries = deliveryService.getAllPharmacyDeliveries(new Long("9"), 0);
		showDeliveries(deliveries);
		Assert.assertNotEquals(deliveries.size(), 0);
	}
	
	
	@Test
	public void getDelivery(){
	    delivery = deliveryService.getDelivery(new Long("28"));	    
	    System.out.println("Delivery: id: " + delivery.getId() + ", date: "+ delivery.getDate());				
		Assert.assertNotNull(delivery);
	}
	
	
	@Test
	public void getPharmacyDeliveriesByPeriod(){
		
		String strFrom="2017-01-31";  
	    Date from = Date.valueOf(strFrom);
	    System.out.println("FromDate: " + from.toString());
	    
	    String strTo="2017-02-05";  
	    Date to = Date.valueOf(strTo);
	    System.out.println("ToDate: " + to.toString());
		
		deliveries = deliveryService.getPharmacyDeliveriesByPeriod(from, to, new Long("8"), 0);
		
		showDeliveries(deliveries);
		Assert.assertNotEquals(deliveries.size(), 0);
		
	}
	
	
	@Test
	public void getPharmacyMedicineDeliveriesByPeriod(){
		String strFrom="2017-01-31";  
	    Date from = Date.valueOf(strFrom);
	    System.out.println("FromDate: " + from.toString());
	    
	    String strTo="2017-02-05";  
	    Date to = Date.valueOf(strTo);
	    System.out.println("FromDate: " + to.toString());
		
		deliveries = deliveryService.getPharmacyMedicineDeliveriesByPeriod(from, to, new Long("8"), new Long("3"), 0);
		showDeliveries(deliveries);
		Assert.assertNotEquals(deliveries.size(), 0);		
	}
*/
	
	@Test
	public void deleteDelivery(){
		Assert.assertEquals(deliveryService.deleteDelivery(new Long("29")), true);
	}
	
	public Delivery generateDeliveryInstance() {

		//Timestamp deliveryDate = new Timestamp(System.currentTimeMillis());
		String str="2017-02-03";  
	    Date deliveryDate = Date.valueOf(str);
		Pharmacy pharmacy = pharmacyService.getPharmacy(new Long("9"));

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
	
	private void showDeliveries(List<Delivery> deliveries){
		for(Delivery delivery: deliveries)
			System.out.println("Delivery: " + delivery + ", pharmacy:" + delivery.getPharmacy() + "\n");
		
	}

}
