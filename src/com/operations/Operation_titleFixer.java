package com.operations;

import java.io.File;
import java.util.ArrayList;

import com.msc.Music_Form;

public class Operation_titleFixer {

	public static void editTitle(ArrayList<File> files, String selection){
		// selection : "title" , "artist-title" , "artisttitle" , "title-artist" , "titleartist"
		ArrayList<File> newList = new ArrayList<File>();
		String fpath = "";
		String extension = "";
		String title;
		String artist;
		String newName = "";
		for(File f : files){
			fpath = Operation_pathOps.seperatePath(f).toString();
			extension = Operation_pathOps.seperateNameAndExtension(f)[1];
			if ((extension.equals("mp3")) || (extension.equals("MP3"))){
				title = Operation_metadata.getTitle(f);
				artist = Operation_metadata.getArtist(f);

				if (selection.equals("title")){
					newName = title;
				}
				else if (selection.equals("artist-title")){
					if (Operation_metadata.isValidMetadata(artist)){
						newName = artist + " - " + title;
					}
					else{
						newName = title;
					}
				}
				else if (selection.equals("artisttitle")){
					if (Operation_metadata.isValidMetadata(artist)){
						newName = artist + " " + title;
					}
					else{
						newName = title;
					}
				}
				else if (selection.equals("title-artist")){
					if (Operation_metadata.isValidMetadata(artist)){
						newName = title + " - " + artist;
					}
					else{
						newName = title;
					}
				}
				else if (selection.equals("titleartist")){
					if (Operation_metadata.isValidMetadata(artist)){
						newName = title + " " + artist;
					}
					else{
						newName = title;
					}
				}
				String newFullName = fpath + "/" + newName + "." + extension;
				//System.out.println(newFullName);
				f.renameTo(new File(newFullName));
				newList.add(new File(newFullName));
			}
		}
		Music_Form.files = newList;
		Music_Form.refreshList();
		for(File f : newList){
			System.out.println(f.toString());
		}
	}

}