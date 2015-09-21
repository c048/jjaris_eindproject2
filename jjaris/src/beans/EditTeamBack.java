package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotEmpty;

import daos.TeamDAO;
import entities.Team;
import entities.Werknemer;

@Named
@RequestScoped
public class EditTeamBack implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String naam;
	private int personeelsnrVerantwoordelijke;
	@Inject
	private ParameterBack parameterback;
	@Inject
	private TeamDAO tDao;
	private Team team;

	@PostConstruct
	public void init() {
		team = tDao.getTeam(parameterback.getCode());
		if (team.getTeamverantwoordelijke() != null) {
			personeelsnrVerantwoordelijke = team.getTeamverantwoordelijke().getPersoneelsnummer();
		}
		naam = team.getNaam();
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public int getPersoneelsnrVerantwoordelijke() {
		return personeelsnrVerantwoordelijke;
	}

	public void setPersoneelsnrVerantwoordelijke(int personeelsnrVerantwoordelijke) {
		this.personeelsnrVerantwoordelijke = personeelsnrVerantwoordelijke;
	}

	public Team getTeam() {
		return team;

	}

	public List<Werknemer> getTeamleden() {
		return getTeam().getTeamleden();
	}

	private void setTeamverantwoordelijke() {
		if(getPersoneelsnrVerantwoordelijke() != 0){
		team.setTeamverantwoordelijke(getTeam().getTeamlid(getPersoneelsnrVerantwoordelijke()));
		}else{
			team.setTeamverantwoordelijke(null);//geen teamverantwoordelijke geselecteerd
		}
	}

	public String updateTeam() {
		setTeamverantwoordelijke();
		getTeam().setNaam(getNaam());
		tDao.updateTeam(getTeam());
		return "teamsHr";
	}

}
