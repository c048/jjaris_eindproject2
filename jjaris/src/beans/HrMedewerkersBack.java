package beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.WerknemerDAO;
import entities.Werknemer;

@Named("HrManageMedewerker")
@RequestScoped
public class HrMedewerkersBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private WerknemerDAO Dao;
	private List<Werknemer> werknemers;
	private Werknemer werknemer;
	private String naam;
	private String Voornaam;
	private String team;
	private int ID;

	public int getID() {

		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public List<Werknemer> getWerknemers() {
		return werknemers;
	}

	public void setWerknemers(List<Werknemer> werknemers) {
		this.werknemers = werknemers;

	}

	public Werknemer getWerknemer() {
		return werknemer;
	}

	public void setWerknemer(Werknemer werknemer) {
		this.werknemer = werknemer;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVoornaam() {
		return Voornaam;
	}

	public void setVoornaam(String voornaam) {
		Voornaam = voornaam;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String zoekWerkNemer(String naam, String voornaam,
			int Personeelsnummer) {
werknemers.addAll( Dao.getWerknemers(naam, voornaam, Personeelsnummer));
return "HR.xhtml";
		

	}
	
	public String verwijderWerknemer(int personeelsnummer){
		Werknemer tmpw = Dao.getWerknemer(personeelsnummer);
		Dao.verwijderWerknemer(tmpw);
		return "hr.xhtml";
		
	}
	
	
	

}
