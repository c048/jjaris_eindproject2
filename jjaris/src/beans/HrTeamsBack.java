package beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import utils.Filter;
import daos.TeamDAO;
import daos.WerknemerDAO;
import entities.Team;
import entities.Werknemer;

@Named
@RequestScoped
public class HrTeamsBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject	
	private TeamDAO tDao;
	@Inject
	private ParameterBack parameters;
	
	private List<Team> teams;
	
	private String teamNaam,naamTeamVerantwoordelijke;
	private int teamCode;
	
	
	public List<Team> getTeams() {
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
	
	public String TeamAanpassen(int code){
		String url = "Update_team.xhtml?"+Integer.toString(code);
		return url;
		
	}
	
	
	public String verwijderTeam(int code){
		Team tmpw = tDao.getTeam(code);
		tDao.verwijderTeam(tmpw);;
		return "teamsHr";
		
	}
	
	public String editTeam(int code){
		parameters.setCode(code);
		return "teamHrEdit";
	}
	
	public String zoek(){
		Filter f = new Filter();
		if (!getTeamNaam().trim().equals("")){
			f.voegFilterToe("naam", getTeamNaam());
		}
		if (!getTeamVerantwoordelijke().trim().equals("")){
			f.voegFilterToe("teamverantwoordelijke.naam", getTeamVerantwoordelijke());
		}
		if (getTeamCode() != 0){
			f.voegFilterToe("code", getTeamCode());
		}
		
		teams = tDao.getTeams(f);
		return "teamsHr";
	}
	
}
