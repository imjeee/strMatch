import static org.junit.Assert.*;

import org.junit.Test;


public class StrMatchTest {

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testBruteForce() {
		StrMatch s = new StrMatch();
		assertEquals(1, StrMatch.bruteForce("",
				"hello world"));
	}
	
	@Test
	public void testKMP() {
		StrMatch s = new StrMatch();
		assertEquals(1, StrMatch.knuthMorrisPratt("hello",
				"hello world"));
	}

}