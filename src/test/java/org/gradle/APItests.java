package org.gradle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class APItests {
	private final String appKey = "205";
//	private final String appKey = "49CF2CD3EF5E858FB28A";
	private final String devKey = "2Dx";
//	private final String devKey = "0HC6gQhPAy6yA359jtjV";

	@Test
	public void testInitializeAndGetSessionKey() {
		APIcaller caller = new APIcaller(devKey, appKey, true);
		assertEquals("0", caller.getStatus());
		assertTrue(caller.hasSession());
	}

	@Test
	public void testSearch() {
		APIcaller caller = new APIcaller(devKey, appKey, true);
		try {
			caller.search("Aspall Cyder Vinegar");
			assertEquals("0", caller.getStatus());
			caller.search("Apple Juice");
			assertEquals("0", caller.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertEquals(2, caller.getCalls());
	}
	
	@Test
	public void testSearchFinds(){
		APIcaller caller = new APIcaller(devKey, appKey, true);
		try {
			caller.search("Apple Juice");
			assertEquals("0", caller.getStatus());
		} catch (Exception e) {
			assertTrue(false);
		}
		assertTrue(0 < Integer.parseInt(caller.getPreviousPageResults().get("\"TotalProductCount\"")));
	}
	
	@Test
	public void testNoExceptionsInOrder() {
		APIcaller caller = new APIcaller(devKey, appKey, true);
		try {
			ArrayList<String> testList = new ArrayList<String>();
			testList.add("Apple Juice");
			assertTrue(caller.order(testList));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testScoreFunction() {
		String test1 = "Chips Ahoy Chocolate Chip Cookies Family Size";
		String test2 = "Lays Barbecue flavored Chips";
		String test3 = "Hershey's Chocolate Chip Muffins";
		
		int result1 = APIcaller.score(test1, test2, (double) 10);
		int result2 = APIcaller.score(test1, test3, (double) 10);
		
		assertTrue(result2 < result1);
		
		int result1with1 = APIcaller.score(test1, test1, (double) 10);
		
		assertEquals(Integer.MIN_VALUE, result1with1);
	}
	
	

//	@Test
//	public void testSelectionsCorrectness() {
//		APIcaller caller = new APIcaller(devKey, appKey, true);
//		try {
//			caller.search("Aspall Cyder Vinegar");
//			assertEquals("0", caller.getStatus());
//			caller.search("Apple Juice");
//			assertEquals("0", caller.getStatus());
//		} catch (Exception e) {
//			e.printStackTrace();
//			assertTrue(false);
//		}
//		Assert.assertArrayEquals(new Object[]{"23548", "12345"}, caller.getSelection());
//	}	
	
}
