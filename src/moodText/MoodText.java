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
				String[] split = parsedLine.split(" ");
			//	System.out.println(parsedLine);
				
				//first one to count gets negate
				phrases: for(int r=0;r<split.length;r++){
					double perphraseSum = 0;
					String [] key = split[r].split(" ");
					boolean containedKey=false;
					
					int negate = 1;
					words: for(int c=0;c<key.length;c++){
						if (negateWords.contains(key[c])){
							System.out.print("here");
							negate = -1;
						}
						if (wordBank.containsKey(key[c])){
							System.out.println(key[c]);
							absTotal = absTotal + Math.abs(wordBank.get(key[c]));
							perphraseSum = (perphraseSum + wordBank.get(key[c]))*negate;
							if (negate == -1) negate = 1;
							System.out.println("("+ perphraseSum +")");
							containedKey=true;
						}
					}
					if (containedKey){
						//System.out.println("phrase sum" + phraseSum);
						phraseSum.add(perphraseSum);
					}
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
