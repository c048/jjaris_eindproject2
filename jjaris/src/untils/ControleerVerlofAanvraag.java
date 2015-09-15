package untils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;

import daos.CollectieveSluitingDAO;
import entities.VerlofAanvraag;
import entities.Werknemer;

public class ControleerVerlofAanvraag {

	public static boolean isGeldigeVerlofPeriode(Werknemer werknemer, Calendar begindatum, Calendar einddatum) throws InputMismatchException {
		if(begindatum.compareTo(einddatum) >= 0) {
			throw new InputMismatchException("Startdatum ligt na einddatum");
		}
		if(begindatum.get(Calendar.YEAR) == einddatum.get(Calendar.YEAR)) {
			begindatum.get(Calendar.YEAR);
			if(werknemer.getAantalBeschikBareVerlofDagen(begindatum.get(Calendar.YEAR)) == getCollectieveDagen(begindatum, einddatum)) {
				return true;
			}
		} else {
			Calendar tmpYearCal = new GregorianCalendar(begindatum.get(Calendar.YEAR), 12, 31);
			if(werknemer.getAantalBeschikBareVerlofDagen(begindatum.get(Calendar.YEAR)) == getCollectieveDagen(begindatum, tmpYearCal)) {
				tmpYearCal.set(einddatum.get(Calendar.YEAR), 12, 31);
				if(werknemer.getAantalBeschikBareVerlofDagen(begindatum.get(Calendar.YEAR)) == getCollectieveDagen(begindatum, tmpYearCal)) {
					return true;
				}
			}
		}
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
