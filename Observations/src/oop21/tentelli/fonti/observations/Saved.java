package oop21.tentelli.fonti.observations;

import java.io.File;


public class Saved {

	public static final String SEP = File.separator;
	public static final String ROOT = System.getProperty("user.home");
	private final String nameApp = "Observations";
	
	public boolean makeDir() {
		
		File name = new File(ROOT + SEP + nameApp + "save");
		return name.mkdir();

	}
	
}
