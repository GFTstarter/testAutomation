package br.com.gft.testautomation.common.login;

import org.apache.commons.lang3.text.WordUtils;

/** Class that contains methods to support the Login module. */
public class LoginUtils {

	/** Method that split the email into first name and last name.
	 * Receive the whole email and return a array of Strings containing
	 * the first name and last name. */
	public static String[] splitEmail(String email){
		
		String[] names = email.split("\\.", -1);

		//If contains "-" in the first name
		if (names[0].contains("-")){
			String[] firstNames = names[0].split("\\-", -1);
			names[0] = WordUtils.capitalize(firstNames[0]) + " " + WordUtils.capitalize(firstNames[1]);
		}		
		//If contains "-" in the last name
		if (names[1].contains("-")){
			 String[] surnames = names[1].split("\\-", -1);
			 names[1] = surnames[0] + " " + WordUtils.capitalize(surnames[1]);			 
		}else{
			//Capitalize the last name
			names[1] = WordUtils.capitalize(names[1]);
		}		
		
		//Remove the @something.com
		if (names[1].contains("@")){
			String afterAt[] = names[1].split("\\@", -1);
			names[1] = afterAt[0];
		}
		//Capitalize the first name
		names[0] = WordUtils.capitalize(names[0]);
		
		return names; 				
	}
}
