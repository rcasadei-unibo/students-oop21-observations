package org.observations.Model;

/**
 * Simple class for create folder from root, return a filepath string 
 */

import java.io.IOException;
import java.util.ArrayList;

public interface Saved {

	/** create a folder and subfolder require for the path requested 
	 * @param dir
	 * 			absolute path for create folder
	 */
	void makeDir(final String dir);

	/** create a file request in the path selected
	 * @param name
	 * 			absolute path for create file
	 */
	void makeFile(final String name) throws IOException;
	
	
	/** create file and write all the file in the array list
 	 * @param path, arrayList
 	 * 			path absolute path for create file if missed
 	 * 			arrayList list of all file to copy
 	 */
	void writeList(final String path, final ArrayList<String> arrayList) throws IOException;

}