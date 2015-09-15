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
	private EntityManager em;
	
	@Transactional
	public void voegVerlofAanvraagToe(VerlofAanvraag verlofAanvraag){
		
		em.persist(verlofAanvraag);
		
	}
	
	@Transactional
	public void updateVerlofAanvraag(VerlofAanvraag verlofAanvraag){
		
		VerlofAanvraag tmp = em.find(VerlofAanvraag.class, verlofAanvraag.getId());
		tmp.setWerknemer(verlofAanvraag.getWerknemer());
		tmp.setAanvraagdatum((GregorianCalendar) verlofAanvraag.getAanvraagdatum());
		tmp.setPeriode((GregorianCalendar) verlofAanvraag.getStartdatum(), (GregorianCalendar) verlofAanvraag.getEinddatum());
		tmp.setReactiedatum((GregorianCalendar) verlofAanvraag.getReactiedatum());
		tmp.setReden(verlofAanvraag.getReden());
		tmp.setToestand(verlofAanvraag.getToestand());
		
		
	
	}
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragenTeam(int teamCode){
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM verlofaanvraag c "
				+ "WHERE c.code = :teamcode ",VerlofAanvraag.class);
		
		return (List<VerlofAanvraag>) query.setParameter("code", teamCode).getResultList();
		
	}
	
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragenWerknemer(int personeelnr){
		
//		Werknemer werknemer = em.find(Werknemer.class, personeelnr);
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM verlofaanvraag c "
				+ "WHERE c.personeelsnummer = :personeelnr ",VerlofAanvraag.class);
		
		
		
		return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr).getResultList();
	}
	
	
//	public List<VerlofAanvraag> getVerlofAanvragen(Calendar startdatum, Calendar einddatum, Toestand toestand,int teamCode,int personeelnr){
//		
//	}
//	
	
}
	//Niet doen dus
//	public static List<VerlofAanvraag> getVerlofAanvragen(LocalDate startdatum, LocalDate einddatum, Toestand toestand,int teamCode,int personeelnr){
//		String querystring = "SELECT c FROM verlofaanvraag c WHERE c.startdatum <= :startdatum AND c.einddatum <= :einddatum";
//		
//		if(startdatum != null){
//			querystring = querystring + " AND c.startdatum <= :startdatum";
//			
//		}
//		
//		if(einddatum != null){
//			querystring = querystring + " AND c.einddatum <= :einddatum";
//		}
//		
//		if(toestand != null){
//			querystring = querystring + " AND c.toestand = :toestand";
//		}
//		
//		TypedQuery<VerlofAanvraag> query = em.createQuery(querystring,VerlofAanvraag.class);
//		
//		
//		if(startdatum == null){
//			if(einddatum == null){
//				if(toestand == null){
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.getResultList();
//				}else{
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("toestand", toestand)
//							.getResultList();
//				}
//			}else{
//				if(toestand == null){
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("einddatum", einddatum)
//							.getResultList();
//				}else{
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("toestand", toestand)
//							.setParameter("einddatum", einddatum)
//							.getResultList();
//				}
//				
//			}
//			}else{
//			if(einddatum == null){
//				if(toestand == null){
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("startdatum", startdatum)
//							.getResultList();
//				}else{
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("toestand", toestand)
//							.setParameter("startdatum", startdatum)
//							.getResultList();
//				}
//			}else{
//				if(toestand == null){
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("einddatum", einddatum)
//							.setParameter("startdatum", startdatum)
//							.getResultList();
//				}else{
//					return (List<VerlofAanvraag>) query.setParameter("personeelnr", personeelnr)
//							.setParameter("teamCode", teamCode)
//							.setParameter("toestand", toestand)
//							.setParameter("einddatum", einddatum)
//							.setParameter("startdatum", startdatum)
//							.getResultList();
//				}
//				
//			}
//			
//	
//	}
//
//}