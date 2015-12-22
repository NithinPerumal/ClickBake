package org.gradle;



import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;
import org.w3c.tidy.Tidy;

public class JTidyHTMLtest {
	
	File htmlpath = new File("html/");
	
	@Test
	public void validateAllHTML() {
		ArrayList<String> files = listFilesForFolder(htmlpath);
		Tidy tidy = new Tidy();
		for (String file : files) {
			if (file.contains(".html")) {
				String content = "";
				try {
					Scanner sscanner = new Scanner(new File(htmlpath.getPath() + "/" + file));
					Scanner scanner = sscanner.useDelimiter("\\Z");
					content = scanner.next();
					scanner.close();
					sscanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tidy.parse(new ByteArrayInputStream(content.getBytes()), System.out);
				if (tidy.getParseErrors() > 0) {
					fail("HTML is invalid in file " + file);
				}
			}
		}
	}
	
	@Test
	public void validataPHPoutput() {
		ArrayList<String> files = listFilesForFolder(htmlpath);
		Tidy tidy = new Tidy();
		for (String file : files) {
			if (file.contains(".php")) {
				String content = "";
				try {
					Process p = Runtime.getRuntime().exec("php " + htmlpath.getPath() + "/" + file);
					p.waitFor();
					String line;
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			        while((line = input.readLine()) != null){
			            content = content + line;
			        }
			        input.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tidy.parse(new ByteArrayInputStream(content.getBytes()), System.out);
				if (tidy.getParseErrors() > 0) {
					fail("PHP output is invalid in file " + file);
				}
			}
		}
	}
	
	public ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> ret = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	ret.addAll(listFilesForFolder(fileEntry));
	        } else {
	            ret.add(fileEntry.getName());
	        }
	    }
	    return ret;
	}
}
