package beans;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import utils.DatumBuilder;
import utils.Filter;
import daos.VerlofAanvraagDAO;
import entities.VerlofAanvraag;
import entities.Werknemer;

@Named("verlofMedewerkerBack")
@RequestScoped
public class VerlofMedewerkerBack {
	
	@Inject
	private LoginBack loginBack;
	@Inject
	private VerlofAanvraagDAO verlofAanvragen;
	private int filterStartJaar;
	private int filterStartMaand;
	private int filterStartDag;
	private int filterEindJaar;
	private int filterEindMaand;
	private int filterEindDag;
	private int startJaar;
	private int startMaand;
	private int startDag;
	private int eindJaar;
	private int eindMaand;
	private int eindDag;
	
	public int getFilterStartJaar() {
		return filterStartJaar;
	}

	public void setFilterStartJaar(int filterStartJaar) {
		this.filterStartJaar = filterStartJaar;
	}

	public int getFilterStartMaand() {
		return filterStartMaand;
	}

	public void setFilterStartMaand(int filterStartMaand) {
		this.filterStartMaand = filterStartMaand;
	}

	public int getFilterStartDag() {
		return filterStartDag;
	}

	public void setFilterStartDag(int filterStartDag) {
		this.filterStartDag = filterStartDag;
	}

	public int getFilterEindJaar() {
		return filterEindJaar;
	}

	public void setFilterEindJaar(int filterEindJaar) {
		this.filterEindJaar = filterEindJaar;
	}

	public int getFilterEindMaand() {
		return filterEindMaand;
	}

	public void setFilterEindMaand(int filterEindMaand) {
		this.filterEindMaand = filterEindMaand;
	}

	public int getFilterEindDag() {
		return filterEindDag;
	}

	public void setFilterEindDag(int filterEindDag) {
		this.filterEindDag = filterEindDag;
	}
	
	public int getStartJaar() {
		return startJaar;
	}

	public void setStartJaar(int startJaar) {
		this.startJaar = startJaar;
	}

	public int getStartMaand() {
		return startMaand;
	}

	public void setStartMaand(int startMaand) {
		this.startMaand = startMaand;
	}

	public int getStartDag() {
		return startDag;
	}

	public void setStartDag(int startDag) {
		this.startDag = startDag;
	}

	public int getEindJaar() {
		return eindJaar;
	}

	public void setEindJaar(int eindJaar) {
		this.eindJaar = eindJaar;
	}

	public int getEindMaand() {
		return eindMaand;
	}

	public void setEindMaand(int eindMaand) {
		this.eindMaand = eindMaand;
	}

	public int getEindDag() {
		return eindDag;
	}

	public void setEindDag(int eindDag) {
		this.eindDag = eindDag;
	}
	
	public Date convertDate(Calendar calendar){
		return calendar.getTime() ;
	}

	public List<VerlofAanvraag> searchAanvragen() {
		if(getFilterStartDag() == 0 || getFilterEindDag() == 0) {
			return verlofAanvragen.getVerlofAanvragenWerknemer(loginBack.getIngelogdeWerknemer().getPersoneelsnummer());
		} else {
			Filter tmpFilter = new Filter();
			tmpFilter.voegFilterToe("startdatum", new DatumBuilder(filterStartDag, filterStartMaand, filterStartJaar));
			tmpFilter.voegFilterToe("einddatum", new DatumBuilder(filterEindDag, filterEindMaand, filterEindJaar));
			tmpFilter.voegFilterToe("werknemer.personeelsnummer", loginBack.getIngelogdeWerknemer().getPersoneelsnummer());
			return verlofAanvragen.getVerlofAanvragen(tmpFilter);
		}
	}
	
	public String cancelAanvraag(String id) {
		VerlofAanvraag tmpVerlof = verlofAanvragen.getVerlofAanvraag(Integer.parseInt(id));
		tmpVerlof.annuleren();
		verlofAanvragen.updateVerlofAanvraag(tmpVerlof);
		return null;
	}
	
	public String createAanvraag() {
		try {
			Calendar tmpStart = (new DatumBuilder(startDag, startMaand, startJaar)).buildCalendar();
			Calendar tmpEind = (new DatumBuilder(eindDag, eindMaand, eindJaar)).buildCalendar();
			verlofAanvragen.voegVerlofAanvraagToe(new VerlofAanvraag((GregorianCalendar) tmpStart,(GregorianCalendar) tmpEind, loginBack.getIngelogdeWerknemer()));
		} catch (Exception e) {
			System.out.println("Error on create aanvraag: " + e.getMessage());
		}
		return null;
	}
}
