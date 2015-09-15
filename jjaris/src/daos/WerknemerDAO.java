package daos;


import java.util.ArrayList;
import java.util.List;










import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import entities.Team;
import entities.Werknemer;

@ApplicationScoped
public class WerknemerDAO {
@PersistenceContext(unitName = "jjaris")
private EntityManager em  ;
	
//static{
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jjaris");
//	em = emf.createEntityManager();
//	System.out.println("in static van werknemerDAO");
//}

/**
 * 	Voegt een werknemer toe aan de database. Via Cascade zou ook het adres en het Jaarlijks verlof moeten ingevuld worden
 * @param werknemer:Werknemer
 * @throws EntityExistsException als er al een werknemer met dezelfde id bestaat
 * @throws TransactionRequiredException als er een probleem is met het opzetten van de tranacties
 */
	@Transactional
	public void voegWerknemerToe(Werknemer werknemer){
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
		Team team = werknemer.getTeam();
		
		if (team != null){
		em.find(Team.class, team.getCode());
		em.persist(team);
		}
		em.persist(werknemer);
			
//			tx.commit();
		
	}
	
	/**
	 * Geeft een lijst terug van alle werknemers
	 * @return List<Werknemer>
	 */
	public List<Werknemer> getAlleWerknemers(){
		List<Werknemer> werknemers = new ArrayList<Werknemer>();
		return werknemers;
	}
	
	
}
