package tests;

import java.util.GregorianCalendar;

import org.junit.Test;

import static org.junit.Assert.*;
import entities.VerlofAanvraag;
import entities.Werknemer;

public class Tests {
	
	@Test
	public void test(){
		Werknemer werknemer = new Werknemer();
		werknemer.setNaam("Frans");
		assertTrue(werknemer.getNaam().equals("Frans"));
	}
	/**
	 * test periode midden in week
	 */
	@Test
	public void testGetPeriode1(){
		VerlofAanvraag verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(01, 01, 2015), new GregorianCalendar(15, month, dayOfMonth))
	}
	/**
	 * test periode midden in week
	 */
	@Test
	public void testGetPeriode2(){
		
	}
	/**
	 * test periode overlappend met weekend
	 */
	@Test
	public void testGetPeriode3(){
		
	}
	/**
	 * test periode overlappend met weekend en feestdag
	 */
	@Test
	public void testGetPeriode4(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
