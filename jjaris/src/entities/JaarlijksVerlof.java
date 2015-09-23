package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class JaarlijksVerlof implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private static final long serialVersionUID = 1L;
	private int jaar;
	private int aantalDagen;

	@ManyToOne
	private Werknemer werknemer;

	public JaarlijksVerlof() {

	}

	public JaarlijksVerlof(int jaar, int aantalDagen, Werknemer w) {
		setJaar(jaar);
		setWerknemer(w);
		setAantalDagen(aantalDagen);
	}

	public Werknemer getWerknemer() {
		return werknemer;
	}

	public void setWerknemer(Werknemer werknemer) {
		if (werknemer != null){
			this.werknemer = werknemer;
		}else throw new NullPointerException("parameter werknemer mag niet null zijn");
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJaar() {
		return jaar;
	}

	public void setJaar(int jaar) {
		Calendar now = new GregorianCalendar();
		int huidigJaar = now.get(Calendar.YEAR);
		if (jaar >= huidigJaar) {
			this.jaar = jaar;
		} else
			throw new IllegalArgumentException(
					"Er kan geen jaarlijks verlof voor een jaar in het verleden ingevuld worden");
	}

	public int getAantalDagen() {
		return aantalDagen;
	}

	public void setAantalDagen(int aantalDagen) {
		if (aantalDagen >= 0) {
			this.aantalDagen = aantalDagen;
		}else throw new IllegalArgumentException("Het aantal dagen moet groter dan of gelijk aan 0 zijn");
	}

	@Override
	public int hashCode() {
		return (getId() + "").hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		return getId() == ((JaarlijksVerlof) obj).getId();
	}

}
