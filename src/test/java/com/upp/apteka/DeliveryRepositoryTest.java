package com.upp.apteka;


import java.sql.Date;
import java.text.ParseException;
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
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Delivery;
import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.bo.Medicine;
import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.repository.DeliveryRepository;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.repository.PharmacyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class DeliveryRepositoryTest {

	/*
	@Autowired
	@Qualifier("deliveryRepository")
	private IRepository<Delivery, Long, HqlSpecification> deliveryRepository;

	@Autowired
	@Qualifier("pharmacyRepository")
	private IRepository<Pharmacy, Long, HqlSpecification> pharmacyRepository;

	@Autowired
	@Qualifier("medicineRepository")
	private IRepository<Medicine, Long, HqlSpecification> medicineRepository;
	
	*/
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private MedicineRepository medicineRepository;

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
	/*
	@Test
	public void getAllDeliveries(){
		List<Delivery> deliveries = deliveryRepository.getAll();
		for(Delivery delivery: deliveries)
			System.out.println("delivery: " + delivery.toString() + "\n");
		Assert.assertNotNull(deliveries);
	}
	
	@Test	
	public void getDelivery(){
		delivery = deliveryRepository.read(new Long("9"));
		
		System.out.println("Delivery: " + delivery.toString());
		
		List<DeliveryMedicine> deliveryMedicines = delivery.getDeliveryMedicines();
		
		for(DeliveryMedicine med: deliveryMedicines)
			System.out.println("medecine: " + med.toString() + "\n");
		Assert.assertNotNull(delivery.getId());
	}
	*/
	/*
	@Test
	public void getDeliveryByPeriod() throws ParseException{
		/*
		Timestamp to = new Timestamp(System.currentTimeMillis());
		System.out.println("ToDate: " + to.toString());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date) dateFormat.parse("23/01/2017");
		long time = date.getTime();
		Timestamp from = new Timestamp(time);
		System.out.println("FromDate: " + from.toString());
		*/
		/*
		String strFrom="2017-01-12";  
	    Date from = Date.valueOf(strFrom);
	    System.out.println("FromDate: " + from.toString());
	    
	    String strTo="2017-01-31";  
	    Date to = Date.valueOf(strTo);
	    System.out.println("FromDate: " + to.toString());
		
		//List<Delivery> deliveries = deliveryRepository.searchByCriteria(DeliverySpecificationUtils.findDeliveryByPeriodAndPharmacy(from, to, new Long("8")));
		List<Delivery> deliveries = deliveryRepository.findPharmacyDeliveriesByPeriod(from, to, new Long("8"));
		for(Delivery delivery: deliveries)
			System.out.println("delivery: " + delivery.toString() + "\n");
		Assert.assertNotNull(deliveries);
	}
		
		@Test
		public void getPharmacyMedicineDeliveryByPeriod() throws ParseException{
					
			String strFrom="2017-01-12";  
		    Date from = Date.valueOf(strFrom);
		    System.out.println("FromDate: " + from.toString());
		    
		    String strTo="2017-01-31";  
		    Date to = Date.valueOf(strTo);
		    System.out.println("FromDate: " + to.toString());
			
			//List<Delivery> deliveries = deliveryRepository.searchByCriteria(DeliverySpecificationUtils.findDeliveryByPeriodAndPharmacy(from, to, new Long("8")));
			List<Delivery> deliveries = deliveryRepository.findPharmacyMedicineDeliveriesByPeriod(from, to, new Long("8"), new Long("3"), 0, 5);
			for(Delivery delivery: deliveries){
				System.out.println("delivery: " + delivery.toString() + "\n");
			List<DeliveryMedicine> pharmacyMedicines = delivery.getDeliveryMedicines();
			
			for(DeliveryMedicine med: pharmacyMedicines){
			System.out.println("Del med name: " + med.getMedicine().getName() + "\n");
			}
			}
			Assert.assertNotEquals(deliveries.size(), 0);		
		
	}
	*/	
		@Test
		public void checkIfDeliveryMedicineSold(){
			Assert.assertEquals(deliveryRepository.checkIfDeliveryMedicineSold(new Long("29")), false);
		}
	
	/*
	@Test
	public void deleteDelivery(){
		Assert.assertEquals(deliveryRepository.delete(new Long("10")), true);
	}
	*/

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
