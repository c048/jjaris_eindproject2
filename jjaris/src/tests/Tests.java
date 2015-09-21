package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
		Werknemer werknemer = new Werknemer();
		System.out.println("nr van zaterdag: "+Calendar.SATURDAY);
		System.out.println("nr van zondag: "+Calendar.SUNDAY);
		VerlofAanvraag verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 15),werknemer);
		assertEquals(11, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 18),werknemer);
		assertEquals(12, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 30),werknemer);
		assertEquals(22, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 0, 31),werknemer);
		assertEquals(22, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,1), new GregorianCalendar(2015, 2, 31),werknemer);
		assertEquals(64, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,3), new GregorianCalendar(2015, 0, 31),werknemer);
		assertEquals(20, verlofaanvraag.getPeriode());
	}
	/**
	 * 
	 */
	@Test
	public void testGeldigVerlof1(){
		Werknemer w = new Werknemer();
		VerlofAanvraag va = new VerlofAanvraag(new GregorianCalendar(2015, 9, 1), new GregorianCalendar(2015, 9, 22),w);
		assertTrue(va.geldigVerlof(new GregorianCalendar(2015, 11, 1), new GregorianCalendar(2015, 11, 12)));
		
	}
	
	@Test
	public void testGeldigVerlof2(){
		Werknemer w = new Werknemer();
		VerlofAanvraag va = new VerlofAanvraag(new GregorianCalendar(2015, 9, 1), new GregorianCalendar(2015, 9, 22),w);
		assertFalse(va.geldigVerlof(new GregorianCalendar(2015, 9, 10), new GregorianCalendar(2015, 10, 12)));
		
	}
	
	
	
	
	
	
	
	
	
}
