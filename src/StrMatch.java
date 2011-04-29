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
	
	/*
	 * String input = "1 fish 2 fish red fish blue fish";
     Scanner s = new Scanner(input);
     s.findInLine("(\\d+) fish (\\d+) fish (\\w+) fish (\\w+)");
     MatchResult result = s.match();
     for (int i=1; i<=result.groupCount(); i++)
         System.out.println(result.group(i));
     s.close(); 
	 */

	// Rabin-Karp Algorithm

	// Knuth-Morris-Pratt Algorithm

	// Boyer-Moore Algorithm

}
