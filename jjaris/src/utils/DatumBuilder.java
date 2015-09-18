package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;



/**
 * Apparte classe om datums van drie ints te controleren op 
 * geldigheid van de datum
 * en hen om te zetten naar een datum type Date (
 * @author user
 *
 */
public class DatumBuilder {
	/**
	 * 
	 * @param dag :integer max 2 cijfers min 1 cijfers
	 * @param maand :integer max 2 cijfers min 1 cijfers
	 * @param jaar integer max 4 cijfers min 4 cijfers
	 * @return Datum type Date
	 */
	private int dag;
	private int maand;
	private int jaar;
	
	public DatumBuilder(int dag, int maand, int jaar) {
		try {
			setDag(dag);
			setMaand(maand);
			setJaar(jaar);
		} catch (InputMismatchException e) {
			throw new InputMismatchException(e.getMessage());
		}
	}
	
	private int getDag() {
		return dag;
	}

	private void setDag(int dag) {
		this.dag = dag;
	}

	private int getMaand() {
		return maand;
	}

	private void setMaand(int maand) {
		this.maand = maand;
	}

	private int getJaar() {
		return jaar;
	}

	private void setJaar(int jaar) {
		this.jaar = jaar;
	}

	public Calendar buildCalendar (){
		return new GregorianCalendar(getJaar(), getMaand(), getDag());
	}
	
	public Date buildDate() {
		return buildCalendar().getTime();
	}
}
