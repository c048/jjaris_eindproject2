package beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daos.VerlofAanvraagDAO;
import entities.Team;
import entities.VerlofAanvraag;
import entities.Werknemer;


@Named("verlofTeamBack")
@RequestScoped
public class VerlofTeamBack implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Inject	
	private VerlofAanvraagDAO vDao;
	@Inject
	private ParameterBack parameters;
	
	private List<VerlofAanvraag> verlofaanvragen;
	
	private List<Werknemer> werknemers;
	private Werknemer werknemer;
	
	private Team team;
	
	
	
	

}
