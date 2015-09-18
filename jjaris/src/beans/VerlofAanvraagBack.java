package beans;

import java.io.Serializable;
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


@Named("verlofaanvraag")
@RequestScoped
public class VerlofAanvraagBack implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBack user;
	@Inject
	private VerlofAanvraagDAO verlofaanvraagDAO;
	private Date startdatum;
	private Date einddatum;
	private int startJaar;
	private int startMaand;
	private int startDag;
	private int eindJaar;
	private int eindMaand;
	private int eindDag;
	
	
	
	private Filter filter= new Filter();
	private int zoekToestand ;

	/**
	 * Lijst met verlofaanvragen per werknemer gefilterd standaard lege filters
	 * behalve geannulleerde aanvragen
	 * @return
	 */
	public List<VerlofAanvraag> getAanvragen(){
		filter.voegFilterToe("werknemer.personeelsnummer",user.getIngelogdeWerknemer().getPersoneelsnummer() );
		if(startdatum!=null){filter.voegFilterToe("startdatum", converteerDatum(startdatum));}
		if(einddatum!=null) {filter.voegFilterToe("einddatum", converteerDatum(einddatum));}
		else{System.out.println("***********************************geen filter");}
		if(zoekToestand<0 && zoekToestand>3){filter.voegFilterToe("toestand", zoekToestand);}
		else{System.out.println("***********************************geen filter");}
		return verlofaanvraagDAO.getVerlofAanvragen(filter);
	}
	
	public String zoeken(){
		setStartdatum();
		setEinddatum();
		getAanvragen();
		return null;
		
	}
	
	/**
	 * Annuleer een verlofaanvraag	
	 */
	public String annuleren(int id){
		Werknemer werknemer = user.getIngelogdeWerknemer();
		werknemer.annuleerVerlofAanvraag(id);
		verlofaanvraagDAO.updateVerlofAanvraag(verlofaanvraagDAO.getVerlofAanvraag(id));
		return null;				
	}
	/**
	 * Verlof aanvragen
	 */
	public void toevoegen(){
		Werknemer werknemer = user.getIngelogdeWerknemer();
		setStartdatum();
		setEinddatum();
		VerlofAanvraag verlof = new VerlofAanvraag(converteerDatum(startdatum), converteerDatum(einddatum), werknemer);
		verlofaanvraagDAO.voegVerlofAanvraagToe(verlof);	
	}		
	/**
	 * hulpmethode datum date-->gregorian
	 * @param tmp
	 * @return
	 */
	private GregorianCalendar converteerDatum(Date tmp){
		GregorianCalendar newdate =new GregorianCalendar();
		newdate.setTime(tmp);
		return newdate;		
	}

	
	public Date getStartdatum() {
		return startdatum;
	}
	/**
	 * Zet datum aan de hand van de apparte velden met datumbuilder
	 * @param startdatum
	 */
	public void setStartdatum() {
		DatumBuilder tmp = new DatumBuilder(startDag, startMaand, startJaar);
		this.startdatum = tmp.buildDate();
	}
	public Date getEinddatum() {
		return einddatum;
	}
	/**
	 * Zet datum aan de hand van de apparte veldenmet datumbuilder
	 * @param einddatum
	 */
	public void setEinddatum() {
		DatumBuilder tmp = new DatumBuilder(eindDag, eindMaand, eindJaar);
		this.einddatum = tmp.buildDate();
	}
	
	public Date converteerNaarDate(Calendar calendar){
		return calendar.getTime() ;
	}
//	public getEinddatumString(){
//		converteerDatum(einddatum).
//	}
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
	public int getZoekToestand() {
		return zoekToestand;
	}
	public void setZoekToestand(int zoekToestand) {
		this.zoekToestand = zoekToestand;
	}
	
	
}
