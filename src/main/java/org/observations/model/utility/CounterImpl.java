package org.observations.model.utility;

/**
 * Utility class for counter number of time same observation
 */

import java.io.IOException;
import java.util.ArrayList;

import org.observations.model.Counter;

public class CounterImpl implements Counter {

	public CounterImpl() {
		super();
	}
	
	/**
	 * Remove separator " - " for all string in the list passed.
	 * Return array with this string splitted.
	 * @param arrayList
	 * 					arrayList is the list of all observations 
	 */
	private ArrayList<Pair<String, String>> splitDatas(final ArrayList<String> arrayList) throws IOException{
		ArrayList<Pair<String, String>> list = new ArrayList<>();
		for (final String string : arrayList) {
			String[] split = string.split(" - ");
			list.add(new Pair<>(split[0], split[1]));
		}
		return list;
	}

	/**
	 * Count all equals occurency of the list passed contained all data.
	 * Return array with first item is a string with the type of observations, second is the number of occurrency.
	 * @param arrayList
	 * 					arrayList is the list of all observations 
	 */
	public ArrayList<Pair<String, Integer>> counter(final ArrayList<String> arrayList) throws IOException {
		ArrayList<Pair<String, Integer>> list = new ArrayList<>();
		for (final Pair<String, String> counter : this.splitDatas(arrayList)) {
			ArrayList<Pair<String, Integer>> tempList = new ArrayList<>();
			tempList.addAll(list);
			String element = counter.getX();
			int inc = 0;
			if (!list.isEmpty()) {	
				for (final Pair<String, Integer> item : tempList) {
					if (item.getX().equals(element)) {
						inc = item.getY();
						list.remove(new Pair<>(item.getX(), item.getY()));
					}
				}
			}
			list.add(new Pair<>(element, ++inc));
		}
		return list;
	}
	
}
