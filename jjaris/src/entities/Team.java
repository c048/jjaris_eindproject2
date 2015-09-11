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
	private List<Werknemer> teamleden;
	private Werknemer teamverantwoordelijke;
	private static final long serialVersionUID = 1L;

	public Team() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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
   
}
