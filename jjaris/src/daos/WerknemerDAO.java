package daos;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import utils.Filter;
import entities.JaarlijksVerlof;
import entities.Team;
import entities.VerlofAanvraag;
import entities.Werknemer;

@ApplicationScoped
public class WerknemerDAO {
	@PersistenceContext(unitName = "jjaris")
	private EntityManager em;

	/**
	 * Voegt een werknemer toe aan de database. Via Cascade zou ook het adres en
	 * het Jaarlijks verlof moeten ingevuld worden
	 * 
	 * @param werknemer
	 *            :Werknemer
	 * @throws EntityExistsException
	 *             als er al een werknemer met dezelfde id bestaat
	 * @throws TransactionRequiredException
	 *             als er een probleem is met het opzetten van de tranacties
	 */
	@Transactional
	public void voegWerknemerToe(Werknemer werknemer) {
		Team team = werknemer.getTeam();
		if (team != null && team.getCode() == 0) {
			em.persist(team);
		}
		em.persist(werknemer);

	}

	/**
	 * Geeft een lijst terug van alle werknemers
	 * 
	 * @return List<Werknemer>
	 */
	public List<Werknemer> getAlleWerknemers() {

		TypedQuery<Werknemer> tqry = em.createQuery("SELECT w FROM Werknemer w", Werknemer.class);
		return tqry.getResultList();

	}

	/**
	 * Geeft een werknemer terug de opgegeven personeelsnummer
	 * 
	 * @param personeelnr
	 * @return Werknemer
	 */
	public Werknemer getWerknemer(int personeelnr) {
		return em.find(Werknemer.class, personeelnr);
	}

	/**
	 * Geeft alle werknemers uit een bepaald team terug
	 * 
	 * @param teamCode
	 * @return List<Werknemer>
	 */
	public List<Werknemer> getWerknemers(int teamCode) {
		TypedQuery<Werknemer> tqry = em.createQuery("SELECT w FROM Werknemer w WHERE w.team.code = :code", Werknemer.class);
		tqry.setParameter("code", teamCode);
		return tqry.getResultList();
	}

	/**
	 * update een werknemer in de database (opgelet: de verlofaanvragen worden
	 * niet automatisch aangepast als er wijzigingen zouden zijn!!)
	 * 
	 * @param werknemer
	 */
	@Transactional
	public void updateWerknemer(Werknemer werknemer) {
		Werknemer tmp = em.find(Werknemer.class, werknemer.getPersoneelsnummer());
		if (tmp != null ) {
				tmp.setGegevens(werknemer);
		}
		else
			throw new IllegalArgumentException("geen werknemer is gevonden met  personeel nr" +werknemer.getPersoneelsnummer() );

	}

	/**
	 * Verwijdert een werknemer uit de database en ook zijn bijhorende
	 * verlofaanvragen
	 * 
	 * @param werknemer
	 */
	@Transactional
	public void verwijderWerknemer(Werknemer werknemer) {

		if (werknemer != null && !werknemer.isVerantwoordelijke()) {
			Werknemer w = em.find(Werknemer.class, werknemer.getPersoneelsnummer());

			List<VerlofAanvraag> verlofaanvragen = w.getAlleVerlofAanvragen();
			for (VerlofAanvraag verlofAanvraag : verlofaanvragen) {
				VerlofAanvraag va = em.find(VerlofAanvraag.class, verlofAanvraag.getId());
				em.remove(va);
			}

			List<JaarlijksVerlof> jaarlijkseverloven = w.getJaarlijkseverloven();
			for (JaarlijksVerlof jaarlijksVerlof : jaarlijkseverloven) {
				JaarlijksVerlof jv = em.find(JaarlijksVerlof.class, jaarlijksVerlof.getId());
				em.remove(jv);
			}

			em.remove(w);

		} else {
			if (werknemer == null) {
				throw new NullPointerException("WerknemerDAO.verwijderWerknemer kan niet worden uitgevoerd op null");
			} else {
				throw new IllegalArgumentException(
						"WerknemerDAO.verwijderWerknemer kan niet worden uitgevoerd, omdat de werknemer teamverantwoordelijke is");
			}
		}
	}

	/**
	 * Geeft een werknemer met een bepaald e-mail adres terug
	 * 
	 * @param email
	 * @return Werknemer
	 */
	public Werknemer getWerknemer(String email) {
		TypedQuery<Werknemer> tqry = em.createQuery("SELECT w FROM Werknemer w WHERE w.email LIKE :email", Werknemer.class);
		tqry.setParameter("email", "%" + email + "%");
		return tqry.getSingleResult();
	}

	/**
	 * Geeft een lijst met werknemers terug met een gedeeltelijke naam en
	 * gedeeltelijke voornaam
	 * 
	 * @param zoekNaam
	 * @param zoekVoornaam
	 * @return List<Werknemer>
	 */
	public List<Werknemer> getWerknemers(String zoekNaam, String zoekVoornaam) {
		TypedQuery<Werknemer> tqry = em.createQuery("SELECT w FROM Werknemer w WHERE w.naam LIKE :naam AND w.voornaam LIKE :voornaam",
				Werknemer.class);
		tqry.setParameter("naam", "%" + zoekNaam + "%");
		tqry.setParameter("voornaam", "%" + zoekVoornaam + "%");
		return tqry.getResultList();
	}

	/**
	 * Geeft een lijst met werknemers terug met een gedeeltelijke naam en
	 * gedeeltelijke voornaam en een personeelsnummer. Als het personeelsnr 0 is
	 * dan zoekt hij enkel op naam en voornaam
	 * 
	 * @param zoekNaam
	 * @param zoekVoornaam
	 * @param personeelsnr
	 * @returnList<Werknemer>
	 */
	public List<Werknemer> getWerknemers(String zoekNaam, String zoekVoornaam, int personeelsnr) {
		if (personeelsnr != 0) {
			TypedQuery<Werknemer> tqry = em.createQuery(
					"SELECT w FROM Werknemer w WHERE w.naam LIKE :naam AND w.voornaam LIKE :voornaam AND w.personeelsnummer = :nr", Werknemer.class);
			tqry.setParameter("naam", "%" + zoekNaam + "%");
			tqry.setParameter("voornaam", "%" + zoekVoornaam + "%");
			tqry.setParameter("nr", personeelsnr);
			return tqry.getResultList();
		} else
			return getWerknemers(zoekNaam, zoekVoornaam);

	}

	public List<Werknemer> getWerknemers(Filter filter) {
		String querystring = "SELECT w FROM Werknemer w";
		if (!filter.isEmpty()) {
			querystring += " WHERE";
			int aantal = 0;
			for (String key : filter) {
				if (aantal != 0) {
					querystring = querystring + " AND";
				}
				if (key.equals("naam") || key.equals("voornaam")) {
					querystring += " w." + key + " LIKE :" + key;
					aantal++;
				}
				if (key.equals("personeelsnummer") || key.equals("team.code")) {
					querystring += " w." + key + " = :" + key.replace(".", "");
					aantal++;
				}
			}
		}

		TypedQuery<Werknemer> query = em.createQuery(querystring, Werknemer.class);

		for (String key : filter) {
			if (key.equals("naam") || key.equals("voornaam")) {
				query.setParameter(key, "%" + filter.getValue(key) + "%");
			} else {
				query.setParameter(key.replace(".", ""), filter.getValue(key));
			}
		}

		return query.getResultList();

	}

}
