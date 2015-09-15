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
import entities.Werknemer;

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
	
	@Transactional
	public static List<VerlofAanvraag> getVerlofAanvragenTeam(int teamCode){
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM verlofaanvraag c "
				+ "WHERE c.code = :teamcode ",VerlofAanvraag.class);
		
		
		
		return (List<VerlofAanvraag>) query.setParameter("code", teamCode).getResultList();
		
		
		
		
		
	}
	
	
	@Transactional
	public static List<VerlofAanvraag> getVerlofAanvragenWerknemer(int personeelnr){
		
//		Werknemer werknemer = em.find(Werknemer.class, personeelnr);
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM verlofaanvraag c "
				+ "WHERE c.personeelsnummer = :personeelnr ",VerlofAanvraag.class);
		
		
		
		return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr).getResultList();
	}
	
//	public static List<VerlofAanvraag> getVerlofAanvragen(LocalDate startdatum, LocalDate einddatum, Toestand toestand,int teamCode,int personeelnr){
//		
//	}

}
