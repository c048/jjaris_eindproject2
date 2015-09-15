package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CollectiefVerlof extends CollectieveSluiting implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private GregorianCalendar einddatum;
	
	
	
	public CollectiefVerlof() {
		super();
	}

	public CollectiefVerlof(GregorianCalendar startdatum, GregorianCalendar einddatum, String omschrijving,
			boolean terugkerend) {
		super(startdatum, omschrijving, terugkerend);
		this.einddatum=einddatum;

	}

	public GregorianCalendar getEinddatum() {
		return einddatum;
	}

	public void setEinddatum(GregorianCalendar einddatum) {
		this.einddatum = einddatum;
	}
	
	
	/**
	 * Geeft terug hoewel weekdagen (weekend dagen zijn al exclusieve)  zit er in een Collectieve Sluiting Periode
	 * @return int: aantal dagen
	 */
	public int getAantalDagen() {
		int aantalDagen=0;
		int aantalWeekendDagen=0;
		Calendar startdatum=super.getStartdatum();
		while(startdatum.after(einddatum)) {
		    if(startdatum.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || startdatum.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
		    	aantalWeekendDagen++;
		    aantalDagen++;
		    startdatum.add(Calendar.DATE,1);
		}
		
		return aantalDagen-aantalWeekendDagen;
	}

	

	@Override
	public String toString() {
		return 	"CollectiefVerlof [getId()=" + getId() + ", getStartdatum()="
				+ getStartdatum() + ", getOmschrijving()=" + getOmschrijving()
				+ ", isTerugkerend()=" + isTerugkerend() +  "]";
	}
	
	
	
	
}