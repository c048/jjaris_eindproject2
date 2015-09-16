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
			Team t = em.find(Team.class, team);
			em.remove(t);
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
	 * update een team in de database
	 * 
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

	/**
	 * Geeft alle teams met een gedeeltelijke naam en een gedeeltelijke zoeknaam
	 * van de teamverantwoordelijke en een bepaalde teamcode
	 * 
	 * @param zoekNaam
	 * @param zoekVerantwoordelijke
	 * @param code
	 * @return
	 */
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke, int code) {
		if (code != 0) {
			TypedQuery<Team> query = em.createQuery("SELECT c FROM Team c WHERE c.naam = :%naam% AND "
					+ "c.teamverantwoordelijke.naam = :%leider% AND c.code = :code", Team.class);
			return (List<Team>) query.setParameter("naam", zoekNaam).setParameter("leider", zoekVerantwoordelijke).setParameter("code", code)
					.getResultList();
		}else return getTeams(zoekNaam, zoekVerantwoordelijke);

	}

	/**
	 * Geeft alle teams terug met een gedeeltelijke naam en gedeeltelijke naam verantwoordelijke
	 * @param zoekNaam
	 * @param zoekVerantwoordelijke
	 * @return
	 */
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke) {

		TypedQuery<Team> query = em.createQuery("SELECT c FROM Team c WHERE c.naam = :%naam% AND " + "c.teamverantwoordelijke.naam = :%leider%",
				Team.class);
		return (List<Team>) query.setParameter("naam", zoekNaam).setParameter("leider", zoekVerantwoordelijke).getResultList();

	}

	/**
	 * Geeft alle teams uit de database. Geeft null terug als er geen teams
	 * aanwezig zijn
	 * 
	 * @return List<Team>
	 */
	public List<Team> getTeams() {
		List<Team> l = null;

		TypedQuery<Team> tqry = em.createQuery("SELECT t FROM Team t", Team.class);
		l = tqry.getResultList();

		return l;
	}

}
