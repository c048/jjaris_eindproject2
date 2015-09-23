package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
	@Inject
	private LoginBack loginBack;

	@PersistenceContext
	private EntityManager manager;

	private JaarlijksVerlof verlof;
	private Werknemer werknemer;

	private int ID, code;
	private int Jaar;

	private List<JaarlijksVerlof> delijst2;

	public JaarlijksVerlofHrBack() {
	}

	@PostConstruct
	public void init() {
		

			delijst2 = new ArrayList<JaarlijksVerlof>();
			List<Werknemer> alleWerknemers = dao.getAlleWerknemers();
			for (Werknemer werknemer : alleWerknemers) {
				delijst2.addAll(werknemer.getJaarlijkseverloven());
			}

		if (getDelijst2().isEmpty()){
			setFacesMessage("Geen jaarlijkse verloven gevonden!");
		}
	}

	public List<JaarlijksVerlof> getjJaarlijksVerlof() {
		Filter f = new Filter();
		List<Werknemer> tempverlf = new ArrayList<Werknemer>();
		List<JaarlijksVerlof> tempverlfB = new ArrayList<JaarlijksVerlof>();

		if (getID() != 0) {
			f.voegFilterToe("personeelsnummer", getID());
		}

		if (getCode() != 0) {
			f.voegFilterToe("team.code", getCode());
		}

		tempverlf = dao.getWerknemers(f);

		for (Werknemer werknemer : tempverlf) {
			if (Jaar != 0) {
				if (werknemer.getJaarlijksVerlofVanJaar(Jaar) != null) {
					tempverlfB.add(werknemer.getJaarlijksVerlofVanJaar(Jaar));
				}
			} else {
				tempverlfB.addAll(werknemer.getJaarlijkseverloven());
			}
		}

		if (tempverlfB.isEmpty()) {
			setFacesMessage("Geen gegevens gevonden met deze parameters!");
		}

		return tempverlfB;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getJaar() {
		return Jaar;
	}

	public void setJaar(int jaar) {
		Jaar = jaar;
	}

	public Werknemer getWerknemer() {
		return werknemer;
	}

	public void setWerknemer(Werknemer werknemer) {
		this.werknemer = werknemer;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public JaarlijksVerlof getVerlof() {
		return verlof;
	}

	public String zoek() {
		delijst2 = getjJaarlijksVerlof();
		resetParameters();
		return null;
	}

	public void setVerlof(JaarlijksVerlof verlof) {
		this.verlof = verlof;
	}

	public List<JaarlijksVerlof> getDelijst2() {
		return delijst2;
	}

	public String createJaarlijksVerlof() {
		loginBack.changePage("jaarlijksVerlofHrCreate");
		return null;
	}

	public void setFacesMessage(String msg) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}

	public void resetParameters() {
		setID(0);
		setCode(0);
		setJaar(0);
	}

}
