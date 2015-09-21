package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import utils.DatumBuilder;
import daos.TeamDAO;
import daos.WerknemerDAO;
import entities.Adres;
import entities.Team;
import entities.Werknemer;

@Named("medewerkerHrManageBack")
@RequestScoped
public class MedewerkerHrManageBack implements Serializable {

	@Inject
	private WerknemerDAO werknemerDAO;
	@Inject
	private TeamDAO teamDAO;
	@Inject
	private ParameterBack params;

	private static final long serialVersionUID = 1L;
	private Werknemer werknemer;
	private int gebDag;
	private int gebJaar;
	private int gebMaand;

	public MedewerkerHrManageBack() {
		reset();
	}

	@PostConstruct
	public void init() {
		if (params.getPersoneelsnummer() != 0) {
			werknemer = werknemerDAO.getWerknemer(params.getPersoneelsnummer());
			setGebDag(werknemer.getGeboortedatum().get(Calendar.DAY_OF_MONTH));
			setGebJaar(werknemer.getGeboortedatum().get(Calendar.YEAR));
			setGebMaand(werknemer.getGeboortedatum().get(Calendar.MONTH) + 1);
		}
	}

	public Team getTeam() {
		return werknemer.getTeam();
	}

	public int findPersoneelsnummer() {
		return params.getPersoneelsnummer();
	}

	public void setTeam(Team team) {
		this.werknemer.setTeam(team);
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
		if(!passwoord.isEmpty()) {
			werknemer.setPasswoord(passwoord);
		}
	}

	public void setGemeente(String gemeente) {
		werknemer.getAdres().setGemeente(gemeente);
	}

	public void setHuisnummer(String huisnummer) {
		werknemer.getAdres().setHuisnummer(huisnummer);
	}

	public void setPostcode(String postcode) {
		werknemer.getAdres().setPostcode(postcode);
	}

	public void setStraat(String straat) {
		werknemer.getAdres().setStraat(straat);
	}

	public void setBusnummer(String busnummer) {
		werknemer.getAdres().setBusnummer(busnummer);
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
		return werknemer.getAdres().getGemeente();
	}

	public String getHuisnummer() {
		return werknemer.getAdres().getHuisnummer();
	}

	public String getPostcode() {
		return werknemer.getAdres().getPostcode();
	}

	public String getStraat() {
		return werknemer.getAdres().getStraat();
	}

	public String getBusnummer() {
		return werknemer.getAdres().getBusnummer();
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

	public void reset() {
		werknemer = new Werknemer();
		werknemer.setAdres(new Adres());
		werknemer.setTeam(new Team());
	}
	
	public void valideerMedewerker() throws IllegalArgumentException {
		DatumBuilder tmpBuilder = new DatumBuilder(gebDag, gebMaand,gebJaar);
		if(tmpBuilder.isVoorVandaag()) {
			werknemer.setGeboortedatum(tmpBuilder.buildCalendar());
		} else {
			throw new IllegalArgumentException("Geboortejaar kan niet in de toekomst liggen");
		}
	}

	public String addMedewerkerContinue() {
		if (verwerkMedewerker() != null) {
			reset();
		}
		return null;
	}

	public String verwerkMedewerker() {
		try {
			valideerMedewerker();
			if (werknemerDAO.getWerknemer(werknemer.getEmail()) == null) {
				werknemerDAO.voegWerknemerToe(werknemer);
			} else {
				if (params.getPersoneelsnummer() != 0) {
					werknemer.setPersoneelsnummer(params.getPersoneelsnummer());
					werknemerDAO.updateWerknemer(werknemer);
					params.reset();
				} else {
					throw new IllegalArgumentException("Er bestaat al een persoon met dit e-mail adres");
				}
			}
		} catch (IllegalArgumentException e){
			setFacesMessage(e.getMessage());
			return null;
		} catch (Exception e) {
			setFacesMessage("Onbekende error, contacteer IT-support!");
			return null;
		}
		return "medewerkersHr";
	}
	
	public void setFacesMessage(String msg ) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}

	public String cancel() {
		params.reset();
		return "medewerkersHr";
	}
}
