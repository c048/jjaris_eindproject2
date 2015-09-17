package beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.VerlofAanvraagDAO;
import entities.VerlofAanvraag;
import entities.Werknemer;


@Named("verlofaanvraag")
@RequestScoped
public class VerlofAanvraagBack implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBack user;
	@Inject
	private VerlofAanvraagDAO verlofaanvraag;
	
	/**
	 * Lijst met verlofaanvragen per werknemer
	 * behalve geannulleerde aanvragen
	 * @return
	 */
	public List<VerlofAanvraag> getAanvragen(){
		return null;
		
	}
	/**
	 * Anulleer een verlofaanvraag	
	 */
	public void annuleren(int id){
		Werknemer werknemer = user.getIngelogdeWerknemer();
		werknemer.annuleerVerlofAanvraag(id);
		verlofaanvraag.updateVerlofAanvraag(verlofaanvraag.getVerlofAanvraag(id));
		
		
	}
	/**
	 * Verlof aanvragen
	 */
	public void aanvragen(){
		user.getIngelogdeWerknemer();
		
	}
	
	
	
	
	
	
}
