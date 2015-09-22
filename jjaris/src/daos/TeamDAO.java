package daos;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import utils.Filter;
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
	 * @param team
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	@Transactional //Indien in een transactional methode een exceptie wordt gegooid, krijg je tijdens de uitvoering een TransactionalException
	public void verwijderTeam(Team team) {

		if (team != null && team.getTeamleden().isEmpty()) {
//			EntityTransaction tx =  em.getTransaction();
//			tx.begin();
			System.out.printf("team %s aan het verwijderen",team.getNaam());
			Team t = em.find(Team.class, team.getCode());
			System.out.println("team gevonden voor verwijdering");
			em.remove(t);
//			tx.commit();
			
		} else {
			if (team == null) {
				throw new NullPointerException("TeamDAO.verwijderTeam kan niet worden uitgevoerd op null");
			} else {
				System.out.println("team heeft nog teamleden in else methode");
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
			TypedQuery<Team> query = em.createQuery("SELECT c FROM Team c WHERE c.naam LIKE :naam AND "
					+ "c.teamverantwoordelijke.naam LIKE :leider AND c.code = :code ORDER BY c.naam", Team.class);
			query.setParameter("naam", "%" + zoekNaam + "%");
			query.setParameter("leider", "%" + zoekVerantwoordelijke + "%");
			query.setParameter("code", code);
			return query.getResultList();
		} else {
			return getTeams(zoekNaam, zoekVerantwoordelijke);
		}

	}

	/**
	 * Geeft alle teams terug met een gedeeltelijke naam en gedeeltelijke naam
	 * verantwoordelijke
	 * 
	 * @param zoekNaam
	 * @param zoekVerantwoordelijke
	 * @return
	 */
	public List<Team> getTeams(String zoekNaam, String zoekVerantwoordelijke) {

		TypedQuery<Team> query = em.createQuery("SELECT c FROM Team c WHERE c.naam LIKE :naam AND " + "c.teamverantwoordelijke.naam LIKE :leider"+" ORDER BY c.naam",
				Team.class);
		query.setParameter("naam", "%" + zoekNaam + "%");
		query.setParameter("leider", "%" + zoekVerantwoordelijke + "%");
		return (List<Team>) query.getResultList();

	}

	/**
	 * Geeft alle teams uit de database. Geeft null terug als er geen teams
	 * aanwezig zijn
	 * 
	 * @return List<Team>
	 */
	public List<Team> getTeams() {
		List<Team> l = null;

		TypedQuery<Team> tqry = em.createQuery("SELECT t FROM Team t ORDER BY t.naam", Team.class);
		l = tqry.getResultList();

		return l;
	}

	public List<Team> getTeams(Filter f) {
		String queryString = "SELECT c FROM Team c";

		if (!f.isEmpty()) {
			queryString += " WHERE";
			int aantal = 0;
			for (String key : f) {
				if (aantal != 0) {
					queryString += " AND";
				}
				if (key.equals("naam") || key.equals("teamverantwoordelijke.naam")) {
					queryString += " c." + key + " LIKE :" + key.replace(".", "");
					aantal++;
				}
				if (key.equals("code")) {
					queryString += " c." + key + " = :" + key;
					aantal++;
				}
			}
			queryString+= " ORDER BY c.naam";
		}
		System.out.println(queryString);
		TypedQuery<Team> query = em.createQuery(queryString, Team.class);

		for (String key : f) {
			if (key.equals("naam")||key.equals("teamverantwoordelijke.naam")) {
				query.setParameter(key.replace(".", ""), "%" + f.getValue(key) + "%");
			}else {
				query.setParameter(key, f.getValue(key));
			}
		}

		return query.getResultList();

	}

}
