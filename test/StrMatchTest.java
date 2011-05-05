import static org.junit.Assert.*;
import java.io.IOException;

import org.junit.Test;

public class StrMatchTest {
	/*
	 * @Test public void testMain() { fail("Not yet implemented"); }
	 */

	@Test
	public void testBruteForce() throws Exception {
		StrMatch s = new StrMatch();
		StrMatch.LookBackStringBuffer lbsb = new StrMatch.LookBackStringBuffer("hello world my name is ryan, and i can shoot 3s like no others.");
		long result[] = s.bruteForce("h", lbsb);
		
		assertEquals(1, result[0]);
	}

	@Test
	public void testKMP() throws Exception {
		StrMatch s = new StrMatch();
		StrMatch.LookBackStringBuffer lbsb = new StrMatch.LookBackStringBuffer(
				"he\\llo world my name is ryan, but i can't play with black ppl.");
		long result[] =s.knuthMorrisPratt(".", lbsb);
		assertEquals(1, result[0]);
	}

	@Test
	public void testBM() throws Exception {
		StrMatch s = new StrMatch();
		StrMatch.LookBackStringBuffer lbsb = new StrMatch.LookBackStringBuffer(
				"hello world my name is ryan, but i can't play with black ppl.");
		assertEquals(1, s.boyerMoore("pl.", lbsb));
	}

	// @Test
	// public void testSKMP() throws Exception {
	// StrMatch s = new StrMatch();
	// assertEquals(1, s.knuthMorrisPrattS("\\l",
	// "hello world my name is ryan, but i can't play with black ppl."));
	// }

	@Test
	public void testRK() throws IOException, Exception {
		StrMatch s = new StrMatch();

		StrMatch.LookBackStringBuffer lbsb = new StrMatch.LookBackStringBuffer("hello world");
		// assertEquals(1, s.rabinKarp("h", lbsb));
		// assertEquals(-1, s.rabinKarp("naem", lbsb));
		// assertEquals(1, s.rabinKarp("hello ", lbsb));
		// assertEquals(1, s.rabinKarp("world", lbsb));
		long[] result = s.rabinKarp("d", lbsb);

		assertEquals(1, result[0]);
	}

}