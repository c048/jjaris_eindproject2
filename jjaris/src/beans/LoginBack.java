package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

//import daos.TeamDAO;
import daos.WerknemerDAO;
import entities.Team;
import entities.Werknemer;

@Named
@SessionScoped
public class LoginBack implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;
	private String paswoord;
	@Inject
	private WerknemerDAO wDao;
//	@Inject
//	private TeamDAO tDao;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public Werknemer getIngelogdeWerknemer() {
		Werknemer w = wDao.getWerknemer(email);
		return w;
	}

	public Team getTeamIngelogdeWerknemer() {
		Werknemer w = getIngelogdeWerknemer();
		return w.getTeam();

	}

	public String login() {
		Werknemer w = wDao.getWerknemer(email);
		if (w.controleerPasswoord(getPaswoord())) {
			if (w.isHR()) {
				return "medewerkershr";
			} else {
				if (w.isVerantwoordelijke()) {
					return "verantwoordelijke";
				} else {
					return "verlofMedewerker";
				}

			}

		} else {
			FacesMessage msg = new FacesMessage("Ongeldige login gegevens");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
			return null;
		}
	}

}
