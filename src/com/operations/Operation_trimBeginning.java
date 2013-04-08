package com.operations;

import java.io.File;
import java.util.ArrayList;

import com.msc.Approval_Form;

public class Operation_trimBeginning {
	
	public static File trimBeginning(File file, String path, String bandName){
		String fname = file.getName();
		fname = trimFirstPart(fname, "-", bandName);
		fname = trimFirstPart(fname, "_", bandName);
		fname = trimFirstPart(fname, " ", bandName);
		fname = trimWhiteSpaces(fname);
		File newFile = new File(path+"/"+fname);
		return newFile;
	}


	private static String trimFirstPart(String fname, String seperator, String bandName){
		String newName = fname;	
		if (fname.indexOf(seperator) != -1){
			String[] spl = fname.split(seperator);
			newName = "";
			// band name trim
			if (bandName != null){
				double max = 0;
				int maxindex = 0;
				String strToCheck = "";
				String maxStr = "";
				double acceptRate = 0.0;
				for(int j = 1; j <= spl.length; j++){
					for(int z = 0; z < j; z++){
						strToCheck += spl[z];
					}
					if(matchRate(strToCheck,bandName) > max){
						max = matchRate(strToCheck,bandName);
						maxindex = j; //first n words not to include
						maxStr = strToCheck;
					}
					strToCheck = "";
				}
				acceptRate = findAcceptRate(maxStr);
				if(max < acceptRate){
					maxindex = 0; //all words will be included
				}
				if(maxindex == spl.length){
					maxindex = 0;
				}
				for(int y = maxindex; y < spl.length; y++){
					newName = newName + spl[y] + " ";
				}
			}
			// number trim
			else{
				int toChar = spl.length;
				for(int j = 0; j < spl.length; j++){
						if(takeFirstStr(spl[j])){
							spl[j] = firstLetterToUpper(spl[j]);
							newName = newName + spl[j] + " ";
						}
						else{
							toChar = j;
							break;
						}	
				}
				for(int x = toChar+1; x < spl.length; x++){
					spl[x] = firstLetterToUpper(spl[x]);
					newName = newName + spl[x] + " ";
				}
			}
		}
		return newName;
	}
	
	public static double findAcceptRate(String maxStr){
		double acceptRate = 0;
		if(maxStr.length() <= 3){
			acceptRate = 0.45;
		}
		else if((maxStr.length() == 4) || (maxStr.length() == 5)){
			acceptRate = 0.5;
		}
		else if((maxStr.length() == 6) || (maxStr.length() == 7)){
			acceptRate = 0.55;//--
		}
		else if((maxStr.length() == 8) || (maxStr.length() == 9)){
			acceptRate = 0.6;//--
		}
		else{
			acceptRate = 0.65;
		}
		return acceptRate;
	}

	private static boolean takeFirstStr(String bandNameFile) { // for number
		int countString = 0;
		int countNumeric = 0;
		bandNameFile = trimWhiteSpaces(bandNameFile);
		for(int j = 0; j < bandNameFile.length(); j++){
			char current_char = bandNameFile.charAt(j);
			if(isNumeric(current_char)){
				countNumeric++;
			}
			else{
				countString++;
			}
		}
		if ((countString == 0) && (countNumeric > 0)){
			return false;
		}
		else{
			return true;
		}
	}

	public static double matchRate(String bandNameFile, String bandName){ // returns the rate of match
		int cntEqualChars = 0, cntEqualPlacement = 0, cntTotalPlacement = 0;
		double rate1 = 0; double rate2 = 0;
		String longer, shorter, shorterYedek;
		bandNameFile = bandNameFile.toLowerCase();
		bandNameFile = trimWhiteSpaces(bandNameFile);
		bandName = bandName.toLowerCase();
		bandName = trimWhiteSpaces(bandName);
		// trim non-letter characters
		for(int i = 0; i < bandName.length(); i++){
			char chr = bandName.charAt(i);
			if(!((chr > 'a') && (chr < 'z'))){
				bandName = bandName.substring(0, i) + bandName.substring(i+1,bandName.length());
			}
		}
		for(int i = 0; i < bandNameFile.length(); i++){
			char chr = bandNameFile.charAt(i);
			if(!((chr > 'a') && (chr < 'z'))){
				bandNameFile = bandNameFile.substring(0, i) + bandNameFile.substring(i+1,bandNameFile.length());
			}
		}


		if (bandName.length() > bandNameFile.length()){
			longer = bandName;
			shorter = bandNameFile;
			shorterYedek = bandNameFile;
		}
		else{
			longer = bandNameFile;
			shorter = bandName;
			shorterYedek = bandName;
		}

		for(int i = 0; i < longer.length(); i++){
			char chr = longer.charAt(i);
			int indexof = shorter.indexOf(chr);
			if(indexof != -1){
				cntEqualChars++;
				shorter = shorter.substring(0,indexof) + shorter.substring(indexof+1, shorter.length());
			}
		}
		shorter = shorterYedek;
		rate1 = (((double)cntEqualChars/longer.length()*60)/100 + ((double)cntEqualChars/shorter.length()*40)/100);

		for(int i = 0; i < longer.length()-1; i++){
			String substr = longer.substring(i, i+2);
			int indexof = shorter.indexOf(substr);
			if(shorter.indexOf(substr) != -1){
				cntEqualPlacement++;
				shorter = shorter.substring(0,indexof) + shorter.substring(indexof+1, shorter.length());
			}
			cntTotalPlacement++;
		}
		rate2 = (double)cntEqualPlacement/cntTotalPlacement;

		double rate_total = (rate1 + rate2)/2;
		return rate_total;
	}



	private static boolean isNumeric(char current){
		try{
			Integer.parseInt(Character.toString(current));
		}
		catch (Exception e){
			return false;
		}
		return true;
	}

	public static String firstLetterToUpper(String spl) {
		spl = trimWhiteSpaces(spl);
		String spl_first = spl.substring(0, 1);
		String spl_rest = spl.substring(1);
		spl_first = spl_first.toUpperCase();
		spl = spl_first+spl_rest;
		return spl;
	}
	public static String trimWhiteSpaces(String input){

		try{
			while(input.charAt(0) == ' '){
				input = input.substring(1);
			}
			while(input.charAt(input.length()-1) == ' '){
				input = input.substring(0,input.length()-1);
			}
		}
		catch(Exception e){
		}
		return input;
	}
	
	public static void trimBeginnings(ArrayList<File> files, String bandName){
		int x = 1;
		ArrayList<File> files_new = new ArrayList<File>();
		for(int i = 0; i < files.size(); i++){
			File oldFile = files.get(i);
			String path = Operation_pathOps.seperatePath(oldFile);
			File newFile = trimBeginning(files.get(i),path, bandName);
			if (!oldFile.getName().equals(newFile.getName())){
			newFile = Operation_pathOps.changeNameIfFileExists(files_new, newFile);
			}
			files_new.add(newFile);
		}
		Approval_Form approve = new Approval_Form(files_new, files);
		approve.runApproval();
	}

}
