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
	@Column(nullable = false)
	private String naam;
	@Column(nullable = false)
	private String voornaam;
	@Column(unique = true, nullable = false)
	private String email;
	@Temporal(TemporalType.DATE)
	private Calendar geboortedatum;
	private String passwoord;
	@OneToMany(mappedBy = "werknemer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<JaarlijksVerlof> jaarlijkseverloven;
	@OneToMany(mappedBy = "werknemer", fetch = FetchType.EAGER)
	private List<VerlofAanvraag> verlofaanvragen;
	@ManyToOne
	private Team team;
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

	public void setNaam(String naam) throws IllegalArgumentException {
		if (!(naam.trim().equals(""))) {
			this.naam = naam;
		} else {
			throw new IllegalArgumentException("Achternaam mag niet leeg zijn!");
		}
	}

	public String getVoornaam() {
		return this.voornaam;
	}

	public void setVoornaam(String voornaam) throws IllegalArgumentException {
		if (!(voornaam.trim().equals(""))) {
			this.voornaam = voornaam;
		} else {
			throw new IllegalArgumentException("Voornaam mag niet leeg zijn");
		}
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) throws IllegalArgumentException {
		if (!(email.trim().equals(""))) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("Email mag niet leeg zijn");
		}
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

	public void setPasswoord(String passwoord) throws IllegalArgumentException {
		if (!(passwoord.trim().equals(""))) {
			this.passwoord = passwoord;
		} else {
			throw new IllegalArgumentException("Passwoord mag niet leeg zijn");
		}
	}

	public boolean controleerPasswoord(String teControlerenPaswoord) {
		return teControlerenPaswoord.equals(getPasswoord());
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		// System.out.println(team);
		if (team != null) {
			this.team = team;
			if (!getTeam().zitWerknemerInTeam(this)) {
				getTeam().voegTeamlidToe(this);
			}
		} else {
			throw new NullPointerException(
					"Werknemer.setTeam niet gelukt: kan geen null invullen");
		}
	}

	public Adres getAdres() {
		return this.adres;
	}

	public void setAdres(Adres adres) throws IllegalArgumentException {
		this.adres = adres;
	}

	public List<JaarlijksVerlof> getJaarlijkseverloven() {
		if (jaarlijkseverloven != null){
		jaarlijkseverloven = jaarlijkseverloven.stream().distinct().collect(Collectors.toList());
		}
		return jaarlijkseverloven;
	}

	public void setJaarlijkseverloven(List<JaarlijksVerlof> jaarlijkseverloven) {
		if (jaarlijkseverloven != null) {
			this.jaarlijkseverloven = new ArrayList<JaarlijksVerlof>();
			// na het wegschrijven en ophalen uit de database hebben de
			// jaarlijkse verloven een andere ID gekregen en deze zouden anders
			// dubbel in de lijst kunnen komen
			for (JaarlijksVerlof jaarlijksVerlof : jaarlijkseverloven) {
				if (!getJaarlijkseverloven().contains(jaarlijksVerlof)) {
					voegJaarlijksVerlofToe(jaarlijksVerlof);
				}
			}
		}

	}

	public List<VerlofAanvraag> getVerlofaanvragen() {
		if (verlofaanvragen != null){
		verlofaanvragen = verlofaanvragen.stream().distinct().collect(Collectors.toList());
		}
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
		//setJaarlijkseverloven(werknemer.getJaarlijkseverloven());
		setPasswoord(werknemer.getPasswoord());
		setTeam(werknemer.getTeam());
	}

	public boolean isVerantwoordelijke() {
		if (team.getTeamverantwoordelijke() != null) {
			return team.getTeamverantwoordelijke().personeelsnummer == this.personeelsnummer;
		}
		return false;
	}

	public boolean isHR() {
		return team.isHr();
	}

	public int getAantalBeschikBareVerlofDagen(int jaartal) {
		int tmpJaar = getJaarlijksVerlof(jaartal);
		List<VerlofAanvraag> va = getOpenstaandeVerlofaanvragenJaar(jaartal);
		
		int verlofdagen = 0;
		
		for (VerlofAanvraag verlofAanvraag : va) {
			if (verlofAanvraag.isStartEnEinddatumInZelfdeJaar()) {
				verlofdagen += verlofAanvraag.getPeriode();
			} else {
				if (verlofAanvraag.geefJaarStartdatum() == jaartal) {
					verlofdagen += verlofAanvraag.getPeriodeInJaarStartdatum();
				}else{
					verlofdagen += verlofAanvraag.getPeriodeInJaarEinddatum();
				}
			}
		}
		
		return (tmpJaar - verlofdagen);
	}

	// public void voegVerlofAanvroegToe(GregorianCalendar startdatum,
	// GregorianCalendar einddatum) {
	// VerlofAanvraag tmpAanvraag = new VerlofAanvraag(startdatum,
	// einddatum,this);
	// getVerlofaanvragen().add(tmpAanvraag);
	// }

	public void voegVerlofAanvraagToe(VerlofAanvraag va) {

		if (va != null) {
			if (!getVerlofaanvragen().contains(va)) {

				if (!va.getWerknemer().equals(this)) {
					throw new IllegalArgumentException(
							"Werknemer.voegVerlofAanvraagToe : kan verlof niet toevoegen: verlofaanvraag behoort niet toe aan deze werknemer");
				}

				if (va.isStartEnEinddatumInZelfdeJaar()) {
					if (va.getPeriode() > getAantalBeschikBareVerlofDagen(va.geefJaarStartdatum())) {
						va.afkeuren("Afkeuring door systeem - Onvoldoende verlofdagen beschikbaar");
					}
				} else {
					if (va.getPeriodeInJaarStartdatum() > getAantalBeschikBareVerlofDagen(va.geefJaarStartdatum())
							|| va.getPeriodeInJaarEinddatum() > getAantalBeschikBareVerlofDagen(va.geefJaarEinddatum())) {
						va.afkeuren("Afkeuring door systeem - Onvoldoende verlofdagen beschikbaar");
					}
				}

				getVerlofaanvragen().add(va);
			}

		} else
			throw new NullPointerException(
					"Werknemer.voegVerlofAanvraagToe : kan verlof niet toevoegen: parameter verlofaanvraag is null");
	}

	public void annuleerVerlofAanvraag(int verlofaanvraagId)
			throws NullPointerException {
		VerlofAanvraag tmpAanvraag = getVerlofaanvragen().stream()
				.filter(v -> v.getId() == verlofaanvraagId).findFirst()
				.orElse(null);
		if (tmpAanvraag == null) {
			throw new NullPointerException();
		}
		tmpAanvraag.annuleren();
	}

	public List<VerlofAanvraag> getLopendeVerlofAanvragen() {
		return getVerlofaanvragen().stream()
				.filter(v -> v.getToestand().equals(Toestand.INGEDIEND))
				.collect(Collectors.toList());
	}

	public int getJaarlijksVerlof(int jaartal) {
		JaarlijksVerlof tmpJaar = getJaarlijkseverloven().stream()
				.filter(j -> j.getJaar() == jaartal).findFirst().orElse(null);
		if (tmpJaar == null) {
			return 0;
		}
		return tmpJaar.getAantalDagen();
	}

	public JaarlijksVerlof getJaarlijksVerlofVanJaar(int jaartal) {
		JaarlijksVerlof tmpJaar = getJaarlijkseverloven().stream()
				.filter(j -> j.getJaar() == jaartal).findFirst().orElse(null);
		// if (tmpJaar == null){
		// throw new
		// NullPointerException("Werknemer.getJaarlijksVerlofVanJaar(int jaartal) : er bestaat geen jaarlijks verlof met dit jaartal");
		// }
		return tmpJaar;
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen(
			GregorianCalendar begindatum, GregorianCalendar einddatum,
			Toestand toestand) {
		if (verlofaanvragen.isEmpty()) {
			// System.out.println("verlofaanvragen van: " + getVolledigeNaam() +
			// "zijn leeg");
			return verlofaanvragen;
		}
		return getVerlofaanvragen().stream().filter(v -> (v.getStartdatum().before(einddatum) && v.getEinddatum().after(begindatum)) && v.getToestand() == (toestand))
				.collect(Collectors.toList());
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen() {
		return getVerlofaanvragen();
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen(GregorianCalendar begindatum, GregorianCalendar einddatum) {
		if (verlofaanvragen.isEmpty()) {
			return verlofaanvragen;
		}else return getVerlofaanvragen().stream().filter(v -> (v.getStartdatum().before(einddatum) && v.getEinddatum().after(begindatum))).collect(Collectors.toList());
	}
	
	public List<VerlofAanvraag> getOpenstaandeVerlofaanvragenJaar(int jaartal){
		if (verlofaanvragen.isEmpty()) {
			return verlofaanvragen;
		}else return getVerlofaanvragen().stream().filter(v -> (v.geefJaarStartdatum() == jaartal || v.geefJaarEinddatum() == jaartal)).filter(va -> va.getToestand().equals(Toestand.INGEDIEND) || va.getToestand().equals(Toestand.GOEDGEKEURD)).distinct().collect(Collectors.toList());
	}

	public void voegJaarlijksVerlofToe(JaarlijksVerlof jaarlijksverlof) {
		if (jaarlijksverlof != null) {
			if (jaarlijksverlof.getWerknemer().equals(this) && !heeftReedsJaarlijksVerlof(jaarlijksverlof.getJaar())) {
				getJaarlijkseverloven().add(jaarlijksverlof);

			} else
				throw new IllegalArgumentException(
						"Werknemer.voegJaarlijksVerlofToe :kan JaarlijksVerlof niet toevoegen: behoort niet toe aan deze werknemer ");
		} else
			throw new NullPointerException(
					"Werknemer.voegJaarlijksVerlofToe :kan JaarlijksVerlof niet toevoegen: parameter is null ");
	}
	
	public boolean heeftReedsJaarlijksVerlof(int jaartal){
		boolean heeftVerlofJaar = false;
		for (JaarlijksVerlof jaarlijksVerlof : getJaarlijkseverloven()) {
			if (jaarlijksVerlof.getJaar() == jaartal){
				heeftVerlofJaar = true;
				break;
			}
		}
		return heeftVerlofJaar;
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

	@Override
	public String toString() {
		return String
				.format("Werknemer %s %s met nr %s met adres %s, behoort tot Team %s%n",
						getVoornaam(), getNaam(), getPersoneelsnummer(),
						getAdres(), getTeam().getNaam());
	}

	public String getVolledigeNaam() {
		return String.format("%s %s", getVoornaam(), getNaam());
	}
}
