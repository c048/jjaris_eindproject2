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
	public VerlofAanvraag(GregorianCalendar startDatum,
			GregorianCalendar eindDatum, Werknemer werknemer) {
		GregorianCalendar now = new GregorianCalendar();
		setWerknemer(werknemer);
		setPeriode(startDatum, eindDatum);
		setToestand(Toestand.INGEDIEND);
		setAanvraagdatum(now);
		//opgelet de verlofaanvraag zal niet toegevoegd worden aan de werknemer als er een verlofaanvraag in de werknemer zit met dezelde id
		//als de verlofaanvraag nog niet in de database zit, heeft deze id 0
		//id wordt gegenereert door de database
		if (!werknemer.getVerlofaanvragen().contains(this)) {
			werknemer.voegVerlofAanvraagToe(this);
			System.out.println("werknemer.voegVerlofAanvraagToe(this):"+this);
		}

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

			Calendar datum = new GregorianCalendar();
			datum.setTime(startdatum.getTime());

			while (einddatum.compareTo(datum)>=0) {
				int weekdag = datum.get(Calendar.DAY_OF_WEEK);
				if (weekdag != Calendar.SATURDAY && weekdag != Calendar.SUNDAY) {
					System.out.println(weekdag);
					weekdagTeller++;
				}
				datum.add(Calendar.DAY_OF_YEAR, 1);
			}

//			if (einddatum.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
//					&& einddatum.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
//				weekdagTeller++;
//			}
			// ControleerVerlofAanvraag aanvraag = new
			// ControleerVerlofAanvraag();
			// int aantal = aanvraag.getAantalFeestdagenOpWeekdag(startdatum,
			// einddatum);
			// weekdagTeller -= aantal;
		}

		return weekdagTeller;
	}

	/**
	 * Methode om start en einddatum in een keer te setten met geîntegreerde
	 * check
	 * 
	 * @param startDatum
	 * @param eindDatum
	 */
	public void setPeriode(GregorianCalendar startDatum,
			GregorianCalendar eindDatum) {
		if (geldigVerlof(startDatum, eindDatum)) {
			this.startdatum = startDatum;
			this.einddatum = eindDatum;
		}
	}

	/**
	 * hulpmethode Check of een verlof kan aangevraagd worden -einddatum na
	 * begindatum -Startdatum voor einddatum +14 dagen
	 * 
	 * @return true als een verlof kan aangevraagd worden
	 * 
	 */
	public boolean geldigVerlof(GregorianCalendar startDatum,
			GregorianCalendar eindDatum) {

		if (startDatum.before(eindDatum) && isStartdatumGeldig(startDatum)
				&& !isOverlappend(startDatum, eindDatum)) {
			return true;
		}
		return false;
	}

	public boolean isStartdatumGeldig(GregorianCalendar startDatum) {
		GregorianCalendar minStartdatum = new GregorianCalendar();
		minStartdatum.add(Calendar.DAY_OF_MONTH, 14);
		if (minStartdatum.before(startDatum)) {
			return true;
		} else
			return false;
	}

	/**
	 * hulpfunctie voor isOverlappend(GregorianCalendar startDatum,
	 * GregorianCalendar eindDatum)
	 * 
	 * @param startDatum
	 * @param eindDatum
	 * @return
	 */
	public boolean verlofOverlapt(GregorianCalendar startDatum,
			GregorianCalendar eindDatum) {
		if (startDatum.before(eindDatum)) {
			// System.out.println("startdatum ligt voor einddatum van te controleren periode");
			if (getStartdatum() == null && getEinddatum() == null) {
				return false;
			}
			if (eindDatum.before(getStartdatum())
					|| getEinddatum().before(startDatum)) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new InputMismatchException(
					"startDatum moet voor eindDatum liggen");
		}
	}

	public boolean isOverlappend(GregorianCalendar startDatum,
			GregorianCalendar eindDatum) {
		
		List<VerlofAanvraag> verlofaanvragen = getVerlofAanvragenWerknemer(
				startDatum, eindDatum);

		if (verlofaanvragen != null && !verlofaanvragen.isEmpty()) {
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				System.out.println(verlofAanvraag);
				if (verlofAanvraag.verlofOverlapt(startDatum, eindDatum)) {
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
		this.werknemer = werknemer;

	}

	@Override
	public String toString() {
		return String
				.format("Verlofaanvraag van %s tot %s van %s",
						getStartdatumAsString(), getEinddatumAsString(),
						getWerknemer() == null ? "" : getWerknemer()
								.getVolledigeNaam());
	}

	public String getStartdatumAsString() {
		return String.format("%s/%s/%s",
				getStartdatum().get(Calendar.DAY_OF_MONTH), getStartdatum()
						.get(Calendar.MONTH) + 1,
				getStartdatum().get(Calendar.YEAR));
	}

	public String getEinddatumAsString() {
		return String.format("%s/%s/%s",
				getEinddatum().get(Calendar.DAY_OF_MONTH),
				getEinddatum().get(Calendar.MONTH) + 1,
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
	public List<VerlofAanvraag> getVerlofAanvragenWerknemer(
			GregorianCalendar startDatum, GregorianCalendar eindDatum) {
		if (getWerknemer() != null) {
			List<VerlofAanvraag> verlofaanvragen = werknemer
					.getAlleVerlofAanvragen(startDatum, eindDatum,
							Toestand.INGEDIEND);
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				System.out.println(verlofAanvraag);
			}
			List<VerlofAanvraag> verlofaanvragenGoedgekeurd = werknemer
					.getAlleVerlofAanvragen(startDatum, eindDatum,
							Toestand.GOEDGEKEURD);
			verlofaanvragen.addAll(verlofaanvragenGoedgekeurd);
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				System.out.println(verlofAanvraag);
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
