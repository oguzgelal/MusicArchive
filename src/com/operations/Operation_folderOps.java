package com.operations;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.msc.Music_Form;
import com.msc.Notification_Form;

public class Operation_folderOps {

	public static void extractFolders(ArrayList<File> files, File path, boolean ask){
		ArrayList<File> problem_files = new ArrayList<File>();
		int res;
		if (ask){
		res = JOptionPane.showConfirmDialog(null, "Are you sure you want to make this change ?", "Apply Changes ?", JOptionPane.YES_NO_OPTION);
		}
		else{
			res = JOptionPane.YES_OPTION;
		}
		if (res == JOptionPane.YES_OPTION){
			for(File file : files){
				try{
					if (!Operation_pathOps.seperatePath(file).toString().equals(path.toString())){
						File newFile = new File(Operation_trimBeginning.trimWhiteSpaces(path.toString())+"/"+Operation_trimBeginning.trimWhiteSpaces(file.getName()));
						ArrayList<File> controlFiles = Operation_pathOps.searchPath(path, false);
						newFile = Operation_pathOps.changeNameIfFileExists(controlFiles, newFile);
						file.renameTo(newFile);
					}
				}
				catch (Exception e){
					problem_files.add(file);
				}
			}
			if(!problem_files.isEmpty()){
				Notification_Form form = new Notification_Form(problem_files, "An error has been detected with following file(s).");
				form.runNotification();
			}
			problem_files.clear();
			ArrayList<File> searchPath = Operation_pathOps.searchPathFolders(path);
			for(File file : searchPath){
				try{
					deletePath(file);
				}
				catch(Exception e){
					problem_files.add(file);
				}
			}
			if(!problem_files.isEmpty()){
				Notification_Form form = new Notification_Form(problem_files, "An error has been detected deleting following folder(s).");
				form.runNotification();
			}
			ArrayList<File> newFilesList = new ArrayList<File>();
			newFilesList = Operation_pathOps.searchPath(path, true);
			Music_Form.files = newFilesList;
			Music_Form.refreshList();
		}
	}

	public static void deletePath(File file){
		File[] filesToDelete = file.listFiles();
		for(File file_i : filesToDelete){
			if(file_i.isDirectory()){
				deletePath(file_i);
			}
			else{
				file_i.delete();
			}
		}
		file.delete();
	}

	public static void Folderify(File path, String type, boolean ask, boolean refreshForm){
		// type : "byartist", "byalbum", "byartistalbum"
		ArrayList<File> files = Operation_pathOps.searchPath(path, true);
		extractFolders(files, path, ask);
		files = Operation_pathOps.searchPath(path, false);
		ArrayList<String> artists = new ArrayList<String>();
		ArrayList<String> albums = new ArrayList<String>();
		if (type.equals("byartist")){
			Music_Form.setFolderifyToWait();
			
			for(File f : files){
				FolderifyByArtist(path, artists, f);
			}
			
			ArrayList<File> newFiles = Operation_pathOps.searchPath(path, true);
			Music_Form.files = newFiles;
			Music_Form.refreshList();
			Music_Form.closeThis();
		}
		
		else if (type.equals("byalbum")){
			Music_Form.setFolderifyToWait();

			for(File f : files){
				FolderifyByAlbum(path, albums, f);
			}

			ArrayList<File> newFiles = Operation_pathOps.searchPath(path, true);
			Music_Form.files = newFiles;
			Music_Form.refreshList();
			Music_Form.closeThis();
		}
		else if (type.equals("byartistalbum")){
			Music_Form.setFolderifyToWait();

			for(File f : files){
				FolderifyByArtist(path, artists, f);
			}

			ArrayList<File> newFolders = new ArrayList<File>();
			newFolders = Operation_pathOps.searchPathFolders(path);
			for(File f : newFolders){
				Folderify(f, "byalbum", false, false); // recursive
			}
			ArrayList<File> newFiles = Operation_pathOps.searchPath(path, true);
			Music_Form.files = newFiles;
			Music_Form.refreshList();
			Music_Form.closeThis();
		}
	}
	
	public static void FolderifyByArtist(File path, ArrayList<String> artists, File f){
		String artist = "";
		if ((Operation_pathOps.seperateNameAndExtension(f)[1].equals("mp3")) || (Operation_pathOps.seperateNameAndExtension(f)[1].equals("MP3"))){
			artist = Operation_metadata.getArtist(f);
			artist = artist.replaceAll("/", "");
			if (artist.equals("")){
				artist = "Unknown Artist";
			}
		}
		else{
			artist = "Unsupported File(s)";
		}
		//System.out.print(f.getName() + " - " + artist);
		double maxMatch = 0;
		String maxStr = "";
		for(int i = 0; i < artists.size(); i++){
			double rateMatch = Operation_trimBeginning.matchRate(artist, artists.get(i));
			if (rateMatch > maxMatch){
				maxMatch = rateMatch;
				maxStr = artists.get(i);
			}
		}
		double acceptRate = Operation_trimBeginning.findAcceptRate(maxStr);
		if (maxMatch < acceptRate){
			artists.add(artist);
			Operation_folderOps.moveFiletoFolder(path, f, artist);
			//System.out.println(artist + "-> New");
		}
		else{
			Operation_folderOps.moveFiletoFolder(path, f, maxStr);
			//System.out.println(maxStr + "-> Exists");
		}
	}
	
	public static void FolderifyByAlbum(File path, ArrayList<String> albums, File f){
		String album = "";
		
		if ((Operation_pathOps.seperateNameAndExtension(f)[1].equals("mp3")) || (Operation_pathOps.seperateNameAndExtension(f)[1].equals("MP3"))){
			album = Operation_metadata.getAlbum(f);
			album = album.replaceAll("/", "");
			if (album.equals("")){
				album = "Unknown Album";
			}
		}
		else{
			album = "Unsupported File(s)";
		}
		double matchMax = 0;
		String strMax = "";
		for(int i = 0; i < albums.size(); i++){
			double rateMatch = Operation_trimBeginning.matchRate(album, albums.get(i));
			if (rateMatch > matchMax){
				matchMax = rateMatch;
				strMax = albums.get(i);
			}
		}
		double acceptRate = Operation_trimBeginning.findAcceptRate(strMax);
		if (matchMax < acceptRate){
			albums.add(album);
			Operation_folderOps.moveFiletoFolder(path, f, album);
			//System.out.print(" : New Folder (" + album + ")");
		}
		else{
			Operation_folderOps.moveFiletoFolder(path, f, strMax);
			//System.out.print(" : Existing Folder (" + maxStr + ")");
		}
		//System.out.println();
	}
	
	public static void moveFiletoFolder(File path, File file, String folder){
		File newdir = new File(path.toString()+"/"+folder);
		if(!newdir.exists()){
			newdir.mkdir();
		}
		File newFile = new File(path.toString()+"/"+folder+"/"+file.getName());
		//System.out.println(path.toString()+"/"+folder+"/"+file.getName());
		file.renameTo(newFile);
	}

}