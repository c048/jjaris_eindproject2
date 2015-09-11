package entities;

import java.io.Serializable;
import java.lang.String;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Entity
public class Werknemer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int personeelsnummer;
	private String naam;
	private String Voornaam;
	private String email;
	private LocalDate geboortedatum;
	private String passwoord;
	private List<JaarlijksVerlof> jaarlijkseverloven;
	private List<VerlofAanvraag> verlofaanvragen;
	private Team team;
	private Adres adres;
	private static final long serialVersionUID = 1L;

	public Werknemer() {
		super();
	}   
	public int getPersoneelsnummer() {
		return this.personeelsnummer;
	}

	public void setPersoneelsnummer(int personeelsnummer) {
		this.personeelsnummer = personeelsnummer;
	}   
	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}   
	public String getVoornaam() {
		return this.Voornaam;
	}

	public void setVoornaam(String Voornaam) {
		this.Voornaam = Voornaam;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public LocalDate getGeboortedatum() {
		return this.geboortedatum;
	}

	public void setGeboortedatum(LocalDate geboortedatum) {
		this.geboortedatum = geboortedatum;
	}   
	public String getPasswoord() {
		return this.passwoord;
	}

	public void setPasswoord(String passwoord) {
		this.passwoord = passwoord;
	} 
	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}   
	public Adres getAdres() {
		return this.adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	public List<JaarlijksVerlof> getJaarlijkseverloven() {
		return jaarlijkseverloven;
	}
	public void setJaarlijkseverloven(List<JaarlijksVerlof> jaarlijkseverloven) {
		this.jaarlijkseverloven = jaarlijkseverloven;
	}
	public List<VerlofAanvraag> getVerlofaanvragen() {
		return verlofaanvragen;
	}
	public void setVerlofaanvragen(List<VerlofAanvraag> verlofaanvragen) {
		this.verlofaanvragen = verlofaanvragen;
	}
   
}
