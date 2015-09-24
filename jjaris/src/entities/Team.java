package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Team
 *
 */
@Entity
public class Team implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int code;
	private String naam;
	private boolean HR;
	@OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
	private List<Werknemer> teamleden;
	@OneToOne
	private Werknemer teamverantwoordelijke;
	private static final long serialVersionUID = 1L;

	public Team() {
		this.teamleden = new ArrayList<Werknemer>();
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		if (naam != null && !naam.trim().equals("")) {
			this.naam = naam;
		} else {
			throw new IllegalArgumentException("De naam van het team mag niet null of een spatie zijn");
		}
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean getHR() {
		return this.HR;
	}

	public void setHR(boolean HR) {
		this.HR = HR;
	}

	public Werknemer getTeamlid(int personeelsnummer) {
		Werknemer w = null;
		for (Werknemer werknemer : getTeamleden()) {
			if (werknemer.getPersoneelsnummer() == personeelsnummer) {
				w = werknemer;
				break;
			}
		}
		return w;
	}

	public List<Werknemer> getTeamleden() {
		if (teamleden != null) {
			this.teamleden = this.teamleden.stream().distinct().collect(Collectors.toList());
		}

		return this.teamleden;
	}

	public void setTeamleden(List<Werknemer> teamleden) {
		if (teamleden != null) {
			this.teamleden = teamleden;
		}
	}

	public Werknemer getTeamverantwoordelijke() {
		return this.teamverantwoordelijke;
	}

	public void setTeamverantwoordelijke(Werknemer teamverantwoordelijke) {
		if (teamverantwoordelijke != null) {
			this.teamverantwoordelijke = teamverantwoordelijke;
			this.teamverantwoordelijke.setTeam(this);
		} else {
			this.teamverantwoordelijke = null;

		}

	}

	// vanaf hier Stef, nog geen foutafhandeling!!
	/**
	 * is het team een team van HR?
	 *
	 */
	public boolean isHr() {
		return this.HR;
	}

	/**
	 * Lijst van verlofaanvragen tussen 2 datums aanvragen -- te testen
	 * 
	 * @param startdatum
	 * @param einddatum
	 * @return
	 */
	public List<VerlofAanvraag> getVerlofAanvragen(Calendar startdatum, Calendar einddatum) {
		List<VerlofAanvraag> TeamAanVraag = new ArrayList<VerlofAanvraag>();
		List<VerlofAanvraag> persoonlijkeaanvraag = new ArrayList<VerlofAanvraag>();

		for (Werknemer w : getTeamleden()) {
			persoonlijkeaanvraag.addAll(w.getVerlofaanvragen());
		}

		for (VerlofAanvraag verlofAanvraag : persoonlijkeaanvraag) {
			if (verlofAanvraag.getStartdatum().equals(startdatum) && verlofAanvraag.getEinddatum().equals(einddatum)) {
				TeamAanVraag.add(verlofAanvraag);
			}

		}

		return TeamAanVraag;
	}

	/**
	 * Lijst van verlofaanvragen met bepaalde status-- te testen
	 * 
	 * @param toestand
	 * @return
	 */
	public List<VerlofAanvraag> getVerlofAanvragen(Toestand toestand) {
		List<VerlofAanvraag> TeamAanVraag = new ArrayList<VerlofAanvraag>();
		List<VerlofAanvraag> tmpList = new ArrayList<VerlofAanvraag>();

		for (Werknemer w : getTeamleden()) {
			tmpList.addAll(w.getVerlofaanvragen());

		}
		for (VerlofAanvraag verlofAanvraag : tmpList) {
			if (verlofAanvraag.getToestand() == toestand) {
				TeamAanVraag.add(verlofAanvraag);
			}

		}

		return TeamAanVraag;
	}

	/**
	 * Lijst van verlofaanvragen -- te testen
	 * 
	 * @param startdatum
	 * @param einddatum
	 * @param toestand
	 * @param personeelsnummer
	 * @return
	 */
	public List<VerlofAanvraag> getVerlofAanvragen(Calendar startdatum, Calendar einddatum, Toestand toestand, int personeelsnummer) {
		List<VerlofAanvraag> TeamAanVraag = new ArrayList<VerlofAanvraag>();
		List<VerlofAanvraag> tmpList = new ArrayList<VerlofAanvraag>();
		for (Werknemer w : getTeamleden()) {
			if (w.getPersoneelsnummer() == personeelsnummer) {
				tmpList.addAll(w.getVerlofaanvragen());
			}
			for (VerlofAanvraag verlofAanvraag : tmpList) {
				if (verlofAanvraag.getStartdatum().equals(startdatum) && verlofAanvraag.getEinddatum().equals(einddatum)
						&& verlofAanvraag.getToestand() == toestand) {
					TeamAanVraag.add(verlofAanvraag);
				}
			}
		}

		return TeamAanVraag;
	}

	/**
	 * voeg een teamlid toe
	 * 
	 * @param teamlid
	 */
	public void voegTeamlidToe(Werknemer teamlid) {
		teamleden.add(teamlid);

	}

	/**
	 * verwijder een teamlid
	 * 
	 * @param teamlid
	 */
	public void verwijderTeamlid(Werknemer teamlid) {
		getTeamleden().remove(teamlid);
	}

	/**
	 * zit een bepaalde werknemer in dit team?
	 * 
	 * @param werknemer
	 * @return
	 */
	public boolean zitWerknemerInTeam(Werknemer werknemer) {
		if (getTeamleden() != null && !teamleden.isEmpty() && teamleden.contains(werknemer)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		return getCode() == ((Team) obj).getCode();
	}

	@Override
	public int hashCode() {
		return (getCode() + "").hashCode();
	}

	@Override
	public String toString() {
		return String.format("Team %s met nr %s met teamverantwoordelijke %s %s%n", getNaam(), getCode(), getTeamverantwoordelijke().getNaam(),
				getTeamverantwoordelijke().getVoornaam());
	}

}
