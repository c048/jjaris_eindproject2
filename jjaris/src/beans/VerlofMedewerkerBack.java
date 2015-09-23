package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import utils.DatumBuilder;
import utils.Filter;
import utils.SendMail;
import daos.VerlofAanvraagDAO;
import entities.Team;
import entities.Toestand;
import entities.VerlofAanvraag;
import entities.Werknemer;

@Named("verlofaanvraag")
@RequestScoped
public class VerlofMedewerkerBack implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBack user;
	@Inject
	private VerlofAanvraagDAO verlofaanvraagDAO;
	private int startJaar;
	private int startMaand;
	private int startDag;
	private int eindJaar;
	private int eindMaand;
	private int eindDag;
	private Toestand toestand = null;
	private Team team;

	private List<VerlofAanvraag> verlofaanvragen;

	/**
	 * Lijst met verlofaanvragen per werknemer gefilterd standaard lege filters
	 * behalve geannulleerde aanvragen
	 * 
	 * @return
	 */
	public List<VerlofAanvraag> getAanvragen() {
		if (verlofaanvragen == null){
			verlofaanvragen = verlofaanvraagDAO.getVerlofAanvragenWerknemer(user.getIngelogdeWerknemer().getPersoneelsnummer());
		}
		return verlofaanvragen;
	}

	public String zoeken() {
		try {
			Date startdatum = buildDatum(startDag, startMaand, startJaar);
			Date einddatum = buildDatum(eindDag, eindMaand, eindJaar);
			Filter filter = new Filter();
			filter.voegFilterToe("werknemer.personeelsnummer", user.getIngelogdeWerknemer().getPersoneelsnummer());
			if (startdatum != null) {
				System.out.println("filter startdatum toevoegen");
				filter.voegFilterToe("startdatum", converteerDatum(startdatum));
			}
			if (einddatum != null) {
				System.out.println("filter einddatum toevoegen");
				filter.voegFilterToe("einddatum", converteerDatum(einddatum));
			}
			if (toestand != null) {
				filter.voegFilterToe("toestand", toestand);
			}
			verlofaanvragen = verlofaanvraagDAO.getVerlofAanvragen(filter);
		} catch (IllegalArgumentException iae) {
			FacesMessage msg = new FacesMessage(iae.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
		}

		resetParameters();
		return null;

	}

	/**
	 * Annuleer een verlofaanvraag
	 */
	public String annuleren(int id) {
		try {
			VerlofAanvraag va = verlofaanvraagDAO.getVerlofAanvraag(id);
			va.annuleren();
			verlofaanvraagDAO.updateVerlofAanvraag(va);
			System.out.println("IETS**************************************************************************");
			
				StringBuilder verlof = new StringBuilder();
				verlof.append("De verlofaanvraag van "+user.getIngelogdeWerknemer().getNaam());
				verlof.append("\n met id: "+va.getId());
				verlof.append("\n is geannuleerd");
				SendMail.SendEmail(user.getIngelogdeWerknemer().getTeam().getTeamverantwoordelijke().getEmail(),	
					"Verlofaanvraag gewijzigd", verlof.toString());
				
				Filter filter = new Filter();
				filter.voegFilterToe("werknemer.personeelsnummer", user.getIngelogdeWerknemer().getPersoneelsnummer());
				verlofaanvragen = verlofaanvraagDAO.getVerlofAanvragen(filter);
		} 
			catch (IllegalArgumentException iae) {
				FacesMessage msg = new FacesMessage(iae.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				FacesContext.getCurrentInstance().renderResponse();
			}

		
	
		return null;
	}

	/**
	 * Verlof aanvragen
	 */
	public String toevoegen() {

		try {
			Date startdatum = buildDatum(startDag, startMaand, startJaar);
			Date einddatum = buildDatum(eindDag, eindMaand, eindJaar);
			if (startdatum != null && einddatum != null) {
				Werknemer werknemer = user.getIngelogdeWerknemer();
				VerlofAanvraag verlof = new VerlofAanvraag(converteerDatum(startdatum), converteerDatum(einddatum), werknemer);
				verlofaanvraagDAO.voegVerlofAanvraagToe(verlof);
			} else {
				FacesMessage msg = new FacesMessage("Gelieve de datumvelden in te vullen");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				FacesContext.getCurrentInstance().renderResponse();
			}
				StringBuilder verlof = new StringBuilder();
				verlof.append("De verlofaanvraag van "+user.getIngelogdeWerknemer().getNaam());
				verlof.append("\n met id: "+user.getIngelogdeWerknemer().getPersoneelsnummer());
				verlof.append("\n is aangevraagd");
				SendMail.SendEmail(user.getIngelogdeWerknemer().getTeam().getTeamverantwoordelijke().getEmail(),	
					"Verlofaanvraag gewijzigd", verlof.toString());
		} catch (IllegalArgumentException iae) {
			FacesMessage msg = new FacesMessage(iae.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
		}
		return null;

	}

	/**
	 * hulpmethode datum date-->gregorian
	 * 
	 * @param tmp
	 * @return
	 */
	private GregorianCalendar converteerDatum(Date tmp) {
		GregorianCalendar newdate = new GregorianCalendar();
		newdate.setTime(tmp);
		return newdate;
	}

	/**
	 * Zet datum aan de hand van de apparte velden met datumbuilder
	 * 
	 * @param startdatum
	 */

	private Date buildDatum(int dag, int maand, int jaar) {
		if (dag != 0 && maand != 0 && jaar != 0) {
			DatumBuilder tmp = new DatumBuilder(dag, maand, jaar);
			return tmp.buildDate();
		} else {
			return null;
		}
	}

	/**
	 * Zet datum aan de hand van de apparte veldenmet datumbuilder
	 * 
	 * @param einddatum
	 */

	public Date converteerNaarDate(Calendar calendar) {
		return calendar.getTime();
	}

	public int getStartJaar() {
		return startJaar;
	}

	public void setStartJaar(int startJaar) {
		this.startJaar = startJaar;
	}

	public int getStartMaand() {
		return startMaand;
	}

	public void setStartMaand(int startMaand) {
		this.startMaand = startMaand;
	}

	public int getStartDag() {
		return startDag;
	}

	public void setStartDag(int startDag) {
		this.startDag = startDag;
	}

	public int getEindJaar() {
		return eindJaar;
	}

	public void setEindJaar(int eindJaar) {
		this.eindJaar = eindJaar;
	}

	public int getEindMaand() {
		return eindMaand;
	}

	public void setEindMaand(int eindMaand) {
		this.eindMaand = eindMaand;
	}

	public int getEindDag() {
		return eindDag;
	}

	public void setEindDag(int eindDag) {
		this.eindDag = eindDag;
	}

	public String getToestand() {
		return toestand == null ? "" : toestand.toString();
	}

	public void setToestand(String toestand) {
		if (toestand != null && !toestand.isEmpty()) {
			this.toestand = Toestand.valueOf(toestand);
		}
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void resetParameters() {
		startDag = 0;
		startMaand = 0;
		startJaar = 0;
		eindDag = 0;
		eindJaar = 0;
		eindMaand = 0;
		toestand = null;

	}

}
