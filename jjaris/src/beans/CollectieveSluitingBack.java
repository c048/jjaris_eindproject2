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
	
	 // private Calendar startdatum;
	 // private Calendar einddatum;
	
	private String omschrijvingF;
	private String omschrijvingCV;
	
	private boolean terugkerendF;
	private boolean terugkerendCV;
	



	public String voegFeestdagToe() {
	
		Calendar feestdag = new GregorianCalendar();

		feestdag.set(Calendar.YEAR, startdatumFJaar);
		feestdag.set(Calendar.MONTH, startdatumFMaand);
		feestdag.set(Calendar.DAY_OF_MONTH, startdatumFDag);
		
		//System.out.println("*********Debug: FeestDatum : " + feestdag.get(Calendar.DAY_OF_MONTH)
		//				+"-"+feestdag.get(Calendar.MONTH)+"-"+feestdag.get(Calendar.YEAR));
		dao.voegFeestdagToe(feestdag, omschrijvingF, terugkerendF);
		
		return "collectieveSluiting";
	}
	
	public String voegCollectieveVerlofToe() {
		//TODO
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
