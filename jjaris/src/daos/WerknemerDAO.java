package daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import entities.Werknemer;

public class WerknemerDAO {
	@PersistenceContext
private static EntityManager em ;
	
	
	@Transactional
	public static void voegWerknemerToe(Werknemer werknemer){
		em.get
	}
}
