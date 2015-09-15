package tests;

import org.junit.Test;
import static org.junit.Assert.*;

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
	public void testGetPeriode2(){
		
	}
	/**
	 * test periode overlappend met weekend
	 */
	@Test
	public void testGetPeriode3(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
