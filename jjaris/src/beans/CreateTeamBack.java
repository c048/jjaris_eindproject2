package beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotEmpty;

import daos.TeamDAO;
import entities.Team;

@Named
@RequestScoped
public class CreateTeamBack implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Team naam mag niet leeg zijn")
	private String naam;
	private String HR = "false";
	@Inject
	private TeamDAO tDao;
	@Inject
	private LoginBack loginBean;

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getHR() {
		return HR;
	}

	public void setHR(String hR) {
		HR = hR;
	}

	public String voegTeamToe() {
		Team t = new Team();
		try {
			t.setNaam(getNaam());
			if (getHR().equals("true")) {
				t.setHR(true);
			} else {
				t.setHR(false);
			}
			tDao.voegTeamToe(t);
		} catch (IllegalArgumentException iae) {
			FacesMessage msg = new FacesMessage(iae.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().renderResponse();
		}
		loginBean.changePage("teamsHr");
		return null;
	}
	
	public String annuleren(){
		loginBean.changePage("teamsHr");
		return null;
	}

}
