package beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.TeamDAO;
import entities.Team;

@Named
@RequestScoped
public class ManageTeamBack implements Serializable {
	private static final long serialVersionUID = 1L;
	private String naam;
	private String HR = "false";
	@Inject
	private TeamDAO tDao;

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
		t.setNaam(getNaam());
		if (getHR().equals("true")) {
			t.setHR(true);
		} else {
			t.setHR(false);
		}
		tDao.voegTeamToe(t);
		return null;

	}

}
