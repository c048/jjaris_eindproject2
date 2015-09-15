package untils;

import java.util.Calendar;
import java.util.InputMismatchException;

import daos.CollectieveSluitingDAO;
import entities.VerlofAanvraag;
import entities.Werknemer;

public class ControleerVerlofAanvraag {

	public static boolean isGeldigeVerlofPeriode(Werknemer werknemer, Calendar begindatum, Calendar einddatum) throws InputMismatchException {
		if(begindatum.compareTo(einddatum) >= 0) {
			throw new InputMismatchException("Startdatum ligt na einddatum");
		}
//		if(werknemer.getAantalBeschikBareVerlofDagen(jaartal)getCollectieveDagen(begindatum, einddatum) {
//			return true;
//		}
		return false;
	}
	
	public static int getAantalOpTeNemenDagen(VerlofAanvraag verlofAanvraag) {
		return getCollectieveDagen(verlofAanvraag.getStartdatum(), verlofAanvraag.getEinddatum());
	}
	
	public static int getCollectieveDagen(Calendar begindatum, Calendar einddatum) {
		return CollectieveSluitingDAO.getCollectieveVerloven(begindatum, einddatum).size();
	}
	
	public static int getAantalFeestdagenOpWeekdag(Calendar begindatum, Calendar einddatum) {
		return CollectieveSluitingDAO.getFeestdagen(begindatum, einddatum).size();
	}
	
	public static int getAantalCollectieveDagen(int jaartal) {
		return CollectieveSluitingDAO.getAlleCollectieveVerloven(jaartal).stream().mapToInt(i -> i.getAantalDagen()).sum();
	}
}
