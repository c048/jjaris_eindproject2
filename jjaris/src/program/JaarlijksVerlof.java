package program;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JaarlijksVerlof {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
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
