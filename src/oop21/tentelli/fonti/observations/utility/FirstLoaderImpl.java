package oop21.tentelli.fonti.observations.utility;

/**
 * Only one use class, when start first time, create file and folder basic. when launch after first start do nothing
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import oop21.tentelli.fonti.observations.FirstLoader;
import oop21.tentelli.fonti.observations.Saved;

public class FirstLoaderImpl implements FirstLoader {
	
	private final ArrayList<String> arrayMomentsList = new ArrayList<>();
	private final ArrayList<String> arrayTypeList = new ArrayList<>();

	public FirstLoaderImpl() {
		super();
	}

	/** 
	 * create folder and file required, fill the file list the first time launched
	 */
	public void firstLoad(final String dir, final String students, final String moments, final String types,
							final Saved save) throws IOException {
		if (!new File(dir + students).exists()) {
			save.makeDir(dir + students);
		}
		if (!new File(dir + moments).exists() && !new File(dir + types).exists()) {
			for (final MomentsList item : MomentsList.values()) {
				this.arrayMomentsList.add(item.getText());
			}
			for (final ObservationsList item : ObservationsList.values()) {
				this.arrayTypeList.add(item.getText());
			}
			save.writeList(dir + moments, this.arrayMomentsList);
			save.writeList(dir + types, this.arrayTypeList);
		}
	}
	
}
