import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;


public class StrMatchTest {
/*
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}
*/
	/*
	@Test
	public void testBruteForce() throws Exception {
		StrMatch s = new StrMatch();
		LookBackStringBuffer lbsb = new LookBackStringBuffer("hello world my name is ryan, and i can shoot 3s like no others.");
		assertEquals(1, s.bruteForce("h", lbsb));
	}
	*/
	@Test
	public void testKMP() throws Exception {
		StrMatch s = new StrMatch();
		LookBackStringBuffer lbsb = new LookBackStringBuffer("he\\llo world my name is ryan, but i can't play with black ppl.");
		assertEquals(1, s.knuthMorrisPratt(".", lbsb));
	}
	
//	@Test
//	public void testSKMP() throws Exception {
//		StrMatch s = new StrMatch();
//		assertEquals(1, s.knuthMorrisPrattS("\\l", "hello world my name is ryan, but i can't play with black ppl."));
//	}
	
	@Test
	public void testRK() throws IOException, Exception {
		StrMatch s = new StrMatch();
		LookBackStringBuffer lbsb = new LookBackStringBuffer("hello world");
//		assertEquals(1, s.rabinKarp("h", lbsb));
//		assertEquals(-1, s.rabinKarp("naem", lbsb));
//		assertEquals(1, s.rabinKarp("hello ", lbsb));
//		assertEquals(1, s.rabinKarp("world", lbsb));
		assertEquals(1, s.rabinKarp("d", lbsb));
	}

}