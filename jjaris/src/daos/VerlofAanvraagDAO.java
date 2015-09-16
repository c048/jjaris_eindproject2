package daos;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import utils.Filter;
import entities.VerlofAanvraag;


@ApplicationScoped
public class VerlofAanvraagDAO {
	
	
	@PersistenceContext(unitName = "jjaris")
	private EntityManager em;
	
	
	/**
	 *  Voegt een verlofaanvraag toe aan database.
	 * @param verlofAanvraag:VerlofAanvraag
	 * 
	 */
	@Transactional
	public void voegVerlofAanvraagToe(VerlofAanvraag verlofAanvraag){
		
		em.persist(verlofAanvraag);
		
	}
	
	/**
	 * Verandert een verlofaanvraag in de database.
	 * @param verlofAanvraag:VerlofAanvraag
	 * 
	 */
	
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
	
	/**
	 * Geeft een lijst van verlofaanvragen uit de database volgens teamCode.
	 * @param teamCode:int
	 * @return List<VerlofAanvraag>
	 */
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragenTeam(int teamCode){
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM VerlofAanvraag c "
				+ "WHERE c.werknemer.team.code = :teamcode ",VerlofAanvraag.class);
		
		return (List<VerlofAanvraag>) query.setParameter("code", teamCode).getResultList();
		
	}
	
	/**
	 * Geeft een lijst van verlofaanvragen uit de database volgens personeelnr.
	 * @param personeelnr:int
	 * @return List<VerlofAanvraag>
	 */
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragenWerknemer(int personeelnr){
		
//		Werknemer werknemer = em.find(Werknemer.class, personeelnr);
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM VerlofAanvraag c "
				+ "WHERE c.werknemer.personeelsnummer = :personeelnr ",VerlofAanvraag.class);
		
		
		
		return query.setParameter("personeelnr", personeelnr).getResultList();
	}
	
	/**
	 * Gebruikt de ingevoerde filter om alle verlofaanvragen te filteren. Geeft dan de lijst met verlofaanvragen 
	 * terug die voldoen aan de voorwaarden.
	 * 
	 * @param filter
	 * @return List<VerlofAanvraag>
	 */
	
	public List<VerlofAanvraag> getVerlofAanvragen(Filter filter){
		
		String querystring = "SELECT c FROM VerlofAanvraag c";
		if(filter.getFilter().isEmpty()){
			TypedQuery<VerlofAanvraag> query = em.createQuery(querystring,VerlofAanvraag.class);
			return (List<VerlofAanvraag>) query.getResultList();
		}
		else{
			int aantal = 0;
			querystring = querystring + " WHERE";
			for(Map.Entry<String, Object> entry : filter.getFilter().entrySet()){
				if(aantal != 0){
					querystring = querystring + " AND";
				}
				if(entry.getKey().contains("startdatum")){
					aantal++;
					querystring = querystring + " c."+entry.getKey()+"<= :" + entry.getKey();
					
				}
				if(entry.getKey().contains("einddatum")){
					aantal++;
					querystring = querystring + " c."+entry.getKey()+">= :" + entry.getKey();
					
				}
				if(entry.getKey().contains("c.werknemer.personeelsnummer") || entry.getKey().contains("c.werknemer.team.code")){
					aantal++;
					querystring = querystring + " c."+entry.getKey()+"= :" + entry.getKey();
				}
				else{
					aantal++;
					querystring = querystring + " c."+entry.getKey()+"LIKE :" + entry.getKey();
				}
				
			}
			TypedQuery<VerlofAanvraag> query = em.createQuery(querystring,VerlofAanvraag.class);
			
			for(Map.Entry<String, Object> entry : filter.getFilter().entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
			
			return (List<VerlofAanvraag>) query.getResultList();
		}
		
		
	}
	
	
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