package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;

@Embeddable
public class Adres implements Serializable {
	private static final long serialVersionUID = 1L;
	private String straat;
	private String huisnummer;
	private String busnummer;
	private String postcode;
	private String gemeente;

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) throws IllegalArgumentException {
		if(!(straat.trim().equals(""))) {
			this.straat = straat;
		} else {
			throw new IllegalArgumentException("Straat mag niet leeg zijn");
		}
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

	public void setPostcode(String postcode) throws IllegalArgumentException {
		if(!(postcode.trim().equals(""))) {
			this.postcode = postcode;
		} else {
			throw new IllegalArgumentException("Postcode mag niet leeg zijn");
		}
	}

	public String getGemeente() {
		return gemeente;
	}

	public void setGemeente(String gemeente) throws IllegalArgumentException {
		if(!(gemeente.trim().equals(""))) {
			this.gemeente = gemeente;
		} else {
			throw new IllegalArgumentException("Gemeente mag niet leeg zijn");
		}
	}

	@Override
	public String toString() {
		return String.format("%s %s%s - %s, %s", getStraat(), getHuisnummer(), getBusnummer(), getPostcode(), getGemeente());
	}

}
