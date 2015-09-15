package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Team
 *
 */
@Entity
public class Team implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int code;
	private String naam;
	private boolean HR;
	@OneToMany(mappedBy = "team")
	private List<Werknemer> teamleden;
	@OneToOne
	private Werknemer teamverantwoordelijke;
	private static final long serialVersionUID = 1L;

	public Team() {
		super();
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
	}

	// -- isHr?
	public boolean isHr() {
		return this.getHR();
	}

	// Lijst van verlofaanvragen tussen 2 datums aanvragen

	// Lijst van verlofaanvragen met bepaalde status

	// Lijst van verlofaanvragen

	// voeg een teamlid toe
	public void voegTeamlidToe(Werknemer teamlid) {
		teamleden.add(teamlid);

	}

	// verwijder een teamlid
	public void verwijderTeamlid(Werknemer teamlid) {
	
		teamleden.remove(teamlid);
	}

	// zit een bepaalde werknemer in dit team?
	public boolean zitWerknemerInTeam(Werknemer werknemer) {
		if (teamleden.contains(werknemer)) {
			return true;

		} else {
			return false;
		}

	}
}
