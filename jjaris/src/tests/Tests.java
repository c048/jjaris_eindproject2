package tests;

import java.util.GregorianCalendar;

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
	 */
	@Test
	public void testGetPeriode1(){
		VerlofAanvraag verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,01,01), new GregorianCalendar(2015, 01, 15));
		assertTrue(verlofaanvraag.getPeriode() == 11);
	
	}
	/**
	 * 
	 */
	@Test
	public void testGeldigVerlof(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
