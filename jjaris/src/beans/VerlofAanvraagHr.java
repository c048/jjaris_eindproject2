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
import entities.Toestand;
import entities.VerlofAanvraag;
import entities.Werknemer;


@Named("verlof")
@RequestScoped
public class VerlofAanvraagHr implements Serializable{
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
	private Toestand toestand;
	private List<VerlofAanvraag> alleVerloven;
	
	
	
	
	
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
	public Toestand getToestand() {
		return toestand;
	}
	public void setToestand(Toestand toestand) {
		this.toestand = toestand;
	}
	public List<VerlofAanvraag> getAlleVerloven() {
		return alleVerloven;
	}
	public void setAlleVerloven(List<VerlofAanvraag> alleVerloven) {
		this.alleVerloven = alleVerloven;
	}
	
	
	
	
}