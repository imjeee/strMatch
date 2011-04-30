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
			// read pattern
			p = new File(pattern);
			Scanner scan = new Scanner(p).useDelimiter("&");
			while (scan.hasNext()) {
				String thisPattern = scan.next();
				if (thisPattern.trim().length() != 0) {
					System.out.println(thisPattern);
					eachPattern.add(thisPattern);
				}
			}

		} catch (IOException e) {
			System.out.println("could not read pattern file: " + args[0]);
		}

	}

	// Brute Force Algorithm
	public static int bruteForce(String pattern, String source) {
		assert (pattern.length() > 0) : "pattern cannot be empty";

		for (int i = 0; i < source.length() - pattern.length() + 1; i++) {
			int tempi = i;
			boolean match = true;
			for (int j = 0; j < pattern.length() & match; j++, tempi++) {
				if (source.charAt(tempi) != pattern.charAt(j)) {
					match = false;
				}
			}
			if (match) {
				return 1;
			}
		}

		return -1;
	}

	// Rabin-Karp Algorithm
	public static int rabinKarp(String pattern, String source) {
		int patternHValue = pattern.hashCode();

		return -1;
	}

	// Knuth-Morris-Pratt Algorithm
	public static int knuthMorrisPratt(String pattern, String source) {
		assert (pattern.length() > 0) : "pattern cannot be empty";

		String s = source;
		String w = pattern;

		int m = 0;
		int i = 0;
		int[] T = partialMatchTable(w, s);

		while (m + i < s.length()) {

			if (w.charAt(i) == s.charAt(m + i)) {
				if (i == w.length() - 1) {
					return 1;
				}
				i++;
			} else {
				m = m + i - T[i];
				if (T[i] > -1) {
					i = T[i];
				} else {
					i = 0;
				}
			}
		}

		return -1;
	}

	// partial match table to support Knuth Morris Pratt Algorithm
	private static int[] partialMatchTable(String pattern, String source) {

		String w = pattern;

		int[] T = new int[source.length()];
		T[0] = -1;
		T[1] = 0;

		int pos = 2;
		int cnd = 0;

		while (pos < pattern.length()) {
			if (w.charAt(pos - 1) == w.charAt(cnd)) {
				cnd++;
				T[pos] = cnd;
				pos++;
			} else if (cnd > 0) {
				cnd = T[cnd];
			} else {
				T[pos] = 0;
				pos++;
			}
		}

		return T;
	}

	// Boyer-Moore Algorithm
	public static int boyerMoore(String pattern, String source) {
		
		int pl = pattern.length();
		int sl = source.length();
		
		if(sl == 0 | pl == 0 | pl > sl){
			return -1;
		}
		
		//ALPHABET_SIZE??????????
		int ALPHABET_SIZE = 0;
		
		int[] badcharacter = new int[ALPHABET_SIZE];
		int[] goodsuffix = new int[pl + 1];
		
		int s = 0;
		
		while(s < sl - pl){
			int j = pl;
			while(j > 0 & pattern.charAt(j-1) == source.charAt(s+j-1)){
				j--;
			}
			if(j>0){
				//int k = badcharacter[(size_t) haystack[s+j-1]];
				int k = badcharacter[(int) source.charAt(s-j+1)];
				//int m;
				int m = j-k-1;
				if((k < j) && (m > goodsuffix[j])){
					s += m;
				} else {
					s += goodsuffix[j];
				}
			} else
				return 1;
		}
		
		return -1;
	}
	
	//computer prefix
	private static void computer_prefix(String str, int size, int[] result){
		int q = size;
		int k = 0;
		result[0] = 0;
		
		for(q = 1; q < size; q++){
			while(k > 0 & str.charAt(k) != str.charAt(q)){
				k = result[k-1];
			}
			if(str.charAt(k) == str.charAt(q)){
				k++;
			}
			result[q] = k;
		}
	}
	
	//prepare badcharacter heuristic
	private static void prepareBadChar(String str, int size, int[] result){
		int i = size;
		
		for(i = 0; i < result.length; i++){
			result[i] = -1;
		}
		
		for(int j = 0; j < size; j++){
			result[(int) str.charAt(i)] = i;
		}
	}
	
	//prepare goodsuffix heuristic
	private static void prepareGoodSuffix(String normal, int size, int[] result){
		String left = normal;
		//String right = 
		
	}
	
	
	
	/*
	 *  Knuth Morris Pratt Algorithm*******************************************
	 * algorithm kmp_search: input: an array of characters, S (the text to be
	 * searched) an array of characters, W (the word sought) output: an integer
	 * (the zero-based position in S at which W is found)
	 * 
	 * define variables: an integer, m ← 0 (the beginning of the current match
	 * in S) an integer, i ← 0 (the position of the current character in W) an
	 * array of integers, T (the table, computed elsewhere)
	 * 
	 * while m+i is less than the length of S, do: if W[i] = S[m + i], if i
	 * equals the (length of W)-1, return m let i ← i + 1 otherwise, let m ← m +
	 * i - T[i], if T[i] is greater than -1, let i ← T[i] else let i ← 0
	 * 
	 * (if we reach here, we have searched all of S unsuccessfully) return
	 */
	
	/*
	 * algorithm kmp_table: input: an array of characters, W (the word to be
	 * analyzed) an array of integers, T (the table to be filled) output:
	 * nothing (but during operation, it populates the table)
	 * 
	 * define variables: an integer, pos ← 2 (the current position we are
	 * computing in T) an integer, cnd ← 0 (the zero-based index in W of the
	 * next character of the current candidate substring)
	 * 
	 * (the first few values are fixed but different from what the algorithm
	 * might suggest) let T[0] ← -1, T[1] ← 0
	 * 
	 * while pos is less than the length of W, do: (first case: the substring
	 * continues) if W[pos - 1] = W[cnd], let cnd ← cnd + 1, T[pos] ← cnd, pos ←
	 * pos + 1
	 * 
	 * (second case: it doesn't, but we can fall back) otherwise, if cnd > 0,
	 * let cnd ← T[cnd]
	 * 
	 * (third case: we have run out of candidates. Note cnd = 0) otherwise, let
	 * T[pos] ← 0, pos ← pos + 1
	 */

}
