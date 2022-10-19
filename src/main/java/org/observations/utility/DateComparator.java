package org.observations.utility;

import java.util.Comparator;

public class DateComparator implements Comparator<String> {

    public int compare(String date1, String date2) {
        return date2.compareTo(date1);
    }
}
