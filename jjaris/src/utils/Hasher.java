package utils;

import java.security.MessageDigest;

public class Hasher {
	
	public static String hash(String input) {
		StringBuffer sb = new StringBuffer();
		input = "jfdcn" + input + "sfhf564894231";
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(input.getBytes("UTF-8"));
	        byte[] hash = md.digest();
	        
	        for (int i = 0; i < hash.length; i++) {
	        	sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
	        }   
	    } 
	    catch (Exception e) {
	        e.printStackTrace(System.err);
	    }
		return sb.toString();
	}

}
