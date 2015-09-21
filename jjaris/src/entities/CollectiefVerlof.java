package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="COLLECTIEVEVERLOVEN")
public class CollectiefVerlof extends CollectieveSluiting implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Calendar einddatum;
	
	
	
	public CollectiefVerlof() {
		super();
	}

	public CollectiefVerlof(Calendar startdatum, Calendar einddatum, String omschrijving,
			boolean terugkerend) {
		super(startdatum, omschrijving, terugkerend);
		this.einddatum=einddatum;

	}

	public Calendar getEinddatum() {
		return einddatum;
	}

	public void setEinddatum(Calendar einddatum) {
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
		while(!startdatum.after(einddatum)) {
		    if(startdatum.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || startdatum.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
		    	aantalWeekendDagen++;
		    aantalDagen++;
		    startdatum.add(Calendar.DATE,1);
		}
		
		return aantalDagen-aantalWeekendDagen;
	}

	public String getOmschrijving() {
		return super.getOmschrijving();
	}
	
	public String getBeginDatumStr(){
		Calendar cal=super.getStartdatum();	
		return cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR);
		
	}
	
	public String getEindDatumStr(){
		return einddatum.get(Calendar.DAY_OF_MONTH)+"-"+einddatum.get(Calendar.MONTH)+"-"+einddatum.get(Calendar.YEAR);
		
	}

	@Override
	public String toString() {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
		 
		return 	"CollectiefVerlof [ Startdatum: " + sdf.format(super.getStartdatum()) +" Einddatum: "
				+    sdf.format(einddatum)  + ", Omschrijving: " + getOmschrijving()
				+ ", isTerugkerend? " + isTerugkerend() +  "]";
	}
	
	

	
	
}
