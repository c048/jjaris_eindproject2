package daos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import entities.CollectiefVerlof;
import entities.Feestdag;
//.


@ApplicationScoped
public class CollectieveSluitingDAO {
	
	private int i;
	
	@PersistenceContext(unitName = "jjaris")  
	private EntityManager em;
	
	
	@Transactional
	public  List<Feestdag> getFeestdagen (Calendar begindatum, Calendar einddatum){
		
	
		TypedQuery<Feestdag> query = em.createQuery("SELECT f FROM Feestdag f WHERE f.startdatum"+
						" BETWEEN :begindatum AND :einddatum ",Feestdag.class);

		return (List<Feestdag>) query.setParameter("begindatum", begindatum)
				.setParameter("einddatum", einddatum).getResultList();

	}
	
	@Transactional
	public  List<Feestdag> getFeestdagen (){
		
		TypedQuery<Feestdag> query = em.createQuery("SELECT f FROM Feestdag f",Feestdag.class);

		return (List<Feestdag>) query.getResultList();

	}
	

	public  List<Feestdag> getAlleFeestdagen(int jaartal) {
		
		Calendar begindatum = GregorianCalendar.getInstance();
		begindatum.set(jaartal, Calendar.JANUARY, 1);  //January 1ste  jaartaal
		
		Calendar einddatum = GregorianCalendar.getInstance();
		einddatum.set(jaartal, Calendar.DECEMBER, 31);  //December  31ste  jaartaal
		
		return getFeestdagen(begindatum,einddatum );
	}
	
	@Transactional
	public  List<CollectiefVerlof> getCollectieveVerloven( ) {
				
		TypedQuery<CollectiefVerlof> query = em.createQuery("SELECT c FROM CollectiefVerlof c ",CollectiefVerlof.class);
		
		return (List<CollectiefVerlof>) query.getResultList();
		
	}
	
	@Transactional
	public  List<CollectiefVerlof> getCollectieveVerloven(Calendar begindatum, Calendar einddatum) {
				
		TypedQuery<CollectiefVerlof> query = em.createQuery("SELECT c FROM CollectiefVerlof c WHERE "+
				"((c.startdatum BETWEEN :begindatum  AND :einddatum ) "+
				"OR (c.einddatum BETWEEN :begindatum  AND :einddatum )) "+
				"OR (c.startdatum <= :begindatum AND c.einddatum >= :einddatum ) ",CollectiefVerlof.class);
		
		return (List<CollectiefVerlof>) query.setParameter("begindatum", begindatum)
				.setParameter("einddatum", einddatum).getResultList();
		
	}
	

	public  List<CollectiefVerlof> getAlleCollectieveVerloven (int jaartal) {
		
		Calendar begindatum = GregorianCalendar.getInstance();
		begindatum.set(jaartal, Calendar.JANUARY, 1);  //January 1ste  jaartaal
		
		Calendar einddatum = GregorianCalendar.getInstance();
		einddatum.set(jaartal, Calendar.DECEMBER, 31);  //December  31ste  jaartaal
		
		return getCollectieveVerloven(begindatum,einddatum);
	}
	
	
	
	@Transactional
	public void voegFeestdagToe(Calendar feestdatum , String omschrijving, boolean terugkerend ) {
		
		Feestdag feestdag;
		
		if(feestdatum!=null & omschrijving!=null ) {
			feestdag = new Feestdag(feestdatum, omschrijving, terugkerend);
			em.persist(feestdag);
		}
		

	}
	
	@Transactional
	public void voegCollectieveVerlofToe(Calendar startdatum ,Calendar einddatum,  String omschrijving, boolean terugkerend ) {
		//TODO exception. als alle gegevens zijn niet ingevuld??
		CollectiefVerlof collectieveVerlof;
		
		if(startdatum!=null & omschrijving!=null  & einddatum!=null) {
			collectieveVerlof = new CollectiefVerlof(startdatum, einddatum, omschrijving, terugkerend);
			
			em.persist(collectieveVerlof);
		}

	}
	
	@PreDestroy
	public void SluitAf(){
		em.close();
	}
	
}
