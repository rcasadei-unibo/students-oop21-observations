package oop21.tentelli.fonti.observations;

/*
 * Simple class for create folder from root, return a filepath string 
 */

import java.io.File;


public class Saved {

	public static final String SEP = File.separator;
	public static final String ROOT = System.getProperty("user.home");
	public static final String SAVE_DIR = ROOT + SEP + "Observations" + SEP + "save";
	
	public String makeDir(String name) {
		
		File dir = new File(SAVE_DIR + name);
		dir.mkdirs();
		return dir.toString();
	}
	
}
