package daos;


import java.util.ArrayList;
import java.util.List;





import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import entities.Werknemer;


public class WerknemerDAO {
//@PersistenceContext(unitName = "jjaris")
private static EntityManager em  ;
	
static{
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jjaris");
	em = emf.createEntityManager();
	System.out.println("in static van werknemerDAO");
}

/**
 * 	Voegt een werknemer toe aan de database. Via Cascade zou ook het adres en het Jaarlijks verlof moeten ingevuld worden
 * @param werknemer:Werknemer
 * @throws EntityExistsException als er al een werknemer met dezelfde id bestaat
 * @throws TransactionRequiredException als er een probleem is met het opzetten van de tranacties
 */
	@Transactional
	public static void voegWerknemerToe(Werknemer werknemer){
			em.persist(werknemer);
	}
	
	/**
	 * Geeft een lijst terug van alle werknemers
	 * @return List<Werknemer>
	 */
	public static List<Werknemer> getAlleWerknemers(){
		List<Werknemer> werknemers = new ArrayList<Werknemer>();
		return werknemers;
	}
	
	
}
