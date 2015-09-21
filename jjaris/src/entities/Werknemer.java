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
	@Column(nullable=false)
	private String naam;
	@Column(nullable=false)
	private String voornaam;
	@Column(unique=true,nullable=false)
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
	
	public boolean controleerPasswoord(String teControlerenPaswoord){
		return teControlerenPaswoord.equals(getPasswoord());
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		// System.out.println(team);
		if (team != null){
			this.team = team;
			if (!getTeam().zitWerknemerInTeam(this)) {
				getTeam().voegTeamlidToe(this);
			}
		}else throw new NullPointerException("Werknemer.setTeam niet gelukt: kan geen null invullen");
		
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
		if(team.getTeamverantwoordelijke()!=null){
			return team.getTeamverantwoordelijke().personeelsnummer == this.personeelsnummer;
		}
		return false;
	}

	public boolean isHR() {
		return team.getHR();
	}

	public int getAantalBeschikBareVerlofDagen(int jaartal) {
		int tmpJaar = getJaarlijksVerlof(jaartal);
		int verlofdagen = verlofaanvragen.stream().filter(v -> v.getToestand() == Toestand.INGEDIEND || v.getToestand() == Toestand.GOEDGEKEURD).mapToInt(v -> v.getPeriode()).sum();
		return (tmpJaar - verlofdagen);
	}

//	public void voegVerlofAanvroegToe(GregorianCalendar startdatum, GregorianCalendar einddatum) {
//		VerlofAanvraag tmpAanvraag = new VerlofAanvraag(startdatum, einddatum,this);
//		getVerlofaanvragen().add(tmpAanvraag);
//	}
	
	public void voegVerlofAanvraagToe(VerlofAanvraag va){
		getVerlofaanvragen().add(va);
		if (!va.getWerknemer().equals(this)){
			va.setWerknemer(this);
		}
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
		return verlofaanvragen.stream().filter(v -> (v.getStartdatum().before(einddatum) && v.getEinddatum().after(begindatum)) && v.getToestand() == (toestand)).collect(Collectors.toList());
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen() {
		return verlofaanvragen;
	}

	public List<VerlofAanvraag> getAlleVerlofAanvragen(GregorianCalendar begindatum, GregorianCalendar einddatum) {
		if (verlofaanvragen.isEmpty()){
			return verlofaanvragen;
		}
		return verlofaanvragen.stream().filter(v -> (v.getStartdatum().before(einddatum) && v.getEinddatum().after(begindatum))).collect(Collectors.toList());
	}
	
	public void voegJaarlijksVerlofToe(JaarlijksVerlof jaarlijksverlof) {
		jaarlijkseverloven.add(jaarlijksverlof);
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
		return String.format("Werknemer %s %s met nr %s met adres %s, behoort tot Team %s%n", getVoornaam(), getNaam(), getPersoneelsnummer(),
				getAdres(), getTeam().getNaam());
	}
	
	public String getVolledigeNaam(){
		return String.format("%s %s", getVoornaam(),getNaam());
	}
}
