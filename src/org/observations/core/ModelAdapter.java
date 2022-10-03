package org.observations.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.observations.ModelCore;
import org.observations.utility.Pair;

public class ModelAdapter {

	private final ModelCore mc;

	public ModelAdapter() throws IOException {
		super();
		this.mc = new ModelCoreImpl();
	}
	
	/**
	 * return list of all student observed
	 */
	public List<String> getStudentsList() throws IOException {
		return this.listMaker(this.mc.getCounterStudents());
	}
	
	
	/**
	 * return a map of: key is the student choose, value is list of moment observed
	 * @param student
	 * 			name of student choose
	 * @throws IOException 
	 */
	public Map<String, List<String>> getMomentsList(final String student) throws IOException {
		Map<String, List<String>> map = new HashMap<>();
		this.mc.chooseStudent(student);
		map.put(student, this.listMaker(this.mc.getCounterMoments()));
		return map;
	}
	
	/**
	 * return a map of: key is the student choose, value is list of moment observed
	 * @param student
	 * 			name of student choose
	 * @throws IOException 
	 */
	public Map<String, Map<String, Integer>> getDatesAndObservations(final String moment) throws IOException {
		Map<String, Map<String, Integer>> map = new HashMap<>();
		this.mc.chooseMoment(moment);
		List<String> list = List.copyOf(this.listMaker(this.mc.getCounterDates()));
		for (final String element : list) {
			this.mc.chooseDate(element);
			Map<String, Integer> mapValue = new HashMap<>();
			for (final Pair<String, Integer> pair : this.mc.getCounterDayChoose()) {
				mapValue.put(pair.getX(), pair.getY());
			}
			map.put(element, mapValue);
		}
		return map;
	}
	
	/**
	 * make list of all string from ArrayList of Pair, use only getx for the string attribute
	 * @param array
	 * 			need the array of pair to convert
	 */
	private List<String> listMaker(final ArrayList<Pair<String, Integer>> array){
		List<String> list = new ArrayList<>();
		for (final Pair<String, Integer> pair : array) {
			list.add(pair.getX());
		}
		return list;
	}
	
}
