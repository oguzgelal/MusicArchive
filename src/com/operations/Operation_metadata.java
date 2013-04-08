package com.operations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v2_2;
import org.farng.mp3.id3.ID3v2_3;
import org.farng.mp3.id3.ID3v2_4;
import org.farng.mp3.lyrics3.Lyrics3v2;


public class Operation_metadata {
	static MP3File mp3file;
	static ID3v1 tag1 = new ID3v1();
	static AbstractID3v2 tag2_2 = new ID3v2_2();
	static AbstractID3v2 tag2_3 = new ID3v2_3();
	static Lyrics3v2 tag3_2 = new Lyrics3v2();


	private static void run(File file){
		String fname = file.getName();
		if ((Operation_pathOps.seperateNameAndExtension(file)[1].equals("mp3")) || (Operation_pathOps.seperateNameAndExtension(file)[1].equals("MP3"))){
			try {
				mp3file = new MP3File(file);
			} catch (IOException e) {
				//JOptionPane.showMessageDialog(null, "An error has ocurred ("+e.toString()+")");
				//System.exit(0);
			} catch (TagException e) {
				//JOptionPane.showMessageDialog(null, "An error has ocurred ("+e.toString()+")");
				//System.exit(0);
			}
			catch (Exception e){
				
			}
		}
		if (mp3file != null){
		tag1 = mp3file.getID3v1Tag();
		tag2_2 = mp3file.getID3v2Tag();
		tag2_3 = mp3file.getID3v2Tag();
		tag3_2 = (Lyrics3v2) mp3file.getLyrics3Tag();
		}
	}
	// get title
	public static String getTitle(File file){
		run(file);
		if(tag1 != null){
			String ret1 = tag1.getSongTitle();
			if((ret1 != null) && (ret1 != "")){
				return ret1;
			}
		}
		else if(tag2_2 != null){
			String ret2 = tag2_2.getSongTitle();
			if((ret2 != null) && (ret2 != "")){
				return ret2;
			}
		}
		else if(tag2_3 != null){
			String ret3 = tag2_3.getSongTitle();
			if((ret3 != null) && (ret3 != "")){
				return ret3;
			}
		}
		else if (tag3_2 != null){
			String ret4 = tag3_2.getSongTitle();
			if((ret4 != null) && (ret4 != "")){
				return ret4;
			}
		}
		return Operation_pathOps.seperateNameAndExtension(file)[0];
	}
	
	public static String getArtist(File file){
		run(file);
		if(tag1 != null){
			String ret1 = tag1.getArtist();
			if((ret1 != null) && (ret1 != "") && (ret1 != " ")){
				return ret1;
			}
		}
		else if(tag2_2 != null){
			String ret2 = tag2_2.getLeadArtist();
			if((ret2 != null) && (ret2 != "") && (ret2 != " ")){
				return ret2;
			}
		}
		else if (tag2_3 != null){
			String ret3 = tag2_3.getLeadArtist();
			if((ret3 != null) && (ret3 != "") && (ret3 != " ")){
				return ret3;
			}
		}
		else if (tag3_2 != null){
			String ret4 = tag3_2.getLeadArtist();
			if((ret4 != null) && (ret4 != "") && (ret4 != " ")){
				return ret4;
			}
		}
		return "Unknown Artist";
	}

	public static String getAlbum(File file){
		run(file);
		if(tag1 != null){
			String ret1 = tag1.getAlbum();
			if(ret1 != null){
				return ret1;
			}
		}
		else if(tag2_2 != null){
			String ret2 = tag2_2.getAlbumTitle();
			if(ret2 != null){
				return ret2;
			}
		}
		else if(tag2_3 != null){
			String ret3 = tag2_3.getAlbumTitle();
			if((ret3 != null) && (ret3 != "")){
				return ret3;
			}
		}
		else if (tag3_2 != null){
			String ret4 = tag3_2.getAlbumTitle();
			if(ret4 != null){
				return ret4;
			}
		}
		return "Unknown Album";
	}
	
	public static boolean isValidMetadata(String meta){
		if(meta == null){
		return false;
		}
		else if (meta.equals("")){
			return false;
		}
		else if (meta.equals(" ")){
			return false;
		}
		else{
			String lower = meta.toLowerCase().replaceAll(" ", "");
			if(lower.indexOf("unknown") != -1){
				return false;
			}
			else{
				return true;
			}
		}
	}

	public static void listFile(ArrayList<File> files){
		for(int i = 0; i < files.size(); i++){
			if(files.get(i) != null){
				System.out.print(isValidMetadata(getArtist(files.get(i))));
				System.out.print(" - ");
				System.out.print(getArtist(files.get(i)));
				System.out.println();
			}
			else{
				System.out.println("nullpointerexception!!!!!!");
				break;
			}
		}
	}
}