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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
	public List<VerlofAanvraag> filterAanvragen() {
		filter.voegFilterToe("werknemer.team.code", user.getIngelogdeWerknemer().getTeam().getCode());
		if (toestand != null) {
			filter.voegFilterToe("toestand", toestand);
		}
		return verlofaanvraagDAO.getVerlofAanvragen(filter);
	}
	
	public void filterDates() {
		try {
			Date startdatum = null;
			Date einddatum = null;
			if(startDag != 0 || startMaand != 0 || startJaar != 0) {
				if(startDag != 0 && startMaand != 0 && startJaar != 0) {
					startdatum = buildDatum(startDag, startMaand, startJaar);
				} else {
					setFacesMessage("Gelieve de hele startdatum in te voeren");
				}
			}
			
			if(eindDag != 0 || eindMaand != 0 || eindJaar != 0) {
				if(eindDag != 0 && eindMaand != 0 && eindJaar != 0) {
					einddatum = buildDatum(eindDag, eindMaand, eindJaar);
				} else {
					setFacesMessage("Gelieve de hele einddatum in te voeren");
				}
			}
			
			if(startdatum != null && einddatum == null) {
				setFacesMessage("Gelieve een eind datum in te vullen");
			}
			
			if(einddatum != null && startdatum == null) {
				setFacesMessage("Gelieve een start datum in te vullen");
			}
			
			if(startdatum != null && einddatum != null) {
				if(!startdatum.before(einddatum)) {
					setFacesMessage("Startdatum moet voor einddatum liggen");
				} else {
					filter.voegFilterToe("startdatum", converteerDatum(startdatum));
					filter.voegFilterToe("einddatum", converteerDatum(einddatum));
				}
			}
		} catch (IllegalArgumentException iae) {
			setFacesMessage(iae.getMessage());
		}
	}

	public String zoeken() {
		filterDates();
		return null;
	}

	/**
	 * Zet de gekozen verlofaanvraag in toestand afgekeurd
	 * 
	 * @return
	 */
	public String afkeuren() {
		if (reden.equals("")) {
			setFacesMessage("Geef een reden om af te keuren");
		}
		// if(verlofaanvraagDAO.getVerlofAanvraag(id)){
		//
		// }
		else {
			try {
				VerlofAanvraag v = verlofaanvraagDAO.getVerlofAanvraag(id);
				v.setToestand(Toestand.AFGEKEURD);
				v.setReden(reden);
				verlofaanvraagDAO.updateVerlofAanvraag(v);
			} catch (IllegalArgumentException iae) {
				setFacesMessage(iae.getMessage());
			} catch (Exception e) {
				setFacesMessage("Unexpected Error, contact IT support!");
			}
		}
		return null;
	}

	public String goedkeuren(int id) {
		try {
			VerlofAanvraag v = verlofaanvraagDAO.getVerlofAanvraag(id);
			v.setToestand(Toestand.GOEDGEKEURD);
			verlofaanvraagDAO.updateVerlofAanvraag(v);
		} catch (IllegalArgumentException iae) {
			setFacesMessage(iae.getMessage());
		} catch (Exception e) {
			setFacesMessage("Unexpected Error, contact IT support!");
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

	public String getToestand() {
		return toestand == null ? "": toestand.toString();
	}

	public void setToestand(String toestand) {
		if (toestand != null && !toestand.isEmpty()) {
			this.toestand = Toestand.valueOf(toestand);
		}
	}
	
	public void setFacesMessage(String msg) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}
	
}
