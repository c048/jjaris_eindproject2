package entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VerlofAanvraag implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private LocalDate startdatum;
	private LocalDate einddatum;
	private LocalDate aanvraagdatum;
	private LocalDate reactiedatum;
	private Toestand toestand;
	private String reden;
	private Werknemer werknemer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getStartdatum() {
		return startdatum;
	}
	public void setStartdatum(LocalDate startdatum) {
		this.startdatum = startdatum;
	}
	public LocalDate getEinddatum() {
		return einddatum;
	}
	public void setEinddatum(LocalDate einddatum) {
		this.einddatum = einddatum;
	}
	public LocalDate getAanvraagdatum() {
		return aanvraagdatum;
	}
	public void setAanvraagdatum(LocalDate aanvraagdatum) {
		this.aanvraagdatum = aanvraagdatum;
	}
	public LocalDate getReactiedatum() {
		return reactiedatum;
	}
	public void setReactiedatum(LocalDate reactiedatum) {
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
