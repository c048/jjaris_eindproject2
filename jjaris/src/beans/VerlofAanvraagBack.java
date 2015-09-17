package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.VerlofAanvraagDAO;
import entities.VerlofAanvraag;
import entities.Werknemer;


@Named("verlofaanvraag")
@RequestScoped
public class VerlofAanvraagBack implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBack user;
	@Inject
	private VerlofAanvraagDAO verlofaanvraag;
	private Date startdatum;
	private Date einddatum;
	
	public Date getStartdatum() {
		return startdatum;
	}
	public void setStartdatum(Date startdatum) {
		this.startdatum = startdatum;
	}
	public Date getEinddatum() {
		return einddatum;
	}
	public void setEinddatum(Date einddatum) {
		this.einddatum = einddatum;
	}
	/**
	 * Lijst met verlofaanvragen per werknemer
	 * behalve geannulleerde aanvragen
	 * @return
	 */
	public List<VerlofAanvraag> getAanvragen(){
		Werknemer werknemer = user.getIngelogdeWerknemer();
		return verlofaanvraag.getVerlofAanvragenWerknemer(werknemer.getPersoneelsnummer());
		
		
		
	}
	/**
	 * Anulleer een verlofaanvraag	
	 */
	public String annuleren(int id){
		Werknemer werknemer = user.getIngelogdeWerknemer();
		werknemer.annuleerVerlofAanvraag(id);
		verlofaanvraag.updateVerlofAanvraag(verlofaanvraag.getVerlofAanvraag(id));
		return null;
		
		
	}
	/**
	 * Verlof aanvragen
	 */
	public void toevoegen(){
		user.getIngelogdeWerknemer();
		Werknemer werknemer = user.getIngelogdeWerknemer();
		VerlofAanvraag verlof = new VerlofAanvraag(converteerDatum(startdatum), converteerDatum(einddatum), werknemer);
		verlofaanvraag.voegVerlofAanvraagToe(verlof);
	
	}
	
	
	/**
	 * hulpmethode datume date-->gregorian
	 * @param tmp
	 * @return
	 */
	private GregorianCalendar converteerDatum(Date tmp){
		GregorianCalendar newdate =new GregorianCalendar();
		newdate.setTime(tmp);
		return newdate;
		
	}
	
	
	public Date converteerNaarDate(Calendar calendar){
		return calendar.getTime() ;
	}
//	public getEinddatumString(){
//		converteerDatum(einddatum).
//	}
	
	
	
	
}
