package programs;

import java.io.Serializable;
import java.util.GregorianCalendar;

import java.util.List;

//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

//import javax.transaction.Transactional;

import daos.TeamDAO;
//import daos.TeamDAO;
import daos.WerknemerDAO;
import entities.Adres;
import entities.Team;
import entities.Werknemer;

@Named
@SessionScoped
public class MainWerknemer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private WerknemerDAO testdoa;
	@Inject
	private TeamDAO teamdoa;

	public void main() {
		// Werknemer werknemer1 = new Werknemer();
		// Adres adres1 = new Adres();
		// adres1.setGemeente("Hoboken");
		// adres1.setHuisnummer("11");
		// adres1.setPostcode("2660");
		// adres1.setStraat("Vinkeveldeplein");
		// werknemer1.setAdres(adres1);
		// werknemer1.setEmail("johandelvaux@hotmail.com");
		// werknemer1.setGeboortedatum(new GregorianCalendar(76, 7, 21));
		// werknemer1.setNaam("Delvaux");
		// werknemer1.setVoornaam("Johan");
		// werknemer1.setPasswoord("lol26");
		// Team team1 = teamdoa.getTeam(1);
		// System.out.println(team1);
		// team1.setNaam("JJaris");
		// team1.setHR(false);
		// werknemer1.setTeam(team1);
		// teamdoa.voegTeamToe(team1);
		// team1.setTeamverantwoordelijke(werknemer1);
		// testdoa.voegWerknemerToe(werknemer1);//schrijft werknemer in database
		// teamdoa.updateTeam(team1);
//		List<Team> teams = teamdoa.getTeams();
//		for (Team team : teams) {
//			System.out.println(team);
//		}
		
		List<Werknemer> werknemers = testdoa.getWerknemers("vaux", "");
		for (Werknemer werknemer : werknemers) {
			System.out.println(werknemer);
		}
		
		List<Team> teams = teamdoa.getTeams("ar", "el");
		for (Team team : teams) {
			System.out.println(team);
		}

	}

}
