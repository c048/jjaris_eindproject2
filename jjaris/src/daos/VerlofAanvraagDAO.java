package daos;

import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import entities.Team;
import entities.Toestand;
import entities.VerlofAanvraag;

public class VerlofAanvraagDAO {
	
	
	@PersistenceContext(unitName = "jjaris")
	private static EntityManager em;
	
	@Transactional
	public static void voegVerlofAanvraagToe(VerlofAanvraag verlofAanvraag){
		
		em.persist(verlofAanvraag);
		
	}
	
	@Transactional
	public static void updateVerlofAanvraag(VerlofAanvraag verlofAanvraag){
		
		VerlofAanvraag tmp = em.find(VerlofAanvraag.class, verlofAanvraag.getId());
		tmp.setWerknemer(verlofAanvraag.getWerknemer());
		tmp.setAanvraagdatum((GregorianCalendar) verlofAanvraag.getAanvraagdatum());
		tmp.setPeriode((GregorianCalendar) verlofAanvraag.getStartdatum(), (GregorianCalendar) verlofAanvraag.getEinddatum());
		tmp.setReactiedatum((GregorianCalendar) verlofAanvraag.getReactiedatum());
		tmp.setReden(verlofAanvraag.getReden());
		tmp.setToestand(verlofAanvraag.getToestand());
		
		
	
	}
	
//	@Transactional
//	public static List<VerlofAanvraag> getVerlofAanvragen(int teamCode){
//		
//		
//		
//		
//		
//		
//		
//	}
	
	
//	@Transactional
//	public static List<VerlofAanvraag> getVerlofAanvragen(int personeelnr){
//		
//		
//	}
	
//	public static List<VerlofAanvraag> getVerlofAanvragen(LocalDate startdatum, LocalDate einddatum, Toestand toestand,int teamCode,int personeelnr){
//		
//	}

}
