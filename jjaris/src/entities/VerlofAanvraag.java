package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entities.*;
import daos.CollectieveSluitingDAO;

@Entity
public class VerlofAanvraag implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@Temporal(TemporalType.DATE)
	private Calendar startdatum;
	@Temporal(TemporalType.DATE)
	private Calendar einddatum;
	@Temporal(TemporalType.DATE)
	private Calendar aanvraagdatum;
	@Temporal(TemporalType.DATE)
	private Calendar reactiedatum;
	private Toestand toestand;
	private String reden;
	@ManyToOne
	private Werknemer werknemer;
		
	/**
	 *  Lege Constructor
	 */
	public VerlofAanvraag() {
	}
	
	/**
	 * Constructor met startdatum einddatum 
	 * toestand op INGEDIEND
	 * aanvraagdatum op now
	 */
	public VerlofAanvraag(GregorianCalendar startDatum, GregorianCalendar eindDatum) {
		GregorianCalendar now = new GregorianCalendar();
		setPeriode(startDatum, eindDatum);
		setToestand(Toestand.INGEDIEND);
		setAanvraagdatum(now);		
	}

		/**
		 * Bereken Het aantal dagen tussen begin en einddatum zonder feestdagen
		 * @return
		 */
		public int getPeriode(){
			Calendar datum = new GregorianCalendar();
			datum.setTime(startdatum.getTime());
			int weekdagTeller = 0;
			
			int teller = 0;
			
			while(einddatum.after(datum)){
				int weekdag = datum.get(Calendar.DAY_OF_WEEK);
				if(weekdag != Calendar.SATURDAY && weekdag != Calendar.SUNDAY ){
					System.out.println(weekdag);
					weekdagTeller++;
				}
				datum.add(Calendar.DAY_OF_YEAR,1);
			}
			
			if(einddatum.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && einddatum.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY ){
				weekdagTeller++;
			}
			List<Feestdag> feestdagen = CollectieveSluitingDAO.getFeestdagen(startdatum, einddatum);
			long aantal = feestdagen.stream().filter(f -> f.isWeekdag()).count();
			
			//return weekdagTeller;
			return  (weekdagTeller - (int)aantal);
		}
			
	/**
	 * Methode om start en einddatum in een keer te setten met geîntegreerde check
	 * 
	 * @param startDatum
	 * @param eindDatum
	 */
	public void setPeriode(GregorianCalendar startDatum, GregorianCalendar eindDatum){
		if(geldigVerlof(startDatum, eindDatum)){
			this.startdatum = startDatum;
			this.einddatum = eindDatum;
		}	
	}
	
	/**
	 * hulpmethode
	 * Check of een verlof kan aangevraagd worden
	 * -einddatum na begindatum
	 * -Startdatum voor einddatum +14 dagen
	 * @return true als een verlof kan aangevraagd worden
	 * 
	 */
	public boolean geldigVerlof(GregorianCalendar startDatum, GregorianCalendar eindDatum){
		GregorianCalendar now = new GregorianCalendar();
		now.add(Calendar.DAY_OF_MONTH, 14);
		if(startDatum.before(eindDatum) && startDatum.before(now)){	
			return true;
		}	
		return false;		
	}	
	/**
	 * Verlofaanvraag wordt goedgekeurd door teamleader
	 */
	public void goedkeuren(){
		setToestand(Toestand.GOEDGEKEURD);
		
		}	
	/**
	 *Verlofaanvraag wordt afgekeurd door teamleader 
	 */
	public void afkeuren(String reden){
		setToestand(Toestand.AFGEKEURD);
		setReden(reden);
	}
	/**
	 * Verlofaanvraag wordt geannuleerd 
	 */
	public void annuleren(){
		setToestand(Toestand.GEANNULEERD);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Calendar getStartdatum() {
		return startdatum;
	}
//	public void setStartdatum(GregorianCalendar startdatum) {
//		this.startdatum = startdatum;
//	}
	public Calendar getEinddatum() {
		return einddatum;
	}
//	public void setEinddatum(GregorianCalendar einddatum) {
//		this.einddatum = einddatum;
//	}
	public Calendar getAanvraagdatum() {
		return aanvraagdatum;
	}
	public void setAanvraagdatum(GregorianCalendar aanvraagdatum) {
		this.aanvraagdatum = aanvraagdatum;
	}
	public Calendar getReactiedatum() {
		return reactiedatum;
	}
	public void setReactiedatum(GregorianCalendar reactiedatum) {
		this.reactiedatum = reactiedatum;
	}
	public Toestand getToestand() {
		return toestand;
	}
	public void setToestand(Toestand toestand) {
		this.toestand = toestand;
	}
	public String getReden() {
		return reden;
	}
	public void setReden(String reden) {
		this.reden = reden;
	}
	public Werknemer getWerknemer() {
		return werknemer;
	}
	public void setWerknemer(Werknemer werknemer) {
		this.werknemer = werknemer;
	}
	
}
