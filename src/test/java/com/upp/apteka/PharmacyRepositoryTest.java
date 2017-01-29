package com.upp.apteka;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.AppConfig;
import com.upp.apteka.utils.repository.HibernateSpecification;
import com.upp.apteka.utils.repository.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PharmacyRepositoryTest {

	private static final Logger LOGGER = Logger.getLogger(PharmacyRepositoryTest.class.getName());

	@Autowired
	private ApplicationContext applicationContext;

	private static Repository<Pharmacy, Long, HibernateSpecification> pharmacyRepository;

	private Pharmacy pharmacy;

	/**
	 * @BeforeClass: onceExecutedBeforeAll tests"
	 */

	@BeforeClass
	public static void prepare() {
		// pharmacyRepository = applicationContext.getBean("pharmacyRepository",
		// Repository.class);
		BasicConfigurator.configure(new ConsoleAppender(new SimpleLayout()));
	}

	/**
	 * @Before: executedBeforeEach test"
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		pharmacyRepository = applicationContext.getBean("pharmacyRepository", Repository.class);

		pharmacy = generatePharmacyInstance();
	}

	public static Pharmacy generatePharmacyInstance() {

		Pharmacy pharmacy = new Pharmacy("Green pharmacy", "Zelena, 12 str.", 30);
		return pharmacy;
	}

	@Test
	public void createPharmacy() {
		pharmacyRepository.create(pharmacy);
		assertNotNull(pharmacy.getId());
	}

}
