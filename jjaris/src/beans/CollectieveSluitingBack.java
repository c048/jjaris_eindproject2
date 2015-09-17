package beans;

import java.io.Serializable;
import java.util.Calendar;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.CollectieveSluitingDAO;
import daos.WerknemerDAO;

@Named("collectievesluiting")
@RequestScoped
public class CollectieveSluitingBack implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CollectieveSluitingDAO dao;
	
	private int startdatumJaar;
	private int startdatumMaand;
	private int startdatumDag;
	
	private int einddatumJaar;
	private int einddatumMaand;
	private int einddatumDag;
	
	 // private Calendar startdatum;
	 // private Calendar einddatum;
	
	private String omschrijving;
	private boolean terugkerend;
	


	public int getStartdatumJaar() {
		return startdatumJaar;
	}

	public void setStartdatumJaar(int startdatumJaar) {
		this.startdatumJaar = startdatumJaar;
	}

	public int getStartdatumMaand() {
		return startdatumMaand;
	}

	public void setStartdatumMaand(int startdatumMaand) {
		this.startdatumMaand = startdatumMaand;
	}

	public int getStartdatumDag() {
		return startdatumDag;
	}

	public void setStartdatumDag(int startdatumDag) {
		this.startdatumDag = startdatumDag;
	}

	public int getEinddatumJaar() {
		return einddatumJaar;
	}

	public void setEinddatumJaar(int einddatumJaar) {
		this.einddatumJaar = einddatumJaar;
	}

	public int getEinddatumMaand() {
		return einddatumMaand;
	}

	public void setEinddatumMaand(int einddatumMaand) {
		this.einddatumMaand = einddatumMaand;
	}

	public int getEinddatumDag() {
		return einddatumDag;
	}

	public void setEinddatumDag(int einddatumDag) {
		this.einddatumDag = einddatumDag;
	}

	/*
	public Calendar getStartdatum() {
		return startdatum;
	}

	public void setStartdatum(Calendar startdatum) {
		this.startdatum = startdatum;
	}

	public Calendar getEinddatum() {
		return einddatum;
	}

	public void setEinddatum(Calendar einddatum) {
		this.einddatum = einddatum;
	}
*/
	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public boolean isTerugkerend() {
		return terugkerend;
	}

	public void setTerugkerend(boolean terugkerend) {
		this.terugkerend = terugkerend;
	}

	public String voegFeestdagToe() {
	
		Calendar feestdag = Calendar.getInstance();
		feestdag.set(Calendar.YEAR, startdatumJaar);
		feestdag.set(Calendar.MONTH, startdatumMaand);
		feestdag.set(Calendar.DAY_OF_MONTH, startdatumDag);
		
		dao.voegFeestdagToe(feestdag, omschrijving, terugkerend);
		return "collectieveSluiting";
	}
	
	public String voegCollectieveVerlofToe() {
		//TODO
		return "collectieveSluiting";
	}
	
}
