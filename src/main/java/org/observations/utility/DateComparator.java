package org.observations.utility;

import java.util.Comparator;

/**
 * Simple comparator for comparing chronologically two dates
 */
public class DateComparator implements Comparator<String> {

    /**
     * Compares its two dates for order. Returns a positive integer, zero, or a negative integer as the first date is before, equal, or after the second date.
     *
     * @param date1 the first date to be compared.
     * @param date2 the second date to be compared.
     * @return 0 if both dates are equal, 1 if second date come after the first, -1 if second date came before the first.
     */
    public int compare(String date1, String date2) {
        return date2.compareTo(date1);
    }
}
