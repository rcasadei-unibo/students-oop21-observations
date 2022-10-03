package org.observations.model.utility;

/**
 * Simple class for create folder from root, return a filepath string 
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.observations.Saved;


public class SavedImpl implements Saved {
	
	/** create a folder and subfolder require for the path requested 
	 * @param dir
	 * 			absolute path for create folder
	 */
	public void makeDir(final String dir) {
		final File createFolder = new File(dir);
		createFolder.mkdirs();
	}
	
	/** create a file request in the path selected
	 * @param name
	 * 			absolute path for create file
	 */
	public void makeFile(final String name) {
		final File createFile = new File(name);
		try {
			createFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Impossible create file\n");
			e.printStackTrace();
		}
	}
	
	/* simple filewrite with try catch and print error */
	private void updateList(final FileWriter fw, final String item) throws IOException {
		try {
			fw.write(item + "\n");
		} catch (IOException e) {
			System.out.println("Impossible add item to list");
			e.printStackTrace();
		}
	}
	
	/** create file and write all the file in the array list
	 	 * @param path, arrayList
	 * 			path absolute path for create file if missed
	 * 			arrayList list of all file to copy
	 */
	public void writeList(final String path, final ArrayList<String> arrayList) throws IOException {
		this.makeFile(path);
		final FileWriter fw = new FileWriter(new File(path));
		for (final String item : arrayList) {
			this.updateList(fw, item);
		}
		fw.flush();
		fw.close();
	}
}
