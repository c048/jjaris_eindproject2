package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



/**
 * Apparte classe om datums van drie ints te controleren op 
 * geldigheid van de datum
 * en hen om te zetten naar een datum type Date (
 * @author user
 *
 */
public class DatumBuilder {
	private int dag;
	private int maand;
	private int jaar;
	
	/**
	 * 
	 * @param dag = dag van de maand
	 * @param maand = maand van het jaar
	 * @param jaar = jaartal
	 * @throws IllegalArgumentException
	 */
	public DatumBuilder(int dag, int maand, int jaar) throws IllegalArgumentException {
		try {
			setJaar(jaar);
			setMaand(maand);
			setDag(dag);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	private int getDag() {
		return dag;
	}

	private void setDag(int dag) throws IllegalArgumentException {
		Calendar tmpTime = new GregorianCalendar(getJaar(), getMaand()-1, 1);
		if(dag > tmpTime.getActualMaximum(Calendar.DAY_OF_MONTH) || dag < 1) {
			throw new IllegalArgumentException(String.format("Dag moet tussen 1 en %s liggen!", tmpTime.getActualMaximum(Calendar.MONTH)));
		}
		this.dag = dag;
	}

	private int getMaand() {
		return maand;
	}

	private void setMaand(int maand) throws IllegalArgumentException {
		if(maand > 12 || maand < 1) {
			throw new IllegalArgumentException("Maand moet tussen 1 en 12 liggen!");
		}
		
		this.maand = maand;
	}

	private int getJaar() {
		return jaar;
	}

	private void setJaar(int jaar) {
		this.jaar = jaar;
	}

	public Calendar buildCalendar (){
		return new GregorianCalendar(getJaar(), getMaand()-1, getDag());
	}
	
	public Date buildDate() {
		return buildCalendar().getTime();
	}
	
	@Override
	public String toString() {
		return String.format("%02d-%02d-%04d", getDag(), getMaand(), getJaar());
	}
}
