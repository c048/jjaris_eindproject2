package beans;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.TeamDAO;
import daos.WerknemerDAO;
import entities.Adres;
import entities.Team;
import entities.Werknemer;

@Named("managemedewerkerback")
@RequestScoped
public class ManageMedewerkerBack implements Serializable {

	@Inject
	private WerknemerDAO werknemerDAO;
	@Inject
	private TeamDAO teamDAO;

	private static final long serialVersionUID = 1L;
	private Werknemer werknemer;
	private Adres adres;
	private Team team;
	private int gebDag;
	private int gebJaar;
	private int gebMaand;
	
	public ManageMedewerkerBack () {
		adres = new Adres();
		werknemer = new Werknemer();
		team = new Team();
	}
	
	public void setGebDag(int gebDag) {
		this.gebDag = gebDag;
	}
	
	public void setGebJaar(int gebJaar) {
		this.gebJaar = gebJaar;
	}
	
	public void setGebMaand(int gebMaand) {
		this.gebMaand = gebMaand;
	}
	
	public void setEmail(String email) {
		werknemer.setEmail(email);
	}
	
	public void setNaam(String naam) {
		werknemer.setNaam(naam);
	}
	
	public void setVoornaam(String voornaam) {
		werknemer.setVoornaam(voornaam);
	}
	
	public void setPasswoord(String passwoord) {
		werknemer.setPasswoord(passwoord);
	}
	
	public void setGemeente(String gemeente) {
		adres.setGemeente(gemeente);
	}
	
	public void setHuisnummer(String huisnummer) {
		adres.setHuisnummer(huisnummer);
	}
	
	public void setPostcode(String postcode) {
		adres.setPostcode(postcode);
	}
	
	public void setStraat(String straat) {
		adres.setStraat(straat);
	}
	
	public void setBusnummer(String busnummer) {
		adres.setBusnummer(busnummer);
	}
	
	public String getEmail() {
		return werknemer.getEmail();
	}
	
	public String getPasswoord() {
		return null;
	}
	
	public String getNaam() {
		return werknemer.getNaam();
	}
	
	public String getVoornaam() {
		return werknemer.getVoornaam();
	}
	
	public String getGemeente() {
		return adres.getGemeente();
	}
	
	public String getHuisnummer() {
		return adres.getHuisnummer();
	}
	
	public String getPostcode() {
		return adres.getPostcode();
	}
	
	public String getStraat() {
		return adres.getStraat();
	}
	
	public String getBusnummer() {
		return adres.getBusnummer();
	}
	
	public int getGebDag() {
		return gebDag;
	}
	
	public int getGebJaar() {
		return gebJaar;
	}

	public int getGebMaand() {
		return gebMaand;
	}
	
	public List<Team> findAllTeams() {
		return teamDAO.getTeams();
	}
	
	private void createGeboorteJaar() {
		if(getGebDag() <= 0) {
			throw new IllegalArgumentException("Geboortedag mag niet leeg zijn!");
		}
		if(getGebMaand() <= 0 || getGebMaand() > 12) {
			throw new IllegalArgumentException("Geboortemaand moet tussen 1 en 12 liggen!");
		}
		if(getGebJaar() <= 0) {
			throw new IllegalArgumentException("U bent te oud!");
		}
		werknemer.setGeboortedatum(new GregorianCalendar(getGebJaar(), getGebMaand()-1, getGebDag()));
	}
	
	public String addMedewerker() {
		try {
			createGeboorteJaar();
			werknemer.setAdres(adres);
//			werknemer.setTeam(team);
			werknemerDAO.voegWerknemerToe(werknemer);
		} catch (Exception e){
			return null;
		}
		return "HR";
	}
}
