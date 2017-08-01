package moodText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
enum MOOD {POSITIVE,NEGATIVE,NEUTRAL};
public class MoodText {
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
	
	public static void main(String[] args) {
		Map <String, Integer> wordBank = loadWordBank();
		
		FileReader fr;
		
		try {
			fr = new FileReader("story.txt");
			BufferedReader br = new BufferedReader (fr);
			String wholeLine;
			List<String> negateWords = Arrays.asList("not","dont","never","no");
			List<Double> phraseSum = new ArrayList<Double>();
			double absTotal=0;
			lines: while ((wholeLine=br.readLine())!=null){
		        String parsedLine = wholeLine.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
				parsedLine = parsedLine.toLowerCase();
		        String[] words = parsedLine.split(" ");
		        
		        
				int negate = 1;
				//first one to count gets negate
				phrases: for(int r=0;r<words.length;r++){
					double perphraseSum = 0;					

					
					
					if (negateWords.contains(words[r])){
						System.out.println("N: " + words[r]);
						negate = -1;
					}
					else if (wordBank.containsKey(words[r])){
						double wordRating = wordBank.get(words[r])*negate;

						System.out.println("M: " + words[r] + wordRating);

						absTotal = absTotal + Math.abs(wordRating);
						perphraseSum = perphraseSum + wordRating;
						if (negate == -1) negate = 1;
						phraseSum.add(perphraseSum);
					}

				
				}
			}
			double total=0;
			for(double r : phraseSum){
				total = total + r;
			}
			double score = total/absTotal;
			System.out.println(score);
			if (score<-0.25) {
				System.out.println("NEGATIVE");
				openSpotify(MOOD.NEGATIVE);
			}
			else if (score>0.25){
				System.out.println("POSITIVE");
				openSpotify(MOOD.POSITIVE);

			}
			else {
				System.out.println("NEUTRAL");
				openSpotify(MOOD.NEUTRAL);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
