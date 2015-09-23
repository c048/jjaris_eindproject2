package daos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
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
	public void updateVerlofAanvraag(VerlofAanvraag verlofAanvraag) throws IllegalArgumentException{	
		try {
			VerlofAanvraag tmp = em.find(VerlofAanvraag.class, verlofAanvraag.getId());
			tmp.setWerknemer(verlofAanvraag.getWerknemer());
			tmp.setAanvraagdatum((GregorianCalendar) verlofAanvraag.getAanvraagdatum());
			tmp.setPeriode((GregorianCalendar) verlofAanvraag.getStartdatum(), (GregorianCalendar) verlofAanvraag.getEinddatum());
			tmp.setReactiedatum((GregorianCalendar) verlofAanvraag.getReactiedatum());
			tmp.setReden(verlofAanvraag.getReden());
			tmp.setToestand(verlofAanvraag.getToestand());
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Verlofaanvraag opvragen aan de hand van de ID
	 * @param id
	 * @return
	 */
	public VerlofAanvraag getVerlofAanvraag(int id){
		return em.find(VerlofAanvraag.class, id);
		
	}
	
	/**
	 * Geeft een lijst van verlofaanvragen uit de database volgens teamCode.
	 * @param teamCode:int
	 * @return List<VerlofAanvraag>
	 */
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragen() {
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM VerlofAanvraag c ORDER BY c.startdatum", VerlofAanvraag.class);
		return (List<VerlofAanvraag>) query.getResultList();
	}
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragenTeam(int teamCode){
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM VerlofAanvraag c "
				+ "WHERE c.werknemer.team.code = :teamcode ORDER BY c.startdatum",VerlofAanvraag.class);
		
		return (List<VerlofAanvraag>) query.setParameter("code", teamCode).getResultList();
		
	}
	
	/**
	 * Geeft een lijst van verlofaanvragen uit de database volgens personeelnr.
	 * @param personeelnr:int
	 * @return List<VerlofAanvraag>
	 */
	
	@Transactional
	public List<VerlofAanvraag> getVerlofAanvragenWerknemer(int personeelnr){
		
		TypedQuery<VerlofAanvraag> query = em.createQuery("SELECT c FROM VerlofAanvraag c "
				+ "WHERE c.werknemer.personeelsnummer = :personeelnr ORDER BY c.startdatum",VerlofAanvraag.class);
		
		
		
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
		if(!filter.isEmpty()){
			int aantal = 0;
			querystring = querystring + " WHERE";
			for(String key : filter){
				if(aantal != 0){
					querystring = querystring + " AND";
				}
				if(key.equals("startdatum")){
					aantal++;
					querystring +=  " c."+key+">= :" + key;
					
				}
				if(key.equals("einddatum")){
					aantal++;
					querystring += " c."+key+"<= :" + key;
					
				}
				if(key.equals("werknemer.personeelsnummer") || key.equals("werknemer.team.code") || key.equals("toestand")){
					aantal++;
					querystring += " c."+key+"= :" + key.replace(".", "");
				}
			}
		querystring += " ORDER BY c.startdatum";	
		}
		
		
		TypedQuery<VerlofAanvraag> query = em.createQuery(querystring,VerlofAanvraag.class);
		
		
		for(String key : filter){
			query.setParameter(key.replace(".", ""), filter.getValue(key));
		}
		
		return (List<VerlofAanvraag>) query.getResultList();
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