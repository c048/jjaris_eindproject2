package entities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class VerlofAanvraag implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Temporal(TemporalType.DATE)
	private GregorianCalendar startdatum;
	@Temporal(TemporalType.DATE)
	private GregorianCalendar einddatum;
	@Temporal(TemporalType.DATE)
	private GregorianCalendar aanvraagdatum;
	@Temporal(TemporalType.DATE)
	private GregorianCalendar reactiedatum;
	private Toestand toestand;
	private String reden;
	private Werknemer werknemer;
	
	
	
	/**
	 * Lege Constructor
	 * 
	 */
	public VerlofAanvraag() {
	}
	
	/**
	 * Constructor met startdatum einddatum 
	 * toestand op INGEDIEND
	 * aanvraagdatum op now
	 */
	public VerlofAanvraag(GregorianCalendar startDatum, GregorianCalendar eindDatum);
	
	//TODO
	/**
	 * enkel weekdagen 
	 * 
	 * NOG GEEN REKENING GEHOUDEN MET FEESTDAGEN OF 
	 * 
	 * @return aantal dagen die deze verlofaanvraag bevat 
	 */
	public int getOpTeNemenDagen(){
		
		
		//TODO
		return 0;	
	}
	/**
	 * Methode om start en einddatum in een keer te setten met geîntegreerde check
	 * 
	 * @param startDatum
	 * @param eindDatum
	 */
	public void setPeriode(GregorianCalendar startDatum, GregorianCalendar eindDatum){
		//TODO
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
	public void annuleren(VerlofAanvraag aanvraag){
		setToestand(Toestand.GEANNULEERD);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public GregorianCalendar getStartdatum() {
		return startdatum;
	}
//	public void setStartdatum(GregorianCalendar startdatum) {
//		this.startdatum = startdatum;
//	}
	public GregorianCalendar getEinddatum() {
		return einddatum;
	}
//	public void setEinddatum(GregorianCalendar einddatum) {
//		this.einddatum = einddatum;
//	}
	public GregorianCalendar getAanvraagdatum() {
		return aanvraagdatum;
	}
	public void setAanvraagdatum(GregorianCalendar aanvraagdatum) {
		this.aanvraagdatum = aanvraagdatum;
	}
	public GregorianCalendar getReactiedatum() {
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
