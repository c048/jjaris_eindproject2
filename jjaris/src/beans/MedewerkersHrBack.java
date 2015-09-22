package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionalException;

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
	private LoginBack loginBack; //niet verwijderen
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
		try {
			if (naam != null && !naam.trim().equalsIgnoreCase("")) {
				f.voegFilterToe("naam", getNaam());
			}
			if (voornaam != null && !voornaam.trim().equalsIgnoreCase("")) {
				f.voegFilterToe("voornaam", getVoornaam());
			}
			if (naam != null && ID != 0) {
				f.voegFilterToe("personeelsnummer", getID());
			}
		} catch (IllegalArgumentException iae) {
			setFacesMessage("Geen gegevens gevonden met deze parameters");
		} catch (NullPointerException npe) {
			setFacesMessage("Geen gegevens gevonden met deze parameters");
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
		try {
			dao.verwijderWerknemer(tmpw);
		} catch (TransactionalException te) {
			FacesMessage msg = new FacesMessage(
					"Kan werknemer niet verwijderen (hij is mogelijk nog verantwoordelijke van een team)");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
		}
		// catch (IllegalArgumentException iae) {
		// FacesMessage msg = new FacesMessage(iae.getMessage());
		// msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		// FacesContext.getCurrentInstance().addMessage(null, msg);
		// FacesContext.getCurrentInstance().renderResponse();
		// }
		return null;
	}

	public String editMedewerker(int personeelsnummer) {
		backIsBack.setPersoneelsnummer(personeelsnummer);
		loginBack.changePage("medewerkerHrManage");
		return null;
	}

	public String createMedewerker() {
		loginBack.changePage("medewerkerHrManage");
		return null;
	}
	public void setFacesMessage(String msg) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}
	public int beschikbareverlofdagen(int personbeelsnummer) {
		int currentYear = LocalDate.now().getYear();
		Werknemer tmpw = dao.getWerknemer(personbeelsnummer);

		int tmpaantal = tmpw.getAantalBeschikBareVerlofDagen(currentYear);
		return tmpaantal;

	}

}
