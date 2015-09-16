package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
public class Werknemer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int personeelsnummer;
	private String naam;
	private String voornaam;
	private String email;
	@Temporal(TemporalType.DATE)
	private Calendar geboortedatum;
	private String passwoord;
	@OneToMany(cascade = CascadeType.ALL)
	private List<JaarlijksVerlof> jaarlijkseverloven;
	@OneToMany(mappedBy = "werknemer")
	private List<VerlofAanvraag> verlofaanvragen;
	@ManyToOne
	private Team team;
	@Embedded
	@OneToOne(cascade = CascadeType.ALL)
	private Adres adres;
	private static final long serialVersionUID = 1L;

	public Werknemer() {
		jaarlijkseverloven = new ArrayList<JaarlijksVerlof>();
		verlofaanvragen = new ArrayList<VerlofAanvraag>();
	}
	
	
	public int getPersoneelsnummer() {
		return this.personeelsnummer;
	}

	public void setPersoneelsnummer(int personeelsnummer) {
		this.personeelsnummer = personeelsnummer;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVoornaam() {
		return this.voornaam;
	}

	public void setVoornaam(String Voornaam) {
		this.voornaam = Voornaam;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getGeboortedatum() {
		return this.geboortedatum;
	}

	public void setGeboortedatum(Calendar geboortedatum) {
		this.geboortedatum = (GregorianCalendar) geboortedatum;
	}

	private String getPasswoord() {
		return this.passwoord;
	}

	public void setPasswoord(String passwoord) {
		this.passwoord = passwoord;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		System.out.println(team);
		this.team = team;
		if(!getTeam().zitWerknemerInTeam(this)) {
			getTeam().voegTeamlidToe(this);
		}
	}

	public Adres getAdres() {
		return this.adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public List<JaarlijksVerlof> getJaarlijkseverloven() {
		return jaarlijkseverloven;
	}

	public void setJaarlijkseverloven(List<JaarlijksVerlof> jaarlijkseverloven) {
		this.jaarlijkseverloven = jaarlijkseverloven;
	}

	public List<VerlofAanvraag> getVerlofaanvragen() {
		return verlofaanvragen;
	}

	public void setVerlofaanvragen(List<VerlofAanvraag> verlofaanvragen) {
		this.verlofaanvragen = verlofaanvragen;
	}

	public void setGegevens(Werknemer werknemer) {
		setNaam(werknemer.getNaam());
		setVoornaam(werknemer.getVoornaam());
		setAdres(werknemer.getAdres());
		setEmail(werknemer.getEmail());
		setGeboortedatum(werknemer.getGeboortedatum());
		setJaarlijkseverloven(werknemer.getJaarlijkseverloven());
		setPasswoord(werknemer.getPasswoord());
		setTeam(werknemer.getTeam());
	}

	public boolean isVerantwoordelijke() {
		return team.getTeamverantwoordelijke().personeelsnummer == this.personeelsnummer;
	}

	public boolean isHR() {
		return team.getNaam().equalsIgnoreCase("hr");
	}

	public int getAantalBeschikBareVerlofDagen(int jaartal) {
		JaarlijksVerlof tmpJaar = jaarlijkseverloven.stream().filter(j -> j.getJaar() == jaartal).findFirst().orElse(null);
		if (tmpJaar == null) {
			throw new NullPointerException();
		}
		return tmpJaar.getAantalDagen();
	}

	public void voegVerlofAanvroegToe(GregorianCalendar startdatum, GregorianCalendar einddatum) {
		VerlofAanvraag tmpAanvraag = new VerlofAanvraag(startdatum, einddatum);
		getVerlofaanvragen().add(tmpAanvraag);
	}

	public void annuleerVerlofAanvraag(int verlofaanvraagId) throws NullPointerException {
		VerlofAanvraag tmpAanvraag = verlofaanvragen.stream().filter(v -> v.getId() == verlofaanvraagId).findFirst().orElse(null);
		if (tmpAanvraag == null) {
			throw new NullPointerException();
		}
		tmpAanvraag.annuleren();
	}

	public List<VerlofAanvraag> getLopendeVerlofAanvragen() {
		return verlofaanvragen.stream().filter(v -> v.getToestand().equals(Toestand.INGEDIEND)).collect(Collectors.toList());
	}

	public int getJaarlijksVerlof(int jaartal) throws NullPointerException {
		JaarlijksVerlof tmpJaar = jaarlijkseverloven.stream().filter(j -> j.getJaar() == jaartal).findFirst().orElse(null);
		if (tmpJaar == null) {
			throw new NullPointerException();
		}
		return tmpJaar.getJaar();
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen(GregorianCalendar begindatum, GregorianCalendar einddatum, Toestand toestand) {
		return verlofaanvragen.stream()
				.filter(v -> (v.getStartdatum().before(einddatum) || v.getEinddatum().after(begindatum)) && v.getToestand() == toestand)
				.collect(Collectors.toList());
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen() {
		return verlofaanvragen;
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen(GregorianCalendar begindatum, GregorianCalendar einddatum) {
		return verlofaanvragen.stream().filter(v -> (v.getStartdatum().before(einddatum) || v.getEinddatum().after(begindatum)))
				.collect(Collectors.toList());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		return getPersoneelsnummer() == ((Werknemer) obj).getPersoneelsnummer();
	}

	@Override
	public int hashCode() {
		return (getPersoneelsnummer() + "").hashCode();
	}
}
