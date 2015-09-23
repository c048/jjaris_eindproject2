package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import daos.CollectieveSluitingDAO;
import daos.WerknemerDAO;
import entities.JaarlijksVerlof;
import entities.Werknemer;

@Named("jaarlijkseverlofbeheer")
@RequestScoped
public class JaarlijkseVerlofBeheerBack implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private WerknemerDAO dao;
	@Inject
	private LoginBack loginBack;
	
	@NotNull(message="U moet een jaar opgeven")
	private int jaar;
	@Max(value=50)
	@Min(value=0)
	private int aantalDagen;
	private int personeelsNr;
	
	
	@Inject
	private ParameterBack params;
	
	
	public String voegJaarlijkseVerlofToe() {
		System.out.println("***************Debug:JaarlijkseVerlofBeheerBack:voegJaarlijkseVerlofToe:PersoneelNr:  " +params.getPersoneelsnummer());
		Werknemer werknemer=dao.getWerknemer(params.getPersoneelsnummer());
		if(werknemer!=null) {
			System.out.println("***************Debug:JaarlijkseVerlofBeheerBack:voegJaarlijkseVerlofToe: werknemer gevonden  " );

			if( (werknemer.getJaarlijkseverloven().stream().filter(j -> j.getJaar() == jaar).findFirst().orElse(null)) ==null){
				System.out.println("***************Debug:JaarlijkseVerlofBeheerBack:voegJaarlijkseVerlofToe: werknemer heeft geen jaarlijkse verlof van dit jaar  " );

				JaarlijksVerlof jVerlof=new JaarlijksVerlof();
				jVerlof.setJaar(jaar);
				jVerlof.setAantalDagen(aantalDagen);
				jVerlof.setWerknemer(werknemer);
				werknemer.voegJaarlijksVerlofToe(jVerlof);
				System.out.println("***************Debug:JaarlijkseVerlofBeheerBack:voegJaarlijkseVerlofToe: nieuwe jaarlijkse verlof toegevoegd  " );

				dao.updateWerknemer(werknemer);
				
				System.out.println("***************Debug:JaarlijkseVerlofBeheerBack:voegJaarlijkseVerlofToe: werknemer is geupdated " );

				
				loginBack.changePage("medewerkersHr");
			}
			else {
				setFacesMessage("Verlofdagen van personeel nr "+params.getPersoneelsnummer()+ " is al ingevoerd ");

			}
		}
		else {
			setFacesMessage("Geen werknemer gevonden met personeel nr: "+params.getPersoneelsnummer());
		}
		
		params.reset();
		return null;
	}
		
	public String annuleren() {
		
		params.reset();
		loginBack.changePage("medewerkersHr");
		return null;
	}
	
	public int getJaar() {
		return jaar;
	}
	
	public void setJaar(int jaar) {
		
		Calendar cal=new GregorianCalendar();
		int ditJaar=cal.get(Calendar.YEAR);
		
		if(jaar>=ditJaar) {
			this.jaar = jaar;
		}
		else {		
			setFacesMessage("Jaartal moet van  dit jaar of komende jaren zijn");
		}
	}
	
	public int getAantalDagen() {
		return aantalDagen;
	}
	
	public void setAantalDagen(int aantalDagen) {
		this.aantalDagen = aantalDagen;
	}
	
	public int getPersoneelsNr() {
		return params.getPersoneelsnummer();
	}
	
	
	public String getVoornaam() {
		Werknemer werknemer=dao.getWerknemer(params.getPersoneelsnummer());
		if(werknemer!=null) {
			return werknemer.getVoornaam();
		}
		return null;
	}
	
	public String getNaam() {
		
		Werknemer werknemer=dao.getWerknemer(params.getPersoneelsnummer());
		if(werknemer!=null) {
			return werknemer.getNaam();
		}
		return null;
		
	}
/*	public void setPersoneelsNr(int personeelsNr) {
		this.personeelsNr = personeelsNr;
	}*/
	
	
	
	public void setFacesMessage(String msg ) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}
}
