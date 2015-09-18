package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
@Named
@SessionScoped
public class ParameterBack implements Serializable {
private static final long serialVersionUID = 1L;
private int personeelsnummer, code,jaarlijksverlofId,verlofaanvraagId,collectieveSluitingId;
public int getPersoneelsnummer() {
	return personeelsnummer;
}
public void setPersoneelsnummer(int personeelsnummer) {
	this.personeelsnummer = personeelsnummer;
}
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public int getJaarlijksverlofId() {
	return jaarlijksverlofId;
}
public void setJaarlijksverlofId(int jaarlijksverlofId) {
	this.jaarlijksverlofId = jaarlijksverlofId;
}
public int getVerlofaanvraagId() {
	return verlofaanvraagId;
}
public void setVerlofaanvraagId(int verlofaanvraagId) {
	this.verlofaanvraagId = verlofaanvraagId;
}
public int getCollectieveSluitingId() {
	return collectieveSluitingId;
}
public void setCollectieveSluitingId(int collectieveSluitingId) {
	this.collectieveSluitingId = collectieveSluitingId;
}



}
