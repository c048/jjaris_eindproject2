package tests;

import java.util.GregorianCalendar;

import org.eclipse.persistence.jpa.jpql.Assert.AssertException;
import org.junit.Test;

import static org.junit.Assert.*;
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
		VerlofAanvraag verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,01), new GregorianCalendar(2015, 0, 15));
		assertEquals(11, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,01), new GregorianCalendar(2015, 0, 31));
		assertEquals(22, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,0,01), new GregorianCalendar(2015, 2, 31));
		assertEquals(64, verlofaanvraag.getPeriode());
		
	}
	/**
	 * 
	 */
	@Test
	public void testGeldigVerlof(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
