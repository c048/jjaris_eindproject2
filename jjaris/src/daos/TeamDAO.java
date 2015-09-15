package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import entities.Team;


public class TeamDAO {
	
	@PersistenceContext(unitName = "jjaris")
	private static EntityManager em;
	
	@Transactional
	public static void voegTeamToe(Team team){
		
		
		em.persist(team);
		
		
	}
	
	@Transactional
	public static void verwijderTeam(Team team){
		
		
		em.remove(team);
		
	}
	
	@Transactional
	public static void updateTeam(Team team){
		
		Team tmp = em.find(Team.class, team.getCode());
		tmp.setHR(team.getHR());
		tmp.setNaam(team.getNaam());
		tmp.setTeamleden(team.getTeamleden());
		tmp.setTeamverantwoordelijke(team.getTeamverantwoordelijke());
		
		
		
		
	}
	
	@Transactional
	public static Team getTeam(int code){
		
		Team team = em.find(Team.class, code);
		
		
		return team;
		
		
	}
	
	@Transactional
	public static List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke, int code){
		
		TypedQuery<Team> query = em.createQuery("SELECT c FROM team c WHERE c.naam = :%naam% AND "
				+ "c.werknemer = :%leider% AND c.code = :code",Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam)
				.setParameter("werknemer", zoekVerantwoordelijke)
				.setParameter("code", code).getResultList();
		
	}
	
	@Transactional
	public static List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke){
		
		TypedQuery<Team> query = em.createQuery("SELECT c FROM team c WHERE c.naam = :%naam% AND "
				+ "c.werknemer = :%leider%",Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam)
				.setParameter("werknemer", zoekVerantwoordelijke).getResultList();
		
	}
	
	

}
