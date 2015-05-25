package br.com.gft.testautomation.common.unmarshaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.web.multipart.MultipartFile;

/** Class responsible for converting the XML file to String */
public class XmlToString {

	/** Method responsible for converting the XML file to String. 
	 * Receives a Multipart File and returns a String containing the full text
	 * from the file. */
	public static String convertToString(MultipartFile file){
		
		InputStream xml;
		String fulltext = "";
		try {
			/* Get the InputStream from the file*/
			xml = file.getInputStream();
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			String line;
			try{
				br = new BufferedReader(new InputStreamReader(xml));
				/* Read each line of the InputStream */
				while ((line = br.readLine()) != null){
					sb.append(line);			
					/* Check if there is a '&' in the read line. 
					 * If there is, replace this character with '&amp'.
					 * This way, the xml file will be fully recognized by the 
					 * unmarshaller. */
					if(line.contains("&")){
						line = line.replace("&", "&amp;");						
					}
					/* Append this line to the full text String*/
					fulltext = fulltext + line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null){
					try{
						/* Close the BufferedReader */
						br.close();
					} catch (IOException e){
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return fulltext;
	}
}
