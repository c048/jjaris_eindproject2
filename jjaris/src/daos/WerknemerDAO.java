package daos;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import entities.Team;
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

		if (team != null) {
			if (team.getCode() == 0) {
				em.persist(team);
			}
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
	 * @param personeelnr
	 * @return Werknemer
	 */
	public Werknemer getWerknemer(int personeelnr){
		return em.find(Werknemer.class, personeelnr);
	}
	
	/**
	 * Geeft alle werknemers uit een bepaald team terug
	 * @param teamCode
	 * @return List<Werknemer>
	 */
	public List<Werknemer> getWerknemers (int teamCode){
		TypedQuery<Werknemer> tqry = em.createQuery("SELECT w FROM Werknemer w WHERE w.team.code = :code", Werknemer.class);
		tqry.setParameter("code", teamCode);
		return tqry.getResultList();
	}
	
	public void updateWerknemer(Werknemer werknemer){
		Werknemer tmp = em.find(Werknemer.class, werknemer.getPersoneelsnummer());
		tmp.setAdres(werknemer.getAdres());
		tmp.setEmail(werknemer.getEmail());
		tmp.setGeboortedatum(werknemer.getGeboortedatum());
	}
	
	
	
	
	
	
	

}
