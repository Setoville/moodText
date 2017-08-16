package moodText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum MOOD {POSITIVE,NEGATIVE,NEUTRAL};

public class MoodText {
	
	static final List<String> negateWords = Arrays.asList("not","dont","never","no");
	
	static MOOD mood = MOOD.NEUTRAL;
	public static Map<String, Integer> loadWordBank(){
		Map <String,Integer> loadingMap = new HashMap<String,Integer>();
		
		try {
			FileReader fr = new FileReader("wordbank.txt");
			BufferedReader br = new BufferedReader(fr);
			String kvpline;
			while ((kvpline = br.readLine())!=null){
				String splitString[] = kvpline.split(" ");
				
				if (splitString.length>2) {
					splitString [0] = splitString [0] + " " + splitString[1];
					splitString [1] = splitString [2];
				}

				loadingMap.put(splitString[0], Integer.parseInt(splitString[1]));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loadingMap;
	}
	
	public static void openSpotify(MOOD pm){
		String fs = System.getProperty("file.separator");
		String header = "C:"
				+ fs + "Users" 
				+ fs + "andrewseto";
		String vbs = header
				+ fs + "workspace" 
				+ fs + "moodtext" 
				+ fs + "src"
				+ fs + "res" + fs;
		String exeCommand = header  
				+ fs + "AppData" 
				+ fs + "Roaming"
				+ fs + "Spotify" 
				+ fs + "Spotify.exe";
		String positivevbs = vbs + "positive.vbs";
		String negativevbs = vbs + "negative.vbs";
		String neutralvbs = vbs + "neutral.vbs";
		String [] command = null;
		switch (pm){
			case POSITIVE:
				 command = new String[] {exeCommand,positivevbs};
				break;
			case NEGATIVE:
				 command = new String[] {exeCommand,negativevbs};
				break;
			case NEUTRAL:
				 command = new String[] {exeCommand,neutralvbs};
				break;
			default:
				System.err.println("Error");
				break;
		}
		try {
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	
			
	}
	public static MOOD getEmotion(List<String> list){
		Map <String, Integer> wordBank = loadWordBank();
		double absTotal=0;
		double total=0;
		int negate = 1;

		for(String word:list){
			System.out.println(word);
		}
		
		for(String word:list){
			if (negateWords.contains(word)){
				System.out.println("N: " + word);
				negate = -1;
			}		
			else if (wordBank.containsKey(word)){
				double wordRating = wordBank.get(word)*negate;

				System.out.println("M: " + word + wordRating);
				total=total+wordRating;
				absTotal = absTotal + Math.abs(wordRating);
				if (negate == -1) negate = 1;
			}
			
		}
			
		double score = total/absTotal;
		System.out.println(score);
		if (score<-0.25) {
			System.out.println("NEGATIVE");
		//	openSpotify(MOOD.NEGATIVE);
			return MOOD.NEGATIVE;
		}
		else if (score>0.25){
			System.out.println("POSITIVE");
		//	openSpotify(MOOD.POSITIVE);
			return MOOD.POSITIVE;

		}
		else {
			System.out.println("NEUTRAL");
		//	openSpotify(MOOD.NEUTRAL);
			return MOOD.NEUTRAL;
		}
		
		
	}
	
	private static List<String> getWordsFromFile(String stringfile) {
		
		List <String> wholeListOfWords = new ArrayList<String>();
		FileReader fr;			
		String wholeLine;

		
		try {
			fr = new FileReader(stringfile);
			BufferedReader br = new BufferedReader (fr);
			while ((wholeLine=br.readLine())!=null){
		        String parsedLine = wholeLine.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
				parsedLine = parsedLine.toLowerCase();
		        String[] words = parsedLine.split(" ");
				for(int r=0;r<words.length;r++){
					wholeListOfWords.add(words[r]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return wholeListOfWords;
	}
	
	
	public static MOOD start () {
		List <String> wholeListOfWords = getWordsFromFile("story.txt");
		getEmotion(wholeListOfWords);
		

		return MOOD.NEUTRAL;

		
		
		

	}




	public static void main(String[] args) {
		System.out.println("Mode = 1. Text -> music");
		System.out.println("2. Web -> music");
		System.out.println("3. Web -> web (API)");
		if (args[0].equals("1")){
			MoodText.start();
		}
		else if (args[0].equals("2")){
			//Handler webHandler = new Handler();
		}
		else if (args[0].equals("3")){
			
		}
			
		
	

	}

}
