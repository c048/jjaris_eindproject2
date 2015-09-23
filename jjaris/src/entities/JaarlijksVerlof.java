package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class JaarlijksVerlof implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private static final long serialVersionUID = 1L;
	private int jaar;
	private int aantalDagen;
	
	@ManyToOne
	private Werknemer werknemer;
	
	
	public Werknemer getWerknemer() {
		return werknemer;
	}
	public void setWerknemer(Werknemer werknemer) {
		this.werknemer = werknemer;
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
		this.jaar = jaar;
	}
	public int getAantalDagen() {
		return aantalDagen;
	}
	public void setAantalDagen(int aantalDagen) {
		this.aantalDagen = aantalDagen;
	}
	
	@Override
	public int hashCode() {
		return (getId() + "").hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		return getId() == ((JaarlijksVerlof) obj).getId();
	}

}
