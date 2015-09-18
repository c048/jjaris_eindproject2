package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
	
	private int startdatumFJaar;
	private int startdatumFMaand;
	private int startdatumFDag;
	
	private int startdatumCVJaar;
	private int startdatumCVMaand;
	private int startdatumCVDag;
	
	private int einddatumCVJaar;
	private int einddatumCVMaand;
	private int einddatumCVDag;
	
	
	private String omschrijvingF;
	private String omschrijvingCV;
	
	private boolean terugkerendF;
	private boolean terugkerendCV;
	



	public String voegFeestdagToe() {
	
		Calendar feestdag = new GregorianCalendar();

		feestdag.set(Calendar.YEAR, startdatumFJaar);
		feestdag.set(Calendar.MONTH, startdatumFMaand-1);
		feestdag.set(Calendar.DAY_OF_MONTH, startdatumFDag);
		
		dao.voegFeestdagToe(feestdag, omschrijvingF, terugkerendF);
		
		System.out.println("*********Debug:feestdag" +feestdag.get(Calendar.DAY_OF_MONTH)+
				"-"+feestdag.get(Calendar.MONTH) + 
				"-" +feestdag.get(Calendar.YEAR));
		
		return "collectieveSluiting";
	}
	
	public String voegCollectieveVerlofToe() {

		Calendar csBegindatum = new GregorianCalendar();

		csBegindatum.set(Calendar.YEAR, startdatumCVJaar);
		csBegindatum.set(Calendar.MONTH, startdatumCVMaand-1);
		csBegindatum.set(Calendar.DAY_OF_MONTH, startdatumCVDag);
		
		System.out.println("*********Debug:begindatumCV" +csBegindatum.get(Calendar.DAY_OF_MONTH)+
							"-"+csBegindatum.get(Calendar.MONTH) + 
							"-" +csBegindatum.get(Calendar.YEAR));
		
		Calendar csEinddatum = new GregorianCalendar();
		

		csEinddatum.set(Calendar.YEAR, einddatumCVJaar);
		csEinddatum.set(Calendar.MONTH, einddatumCVMaand-1);
		csEinddatum.set(Calendar.DAY_OF_MONTH, einddatumCVDag);
		
		System.out.println("*********Debug:einddatumCV" +csEinddatum.get(Calendar.DAY_OF_MONTH)+
				"-"+csEinddatum.get(Calendar.MONTH) + 
				"-" +csEinddatum.get(Calendar.YEAR));
		
		dao.voegCollectieveVerlofToe(csBegindatum, csEinddatum,omschrijvingCV, terugkerendCV);
		
		return "collectieveSluiting";
	}

	public int getStartdatumFJaar() {
		return startdatumFJaar;
	}

	public void setStartdatumFJaar(int startdatumFJaar) {
		this.startdatumFJaar = startdatumFJaar;
	}

	public int getStartdatumFMaand() {
		return startdatumFMaand;
	}

	public void setStartdatumFMaand(int startdatumFMaand) {
		this.startdatumFMaand = startdatumFMaand;
	}

	public int getStartdatumFDag() {
		return startdatumFDag;
	}

	public void setStartdatumFDag(int startdatumFDag) {
		this.startdatumFDag = startdatumFDag;
	}

	public int getStartdatumCVJaar() {
		return startdatumCVJaar;
	}

	public void setStartdatumCVJaar(int startdatumCVJaar) {
		this.startdatumCVJaar = startdatumCVJaar;
	}

	public int getStartdatumCVMaand() {
		return startdatumCVMaand;
	}

	public void setStartdatumCVMaand(int startdatumCVMaand) {
		this.startdatumCVMaand = startdatumCVMaand;
	}

	public int getStartdatumCVDag() {
		return startdatumCVDag;
	}

	public void setStartdatumCVDag(int startdatumCVDag) {
		this.startdatumCVDag = startdatumCVDag;
	}

	public int getEinddatumCVJaar() {
		return einddatumCVJaar;
	}

	public void setEinddatumCVJaar(int einddatumCVJaar) {
		this.einddatumCVJaar = einddatumCVJaar;
	}

	public int getEinddatumCVMaand() {
		return einddatumCVMaand;
	}

	public void setEinddatumCVMaand(int einddatumCVMaand) {
		this.einddatumCVMaand = einddatumCVMaand;
	}

	public int getEinddatumCVDag() {
		return einddatumCVDag;
	}

	public void setEinddatumCVDag(int einddatumCVDag) {
		this.einddatumCVDag = einddatumCVDag;
	}

	public String getOmschrijvingF() {
		return omschrijvingF;
	}

	public void setOmschrijvingF(String omschrijvingF) {
		this.omschrijvingF = omschrijvingF;
	}

	public String getOmschrijvingCV() {
		return omschrijvingCV;
	}

	public void setOmschrijvingCV(String omschrijvingCV) {
		System.out.println("*********Debug:omschrijvingCV" +omschrijvingCV );
		this.omschrijvingCV = omschrijvingCV;
	}

	public boolean isTerugkerendF() {
		return terugkerendF;
	}

	public void setTerugkerendF(boolean terugkerendF) {
		this.terugkerendF = terugkerendF;
	}

	public boolean isTerugkerendCV() {
		return terugkerendCV;
	}

	public void setTerugkerendCV(boolean terugkerendCV) {
		this.terugkerendCV = terugkerendCV;
	}
	
	
	
}
