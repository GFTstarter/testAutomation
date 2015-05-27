package br.com.gft.testautomation.common.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** Class responsible for compressing the received list of files into a single .zip file.
 * Class made by Ricardo da Silva (ricardo.da-silva@db.com) */
public class CompressFiles {	

	private static final byte[] BUFFER = new byte[1024];

	private CompressFiles() {}

	/** Method that receives a .zip file and a list of files to be compressed into this .zip file */
	//TODO: DEBUGAR
	public static void zipIt(File outputZipFile, List<File> files){
		try{
			//Creating FileOutputStream and ZipOutputStream
			FileOutputStream fileOutputStream = new FileOutputStream(outputZipFile);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

			/* For each file in the list of files, put the file name into the ZipOutputStream 
			 * with the putNextEntry method, then write it. */
			for(File file : files){
				zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

				FileInputStream fileInputStream = new FileInputStream(file);

				int length;

				while ((length = fileInputStream.read(BUFFER)) > 0) {
					zipOutputStream.write(BUFFER, 0, length);
				}

				fileInputStream.close();
			}

			//Closing the streams
			zipOutputStream.closeEntry();
			zipOutputStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

