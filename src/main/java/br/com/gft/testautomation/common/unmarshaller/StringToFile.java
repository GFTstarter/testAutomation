package br.com.gft.testautomation.common.unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/** Class responsible to revert the file converting, converting a String into file again */
public class StringToFile {

	/** Method responsible to revert the file converting, converting a String into file again,
	 * after the necessary character replacements happened.
	 * Receives a String and returns a File. */
	public static File stringToFile(String xml){
		
		/* Create a new File named release.xml in the memory */
		File file = new File("./release.xml");
		try (FileOutputStream fop = new FileOutputStream(file)){
			if(!file.exists()){
				file.createNewFile();
			}
			
			/* Get the bytes from the String text */
			byte[] contentInBytes = xml.getBytes();
			
			/* Write the bytes into the file */
			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
}
