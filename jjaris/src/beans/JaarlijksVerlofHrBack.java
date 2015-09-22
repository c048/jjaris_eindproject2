package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import utils.Filter;
import daos.WerknemerDAO;
import entities.JaarlijksVerlof;
import entities.Team;
import entities.Werknemer;

@Named("jaarlijks")
@RequestScoped
public class JaarlijksVerlofHrBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private WerknemerDAO dao;

	@PersistenceContext
	private EntityManager manager;

	private JaarlijksVerlof verlof;
	private Werknemer werknemer;
	private Team team;
	private int ID, code;
	private int Jaar;

	private List<JaarlijksVerlof> delijst2;

	public JaarlijksVerlofHrBack() {
try {delijst2 = new ArrayList<JaarlijksVerlof>();
	
} catch (Exception e) {
	setFacesMessage("Geen resultaten gevonden met deze parameters");
}
		
	}

	public List<JaarlijksVerlof> getjJaarlijksVerlof() {

		Filter f = new Filter();
		List<Werknemer> tempverlf = new ArrayList<Werknemer>();
		List<JaarlijksVerlof> tempverlfB = new ArrayList<JaarlijksVerlof>();
		try {
			if (ID != 0) {
				f.voegFilterToe("werknemer.personeelsnummer", getID());
			}
			if (Jaar != 0) {
				f.voegFilterToe("jaar", getJaar());
			}
			if (code != 0) {
				f.voegFilterToe("werknemer.team.code", team.getCode());
			}
			tempverlf = dao.getWerknemers(f);
			for (Werknemer werknemer : tempverlf) {
				tempverlfB.addAll(werknemer.getJaarlijkseverloven());
			}
			
			for (JaarlijksVerlof jaarlijksVerlof : tempverlfB) {
				delijst2.add(jaarlijksVerlof);
			}
		} catch (IllegalArgumentException iae) {
			setFacesMessage("Geen gegevens gevonden met deze parameters");
		} catch (NullPointerException npe) {
			setFacesMessage("Geen gegevens gevonden met deze parameters");
		}
		return delijst2;
	
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public JaarlijksVerlof getVerlof() {
		return verlof;
	}

	public int getJaar() {
		return Jaar;
	}

	public void setJaar(int jaar) {
		Jaar = jaar;
	}

	public String zoek() {
		delijst2 = getjJaarlijksVerlof();

		return null;
	}

	public void setVerlof(JaarlijksVerlof verlof) {
		this.verlof = verlof;
	}

	public Werknemer getWerknemer() {
		return werknemer;
	}

	public void setWerknemer(Werknemer werknemer) {
		this.werknemer = werknemer;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public List<JaarlijksVerlof> getDelijst2() {
		return delijst2;
	}

	public String createJaarlijksVerlof() {
		return "jaarlijkseVerlovenCreate";
	}
	
	public void setFacesMessage(String msg) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}

}
