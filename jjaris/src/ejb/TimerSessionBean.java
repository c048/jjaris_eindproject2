package ejb;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Schedule;
import javax.inject.Inject;

import utils.SendMail;
import daos.VerlofAanvraagDAO;
import entities.Toestand;

/**
 * Session Bean implementation class TimerSessionBean
 */
@Singleton
@Startup
public class TimerSessionBean  {

    @Inject
    VerlofAanvraagDAO verlofAanvraag;
    
    public TimerSessionBean() {
    }

    @Schedule(minute = "1", hour = "0")
    public void job() {
    	verlofAanvraag.getVerlofAanvragen().stream().filter(a -> a.getToestand() == Toestand.INGEDIEND && a.isVerlofBegonnen())
    	.forEach(a -> {
			a.setToestand(Toestand.GOEDGEKEURD); 
			verlofAanvraag.updateVerlofAanvraag(a); 
			SendMail.SendEmail(a.getWerknemer().getEmail(), "autoUpdate", "U verlof is goedgekeurd door het computer systeem.");
			SendMail.SendEmail(a.getWerknemer().getTeam().getTeamverantwoordelijke().getEmail(), "autoUpdate", "Het verlof van " + a.getWerknemer().getVolledigeNaam() + " is goedgekeurd door het computer systeem.");
		});
    }

}
