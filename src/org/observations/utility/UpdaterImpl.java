package org.observations.utility;

/**
 * Updater class for all string passed, create and manage all file in all class.
 * Remember the last hit (student, moment, date) passed for save and manage data.
 * Use {@link ArrayList<String>}
 */

import java.io.IOException;
import java.util.ArrayList;

import org.observations.Loader;
import org.observations.Saved;
import org.observations.Updater;

public class UpdaterImpl implements Updater {

	private final String pathMomentList;
	private final String sep;
	
	private final Loader loader;
	
	private String student;
	private String moment;
	private String date;
	private final String studentRoot;
	private String momentRoot;
	private String dateRoot;
	
	/**
	 * @param pathMomentList, sep, startPath, loader
	 * 			path for moment list
	 * 			file separator for folder
	 * 			start path for student folder
	 * 			loader for load all file needed
	 */
	public UpdaterImpl(final String pathMomentList, final String sep,
								final String startPath, final Loader loader) {
		super();
		this.pathMomentList = pathMomentList;
		this.sep = sep;
		this.studentRoot = startPath;
		this.loader = loader;
	}

	/**
	 * return array list of String for the selected path
	 * @param path
	 * 			path selected for load all file
	 */
	private ArrayList<String> check(final String path) {
		return this.loader.loadFileFolder(path);
	}

	/**
	 * choose or create the student pass
	 * @param student, save
	 * 			student pass: if present is selected else is create with save
	 * 			save: reference to class for create new folder
	 */
	public void chooseStudent(final String student, final Saved save) throws IOException {
		this.student = this.studentRoot + student + this.sep;
		if (!this.loader.loadFileFolder(this.studentRoot).contains(student)) {
			save.makeDir(this.student);
		} else {
			this.momentRoot = this.student;
		}
	}
	
	/**
	 * choose or create the moment pass for last student selected, if moment already present select this root.
	 * list pass is the list of all moment list file create, if new moment is missed add to list e sort
	 * @param moment, updateList, save
	 * 			moment pass: if present is selected else is create with save
	 * 			updateList: if moment create is missed in the list add and sort all list
	 * 			save: reference to class for create new folder
	 */
	public void chooseMoment(final String moment, final ArrayList<String> updateList,
								final Saved save) throws IOException {
		this.moment = this.momentRoot + moment + this.sep;
		if (!this.loader.loadFileFolder(this.momentRoot).contains(moment) && !moment.isBlank()) {
			save.makeDir(this.moment);
			if (!updateList.contains(moment)) {
				updateList.add(moment);
				updateList.sort((a,b)-> a.compareTo(b));
				save.writeList(this.pathMomentList, updateList);
			}
		} else {
				this.dateRoot = this.moment;
		}
	}

	/**
	 * choose or create the date pass for last moment and student selected
	 * @param date, save
	 * 			date pass: if present is selected else is create with save
	 * 			save: reference to class for create new file
	 */
	public void chooseDate(final String date, final Saved save) throws IOException {
		this.date = this.dateRoot + date;
		if (!this.loader.loadFileFolder(this.moment).contains(date)) {
			save.makeFile(this.date);
		}
	}

	/**
	 * return a list of all student observed or empty list, private method observed is a control for item present
	 */
	public ArrayList<String> getObservedStudents() {
		return this.observed(this.studentRoot, this.check(this.studentRoot));
	}

	/**
	 * return a list of all moment observed for last student selected or empty list,
	 * private method observed is a control for item present
	 */
	public ArrayList<String> getObservedMoments() {
		return this.observed(this.momentRoot, this.check(this.momentRoot));
	}

	/**
	 * return a list of all dates observed for last moment and student selected or empty list,
	 * private method observed is a control for item present
	 */
	public ArrayList<String> getObservedDates() {
		return this.observed(this.dateRoot, this.check(this.dateRoot));
	}
	
	/**
	 * return a list of all observations observed for last date, moment and student selected or empty list,
	 * private method observed is a control for item present
	 */
	public ArrayList<String> getObservedDay() throws IOException {
		return this.observed(this.date, this.loader.fillList(this.date));
	}

	/**
	 * add element pass for last date, moment and student selected
	 * @param element, save
	 * 			element pass: if present is selected else is create with save
	 * 			save: reference to class for create new file
	 */
	public void updateObservations(final String element, final Saved save) throws IOException {
		this.updateObservations(this.date, element, save);
	}

	/**
	 * add element pass at path passed, use class save for update file
	 * @param path, element, save
	 * 			path pass: path of file need update
	 * 			element: element to add at file
	 * 			save: reference to class for create new file
	 */
	public void updateObservations(final String path, final String element,	final Saved save) throws IOException {		
		ArrayList<String> list = this.loader.fillList(path);
		list.add(element);
		list.sort((a,b)-> a.compareTo(b));
		save.writeList(path, list);		
	}
	
	/**
	 * private method for control for item present, return empty list or list
	 */
	private ArrayList<String> observed(final String observed, ArrayList<String> returnOK) {
		if (observed.isBlank()) {
			return new ArrayList<>();
		}
		return returnOK;
	}
}
