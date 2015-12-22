package org.gradle;

import java.io.File;


import java.util.ArrayList;

import org.gradle.webScraper.Order;
import org.junit.Test;

import static org.junit.Assert.*;

public class webScraperTest {
	@Test
	public void canScrapFoodNetwork() {
		try {
			
			String url = "http://www.foodnetwork.com/recipes/sandra-lee/a-big-decadent-hot-dog-recipe.html";
			webScraper scrappy = new webScraper(url);

			assertEquals(11, scrappy.getIngredients().size());

			url = "http://www.foodnetwork.com/recipes/da-bus-beef-roast-recipe.html";
			scrappy = new webScraper(url);
			assertEquals("2 cloves minced garlic", scrappy.getIngredients()
					.get(3));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void canScrapAllRecipesDotCom() {
		try {
			String url = "http://allrecipes.com/recipe/223203/orzo-and-chicken-stuffed-peppers/?internalSource=rotd&referringContentType=home%20page";
			webScraper scrappy = new webScraper(url);
			assertEquals("4 cloves garlic, minced", scrappy.getIngredients()
					.get(7));

			url = "http://allrecipes.com/recipe/10197/royal-icing-i/?internalSource=hn_carousel%2002_Royal%20Icing&referringId=16401&referringContentType=recipe%20hub&referringPosition=carousel%2002";
			scrappy = new webScraper(url);
			assertEquals(3, scrappy.getIngredients().size());
		} catch (Exception e) {
			fail(e.getMessage());

		}
	}

	@Test
	public void cantScapThoseOthers() {
		try {
			String url = "http://recipes.wikia.com/wiki/Marco_Polo_Steak_Skillet";
			webScraper scrappy = new webScraper(url);
		} catch (Exception e) {
			assertEquals("Website not Supported", e.getMessage());
		}

		try {
			String url = "How I interweb";
			webScraper scrappy = new webScraper(url);
		} catch (Exception e) {
			assertEquals("Please input a valid URL", e.getMessage());
		}
	}

	@Test
	public void orderUP() {
		try {
			String url = "http://allrecipes.com/recipe/223203/orzo-and-chicken-stuffed-peppers/?internalSource=rotd&referringContentType=home%20page";
			webScraper scrappy = new webScraper(url);
			scrappy.parseIngredients();

			ArrayList<Order> orders = scrappy.getOrders();
			assertEquals("cooking spray", orders.get(0).getItem());
			assertEquals(4, orders.get(7).getQuantity());
			assertEquals("teaspoon", orders.get(9).getMeasurement());

			url = "http://www.foodnetwork.com/recipes/sandra-lee/a-big-decadent-hot-dog-recipe.html";
			scrappy = new webScraper(url);
			scrappy.parseIngredients();

			orders = scrappy.getOrders();
			assertEquals("large onions", orders.get(1).getItem());
			assertEquals(8, orders.get(0).getQuantity());
			assertEquals(null, orders.get(9).getMeasurement());

		} catch (Exception e) {
			fail(e.getMessage());

		}
	}

	@Test
	public void newOutput() {
		try {
			String url = "http://allrecipes.com/recipe/223203/orzo-and-chicken-stuffed-peppers/?internalSource=rotd&referringContentType=home%20page";
			webScraper scrappy = new webScraper(url);
			System.out.println(scrappy.getItems());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void appTest() {
		try {
			//file creation check 
			String username = "peruman";
			File testFile = new File("./json/" + username + "-web.json");
			testFile.delete();
			String url = "http://allrecipes.com/recipe/23891/grilled-cheese-sandwich/?internalSource=search%20result&referringContentType=search%20results";
			url = "http://allrecipes.com/recipe/231765/glazed-pumpkin-donuts/";
			String[] args = { username, url };
			ScraperApp.main(args);
			testFile = new File("./json/" + username + "-web.json");
			if (!(testFile.isFile())) {
				fail("File not created");
			}			
			
			
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
}
