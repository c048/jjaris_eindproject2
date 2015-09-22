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
import daos.VerlofAanvraagDAO;
import entities.Toestand;
import entities.VerlofAanvraag;

@Named("verlofTeamBack")
@RequestScoped
public class VerlofTeamBack implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBack user;
	@Inject
	private VerlofAanvraagDAO verlofaanvraagDAO;
	// private Date startdatum;
	// private Date einddatum;
	private int startJaar;
	private int startMaand;
	private int startDag;
	private int eindJaar;
	private int eindMaand;
	private int eindDag;
	private Toestand toestand = null;

	private int id;
	private String reden;

	private Filter filter = new Filter();
	


	/**
	 * Lijst met verlofaanvragen per team gefilterd standaard lege filters
	 * behalve geannulleerde aanvragen
	 * 
	 * @return
	 */
	public List<VerlofAanvraag> getAanvragen() {
		Date startdatum = buildDatum(startDag, startMaand, startJaar);
		Date einddatum = buildDatum(eindDag, eindMaand, eindJaar);
		filter.voegFilterToe("werknemer.team.code", user
				.getIngelogdeWerknemer().getTeam().getCode());
		
		if (startdatum != null) {
			filter.voegFilterToe("startdatum", converteerDatum(startdatum));
		}
		if (einddatum != null) {
			filter.voegFilterToe("einddatum", converteerDatum(einddatum));
		}
		if (toestand != null) {
			filter.voegFilterToe("toestand", toestand);
		}
		
		return verlofaanvraagDAO.getVerlofAanvragen(filter);
	}

	public String zoeken() {
		getAanvragen();
		return null;

	}

	/**
	 * Zet de gekozen verlofaanvraag in toestand afgekeurd
	 * 
	 * @return
	 */
	public String afkeuren() {
		if (reden.equals("")) {

			FacesMessage msg = new FacesMessage(
					"Geef een reden om af te keuren");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
			return null;
		}
		// if(verlofaanvraagDAO.getVerlofAanvraag(id)){
		//
		// }
		else {
			VerlofAanvraag v = verlofaanvraagDAO.getVerlofAanvraag(id);
			v.setToestand(Toestand.AFGEKEURD);
			v.setReden(reden);
			verlofaanvraagDAO.updateVerlofAanvraag(v);
			return null;
		}
	}

	public String goedkeuren() {
		VerlofAanvraag v = verlofaanvraagDAO.getVerlofAanvraag(id);
		v.setToestand(Toestand.GOEDGEKEURD);
		verlofaanvraagDAO.updateVerlofAanvraag(v);
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

	// public Date getStartdatum() {
	// return startdatum;
	// }
	/**
	 * Zet datum aan de hand van de apparte velden met datumbuilder
	 * 
	 * @param startdatum
	 */
	// public void setStartdatum() {
	// DatumBuilder tmp = new DatumBuilder(startDag, startMaand, startJaar);
	// this.startdatum = tmp.buildDate();

	// }
	// public Date getEinddatum() {
	// return einddatum;
	// }
	/**
	 * Zet datum aan de hand van de apparte veldenmet datumbuilder
	 * 
	 * @param einddatum
	 */
	// public void setEinddatum() {
	// DatumBuilder tmp = new DatumBuilder(eindDag, eindMaand, eindJaar);
	// this.einddatum = tmp.buildDate();
	// }
	private Date buildDatum(int dag, int maand, int jaar) {
		if (dag != 0 && maand != 0 && jaar != 0) {
			DatumBuilder tmp = new DatumBuilder(dag, maand, jaar);
			return tmp.buildDate();
		} else {
			return null;
		}
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReden() {
		return reden;
	}

	public void setReden(String reden) {
		this.reden = reden;
	}

	public Toestand getToestand() {
		return toestand;
	}

	public void setToestand(String toestand) {
		if (toestand != null && !toestand.isEmpty()) {
			this.toestand = Toestand.valueOf(toestand);
		}
	}
	
	

}
