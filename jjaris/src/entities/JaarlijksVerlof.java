package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JaarlijksVerlof implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private static final long serialVersionUID = 1L;
	private int jaar;
	private int aantalDagen;
	
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
	
	
	
	
	

}
