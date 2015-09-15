package programs;


import java.io.Serializable;
import java.util.GregorianCalendar;





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
public class MainWerknemer implements Serializable{
private static final long serialVersionUID = 1L;
@Inject
private WerknemerDAO testdoa; 
//@Transactional
public void main() {
		Werknemer werknemer1 = new Werknemer();
		Adres adres1 = new Adres();
		adres1.setGemeente("Hoboken");
		adres1.setHuisnummer("1");
		adres1.setPostcode("2660");
		adres1.setStraat("Emiel Vanderveldestraat");
		werknemer1.setAdres(adres1);
		werknemer1.setEmail("irisdelvaux@hotmail.com");
		werknemer1.setGeboortedatum(new GregorianCalendar(76, 7, 21));
		werknemer1.setNaam("Delvaux");
		werknemer1.setVoornaam("Iris");
		werknemer1.setPasswoord("lol24");
		Team team1 = new Team();
		team1.setNaam("JJaris");
		team1.setHR(false);
		team1.setTeamverantwoordelijke(werknemer1);
		werknemer1.setTeam(team1);
		
		System.out.println(werknemer1);
		System.out.println(team1);
		
		testdoa.voegWerknemerToe(werknemer1);//schrijft werknemer in database
		//TeamDAO.voegTeamToe(team1);

	}

}
