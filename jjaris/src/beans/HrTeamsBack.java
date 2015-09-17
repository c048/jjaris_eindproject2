package beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.TeamDAO;
import daos.WerknemerDAO;
import entities.Team;
import entities.Werknemer;

@Named("HrManageTeam")
@RequestScoped
public class HrTeamsBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject	
	private TeamDAO Dao;
	private List<Team> teams;
	private Team team;
	private String Teamnaam,Teamcode,TeamVerantwoordelijke;
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public String getTeamnaam() {
		return Teamnaam;
	}
	public void setTeamnaam(String teamnaam) {
		Teamnaam = teamnaam;
	}
	public String getTeamcode() {
		return Teamcode;
	}
	public void setTeamcode(String teamcode) {
		Teamcode = teamcode;
	}
	public String getTeamVerantwoordelijke() {
		return TeamVerantwoordelijke;
	}
	public void setTeamVerantwoordelijke(String teamVerantwoordelijke) {
		TeamVerantwoordelijke = teamVerantwoordelijke;
	}
	
	public String TeamAanpassen(int code){
		String url = "Update_team.xhtml?"+Integer.toString(code);
		return url;
		
	}
	
	
	public String verwijderTeam(int code){
		Team tmpw = Dao.getTeam(code);
		Dao.verwijderTeam(tmpw);
		return "hr.xhtml";
		
	}
	public String EditTeam(int code){
		String url = "Update_team.xhtml?code="+Integer.toString(code);
		return url;
	}
	
}
