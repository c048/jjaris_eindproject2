package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FEESTDAGEN")
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
	
	
	public String getOmschrijving() {
		return super.getOmschrijving();
	}
	
	public String getDatumStr(){
		Calendar cal=super.getStartdatum();	
		return cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR);
		
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
		 
		return "Feestdag [ datum: " +  sdf.format(super.getStartdatum()) + 
				", Omschrijving: " + getOmschrijving()
				+ ", isTerugkerend? " + isTerugkerend() +  "]";
	}
	
	
	

}
