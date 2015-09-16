package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class CollectieveSluiting implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Calendar startdatum;
	private String omschrijving;
	private boolean terugkerend;

	
	
	public CollectieveSluiting() {
	
	}
	
	

	public CollectieveSluiting(Calendar startdatum, String omschrijving,
			boolean terugkerend) {
		this.startdatum = startdatum;
		this.omschrijving = omschrijving;
		this.terugkerend = terugkerend;
	}



	public int getId() {
		return id;
	}
	
	/*public void setId(int id) {
		this.id = id;
	}
	*/
	
	public Calendar getStartdatum() {
		return startdatum;
	}
	
	public void setStartdatum(GregorianCalendar startdatum) {
		this.startdatum = startdatum;
	}
	
	public String getOmschrijving() {
		return omschrijving;
	}
	
	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
	
	public boolean isTerugkerend() {
		return terugkerend;
	}
	
	public void setTerugkerend(boolean terugkerend) {
		this.terugkerend = terugkerend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectieveSluiting other = (CollectieveSluiting) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CollectieveSluiting [id=" + id + ", startdatum=" + startdatum
				+ ", omschrijving=" + omschrijving + ", terugkerend="
				+ terugkerend + "]";
	}
	
	
	
	
	
}
