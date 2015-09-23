package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import utils.ControleerVerlofAanvraag;

@Entity
public class VerlofAanvraag implements Serializable {
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
	 * Lege Constructor
	 */
	public VerlofAanvraag() {
		
	}

	/**
	 * Constructor met startdatum einddatum toestand op INGEDIEND aanvraagdatum
	 * op now
	 * 
	 * @param startDatum
	 * @param eindDatum
	 * @param Werknemer
	 */
	public VerlofAanvraag(GregorianCalendar startDatum, GregorianCalendar eindDatum, Werknemer werknemer) {
		GregorianCalendar now = new GregorianCalendar();
		setWerknemer(werknemer);
		setPeriode(startDatum, eindDatum);
		setToestand(Toestand.INGEDIEND);
		setAanvraagdatum(now);
		
		if (!this.werknemer.getVerlofaanvragen().contains(this)) {
			getWerknemer().voegVerlofAanvraagToe(this);
			System.out.println("werknemer.voegVerlofAanvraagToe(this):" + this);
		}
		// opgelet de verlofaanvraag zal niet toegevoegd worden aan de werknemer
		// als er een verlofaanvraag in de werknemer zit met dezelde id
		// als de verlofaanvraag nog niet in de database zit, heeft deze id 0
		// id wordt gegenereert door de database
		
	}

	/**
	 * Bereken Het aantal weekdagen tussen begin en einddatum zonder feestdagen
	 * nota iris: Er wordt nog geen rekening gehouden met feestdagen - staat in
	 * commentaar
	 * 
	 * @return int
	 */
	public int getPeriode() {
		int weekdagTeller = 0;
		if (getStartdatum() != null && getEinddatum() != null) {
			weekdagTeller = telWeekdagen(getStartdatum(), getEinddatum());
			// ControleerVerlofAanvraag aanvraag = new
			// ControleerVerlofAanvraag();
			// int aantal = aanvraag.getAantalFeestdagenOpWeekdag(startdatum,
			// einddatum);
			// weekdagTeller -= aantal;
		}
		return weekdagTeller;
	}

	/**
	 * Berekent het aantal op te nemen verlofdagen (weekdagen) in het jaar van
	 * de startdatum
	 * 
	 * @return int
	 */
	public int getPeriodeInJaarStartdatum() {
		int weekdagTeller = 0;
		if (getStartdatum() != null && getEinddatum() != null) {
			Calendar einddatumPeriode;
			if (isStartEnEinddatumInZelfdeJaar()) {
				einddatumPeriode = getEinddatum();
			} else {
				einddatumPeriode = new GregorianCalendar(geefJaarStartdatum(), 11, 31);
				// 31 december van het jaar waarin de startdatum ligt
			}
			weekdagTeller = telWeekdagen(getStartdatum(), einddatumPeriode);
		}
		return weekdagTeller;
	}

	private int telWeekdagen(Calendar begindatumPeriode, Calendar einddatumPeriode) {
		int weekdagTeller = 0;
		if (begindatumPeriode != null && einddatumPeriode != null) {
			Calendar datum = new GregorianCalendar();
			datum.setTime(begindatumPeriode.getTime());
			while (einddatumPeriode.compareTo(datum) >= 0) {
				int weekdag = datum.get(Calendar.DAY_OF_WEEK);
				if (weekdag != Calendar.SATURDAY && weekdag != Calendar.SUNDAY) {
					//System.out.println(weekdag);
					weekdagTeller++;
				}
				datum.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		return weekdagTeller;
	}

	public boolean isVerlofBegonnen() {
		if (getStartdatum() != null) {
			Calendar now = new GregorianCalendar();
			return now.compareTo(getStartdatum()) >= 0;
		} else {
			throw new NullPointerException("VerlofAanvraag.isStartdatumVandaag : startdatum is null");
		}
	}

	/**
	 * Berekent het aantal op te nemen verlofdagen (weekdagen) in het jaar van
	 * de einddatum
	 * 
	 * @return int
	 */
	public int getPeriodeInJaarEinddatum() {
		int weekdagTeller = 0;
		if (getStartdatum() != null && getEinddatum() != null) {
			Calendar begindatumPeriode;
			if (isStartEnEinddatumInZelfdeJaar()) {
				begindatumPeriode = getStartdatum();
			} else {
				begindatumPeriode = new GregorianCalendar(geefJaarEinddatum(), 0, 1);
				// 1 januari van het jaar waarin de einddatum valt
			}

			weekdagTeller = telWeekdagen(begindatumPeriode, getEinddatum());

		}

		return weekdagTeller;
	}

	public int geefJaarStartdatum() {
		if (getStartdatum() != null) {
			return getStartdatum().get(Calendar.YEAR);
		} else {
			throw new NullPointerException("VerlofAanvraag.geefJaarStartdatum :Verlofaanvraag startdatum is null");
		}
	}

	public int geefJaarEinddatum() {
		if (getEinddatum() != null) {
			return getEinddatum().get(Calendar.YEAR);
		} else
			throw new NullPointerException("VerlofAanvraag.geefJaarEinddatum :Verlofaanvraag einddatum is null");
	}

	public boolean isStartEnEinddatumInZelfdeJaar() {
		return geefJaarEinddatum() == geefJaarStartdatum();
	}

	/**
	 * Methode om start en einddatum in een keer te setten met geîntegreerde
	 * check
	 * 
	 * @param startDatum
	 * @param eindDatum
	 */
	public void setPeriode(GregorianCalendar startDatum, GregorianCalendar eindDatum) throws IllegalArgumentException {
		if (geldigVerlof(startDatum, eindDatum)) {
			this.startdatum = startDatum;
			this.einddatum = eindDatum;
		} else {
			throw new IllegalArgumentException(
					"Ongeldige Verlofperiode: startdatum moet voor einddatum liggen, startdatum moet minstens 14 dagen in de toekomst liggen"
							+ " en het verlof mag niet overlappen met een bestaand verlof");
		}
	}

	/**
	 * hulpmethode Check of een verlof kan aangevraagd worden -einddatum na
	 * begindatum -Startdatum voor einddatum +14 dagen
	 * 
	 * @return true als een verlof kan aangevraagd worden
	 * 
	 */
	public boolean geldigVerlof(GregorianCalendar startDatum, GregorianCalendar eindDatum) {
		if (startDatum == null || eindDatum == null) {
			return false;
		}
		if (startDatum.compareTo(eindDatum) <= 0 && isStartdatumGeldig(startDatum) && !isOverlappend(startDatum, eindDatum)) {
			return true;
		}
		return false;
	}

	public boolean isStartdatumGeldig(GregorianCalendar startDatum) {
		GregorianCalendar minStartdatum = new GregorianCalendar();
		minStartdatum.add(Calendar.DAY_OF_MONTH, 14);
		if (minStartdatum.before(startDatum)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * hulpfunctie voor isOverlappend(GregorianCalendar startDatum,
	 * GregorianCalendar eindDatum)
	 * 
	 * @param startDatum
	 * @param eindDatum
	 * @return
	 */
	public boolean verlofOverlapt(GregorianCalendar startDatum, GregorianCalendar eindDatum) {
		if (startDatum.compareTo(eindDatum) <= 0) {
			// System.out.println("startdatum ligt voor einddatum van te controleren periode");
			if (getStartdatum() == null && getEinddatum() == null) {
				return false;
			}
			if (eindDatum.before(getStartdatum()) || getEinddatum().before(startDatum)) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new InputMismatchException("startDatum moet voor eindDatum liggen");
		}
	}

	public boolean isOverlappend(GregorianCalendar startDatum, GregorianCalendar eindDatum) {

		List<VerlofAanvraag> verlofaanvragen = getVerlofAanvragenWerknemer(startDatum, eindDatum);

		if (verlofaanvragen != null && !verlofaanvragen.isEmpty()) {
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				System.out.println(verlofAanvraag);
				if (verlofAanvraag.verlofOverlapt(startDatum, eindDatum) && verlofAanvraag.getId() != getId()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Verlofaanvraag wordt goedgekeurd door teamleader
	 */
	public void goedkeuren() {
		setToestand(Toestand.GOEDGEKEURD);

	}

	/**
	 * Verlofaanvraag wordt afgekeurd door teamleader
	 */
	public void afkeuren(String reden) {
		setToestand(Toestand.AFGEKEURD);
		setReden(reden);
	}

	/**
	 * Verlofaanvraag wordt geannuleerd
	 */
	public void annuleren() {
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

	// public void setStartdatum(GregorianCalendar startdatum) {
	// this.startdatum = startdatum;
	// }
	public Calendar getEinddatum() {
		return einddatum;
	}

	// public void setEinddatum(GregorianCalendar einddatum) {
	// this.einddatum = einddatum;
	// }
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
		if (werknemer != null) {
			this.werknemer = werknemer;
			
		} else {
			throw new NullPointerException("VerlofAanvraag.setWerknemer : parameter werknemer is null");
		}
	}

	@Override
	public String toString() {
		return String.format("Verlofaanvraag van %s tot %s van %s", getStartdatumAsString(), getEinddatumAsString(), getWerknemer() == null ? ""
				: getWerknemer().getVolledigeNaam());
	}

	public String getStartdatumAsString() {
		return String.format("%s/%s/%s", getStartdatum().get(Calendar.DAY_OF_MONTH), getStartdatum().get(Calendar.MONTH) + 1,
				getStartdatum().get(Calendar.YEAR));
	}

	public String getEinddatumAsString() {
		return String.format("%s/%s/%s", getEinddatum().get(Calendar.DAY_OF_MONTH), getEinddatum().get(Calendar.MONTH) + 1,
				getEinddatum().get(Calendar.YEAR));
	}

	/**
	 * Geeft alle Ingediende en goedgekeurde verlofaanvragen tussen start en
	 * einddatum van de werknemer van deze verlofaanvraag
	 * 
	 * @param startDatum
	 * @param eindDatum
	 * @return
	 */
	public List<VerlofAanvraag> getVerlofAanvragenWerknemer(GregorianCalendar startDatum, GregorianCalendar eindDatum) {
		if (getWerknemer() != null) {
			List<VerlofAanvraag> verlofaanvragen = werknemer.getAlleVerlofAanvragen(startDatum, eindDatum, Toestand.INGEDIEND);
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				System.out.println("******* GetVerlofAanvragenWerknemer: " + verlofAanvraag);
			}
			List<VerlofAanvraag> verlofaanvragenGoedgekeurd = werknemer.getAlleVerlofAanvragen(startDatum, eindDatum, Toestand.GOEDGEKEURD);
			verlofaanvragen.addAll(verlofaanvragenGoedgekeurd);
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				System.out.println("******* GetVerlofAanvragenWerknemer: " + verlofAanvraag);
			}
			return verlofaanvragen;
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerlofAanvraag other = (VerlofAanvraag) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
