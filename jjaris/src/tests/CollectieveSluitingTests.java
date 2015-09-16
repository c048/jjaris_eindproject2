package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entities.CollectiefVerlof;
import entities.Feestdag;

public class CollectieveSluitingTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	@Test
	public void IsWeekDagFalse() {
		
		Calendar  hemelvaarts = new GregorianCalendar(2015,Calendar.SEPTEMBER,27);	//zondag
		Feestdag feest=new Feestdag(hemelvaarts, "hemelvaarts", true);
		
		org.junit.Assert.assertFalse("failure - should be false", feest.isWeekdag());
	}
	
	@Test
	public void IsWeekDagTrue() {
		
		Calendar  hemelvaarts = new GregorianCalendar(2015,Calendar.SEPTEMBER,25);	 //vrijdag
		Feestdag feest=new Feestdag(hemelvaarts, "hemelvaarts", true);
		
		org.junit.Assert.assertTrue("failure - should be false", feest.isWeekdag());
	}
	
	@Test
	public void aantalDagenZdrWeekendsinCollectieveSluiting() {
		
		//17 dagen zndr weekends 
		Calendar  startDatum = new GregorianCalendar(2015,Calendar.SEPTEMBER,3);	 
		Calendar  eindDatum = new GregorianCalendar(2015,Calendar.SEPTEMBER,26);	
		
		CollectiefVerlof verlof=new CollectiefVerlof(startDatum, eindDatum,"Bouwverlof",true);
		
		assertEquals(17, verlof.getAantalDagen());
									
		org.junit.Assert.assertTrue("failure - should be false", true);
	}
	
}
