package daos;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.weld.exceptions.IllegalArgumentException;

import entities.Team;

@ApplicationScoped
public class TeamDAO {

	@PersistenceContext(unitName = "jjaris")
	private EntityManager em;

	@Transactional
	public void voegTeamToe(Team team) {

		em.persist(team);

	}

	/**
	 * Verwijdert een team, dat niet null is uit de database, als het geen
	 * werknemers bevat
	 * 
	 * @param team
	 */
	@Transactional
	public void verwijderTeam(Team team) {

		if (team != null && team.getTeamleden().isEmpty()) {
			em.find(Team.class, team);
			em.remove(team);
		} else {
			if (team == null) {
				throw new NullPointerException("TeamDAO.verwijderTeam kan niet worden uitgevoerd op null");
			} else {
				throw new IllegalArgumentException(
						"TeamDAO.verwijderTeam kan niet worden uitgevoerd want er zijn nog werknemers gekoppeld aan dit team");
			}
		}
	}

	/**
	 *update een team in de database
	 * @param team
	 */
	@Transactional
	public void updateTeam(Team team) {

		Team tmp = em.find(Team.class, team.getCode());
		tmp.setHR(team.getHR());
		tmp.setNaam(team.getNaam());
		tmp.setTeamleden(team.getTeamleden());
		tmp.setTeamverantwoordelijke(team.getTeamverantwoordelijke());

	}

	@Transactional
	public Team getTeam(int code) {
		Team team = em.find(Team.class, code);
		return team;

	}

	
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke, int code) {

		TypedQuery<Team> query = em.createQuery("SELECT c FROM team c WHERE c.naam = :%naam% AND " + "c.werknemer = :%leider% AND c.code = :code",
				Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam).setParameter("werknemer", zoekVerantwoordelijke).setParameter("code", code)
				.getResultList();

	}

	
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke) {

		TypedQuery<Team> query = em.createQuery("SELECT c FROM team c WHERE c.naam = :%naam% AND " + "c.werknemer = :%leider%", Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam).setParameter("werknemer", zoekVerantwoordelijke).getResultList();

	}
	
	/**
	 * Geeft alle teams uit de database. Geeft null terug als er geen teams aanwezig zijn
	 * @return List<Team>
	 */
	public List<Team> getTeams(){
		List<Team> l = null;
		
			TypedQuery<Team> tqry = em.createQuery("SELECT t FROM Team t", Team.class);
			l = tqry.getResultList();
		
		return l;
	}

}
