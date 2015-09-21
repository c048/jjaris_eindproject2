package beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import entities.Toestand;


@Named
@ApplicationScoped
public class ToestandBack  {

	public Toestand[] getToestandLijst(){
		return Toestand.values();
				
	}
	
}
