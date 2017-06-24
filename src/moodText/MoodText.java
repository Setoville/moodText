package moodText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoodText {

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
	
	public static void main(String[] args) {
		Map <String, Integer> wordBank = loadWordBank();
		
		double averageRating = 0;
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
			//	System.out.println(parsedLine);
		        
		        
				int negate = 1;
				//first one to count gets negate
				phrases: for(int r=0;r<words.length;r++){
					double perphraseSum = 0;					

					
					
					if (negateWords.contains(words[r])){
						System.out.println("N: " + words[r]);
						negate = -1;
					}
					else if (wordBank.containsKey(words[r])){
						System.out.println("M: " + words[r]);

						double wordRating = wordBank.get(words[r]);
						absTotal = absTotal + Math.abs(wordRating);
						perphraseSum = perphraseSum + wordRating*negate;
						if (negate == -1) negate = 1;
						phraseSum.add(perphraseSum);
					}
					else System.out.println(words[r]);

				
				}
			}
			double total=0;
			for(double r : phraseSum){
				total = total + r;
			}

			System.out.println(total/absTotal);
	//not, never,	
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
