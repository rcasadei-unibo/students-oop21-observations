package org.observations.Model;

/**
 * Only one use class, when start first time, create file and folder basic. when launch after first start do nothing
 */

import java.io.IOException;

public interface FirstLoader {

	/** 
	 * create folder and file required, fill the file list the first time launched
	 */
	void firstLoad(final String dir, final String students, final String moments, final String types,
			final Saved save) throws IOException;

}