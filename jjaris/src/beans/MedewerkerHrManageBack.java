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
import javax.transaction.TransactionalException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

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
	private LoginBack loginBack;
	@Inject
	private ParameterBack params;

	private static final long serialVersionUID = 1L;
	private Werknemer werknemer;
	private int gebDag;
	private int gebJaar;
	@Min(value = 1, message = "Maand kan niet onder 1 liggen")
	@Max(value = 12, message = "Maand kan niet boven 12 liggen")
	private int gebMaand;
	@NotEmpty (message = "U moet een achternaam invullen")
	private String naam;
	@NotEmpty (message = "U moet een voornaam invullen")
	private String voornaam;
	@NotEmpty(message = "U moet een e-mail invoeren")
	private String email;
	@NotEmpty(message = "U moet een straat invoeren")
	private String straat;
	@NotEmpty(message = "U moet een gemeente invoeren")
	private String gemeente;
	@NotEmpty(message = "U moet een postcode invoeren")
	private String postcode;

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
			setEmail(werknemer.getEmail());
			setNaam(werknemer.getNaam());
			setVoornaam(werknemer.getVoornaam());
			setGemeente(werknemer.getAdres().getGemeente());
			setPostcode(werknemer.getAdres().getPostcode());
			setStraat(werknemer.getAdres().getStraat());
		}
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
		this.email = email;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public void setPasswoord(String passwoord) {
		try {
			if(!(passwoord.isEmpty())) { 
				this.werknemer.setPasswoord(passwoord);
			}
		} catch (IllegalArgumentException ie) {
			setFacesMessage(ie.getMessage());
		}
	}

	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}

	public void setHuisnummer(String huisnummer) {
		werknemer.getAdres().setHuisnummer(huisnummer);
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public void setBusnummer(String busnummer) {
		werknemer.getAdres().setBusnummer(busnummer);
	}

	public Team getTeam() {
		return werknemer.getTeam();
	}

	public String getEmail() {
		return email;
	}

	public String getPasswoord() {
		return null;
	}

	public String getNaam() {
		return naam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getGemeente() {
		return gemeente;
	}

	public String getHuisnummer() {
		return werknemer.getAdres().getHuisnummer();
	}

	public String getPostcode() {
		return postcode;
	}

	public String getStraat() {
		return straat;
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

	public int findPersoneelsnummer() {
		return params.getPersoneelsnummer();
	}

	public List<Team> findAllTeams() {
		return teamDAO.getTeams();
	}

	public void reset() {
		werknemer = new Werknemer();
		werknemer.setAdres(new Adres());
		werknemer.setTeam(new Team());
		setGebDag(0);
		setGebMaand(0);
		setGebJaar(0);
		email = null;
		naam = null;
		voornaam = null;
		gemeente = null;
		straat = null;
		postcode = null;
	}
	
	public void valideerMedewerker() throws IllegalArgumentException {
		DatumBuilder tmpBuilder = new DatumBuilder(gebDag, gebMaand, gebJaar);
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
			werknemer.setNaam(getNaam());
			werknemer.setVoornaam(getVoornaam());
			werknemer.setEmail(getEmail());
			werknemer.getAdres().setStraat(getStraat());
			werknemer.getAdres().setPostcode(getPostcode());
			werknemer.getAdres().setGemeente(getGemeente());
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
		} catch (IllegalArgumentException iae){
			setFacesMessage(iae.getMessage());
			return null;
		} catch (Exception e) {
			setFacesMessage("Onbekende error, contacteer IT-support!");
			return null;
		}
		loginBack.changePage("medewerkersHr");
		return null;
	}
	
	public void setFacesMessage(String msg) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}

	public String cancel() {
		params.reset();
		loginBack.changePage("medewerkersHr");
		return null;
	}
}
