package utils;

import java.util.Date;



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
	public Date buildDatum (int dag,int maand,int jaar){
		return null;
		
	}
	
	private int controleerJaar (int jaar){
		return jaar;
		
	}
	private int controleerMaand (int maand){
		return 0;
	}
	private int controleerDag (int dag){
		return 0;
	}
}
