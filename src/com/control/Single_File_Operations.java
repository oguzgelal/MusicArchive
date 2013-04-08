package com.control;

import java.io.File;
import com.operations.*;

public class Single_File_Operations {

	// trim beginning (Operation_trimBeginning)
	public File trimBeginning(File file, String path, String bandName){
		return Operation_trimBeginning.trimBeginning(file, path, bandName);
	}
	
	// seperate name and extension (Operation_pathOps) 
	public static String[] seperateNameAndExtension(File file){
		return Operation_pathOps.seperateNameAndExtension(file);
	}
	
	// seperate path (Operation_pathOps)
	public static String seperatePath(File file){
		return Operation_pathOps.seperatePath(file);
	}
	
	// get Title (Operation_metadata)
	public static String getTitle(File file){
		return Operation_metadata.getTitle(file);
	}
	
	// get Artist (Operation_metadata)
	public static String getArtist(File file){
		return Operation_metadata.getArtist(file);
	}
	
	// get Album (Operation_metadata)
	public static String getAlbum(File file){
		return Operation_metadata.getAlbum(file);
	}
	
	// copy to clipboard (Operation_pathOps)
	public static void copyToClipboard(String str){
		Operation_pathOps.copyToClipboard(str);
	}
}