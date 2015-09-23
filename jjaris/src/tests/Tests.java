package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;


import java.util.List;

import org.junit.Test;

import entities.JaarlijksVerlof;
import entities.Toestand;
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
		werknemer.setNaam("testGetperiode1");
		System.out.println("nr van zaterdag: "+Calendar.SATURDAY);
		System.out.println("nr van zondag: "+Calendar.SUNDAY);
		VerlofAanvraag verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,10,1), new GregorianCalendar(2015, 10, 15),werknemer);
		System.out.println(verlofaanvraag);
		System.out.println(verlofaanvraag.getPeriode());
		assertEquals(10, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,11,1), new GregorianCalendar(2015, 11, 18),werknemer);
		System.out.println(verlofaanvraag);
		System.out.println(verlofaanvraag.getPeriode());
		assertEquals(14, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2015,11,22), new GregorianCalendar(2016, 0, 29),werknemer);
		System.out.println(verlofaanvraag);
		System.out.println(verlofaanvraag.getPeriode());
		assertEquals(29, verlofaanvraag.getPeriode());
		assertEquals(8, verlofaanvraag.getPeriodeInJaarStartdatum());
		assertEquals(21, verlofaanvraag.getPeriodeInJaarEinddatum());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2016,2,1), new GregorianCalendar(2016, 3, 30),werknemer);
		System.out.println(verlofaanvraag);
		System.out.println(verlofaanvraag.getPeriode());
		assertEquals(44, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2016,4,1), new GregorianCalendar(2016, 5, 20),werknemer);
		System.out.println(verlofaanvraag);
		System.out.println(verlofaanvraag.getPeriode());
		assertEquals(36, verlofaanvraag.getPeriode());
		System.out.println("************************");
		verlofaanvraag = new VerlofAanvraag(new GregorianCalendar(2016,6,3), new GregorianCalendar(2016, 6, 31),werknemer);
		System.out.println(verlofaanvraag);
		System.out.println(verlofaanvraag.getPeriode());
		assertEquals(20, verlofaanvraag.getPeriode());
	}
	/**
	 * 
	 */
	@Test
	public void testGeldigVerlof1(){
		Werknemer w = new Werknemer();
		
		w.setNaam("Janssen");
		w.setVoornaam("jefke");
		JaarlijksVerlof jv = new JaarlijksVerlof();
		jv.setAantalDagen(30);
		jv.setJaar(2015);
		jv.setId(1);
		jv.setWerknemer(w);
		w.voegJaarlijksVerlofToe(jv);
		VerlofAanvraag va = new VerlofAanvraag(new GregorianCalendar(2015, 10, 1), new GregorianCalendar(2015, 10, 22),w);
		va.setId(1);
		assertEquals(15, va.getPeriode());
		assertEquals(15,w.getAantalBeschikBareVerlofDagen(2015));
		
		VerlofAanvraag va2 = new VerlofAanvraag(new GregorianCalendar(2015, 11, 1), new GregorianCalendar(2015, 11, 12), w);
		va2.setId(2);
		
		
		assertEquals(9, va2.getPeriode());
		assertEquals(6,w.getAantalBeschikBareVerlofDagen(2015));
		
		//VerlofAanvraag va3 = new VerlofAanvraag(new GregorianCalendar(2015, 11, 1), new GregorianCalendar(2015, 11, 12), w);
		//je krijgt exceptie omdat het een ongeldig verlof is
		//System.out.println(va3+" toestand: "+va3.getToestand());
		JaarlijksVerlof jv2016 = new JaarlijksVerlof();
		jv2016.setAantalDagen(30);
		jv2016.setJaar(2016);
		jv2016.setId(2);
		jv2016.setWerknemer(w);
		w.voegJaarlijksVerlofToe(jv2016);
		
		VerlofAanvraag va4 = new VerlofAanvraag(new GregorianCalendar(2015, 11, 12), new GregorianCalendar(2016, 1, 12),w);
		System.out.println("verlofaanvraag va4 periode 2015:"+va4.getPeriodeInJaarStartdatum());
		System.out.println("verlofaanvraag va4 periode 2016:"+va4.getPeriodeInJaarEinddatum());
		System.out.println("verlofaanvraag va4 : toestand " + (va4.getToestand()==Toestand.AFGEKEURD?"Afgekeurd":"FOUT"));
		
	}
	
	@Test
	public void testGeldigVerlof2(){
		Werknemer w = new Werknemer();
		w.setNaam("testGeldigVerlof2");
		VerlofAanvraag va = new VerlofAanvraag(new GregorianCalendar(2015, 9, 10), new GregorianCalendar(2015, 9, 22),w);
		assertTrue(va.verlofOverlapt(new GregorianCalendar(2015, 9, 15), new GregorianCalendar(2015, 10, 17)));
	
		
	}
	
	
	
	
	
	
	
	
	
}
