package daos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import entities.CollectiefVerlof;
import entities.Feestdag;


@ApplicationScoped
public class CollectieveSluitingDAO {
	
	
	@PersistenceContext(unitName = "jjaris")
	private static EntityManager em;
	
	
	@Transactional
	public  List<Feestdag> getFeestdagen (Calendar begindatum, Calendar einddatum){
		
	
		TypedQuery<Feestdag> query = em.createQuery("SELECT f FROM FEESDAGEN f WHERE f.startdatum"+
						" BETWEEN :begindatum AND :einddatum ",Feestdag.class);

		return (List<Feestdag>) query.setParameter("begindatum", begindatum)
				.setParameter("einddatum", einddatum).getResultList();

	}
	

	public  List<Feestdag> getAlleFeestdagen(int jaartal) {
		
		Calendar begindatum = GregorianCalendar.getInstance();
		begindatum.set(jaartal, Calendar.JANUARY, 1);  //January 1ste  jaartaal
		
		Calendar einddatum = GregorianCalendar.getInstance();
		einddatum.set(jaartal, Calendar.DECEMBER, 31);  //December  31ste  jaartaal
		
		return getFeestdagen(begindatum,einddatum );
	}
	
	@Transactional
	public  List<CollectiefVerlof> getCollectieveVerloven(Calendar begindatum, Calendar einddatum) {
		
		TypedQuery<CollectiefVerlof> query = em.createQuery("SELECT c FROM COLLECTIEVEVERLOVEN c WHERE "+
					"c.startdatum > :begindatum  AND c.einddatum < :einddatum ",CollectiefVerlof.class);
		
		return (List<CollectiefVerlof>) query.setParameter("startdatum", begindatum)
				.setParameter("einddatum", einddatum).getResultList();
		
	}
	
	@Transactional
	public  List<CollectiefVerlof> getAlleCollectieveVerloven (int jaartal) {
		
		Calendar begindatum = GregorianCalendar.getInstance();
		begindatum.set(jaartal, Calendar.JANUARY, 1);  //January 1ste  jaartaal
		
		Calendar einddatum = GregorianCalendar.getInstance();
		einddatum.set(jaartal, Calendar.DECEMBER, 31);  //December  31ste  jaartaal
		
		return getCollectieveVerloven(begindatum,einddatum);
	}
}
