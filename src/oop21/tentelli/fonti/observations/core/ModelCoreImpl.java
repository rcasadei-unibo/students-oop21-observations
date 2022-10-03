package oop21.tentelli.fonti.observations.core;

/**
 * Model core app, create all class need and pass reference for create, write and load. Create a first load:
 * if is first time start software
 * application create file moment list and type observed list format .txt, used for view to show the list of item user can get, if user need
 * more item can add; if the installation are do after, load all list for user. Updater get all file for update and create file and folder
 * user required.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oop21.tentelli.fonti.observations.Updater;
import oop21.tentelli.fonti.observations.Counter;
import oop21.tentelli.fonti.observations.Loader;
import oop21.tentelli.fonti.observations.ModelCore;
import oop21.tentelli.fonti.observations.Saved;
import oop21.tentelli.fonti.observations.utility.UpdaterImpl;
import oop21.tentelli.fonti.observations.utility.CounterImpl;
import oop21.tentelli.fonti.observations.utility.FirstLoaderImpl;
import oop21.tentelli.fonti.observations.utility.LoaderImpl;
import oop21.tentelli.fonti.observations.utility.Pair;
import oop21.tentelli.fonti.observations.utility.SavedImpl;

public class ModelCoreImpl implements ModelCore {

	private static final String SEP = File.separator;
	private static final String ROOT = System.getProperty("user.home");
	private static final String NAME_APP = "Observations";
	private static final String SAVE_DIR = "save";
	private static final String STUDENT_DIR = "students";
	private static final String DIR = ROOT + SEP + NAME_APP + SEP + SAVE_DIR + SEP;
	
	private static final String MOMENTS_LIST = "moments.txt";
	private static final String TYPE_OBSERVED_LIST = "observations.txt";
	private static final String EXTENSION = ".txt";
	
	private final Saved save;
	private final Loader loader;
	private final Updater updater;
	private final Counter counter;
	
	private String student = "";
	private String moment = "";
	
	private String tempStudent = "";
	private String tempMoment = "";
	
	/**
	 * create new object for counter, save and loader, first start and updater with parameter need for class
	 * @param for FirstLoaderImpl: root save, 2 string for name list of moment and type, save and loader for create and load;
	 * 		  for UpdaterImpl: path moments list, separator, path for students dir e loader
	 */
	public ModelCoreImpl() throws IOException {
		super();
		this.save = new SavedImpl();
		this.loader = new LoaderImpl();
		this.counter = new CounterImpl();
		new FirstLoaderImpl().firstLoad(DIR, STUDENT_DIR, MOMENTS_LIST, TYPE_OBSERVED_LIST, save);;
		this.updater = new UpdaterImpl(DIR + MOMENTS_LIST, SEP, DIR + STUDENT_DIR + SEP, loader);
	}
	
	/**
	 * return copy of the list of all moments user can choose
	 */
	public ArrayList<String> getArrayMomentsList() throws IOException {
		return new ArrayList<>(List.copyOf(this.loader.fillList(DIR + MOMENTS_LIST)));
	}

	/**
	 * return copy of the list of all types of observations user can choose
	 */
	public ArrayList<String> getArrayTypeList() throws IOException {
		return new ArrayList<>(List.copyOf(this.loader.fillList(DIR + TYPE_OBSERVED_LIST)));
	}
	
	/**
	 * method for choose student or create if missed
	 * 
	 * @param student
	 * 		string for name student choose/selected 
	 */
	public void chooseStudent(final String student) throws IOException {
		this.student = student;
		this.updater.chooseStudent(student, this.save);
	}
	
	/**
	 * method for choose moment for selected student or create if missed
	 * 
	 * @param moment
	 * 		moment choose/selected for selected student 
	 */
	public void chooseMoment(final String moment) throws IOException {
		this.moment = moment;
		this.updater.chooseMoment(moment, this.getArrayMomentsList(), this.save);
	}

	/**
	 * method for choose data for selected student and moment or create if missed
	 * 
	 * @param data
	 * 		data choose/selected for selected student and moment 
	 */
	public void chooseDate(final String date) throws IOException {
		this.updater.chooseDate(date + EXTENSION, this.save);
	}
	
	/**
	 * method for add new click (observation) at student, moment anda date choose
	 * @param time, type
	 * 					time is a string for the HH:mm:ss of click
	 * 					type is a string for name of type observation
	 */
	public void updateObservations(final String time, final String type) throws IOException {
		this.updater.updateObservations(type + " - " + time, this.save);
	}

	/**
	 * method for add new type of observation in the first list created
	 * @param type
	 * 					type is a string for name of type observation
	 */
	public void addObservationType(final String type) throws IOException {
		if (!this.getArrayTypeList().contains(type)) {
			this.updater.updateObservations(DIR + TYPE_OBSERVED_LIST, type, this.save);
		}
	}
	
	/**
	 * return a copy of list of all observation for selected student, moment and data choose
	 */
	public ArrayList<String> getDataDayChoose() throws IOException {
		return new ArrayList<>(List.copyOf(this.updater.getObservedDay()));
	}
	
	/**
	 * return a copy of list for selected student and moment.
	 * List contain pair: first item is a string with type of observation,
	 * second item is number of time the type observed.
	 * It can be used for generate graph
	 */
	public ArrayList<Pair<String, Integer>> getDataMomentChoose() throws IOException {
		return this.counter.counter(this.momentObservations());
	}
	
	/**
	 * return a copy of list for selected student.
	 * List contain pair: first item is a string with type of observation,
	 * second item is number of time the type observed 
	 * It can be used for generate graph
	 */
	public ArrayList<Pair<String, Integer>> getDataStudentChoose() throws IOException {
		return this.counter.counter(this.studentObservations());
	}
	
	/**
	 * return a copy of list for selected student, moment and date.
	 * List contain pair: first item is a string with type of observation,
	 * second item is number of time the type observed.
	 * It can be used for generate graph and refresh counter types for the day selected.
	 */
	public ArrayList<Pair<String, Integer>> getCounterDayChoose() throws IOException {
		return this.counter.counter(this.getDataDayChoose());
	}
	
	/**
	 * return a copy of list for selected student and moment.
	 * List contain pair: first item is a string with date,
	 * second item is number of all observations in this date.
	 * It can be used for refresh counter view for date button.
	 */
	public ArrayList<Pair<String, Integer>> getCounterDates() throws IOException {
		ArrayList<Pair<String, Integer>> list = new ArrayList<>();
		for (final String element : this.getObservedDates()) {
			this.chooseDate(element);
			ArrayList<Pair<String, Integer>> tempList = this.counter.counter(this.getDataDayChoose());
			int sum = 0;
			for (final Pair<String, Integer> pair : tempList) {
				sum += pair.getY();
			}
			list.add(new Pair<>(element, sum));
		}
		return list;
	}

	public ArrayList<Pair<String, Integer>> getCounterMoments() throws IOException {
		if (this.tempStudent.isBlank()) {
			this.tempMoment = this.moment;
		}
		ArrayList<Pair<String, Integer>> list = new ArrayList<>();
		for (final String element : this.getObservedMoments()) {
			this.chooseMoment(element);
			ArrayList<Pair<String, Integer>> tempList = this.getCounterDates();
			int sum = 0;
			for (final Pair<String, Integer> pair : tempList) {
				sum += pair.getY();
			}
			list.add(new Pair<>(element, sum));
		}
		if (this.tempStudent.isBlank()) {
			this.refreshMoment(this.tempMoment);
			this.resetMoment();
		}
		return list;
	}
	
	/**
	 * return a copy of list for all students.
	 * List contain pair: first item is a string with student,
	 * second item is number of all observations in this student.
	 * It can be used for refresh counter view for student button.
	 */
	public ArrayList<Pair<String, Integer>> getCounterStudents() throws IOException {
		if (this.tempStudent.isBlank() && !this.student.isBlank()) {
			tempStudent = this.student;
			tempMoment = this.moment;
		}
		ArrayList<Pair<String, Integer>> list = new ArrayList<>();
		for (final String element : this.getObservedStudents()) {
			this.chooseStudent(element);
			ArrayList<Pair<String, Integer>> tempList = this.getCounterMoments();
			int sum = 0;
			for (final Pair<String, Integer> pair : tempList) {
				sum += pair.getY();
			}
			list.add(new Pair<>(element, sum));
		}
		this.refreshStudent(this.tempStudent, this.tempMoment);
		this.resetStudent();
		return list;
	}

	/**
	 * Return list of all observation for all date observed for the student and moment choose. 
	 */
	private ArrayList<String> momentObservations() throws IOException {
		ArrayList<String> list = new ArrayList<>();
		for (final String e : this.getObservedDates()) {
			this.chooseDate(e);
			list.addAll(this.getDataDayChoose());
		}
		list.sort((a,b)->a.compareTo(b));
		return list;
	}
	
	/**
	 * Return list of all observation for all dates and all moments observed for the student choose. 
	 */
	private ArrayList<String> studentObservations() throws IOException {
		this.tempMoment = this.moment;
		ArrayList<String> list = new ArrayList<>();
		for (final String e : this.getObservedMoments()) {
			this.chooseMoment(e);
			list.addAll(this.momentObservations());
		}
		list.sort((a,b)->a.compareTo(b));
		this.refreshMoment(this.tempMoment);
		resetMoment();
		return list;
	}
	
	/**
	 * Return list of all student observed. 
	 */
	private ArrayList<String> getObservedStudents() {
		return new ArrayList<>(List.copyOf(this.updater.getObservedStudents()));
	}

	/**
	 * Return list of all moments observed. 
	 */
	private ArrayList<String> getObservedMoments() {
		return new ArrayList<>(List.copyOf(this.updater.getObservedMoments()));
	}

	/**
	 * Return list of all dates observed. 
	 */
	private ArrayList<String> getObservedDates() {
		ArrayList<String> list = new ArrayList<>();
		for (String string : List.copyOf(this.updater.getObservedDates())) {
			string = string.substring(0, string.length()-4);
			list.add(string);
		}
		return list;
	}
	
	/**
	 * simple refresher for choose student and refresh moment.
	 * @param stud, mom
	 * 					stud: student choose
	 * 					mom: moment choose 
	 */
	private void refreshStudent(final String stud, final String mom) throws IOException {
		this.student = stud;
		this.chooseStudent(this.student);
		this.refreshMoment(mom);
	}
	/**
	 * simple refresher for choose moment.
	 * @param mom
	 * 				mom: moment choose 
	 */
	private void refreshMoment(final String mom) throws IOException {
		this.moment = mom;
		this.chooseMoment(this.moment);
	}
	
	/**
	 * simple reset for student for access control
	 */
	private void resetStudent() {
		this.tempStudent = "";
		this.resetMoment();
	}
	
	/**
	 * simple reset for moment for access control
	 */
	private void resetMoment() {
		this.tempMoment = "";
	}
	
}