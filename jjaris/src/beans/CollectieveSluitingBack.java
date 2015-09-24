package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import utils.DatumBuilder;
import daos.CollectieveSluitingDAO;
import entities.CollectiefVerlof;
import entities.Feestdag;

@Named("collectievesluiting")
@RequestScoped
public class CollectieveSluitingBack implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CollectieveSluitingDAO dao;
	
	@Inject
	private LoginBack loginBack;
	@Inject
	private ParameterBack params;
	
	private int startdatumFJaar;
	private int startdatumFMaand;
	private int startdatumFDag;
	
	private int startdatumCVJaar;
	private int startdatumCVMaand;
	private int startdatumCVDag;
	
	private int einddatumCVJaar;
	private int einddatumCVMaand;
	private int einddatumCVDag;
	
	@NotNull
	private String omschrijvingF;
	@NotNull
	private String omschrijvingCV;
	
	private boolean terugkerendF;
	private boolean terugkerendCV;
	



	public String voegFeestdagToe() {

		try {
			DatumBuilder  feestDag=new DatumBuilder(startdatumFDag, startdatumFMaand, startdatumFJaar);
			if(omschrijvingF!=null && !omschrijvingF.equals(""))
				dao.voegFeestdagToe(feestDag.buildCalendar(), omschrijvingF, terugkerendF);
			else
				setFacesMessage("Omschrijving moet ingevuld zijn  ");

		}
		catch(IllegalArgumentException eIllArg){
			setFacesMessage("Incorrect datum  gegevens");
			
		}
		
		params.reset();
		reset();
		loginBack.changePage("collectieveSluitingHr");
		return null;

	}
	
	public String voegCollectieveVerlofToe() {
		
		
		try {
			DatumBuilder  csBegindatum=new DatumBuilder(startdatumCVDag, startdatumCVMaand, startdatumCVJaar);
			DatumBuilder  csEinddatum=new DatumBuilder(einddatumCVDag, einddatumCVMaand, einddatumCVJaar);
			
			if(omschrijvingCV!=null && !omschrijvingCV.equals("")) {
				if(csEinddatum.buildCalendar().after((Calendar)csBegindatum.buildCalendar() ))
						dao.voegCollectieveVerlofToe(csBegindatum.buildCalendar(), csEinddatum.buildCalendar(),omschrijvingCV, terugkerendCV);
				else
					setFacesMessage("Begin datum moet voor eind datum liggen   ");
			}
			else
				setFacesMessage("Omschrijving moet ingevuld zijn  ");
		}
		catch(IllegalArgumentException eIllArg){
			setFacesMessage("Incorrect datum  gegevens");
			
		}
		
		reset();
		params.reset();
		loginBack.changePage("collectieveSluitingHr");
		return null;
	}
	
	public List<Feestdag> getFeestdagen() {
		
		Calendar cal=Calendar.getInstance();
		//alleen de feestdagen die later dan dit jaar zijn getoond worden
		return dao.getFeestdagen().stream().filter(j -> j.getJaar() >= cal.get(Calendar.YEAR)).collect(Collectors.toList()); 

	}
	
	public List<CollectiefVerlof> getCollectieveVerloven() {
		
		Calendar cal=Calendar.getInstance();
		return dao.getCollectieveVerloven().stream().filter(j -> j.getBeginJaar() >= cal.get(Calendar.YEAR)).collect(Collectors.toList()); 
		
		//return dao.getAlleCollectieveVerloven(cal.get(Calendar.YEAR));	

	}
	
	public String terugAction(){
		reset();
		params.reset();
		loginBack.changePage("medewerkersHr");
		return null;
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
	
	public void setFacesMessage(String msg ) {
		FacesMessage fMsg = new FacesMessage(msg);
		fMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void reset(){
		
		startdatumFJaar=0;
		startdatumFMaand=0;
		startdatumFDag=0;
		
		startdatumCVJaar=0;
		startdatumCVMaand=0;
		startdatumCVDag=0;
		
		einddatumCVJaar=0;
		einddatumCVMaand=0;
		einddatumCVDag=0;
		

		omschrijvingF=null;
		omschrijvingCV=null;
		
		terugkerendF=false;
		terugkerendCV=false;
		
	}
	
}
