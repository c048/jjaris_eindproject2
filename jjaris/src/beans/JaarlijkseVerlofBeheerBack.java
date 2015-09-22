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
	
	@NotNull(message="U moet een jaar opgeven")
	private int jaar;
	@Max(value=50)
	@Min(value=0)
	private int aantalDagen;
	private int personeelsNr;
	
	public String voegJaarlijkseVerlofToe() {
		
		Werknemer werknemer=dao.getWerknemer(personeelsNr);
		if(werknemer!=null) {
			if( (werknemer.getJaarlijkseverloven().stream().filter(j -> j.getJaar() == jaar).findFirst().orElse(null)) ==null){
				JaarlijksVerlof jVerlof=new JaarlijksVerlof();
				jVerlof.setJaar(jaar);
				jVerlof.setAantalDagen(aantalDagen);
				jVerlof.setWerknemer(werknemer);
				werknemer.voegJaarlijksVerlofToe(jVerlof);
				dao.updateWerknemer(werknemer);
			}
			else {
				setFacesMessage("Verlofdagen van personeel nr "+personeelsNr+ " is al ingevoerd ");
			}
		}
		else {
			setFacesMessage("Geen werknemer gevonden met personeel nr: "+personeelsNr);
		}

		return "voegJaarlijkseVerloven";
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
			setFacesMessage("Jaartal moet van  deze jaar of komende jaren zijn");
		}
	}
	
	public int getAantalDagen() {
		return aantalDagen;
	}
	
	public void setAantalDagen(int aantalDagen) {
		this.aantalDagen = aantalDagen;
	}
	
	public int getPersoneelsNr() {
		return personeelsNr;
	}
	
	public void setPersoneelsNr(int personeelsNr) {
		this.personeelsNr = personeelsNr;
	}
	
	public void setFacesMessage(String msg ) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}
	

	
	
}
