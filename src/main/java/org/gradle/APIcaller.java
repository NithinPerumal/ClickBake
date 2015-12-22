package org.gradle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class APIcaller {
	private String sesKey;
	private String status;
	private HashMap<String, String> lastRes;
	private HashMap<Integer, String> keyMap = new HashMap<Integer, String>();
	private HashMap<String, String> IDMap = new HashMap<String, String>();
	private HashMap<String, String> nameMap = new HashMap<String, String>();
	private HashMap<String, Double> priceMap = new HashMap<String, Double>();
	private ArrayList<String> results = new ArrayList<String>();
	private int calls = 0;
	private PriorityQueue<Integer> searchranks = new PriorityQueue<Integer>();
	private boolean fake;

	public APIcaller(String devKey, String appKey, boolean fake) {
		try {
			this.fake = fake;
			URL start = new URL(
					"https://secure.techfortesco.com/tescolabsapi/restservice.aspx?command=LOGIN&email=&password=&developerkey="
							+ devKey + "&applicationkey=" + appKey);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					openURL(start, true)));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.length() > 15) {
					if (inputLine.substring(1, 11).equals("StatusCode"))
						this.status = inputLine.substring(14,
								inputLine.length() - 1);
					else if (inputLine.substring(1, 11).equals("SessionKey"))
						this.sesKey = inputLine.substring(15,
								inputLine.length() - 2);
				}

			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		File json = new File("json/" + args[0] + "-web.json");
		boolean ordering = args.length > 1 && args[1].equals("-o");
		boolean fake = args.length < 3 || args[2].equals("-f");
		try {
			BufferedReader in = new BufferedReader(new FileReader(json));
			String line = in.readLine();
			APIcaller caller = new APIcaller("2Dx", "205", fake);
			while ((line = in.readLine()) != null) {
				if (line.length() > 2) {
					makeFakes(line);
					caller.search(line);
				}
			}
			in.close();
			if (ordering)
				caller.order();
			else
				caller.makeJSON(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void makeFakes(String line) {
		try {
			PrintWriter writer = new PrintWriter("json/" + "PRODUCTSEARCH"
					+ APIcaller.clean(line) + "1ety23" + ".json");
			writer.println("{\"ingredients\":[");
			boolean isSet = false;
			String[] brands = new String[] { "Wil Wheaton", "Tesco Value",
					"Nike", "Nithin's Homemade", "Nithin's House of Horrors" };
			for (int i = 0; i < Math.random() * 10 + 2; i++) {
				if (isSet)
					writer.println(",");
				writer.println("{");
				boolean discreet = line.charAt(line.length() - 1) != ('s');
				writer.println("\"Name\": \""
						+ brands[(int) (Math.random() * brands.length)]
						+ (Math.random() > .5 ? "" : " shredded")
						+ " "
						+ (Math.random() <= .999 ? line
								: "JOOOOOOOOOOOOHN CENAAAAA - https://youtu.be/wRRsXxE1KVY")
						// TODO: take this out before any actual release. Until
						// then
						// leave in to promote dev team mental health
						+ (Math.random() > .5 ? "" : " juice") + " "
						+ (int) (Math.random() * (!discreet ? 1000 : 10))
						+ (!discreet ? (Math.random() > .9 ? " Zoidberg" : "g")
						// TODO: take this out before any actual release. Until
						// then
						// leave in to promote dev team mental health
								: " count") + "\",");
				writer.println("\"Price\": "
						+ (double) (Math.round(Math.random() * 1000)) / 100);
				writer.println("}");
				isSet = true;
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeJSON(String oldName) {
		String[] split = oldName.split("\\-|\\.");
		String newName = split[0] + "-tesco";
		try {
			PrintWriter writer = new PrintWriter("json/" + newName + ".json");
			writer.println("{\"ingredients\":[");
			boolean isSet = false;
			for (String s : this.results) {
				if (isSet)
					writer.println(",");
				writer.println("{");
				writer.println("\"Name\": \"" + s + "\",");
				writer.println("\"Price\": " + priceMap.get(s));
				writer.println("}");
				isSet = true;
			}
			writer.println("]}");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object[] getSelection() {
		return this.IDMap.values().toArray();
	}

	public String getStatus() {
		return this.status;
	}

	public boolean order() {
		try {
			ArrayList<String> ingredients = this.results;
			for (String ing : ingredients) {
				this.order(ing);
			}
			this.chooseDelivery();
			this.placeOrder();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean order(ArrayList<String> ingredients) {
		try {
			for (String ing : ingredients) {
				this.order(ing);
			}
			this.chooseDelivery();
			this.placeOrder();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Picking the delivery here
	 * 
	 * @throws Exception
	 */
	public void chooseDelivery() throws Exception {
		URL delSlots = new URL(
				"https://secure.techfortesco.com/tescolabsapi/restservice.aspx?command=LIST_DELIVERY_SLOTS&sessionkey="
						+ sesKey);
		BufferedReader in = new BufferedReader(new InputStreamReader(openURL(
				delSlots, true)));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.length() > 15) {
				if (inputLine.substring(1, 11).equals("StatusCode"))
					this.status = inputLine.substring(14,
							inputLine.length() - 1);
				else if (inputLine.length() > 19
						&& inputLine.substring(1, 15).equals("DeliverySlotId"))
					break;
			}
		}
		in.close();
		String ID = inputLine.substring(19, inputLine.length() - 2);
		URL chooseSlot = new URL(
				"https://secure.techfortesco.com/tescolabsapi/restservice.aspx?command=CHOOSE_DELIVERY_SLOT&deliveryslotid="
						+ ID + "&sessionkey=" + sesKey);
		openURL(chooseSlot, false);
	}

	public void placeOrder() throws Exception {
		openURL(new URL(
				"https://secure.techfortesco.com/tescolabsapi/restservice.aspx?command=READY_FOR_CHECKOUT&sessionkey="
						+ sesKey), false);
	}

	public boolean hasSession() {
		return this.sesKey != null;
	}

	public void search(String searchText) throws Exception {
		URL searchURL = new URL(
				"https://secure.techfortesco.com/tescolabsapi/restservice.aspx?command=PRODUCTSEARCH&searchtext="
						+ APIcaller.clean(searchText)
						+ "&page=1&sessionkey="
						+ sesKey);
		BufferedReader in = new BufferedReader(new InputStreamReader(openURL(
				searchURL, true)));
		String inputLine;
		lastRes = new HashMap<String, String>();
		String ID = "-1";
		int score = 0;
		String name = "";
		Double price = 0.0;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.length() > 18
					&& inputLine.substring(1, 14).equals("BaseProductId")) {
				ID = inputLine.substring(18, inputLine.length() - 2);
			}
			if (inputLine.length() > 15)
				if (inputLine.substring(1, 11).equals("StatusCode"))
					this.status = inputLine.substring(14,
							inputLine.length() - 1);
			if (inputLine.length() > 9
					&& inputLine.substring(0, 7).equals("\"Price\""))
				price = Double.parseDouble(inputLine.substring(9,
						inputLine.length() - 1));
			if (inputLine.length() > 9
					&& inputLine.substring(1, 5).equals("Name")) {
				name = inputLine.substring(9, inputLine.length() - 2);
			} else {
				String[] input = inputLine.split(":");
				String s;
				if (inputLine.length() > 4 && input.length > 1
						&& input[1].length() > 1) {
					s = input[1].substring(1, input[1].length() - 1);
					lastRes.put(input[0], s);
				}
			}
			score = APIcaller.score(name, searchText, price);
			this.searchranks.offer(score);
			this.keyMap.put(score, ID);
			this.nameMap.put(ID, name);
			this.priceMap.put(name, price);
		}
		in.close();
		String key = this.keyMap.get(this.searchranks.poll());

		this.IDMap.put(searchText, key);
		this.results.add(this.nameMap.get(key));
		this.searchranks.clear();
		this.keyMap.clear();
		calls++;
	}

	public static String clean(String in) {
		String out;
		String[] in2 = in.split("\\s|\\/");
		out = in2[0];
		for (int i = 1; i < in2.length; i++)
			out += "+" + in2[i];
		return out;
	}

	public void order(String searchText) throws Exception {
		try {
			if (!this.IDMap.containsKey(searchText))
				this.search(searchText);
			URL orderURL = new URL(
					"https://secure.techfortesco.com/tescolabsapi/restservice.aspx?command=CHANGE_BASKET&productID="
							+ this.IDMap.get(searchText)
							+ "&changeQuantity=1&substitution=YES&notesforshopper=Original+Ingredient+From+Recipe+"
							+ searchText + "&sessionkey=" + sesKey);
			openURL(orderURL, false);
		} catch (Exception e) {
			throw e;
		}
	}

	public static int score(String found, String search, Double price) {
		int total = 100000;
		if (found.equals(search))
			return Integer.MIN_VALUE;
		String[] search2 = search.split(" ");
		String[] found2 = found.split(" ");
		for (int i = 0; i < search2.length; i++) {
			for (int j = i; j < found2.length; j++) {
				if (search2[i].equals(found2[j])) {
					total -= 1000;
					j = found2.length;
				}
			}
		}
		for (int i = 0; i < search2.length; i++) {
			for (int j = 0; j < found2.length; j++) {
				if (search2[i].equals(found2[j])) {
					total -= 500;
					j = found2.length;
				}
			}
		}
		return (int) (total + found.length() - Math.round(price * 100));
	}

	public InputStream openURL(URL url, boolean ret) throws IOException {
		if (!this.fake)
			return url.openStream();
		if (!ret)
			return null;
		else
			return new FileInputStream(this.urlToFile(url));
	}

	public File urlToFile(URL url) {
		String s = url.toString();
		String[] s2 = s.split("=");
		String filename = "";
		for (int i = 1; i < s2.length; i++) {
			filename += s2[i].split("&")[0];
		}
		return new File("json/" + filename + ".json");
	}

	public int getCalls() {
		return this.calls;
	}

	public HashMap<String, String> getPreviousPageResults() {
		return this.lastRes;
	}
}
