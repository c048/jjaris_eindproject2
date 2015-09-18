package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ParameterBack implements Serializable {
	private static final long serialVersionUID = 1L;
	private int personeelsnummer;
	private int code;
	private int jaarlijksverlofId;
	private int verlofaanvraagId;
	private int collectieveSluitingId;

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
	
	public void reset() {
		setCode(0);
		setCollectieveSluitingId(0);
		setJaarlijksverlofId(0);
		setPersoneelsnummer(0);
		setVerlofaanvraagId(0);
	}

}
