package utils;

import java.util.HashMap;
import java.util.Map;


public class Filter {
	
	private Map<String, Object> filter = new HashMap<String, Object>();
	
	
	public Map<String, Object> getFilter() {
		return filter;
	}
	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}
	/**
	 * Voeg een filter toe
	 * @param naam:String
	 * @param object:Object
	 */
	public void voegFilterToe(String naam, Object object){
		
		filter.put(naam, object);
	}
	
	

}
