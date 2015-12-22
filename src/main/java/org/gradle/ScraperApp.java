package org.gradle;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * App that calls webScraper to scrape a webpage for ingredients from a given
 * url, and then writes the results to a file whose name is based on a given username.
 * 
 * args[0] is must be the username
 * args[1] is must be the url. 
 * 
 * @author mcgarrdg
 * 
 */
public class ScraperApp {

	public static void main(String[] args) {
		
		String username = args[0];
		String url = args[1];
		Writer writer = null;
		try {
			String filename = "./json/" + username + "-web.json";
			webScraper scrappy = new webScraper(url);
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "utf-8"));
			writer.write(scrappy.getItems());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

}
