package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import entities.VerlofAanvraag;
import entities.Werknemer;

public class Tests {
	
	/**
	 * test werknemer
	 */
	@Test
	public void test(){
		Werknemer werknemer = new Werknemer();
		werknemer.setNaam("Frans");
		assertTrue(werknemer.getNaam().equals("Frans"));
	}
	/**
	 * test periode midden in Weekends en feestdagen
	 * 
	 * ATT MAAND IS ZERO BASED
	 */
	@Test
	public void testGetPeriode1(){
		System.out.println("nr van zaterdag: "+Calendar.SATURDAY);
		System.out.println("nr van zondag: "+Calendar.SUNDAY);
		VerlofAanvraag verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 15));
		assertEquals(11, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 18));
		assertEquals(12, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 30));
		assertEquals(22, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 31));
		assertEquals(22, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 2, 31));
		assertEquals(64, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,3), new GregorianCalendar(2015, 0, 31));
		assertEquals(20, verlofaanvraag.getPeriode());
	}
	/**
	 * 
	 */
	@Test
	public void testGeldigVerlof(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
