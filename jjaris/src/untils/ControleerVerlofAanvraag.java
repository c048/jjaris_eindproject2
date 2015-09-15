package untils;

import java.util.Calendar;

import entities.VerlofAanvraag;
import entities.Werknemer;

public class ControleerVerlofAanvraag {

	public static boolean isGeldigeVerlofPeriode(Werknemer werknemer, Calendar begindatum, Calendar einddatum) {
		return false;
	}
	
	public static int getAantalOpTeNemenDagen(VerlofAanvraag verlofAanvraag) {
		return 0;
	}
	
	public static int getCollectieveDagen(Calendar begindatum, Calendar einddatum) {
		return 0;
	}
	
	public static int getAantalFeestdagenOpWeekdag(Calendar begindatum, Calendar einddatum) {
		return 0;
	}
	
	public static int getAantalCollectieveDagen(int jaartal) {
		return 0;
	}
}
