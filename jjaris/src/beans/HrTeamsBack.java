package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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
	private int aanpasCode;
	
	
	public int getAanpasCode() {
		return aanpasCode;
	}
	public void setAanpasCode(int aanpasCode) {
		this.aanpasCode = aanpasCode;
	}
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
	
	
	public String verwijderTeam(){
		//String code =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selCodeVerwijder");
		//System.out.println("in verwijder team van HrTeamsBack");
		Team team = tDao.getTeam(getAanpasCode());
		try {
			tDao.verwijderTeam(team);
		} catch (NullPointerException npe) {
			// TODO: handle exception
		}
		
		return "teamsHr";
		
	}
	
	public String editTeam(){
		//String code =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selCodeEdit");
		//System.out.println("in edit team van HrTeamsBack");
		parameters.setCode(getAanpasCode());
		return "teamHrEdit";
	}
	
	public String zoek(){
		System.out.println("in zoek() van HrTeamsBack");
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
