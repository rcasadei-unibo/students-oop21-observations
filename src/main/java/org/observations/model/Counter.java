package org.observations.model;

import java.io.IOException;
import java.util.List;
import org.observations.model.utility.Pair;

/** Simple Counter interface.*/
public interface Counter {

  /**
   * Count all equals occurrence of the list passed contained all data.
   * Return list of pair: getX() = string with type of observations, getY() = number of occurrences.

   * @param list
   *      list of all observations 
   */
  List<Pair<String, Integer>> counter(List<String> list) throws IOException;

}