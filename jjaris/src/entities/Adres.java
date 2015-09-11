package entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Embeddable
public class Adres implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private static final long serialVersionUID = 1L;
	private String straat;
	private String huisnummer;
	private String busnummer;
	private String postcode;
	private String gemeente;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStraat() {
		return straat;
	}
	
	public void setStraat(String straat) {
		this.straat = straat;
	}
	
	public String getHuisnummer() {
		return huisnummer;
	}
	
	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer;
	}
	
	public String getBusnummer() {
		return busnummer;
	}
	
	public void setBusnummer(String busnummer) {
		this.busnummer = busnummer;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getGemeente() {
		return gemeente;
	}
	
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
	
}
