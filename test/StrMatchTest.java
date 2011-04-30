import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class StrMatchTest {
/*
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testBruteForce() throws Exception {
		StrMatch s = new StrMatch();
		LookBackStringBuffer lbsb = new LookBackStringBuffer("hello world");
		assertEquals(1, s.bruteForce("fdsafa", lbsb));
	}
	/*
	@Test
	public void testKMP() {
		StrMatch s = new StrMatch();
		assertEquals(1, s.knuthMorrisPratt("hello",
				"hello world"));
	}
	
	@Test
	public void testRK() {
		StrMatch s = new StrMatch();
		assertEquals(1, s.rabinKarp("h ", "hello world"));
	}*/

}