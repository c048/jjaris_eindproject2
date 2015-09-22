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
import daos.TeamDAO;
import daos.VerlofAanvraagDAO;
import entities.Team;
import entities.Toestand;
import entities.VerlofAanvraag;
import entities.Werknemer;


@Named("verlof")
@RequestScoped
public class VerlofAanvraagHr implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBack user;
	@Inject
	private VerlofAanvraagDAO verlofaanvraagDAO;
	@Inject
	private TeamDAO teamDAO;
	private int startJaar;
	private int startMaand;
	private int startDag;
	private int eindJaar;
	private int eindMaand;
	private int eindDag;
	private Toestand toestand =  null;
	private Team team;
	private int teamID;
	private String voorNaam;
	private int personeelsNr;
	
	private String achterNaam;
	
	private List<VerlofAanvraag> verlofaanvragen;
	
//	private Filter filter= new Filter();

	/**
	 * Lijst met verlofaanvragen per werknemer gefilterd standaard lege filters
	 * behalve geannulleerde aanvragen
	 * @return
	 */
	public List<VerlofAanvraag> getAanvragen(){
		if (verlofaanvragen == null){
			verlofaanvragen = verlofaanvraagDAO.getVerlofAanvragenWerknemer(user.getIngelogdeWerknemer().getPersoneelsnummer());
		}
		return verlofaanvragen;	
		
//		Date startdatum = buildDatum(startDag, startMaand, startJaar);
//		Date einddatum = buildDatum(eindDag, eindMaand, eindJaar);
////		filter.voegFilterToe("werknemer.personeelsnummer",user.getIngelogdeWerknemer().getPersoneelsnummer() );
//		if(startdatum!=null){filter.voegFilterToe("startdatum", converteerDatum(startdatum));}
//		if(einddatum!=null) {filter.voegFilterToe("einddatum", converteerDatum(einddatum));}
//		if(toestand !=null){filter.voegFilterToe("toestand", toestand);}
//		if(personeelsNr !=0){filter.voegFilterToe("werknemer.personeelsnummer", personeelsNr);}
//		filter.voegFilterToe("werknemer.team.code", teamID);
//		
//		return verlofaanvraagDAO.getVerlofAanvragen(filter);
	}
	public void resetParameters() {
		startDag = 0;
		startMaand = 0;
		startJaar = 0;
		eindDag = 0;
		eindJaar = 0;
		eindMaand = 0;
		toestand = null;
		personeelsNr = 0;
	}
	public String zoeken(){
			try {
				Date startdatum = buildDatum(startDag, startMaand, startJaar);
				Date einddatum = buildDatum(eindDag, eindMaand, eindJaar);
				Filter filter = new Filter();
				filter.voegFilterToe("werknemer.personeelsnummer", user.getIngelogdeWerknemer().getPersoneelsnummer());
				if (startdatum != null) {filter.voegFilterToe("startdatum", converteerDatum(startdatum));}
				if (einddatum != null) {filter.voegFilterToe("einddatum", converteerDatum(einddatum));}
				if (toestand != null) {filter.voegFilterToe("toestand", toestand);}
				if(personeelsNr !=0){filter.voegFilterToe("werknemer.personeelsnummer", personeelsNr);}
				filter.voegFilterToe("werknemer.team.code", teamID);
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
	 * Verlof aanvragen
	 */
	public void toevoegen() {

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
		} catch (IllegalArgumentException iae) {
			FacesMessage msg = new FacesMessage(iae.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
		}

	}		
	/**
	 * hulpmethode datum date-->gregorian
	 * @param tmp
	 * @return
	 */
	private GregorianCalendar converteerDatum(Date tmp){
		GregorianCalendar newdate =new GregorianCalendar();
		newdate.setTime(tmp);
		return newdate;		
	}

	

	/**
	 * Zet datum aan de hand van de apparte velden met datumbuilder
	 * @param startdatum
	 */
	
	private Date buildDatum(int dag, int maand, int jaar){
		if (dag != 0 && maand != 0 && jaar !=0){
		DatumBuilder tmp = new DatumBuilder(dag, maand, jaar);
		return tmp.buildDate();
		}
		else{
			return null;
		}
	}
	
	/**
	 * Zet datum aan de hand van de apparte veldenmet datumbuilder
	 * @param einddatum
	 */

	public Date converteerNaarDate(Calendar calendar){
		return calendar.getTime() ;
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
	
	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public void setEindDag(int eindDag) {
		this.eindDag = eindDag;
	}

	public String getToestand() {
		return toestand == null ? "": toestand.toString();
	}

	public void setToestand(String toestand) {
		if(toestand != null && !toestand.isEmpty ()) {
			this.toestand = Toestand.valueOf(toestand);
		}
	}


	public String getVoorNaam() {
		return voorNaam;
	}

	public void setVoorNaam(String voorNaam) {
		this.voorNaam = voorNaam;
	}

	public String getAchterNaam() {
		return achterNaam;
	}

	public void setAchterNaam(String achterNaam) {
		this.achterNaam = achterNaam;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	public List<Team> teamlijst(){
		return teamDAO.getTeams();
	}

	public int getPersoneelsNr() {
		return personeelsNr;
	}

	public void setPersoneelsNr(int personeelsNr) {
		this.personeelsNr = personeelsNr;
	}

}

