package beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionalException;

import utils.Filter;
import daos.TeamDAO;
import entities.Team;

@Named
@RequestScoped
public class HrTeamsBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private TeamDAO tDao;
	@Inject
	private ParameterBack parameters;
	@Inject
	private LoginBack loginBean;

	private List<Team> teams;

	private String teamNaam, naamTeamVerantwoordelijke;
	private int teamCode;
	

	public List<Team> getTeams() {
		if (teams == null){
		teams = tDao.getTeams();
		}
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public String getTeamNaam() {
		return teamNaam;
	}

	public void setTeamNaam(String teamnaam) {
		teamNaam = teamnaam;
	}

	public int getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(int teamcode) {
		teamCode = teamcode;
	}

	public String getTeamVerantwoordelijke() {
		return naamTeamVerantwoordelijke;
	}

	public void setTeamVerantwoordelijke(String teamVerantwoordelijke) {
		naamTeamVerantwoordelijke = teamVerantwoordelijke;
	}

//	public String TeamAanpassen(int code) {
//		String url = "Update_team.xhtml?" + Integer.toString(code);
//		return url;
//
//	}

	public String verwijderenTeam(int teamCode) {
		// String code =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selCodeVerwijder");
		System.out.println("in verwijder team van HrTeamsBack");
		Team team = tDao.getTeam(teamCode);
		try {
			tDao.verwijderTeam(team);
			teams = tDao.getTeams();
		}
//		catch (NullPointerException npe) {
//			FacesMessage msg = new FacesMessage(npe.getMessage());
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//			FacesContext.getCurrentInstance().renderResponse();
//		} 
//		catch (IllegalArgumentException iae) {
//			FacesMessage msg = new FacesMessage(iae.getMessage());
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//			FacesContext.getCurrentInstance().renderResponse();
//		}
		catch (TransactionalException te) {
					FacesMessage msg = new FacesMessage("Kan geen team verwijderen met nog teamleden in");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					FacesContext.getCurrentInstance().renderResponse();
		}
		
		return null;
	}

	public String editeerTeam(int teamCode) {
		// String code =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selCodeEdit");
		System.out.println("in edit team van HrTeamsBack");
		parameters.setCode(teamCode);
		loginBean.changePage("teamHrEdit");
		return null;
	}

	public String zoek() {
		try {
			System.out.println("in zoek() van HrTeamsBack");
			Filter f = new Filter();
			if (!getTeamNaam().trim().equals("")) {
				f.voegFilterToe("naam", getTeamNaam());
			}
			if (!getTeamVerantwoordelijke().trim().equals("")) {
				f.voegFilterToe("teamverantwoordelijke.naam", getTeamVerantwoordelijke());
			}
			if (getTeamCode() != 0) {
				f.voegFilterToe("code", getTeamCode());
			}

			teams = tDao.getTeams(f);
			resetParameters();
		} catch (IllegalArgumentException iae) {
			setFacesMessage("Geen gegevens gevonden met deze parameters");
		} catch (NullPointerException npe) {
			setFacesMessage("Geen gegevens gevonden met deze parameters");
		
		}
		return null;
	}
	
	public void resetParameters(){
		setTeamNaam(null);
		setTeamVerantwoordelijke(null);
		setTeamCode(0);
	}
	
	public String toevoegenTeam(){
		loginBean.changePage("teamHrCreate");
		return null;
	}
	public void setFacesMessage(String msg) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}
}
