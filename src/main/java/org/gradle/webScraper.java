package org.gradle;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class that scrapes webpage for ingredient info.
 * 
 * @author mcgarrdg
 * 
 */
public class webScraper {
	public String url;
	public ArrayList<String> ingredients;
	public HashMap<String, Boolean> measurements;
	public HashMap<String, Integer> numbers;
	public ArrayList<Order> orders = new ArrayList<Order>();

	/**
	 * Order class that organizes ingredients into quantity, measurement and
	 * item.
	 * 
	 * @author mcgarrdg
	 * 
	 */
	public class Order {
		private int quantity;
		private String measurement = null;
		private String item;

		private Order(int quantity, String measurement, String item) {
			this.quantity = quantity;
			this.measurement = measurement;
			this.item = item;
		}

		public int getQuantity() {
			return this.quantity;
		}

		public String getMeasurement() {
			return this.measurement;
		}

		public String getItem() {
			return this.item;
		}
	}

	/**
	 * Returns ingredients as an ArrayList representation.
	 * 
	 * @return
	 */
	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	/**
	 * Returns items from Orders as a string that represents a JSON File. Note,
	 * this does not actually follow JSON syntax, the current form was created
	 * for making easy API calls.
	 * 
	 * @return
	 */
	public String getItems() {
		this.parseIngredients();
		String jsonfile = "{\n";
		for (int i = 0; i < orders.size(); i++) {
			jsonfile += orders.get(i).getItem() + "\n";
		}
		jsonfile += "}";
		return jsonfile;
	}

	/**
	 * Returns the List of full ingredients as a string.
	 * 
	 * @return
	 */
	public String getIngredientsString() {
		String ingredientString = "";
		for (int i = 0; i < ingredients.size(); i++) {
			ingredientString += ingredients.get(i) + "; ";
		}

		return ingredientString;
	}
	/**
	 * Function that returns collected orders as an ArrayList.
	 * 
	 * @return
	 */
	public ArrayList<Order> getOrders() {
		return this.orders;
	}
	/**
	 * Constructor for the webScraper class.
	 * 
	 * @param url
	 * @throws Exception
	 */
	public webScraper(String url) throws Exception {
		this.url = url;
		try {
			URL check = new URL(url);
		} catch (Exception e) {
			throw new Exception("Please input a valid URL");
		}

		// FoodNetwork recipes
		if (url.contains("http://www.foodnetwork.com/")) {
			ingredients = new ArrayList<String>();
			Document doc = Jsoup.connect(url).get();
			Elements ingredientsList = doc.select("li[itemprop=ingredients]");
			for (Element ingredient : ingredientsList) {
				ingredients.add(ingredient.text());
			}

			return;
		}

		// allrecipes.com recipes
		if (url.contains("http://allrecipes.com")) {
			ingredients = new ArrayList<String>();
			Document doc = Jsoup.connect(url).get();
			Elements test = doc
					.select("li[class = checkList__line] label span");
			for (Element ingredient : test) {
				String check = ingredient.text();
				if (!(check.equals("") || check
						.equals("Add all ingredients to list"))) {
					ingredients.add(check);
				}
			}
			return;

		}
		// All other recipe sites not implemented
		else {
			throw new Exception("Website not Supported");
		}

	}

	/**
	 * Method that cleans the list of ingredients for easier API calls.
	 */
	private void cleanIngredients() {
		int end;
		ArrayList<Integer> remove = new ArrayList<Integer>(); 
		for (int i = 0; i < ingredients.size(); i++) {
			
			end = ingredients.get(i).indexOf(":"); 
			if (end != -1) { 
				remove.add(i);
			}

			end = ingredients.get(i).indexOf(" - ");
			if (end != -1) {
				ingredients.set(i, ingredients.get(i).substring(0, end));
			}

			end = ingredients.get(i).lastIndexOf(",");
			if (end != -1) {
				ingredients.set(i, ingredients.get(i).substring(0, end));
			}

			end = ingredients.get(i).lastIndexOf(" to ");
			if (end != -1) {
				ingredients.set(i, ingredients.get(i).substring(0, end));
			}

			end = ingredients.get(i).indexOf(" and ");
			if (end != -1) {
				ingredients.add(ingredients.get(i).substring(end + 5));
				ingredients.set(i, ingredients.get(i).substring(0, end));

			}

		}
		for (int i = remove.size() - 1; i > -1; i--) {
			int removeIndex = remove.get(i);
			ingredients.remove(removeIndex);
			
		}
	}

	/**
	 * Function that parses ingredients into quantity, measurement type
	 * (possibly null), and places them in an Order object. Finishes by adding
	 * each order to Orders arraylist
	 */
	public void parseIngredients() {

		this.initializeMaps();
		this.cleanIngredients();
		Order order;
		String[] pieces;
		boolean hasQuant;

		for (int i = 0; i < ingredients.size(); i++) {
			hasQuant = true;
			pieces = ingredients.get(i).split("\\s");
			int quant;
			try {
				quant = Integer.parseInt(pieces[0]);
			} catch (Exception e) {
				if (numbers.containsKey(pieces[0]))
					quant = numbers.get(pieces[0]);
				else if (pieces[0].contains("/")) {
					quant = 1; 
				}
				else {
					hasQuant = false;
					quant = 1;
				}
			}
			int index;
			if (hasQuant) {
				index = 1;
				if (pieces[1].contains("/")	){
					quant += 1;
					index +=1; 
				}
				
			} else
				index = 0;
			String posMeas = pieces[index];
			if (posMeas.charAt(posMeas.length() - 1) == 's') {
				posMeas = posMeas.substring(0, posMeas.length() - 1);
			}
			int meas = 1;
			if (!measurements.containsKey(posMeas)) {
				meas = 0;
				posMeas = null;
			}

			String item = "";
			for (int j = index + meas; j < pieces.length; j++) {
				item += " " + pieces[j];
			}
			item = item.substring(1);
			order = new Order(quant, posMeas, item);

			orders.add(order);
		}
	}

	/**
	 * Function that initializes Hashmaps for measurement and number matching.
	 */
	private void initializeMaps() {
		measurements = new HashMap<String, Boolean>();
		measurements.put("tablespoon", true);
		measurements.put("teaspoon", true);
		measurements.put("cup", true);
		measurements.put("pint", true);
		measurements.put("quart", true);
		measurements.put("gallon", true);
		measurements.put("fluid ounce", true);
		measurements.put("fl oz", true);
		measurements.put("oz", true);
		measurements.put("pound", true);
		measurements.put("lb", true);
		measurements.put("milliliter", true);
		measurements.put("ml", true);
		measurements.put("liter", true);
		measurements.put("l", true);
		measurements.put("dl", true);
		measurements.put("deciliter", true);
		measurements.put("gram", true);
		measurements.put("kilogram", true);
		measurements.put("kg", true);
		measurements.put("stick", true);
		measurements.put("gill", true);
		measurements.put("mg", true);
		measurements.put("milligram", true);
		measurements.put("inch", true);
		measurements.put("in", true);

		numbers = new HashMap<String, Integer>();
		numbers.put("one", 1);
		numbers.put("two", 2);
		numbers.put("three", 3);
		numbers.put("four", 4);
		numbers.put("five", 5);
		numbers.put("six", 6);
		numbers.put("seven", 7);
		numbers.put("eight", 8);
		numbers.put("nine", 9);
		numbers.put("ten", 10);
	}

}
