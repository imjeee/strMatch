import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StrMatch {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			throw new IllegalArgumentException(
					"usage: pattern.txt source.txt results.txt");
		}

		String pattern = args[0];
		String source = args[1];
		String results = args[2];

		File p;
		FileReader fr = null;
		ArrayList<String> eachPattern = new ArrayList<String>();
		int largestps = 2048; // longest pattern size

		try {
			// read pattern
			p = new File(pattern);
			Scanner scan = new Scanner(p).useDelimiter("&");
			while (scan.hasNext()) {
				String thisPattern = scan.next();

				if (thisPattern.length() > largestps) {
					largestps = thisPattern.length();
				}

				if (thisPattern.trim().length() != 0) {
					// System.out.println(thisPattern);
					eachPattern.add(thisPattern);
				}
			}
		} catch (IOException e) {
			System.out.println("could not read pattern file: " + pattern);
			System.out.println(e);
		}

		try {
			fr = new FileReader(source);
			LookBackStringBuffer lbsb = new LookBackStringBuffer(source,
					largestps);

			for (String s : eachPattern) {

				System.out.println("*********pattern: ");
				System.out.println(s);
				System.out.println();

				lbsb.reset();

				long[] bf = bruteForce(s, lbsb);
				System.out.println("bruteFoce: " + bf[0] + "        " + bf[1]);
				lbsb.reset();

				long[] rk = rabinKarp(s, lbsb);
				System.out.println("rabinKarp: " + rk[0] + "        " + rk[1]);
				lbsb.reset();

				long[] kmp = knuthMorrisPratt(s, lbsb);
				System.out
						.println("knuthMorrisPratt: " + kmp[0] + " " + kmp[1]);
				lbsb.reset();

				long[] bm = boyerMoore(s, lbsb);
				System.out.println("boyerMoore: " + bm[0] + "       " + bm[1]);
				System.out.println();

			}

		} catch (IOException e) {
			System.out.println("could not read sourece file: " + source);
		}

	}

	// Brute Force Algorithm
	public static long[] bruteForce(String pattern, LookBackStringBuffer source)
			throws Exception {
		assert (pattern.length() > 0) : "pattern cannot be empty";

		// result[0] shows success or failure, result[1] shows number of
		// comparisons
		long[] result = new long[2];

		// for (int i = 0; i < source.length() - pattern.length() + 1; i++) {
		for (int i = 0; source.hasAvailable(i + pattern.length()); i++) {
			int tempi = i;
			boolean match = true;
			for (int j = 0; j < pattern.length() & match; j++, tempi++) {
				result[1]++;
				if (source.charAt(tempi) != pattern.charAt(j)) {
					match = false;
				}
			}
			if (match) {
				result[0] = 1;
				return result;
			}
		}

		return result;
	}

	// Rabin-Karp Algorithm, Rolling linear sum
	public static long[] rabinKarp(String pattern, LookBackStringBuffer source)
			throws IOException, Exception {

		long[] result = new long[2];

		if (!source.hasAvailable(pattern.length())) {
			return result;
		}

		String sub = new String();

		long patternHC = 0;
		long sourceHC = 0;

		for (int i = 0; i < pattern.length(); i++) {
			// calculate the whole pattern hash
			long patternCharInt = pattern.charAt(i);
			patternHC += patternCharInt;
			// calculate first source has
			char sourceChar = source.charAt(i);
			long sourceCharInt = (long) sourceChar;
			sourceHC += sourceCharInt;

			sub += sourceChar;
			result[1]++;
		}

		for (int i = pattern.length() - 1; source.hasAvailable(i + 1); i++) {
			// System.out.println("sourceHC " + sourceHC);
			if (patternHC == sourceHC) {

				long[] temp = bruteForce(pattern, new LookBackStringBuffer(sub));

				result[1] += temp[1];
				result[0] = temp[0];

				if (result[0] == 1) {
					return result;
				}
			}
			// System.out.println(source.charAt(i));

			if (!source.hasAvailable(i + 2)) {
				break;
			}

			char next = source.charAt(i + 1);
			long nextC = (long) next;
			sub = sub.substring(1, sub.length()) + next;
			long prevHC = source.charAt(i - pattern.length() + 1);
			sourceHC = sourceHC - prevHC + nextC;

			result[1]++;

			// System.out.println("prev: " + prevHC);

			// System.out.println("next: " + nextC + " source: " + sourceHC);
		}

		return result;
	}

	/*
	 * // Rabin-Karp Algorithm public static int rabinKarp(String pattern,
	 * String source) throws IOException, Exception { assert pattern.length() <
	 * source.length() : "pattern needs to be longer than source";
	 * 
	 * String sub = new String();
	 * 
	 * int primeN = 47; int pl = pattern.length();
	 * 
	 * long patternHC = 0; long sourceHC = 0;
	 * 
	 * for(int i = 0; i < pattern.length(); i++){ //calculate the whole pattern
	 * hash long patternCharInt = pattern.charAt(i); patternHC +=
	 * patternCharInt; //calculate first source has char sourceChar =
	 * source.charAt(i); long sourceCharInt = (long) sourceChar; sourceHC +=
	 * sourceCharInt;
	 * 
	 * sub += sourceChar; }
	 * 
	 * //System.out.println("pattern: " + patternHC + " source: " + sourceHC);
	 * 
	 * for(int i = pattern.length()-1; i < source.length(); i++){
	 * //System.out.println("sourceHC " + sourceHC); if(patternHC == sourceHC){
	 * if(bruteForce(pattern, new LookBackStringBuffer(sub)) != -1){ return 1; }
	 * } //System.out.println(source.charAt(i));
	 * 
	 * if(i+1 >= source.length()){ break; }
	 * 
	 * char next = source.charAt(i+1); long nextC = (long)next; sub =
	 * sub.substring(1, sub.length()) + next; long prevHC = source.charAt(i -
	 * pattern.length() + 1); sourceHC = sourceHC - prevHC + nextC;
	 * 
	 * //System.out.println("prev: " + prevHC);
	 * 
	 * 
	 * //System.out.println("next: " + nextC + " source: " + sourceHC); }
	 * 
	 * 
	 * return -1; }
	 */

	// Knuth-Morris-Pratt Algorithm
	public static long[] knuthMorrisPratt(String pattern,
			LookBackStringBuffer source) throws Exception {
		assert (pattern.length() > 0) : "pattern cannot be empty";

		long[] result = new long[2];
		result[1] += pattern.length();

		LookBackStringBuffer s = source;
		String w = pattern;

		int m = 0;
		int i = 0;
		int[] T = partialMatchTable(w);

		while (s.hasAvailable(m + i + 1)) {

			result[1]++;

			if (w.charAt(i) == s.charAt(m + i)) {
				if (i == w.length() - 1) {
					result[0] = 1;
					return result;
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

		return result;
	}

	// partial match table to support Knuth Morris Pratt Algorithm
	private static int[] partialMatchTable(String pattern) {

		String w = pattern;

		int[] T = new int[pattern.length()];
		T[0] = -1;

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
	public static long[] boyerMoore(String pattern, LookBackStringBuffer source)
			throws Exception {

		long result[] = new long[2];
		int pl = pattern.length();

		if (pl == 0 | !source.hasAvailable(0)) {
			return result;
		}

		int[] badcharacter = prepare_badcharacter(pattern);
		int[] goodSuffix = prepare_goodSuffix(pattern);

		result[1] += pl + pl;

		int s = 0;
		while (source.hasAvailable(s + pl)) {
			result[1]++;
			int j = pl;
			while (j > 0 && (pattern.charAt(j - 1) == source.charAt(s + j - 1))) {
				j--;
			}
			if (j > 0) {
				int k = badcharacter[(int) source.charAt(s + j - 1)];
				int m = j - k - 1;
				if (k < j & m > goodSuffix[j]) {
					s += m;
				} else {
					s += goodSuffix[j];
				}
			} else {
				result[0] = 1;
				return result;
			}
		}
		return result;

	}

	// computer prefix
	private static int[] compute_prefix(String str) {

		int size = str.length();
		int[] result = new int[size];

		int k = 0;

		for (int q = 1; q < size; q++) {

			char ck = str.charAt(k);
			char cq = str.charAt(q);

			while (k > 0 && ck != cq) {
				k = result[k - 1];
			}

			if (ck == cq) {
				k++;
			}

			result[q] = k;
		}
		return result;
	}

	// prepare bad character, bad symbol heuristic
	private static int[] prepare_badcharacter(String str) {

		int size = str.length();
		int charE = 1024;
		int[] result = new int[charE];

		for (int i = 0; i < charE; i++) {
			result[i] = -1;
		}

		for (int j = 0; j < size; j++) {
			result[(int) str.charAt(j)] = j;
		}

		return result;
	}

	// good suffix heuristic
	private static int[] prepare_goodSuffix(String str) {

		int size = str.length();
		int[] result = new int[size + 1];

		// reverse string
		StringBuffer sb = new StringBuffer(str.length());
		for (int i = str.length() - 1; i >= 0; i--) {
			sb.append(str.charAt(i));
		}

		String reversed = sb.toString();

		int[] prefix_normal = compute_prefix(str);
		int[] prefix_reverse = compute_prefix(reversed);

		for (int i = 0; i <= size; i++) {
			result[i] = size - prefix_normal[size - 1];
		}

		for (int ii = 0; ii < size; ii++) {
			int j = size - prefix_reverse[ii];
			int k = ii - prefix_reverse[ii] + 1;

			if (result[j] > k) {
				result[j] = k;
			}
		}
		return result;
	}

	/*
	 * Knuth Morris Pratt Algorithm*******************************************
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