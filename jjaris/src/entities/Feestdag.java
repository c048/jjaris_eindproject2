package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Feestdag extends CollectieveSluiting implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	public Feestdag() {
		super();
		
	}

	public Feestdag(Calendar startdatum, String omschrijving, boolean terugkerend) {
		super(startdatum, omschrijving, terugkerend);
		
	}

	/**
	 * geeft true als feestdag  valt in  een werkdag.
	 * @return : boolean: true/false
	 */
	public boolean isWeekdag() {
		
		int dayOfWeek= super.getStartdatum().get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==Calendar.SATURDAY  || dayOfWeek==Calendar.SUNDAY ) {
			return false;
		}
		return true;
	}
	
	

	@Override
	public String toString() {
		return "Feestdag [getId()=" + getId() + ", getStartdatum()="
				+ getStartdatum() + ", getOmschrijving()=" + getOmschrijving()
				+ ", isTerugkerend()=" + isTerugkerend() +  "]";
	}
	

	

}
