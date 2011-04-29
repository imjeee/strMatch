import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StrMatch {
	
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException(
					"usage: pattern.txt source.txt results.txt");
		}

		String pattern = args[0];
		String source = args[1];
		String results = args[2];

		File p;

		ArrayList<String> eachPattern = new ArrayList<String>();
		
		try {
			//read pattern
			p = new File(pattern);
			Scanner scan = new Scanner(p).useDelimiter("&");
			while (scan.hasNext()) {
				String thisPattern = scan.next();
				if(thisPattern.trim().length() != 0){ 
					System.out.println(thisPattern);
					eachPattern.add(thisPattern);
				}
			}
			
			
			
			
		} catch (IOException e) {
			System.out.println("could not read pattern file: " + args[0]);
		}

	}


	//Brute Force Algorithm
	public static int bruteForce(String pattern, String source){
		
		for(int i = 0; i < source.length() - pattern.length(); i++){
			int tempi = i;
			boolean match = true;
			for(int j = 0; j < pattern.length() & match; j++, tempi++){
				if(source.charAt(tempi) != pattern.charAt(j)){
					match = false;
				}
			}
			if(match){
				return 1;
			}
		}
		return -1;
	}
	
	
	// Rabin-Karp Algorithm
	public static int rabinKarp(String pattern, String source){
		int patternHValue = pattern.hashCode();
		
		return -1;
	}
	

	// Knuth-Morris-Pratt Algorithm
	public static int knuthMorrisPratt(String pattern, String source){
		
		int m = 0;
		int i = 0;
		
		while(m+i < source.length()){
			if(pattern.charAt(i) == source.charAt(m+i)){
				if(i == pattern.length()-1){
					return m;
				}
				i++;
			} else {
				m = m + i - partialMatch(i);
				if(partialMatch(i) > -1){
					i = partialMatch(i);
				} else {
					i = 0;
				}
			}
		}
		
		return -1;
	}

	// Boyer-Moore Algorithm
	public static int boyerMoore(String pattern, String source){
		return -1;
	}

}
