package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import utils.Filter;
import daos.WerknemerDAO;
import entities.Werknemer;

@Named("medewerkersHrBack")
@RequestScoped
public class MedewerkersHrBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private WerknemerDAO dao;

	private Werknemer werknemer;
	private String naam;
	private String voornaam;
	private String team;
	private int ID;
	private List<Werknemer> delijst;
	@Inject
	private ParameterBack backIsBack;

	public MedewerkersHrBack() {
		delijst = new ArrayList<Werknemer>();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String zoek() {
		delijst = getWerknemers();
		return null;
	}

	public List<Werknemer> getWerknemers() {
		Filter f = new Filter();
		if (naam != null && !naam.trim().equalsIgnoreCase("")) {
			f.voegFilterToe("naam", getNaam());
		}

		if (voornaam != null && !voornaam.trim().equalsIgnoreCase("")) {
			f.voegFilterToe("voornaam", getVoornaam());
		}
		if (naam != null && ID != 0) {
			f.voegFilterToe("personeelsnummer", getID());
		}

		return dao.getWerknemers(f);
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
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
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

	public String verwijderWerknemer(int personeelsnummer) {
		Werknemer tmpw = dao.getWerknemer(personeelsnummer);
		dao.verwijderWerknemer(tmpw);
		return null;
	}

	public String editMedewerker(int personeelsnummer) {
		backIsBack.setPersoneelsnummer(personeelsnummer);
		return "medewerkerHrManage";
	}
	
	public String createMedewerker() {
		return "medewerkerHrManage";
	}

	public int beschikbareverlofdagen(int personbeelsnummer) {
		int currentYear = LocalDate.now().getYear();
		Werknemer tmpw = dao.getWerknemer(personbeelsnummer);

		int tmpaantal = tmpw.getAantalBeschikBareVerlofDagen(currentYear);
		return tmpaantal;

	}

}
