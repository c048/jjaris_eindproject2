package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Team;


public class TeamDAO {
	
	@PersistenceContext(unitName = "jjaris")
	private static EntityManager em;
	
	
	public void voegTeamToe(Team team){
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		em.persist(team);
		
		tx.commit();
	}
	
	public void verwijderTeam(Team team){
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		em.remove(team);
		
		tx.commit();
	}
	
	public void updateTeam(Team team){
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		em.persist(team);
		
		tx.commit();
		
	}
	
	public Team getTeam(int code){
		
		Team team = em.find(Team.class, code);
		
		
		return team;
		
		
	}
	
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke, int code){
		
		TypedQuery<Team> query = em.createQuery("SELECT c FROM team c WHERE c.naam = :%naam% AND "
				+ "c.werknemer = :%leider% AND c.code = :code",Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam)
				.setParameter("werknemer", zoekVerantwoordelijke)
				.setParameter("code", code).getResultList();
		
	}
	
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke){
		
		TypedQuery<Team> query = em.createQuery("SELECT c FROM team c WHERE c.naam = :%naam% AND "
				+ "c.werknemer = :%leider%",Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam)
				.setParameter("werknemer", zoekVerantwoordelijke).getResultList();
		
	}
	
	

}
