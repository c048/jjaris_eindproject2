package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
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
	@OneToMany(mappedBy = "team")
	private List<Werknemer> teamleden;
	@OneToOne
	private Werknemer teamverantwoordelijke;
	private static final long serialVersionUID = 1L;

	public Team() {
	}
	
	//toegevoegd door iris - kreeg nullpointerex op voegTeamlidToe
	@PostConstruct
	public void init(){
		teamleden = new ArrayList<Werknemer>();
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
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

	public List<Werknemer> getTeamleden() {
		return this.teamleden;
	}

	public void setTeamleden(List<Werknemer> teamleden) {
		this.teamleden = teamleden;
	}

	public Werknemer getTeamverantwoordelijke() {
		return this.teamverantwoordelijke;
	}

	public void setTeamverantwoordelijke(Werknemer teamverantwoordelijke) {
		this.teamverantwoordelijke = teamverantwoordelijke;
		teamverantwoordelijke.setTeam(this);
		//voegTeamlidToe(teamverantwoordelijke);
	}

	// vanaf hier Stef, nog geen foutafhandeling!!
	/**
	 * is het team een team van HR?
	 *
	 */
	public boolean isHr() {
		return this.getHR();
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
		
		for (Werknemer w : teamleden) {
			persoonlijkeaanvraag.addAll(w.getVerlofaanvragen());
		}
		
		for (VerlofAanvraag verlofAanvraag : persoonlijkeaanvraag) {
			if (verlofAanvraag.getStartdatum().equals(startdatum)
					&& verlofAanvraag.getEinddatum().equals(einddatum)) {
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
		
		for (Werknemer w : teamleden) {
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
	public List<VerlofAanvraag> getVerlofAanvragen(Calendar startdatum,
			Calendar einddatum, Toestand toestand, int personeelsnummer) {
		List<VerlofAanvraag> TeamAanVraag = new ArrayList<VerlofAanvraag>();
		List<VerlofAanvraag> tmpList = new ArrayList<VerlofAanvraag>();
		for (Werknemer w : teamleden) {
			if (w.getPersoneelsnummer() == personeelsnummer) {
				tmpList.addAll(w.getVerlofaanvragen());
			}
			for (VerlofAanvraag verlofAanvraag : tmpList) {
				if (verlofAanvraag.getStartdatum().equals(startdatum)
						&& verlofAanvraag.getEinddatum().equals(einddatum)
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

		teamleden.remove(teamlid);
	}

	/**
	 * zit een bepaalde werknemer in dit team?
	 * 
	 * @param werknemer
	 * @return
	 */
	public boolean zitWerknemerInTeam(Werknemer werknemer) {
		if (teamleden.contains(werknemer)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * maak dit team leeg.
	 */
	public void maakTeamLeeg() {
		teamleden.clear();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj.getClass() != this.getClass()) {
			return false;
		}
		return getCode() == ((Team) obj).getCode();
	}
	
	@Override
	public int hashCode() {
		return (getCode() + "").hashCode();
	}
}
