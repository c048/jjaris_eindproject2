package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;

import javax.inject.Inject;

import daos.CollectieveSluitingDAO;
import entities.Feestdag;
import entities.VerlofAanvraag;
import entities.Werknemer;

public class ControleerVerlofAanvraag {
	@Inject
	private CollectieveSluitingDAO sluitingDao;
	
	public boolean isGeldigeVerlofPeriode(Werknemer werknemer, Calendar begindatum, Calendar einddatum) throws InputMismatchException {
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
	
	public int getAantalOpTeNemenDagen(VerlofAanvraag verlofAanvraag) {
		return getCollectieveDagen(verlofAanvraag.getStartdatum(), verlofAanvraag.getEinddatum());
	}
	
	public int getCollectieveDagen(Calendar begindatum, Calendar einddatum) {
		return sluitingDao.getCollectieveVerloven(begindatum, einddatum).size();
	}
	/**
	 *Vraagt aantal feestdagen op en telt enkel de feestdagen die niet op zaterdag en zondag vallen INC
	 * @param begindatum
	 * @param einddatum
	 * @return int met aantal dagen
	 */
	public int getAantalFeestdagenOpWeekdag(Calendar begindatum, Calendar einddatum) {	
		List<Feestdag> feestdagen = sluitingDao.getFeestdagen(begindatum, einddatum);
		long aantal = feestdagen.stream().filter(f -> f.isWeekdag()).count();		
		return (int) aantal;
	}
	
	public int getAantalCollectieveDagen(int jaartal) {
		return sluitingDao.getAlleCollectieveVerloven(jaartal).stream().mapToInt(i -> i.getAantalDagen()).sum();
	}
}
