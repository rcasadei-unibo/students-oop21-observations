package org.observations.model;

import java.io.IOException;
import java.util.List;
import org.observations.model.utility.Pair;

/**
 * Model core create all class need and pass reference for create, write, save and load.
 * Make first load if is first time start software application create file moment list and
 * type observed list text format used to show the list of item user can get and add in future.
 * Updater get all file for update and create file and folder user required.
 */
public interface ModelCore {

  /**
   * return copy of the list of all moments user can choose.
   */
  List<String> getMomentsList() throws IOException;

  /**
   * return copy of the list of all types of observations user can choose.
   */
  List<String> getTypeList() throws IOException;

  /**
   * method for choose student or create if missed.

   * @param student
   *      string for name student choose/selected 
   */
  void chooseStudent(String student) throws IOException;

  /**
   * method for choose moment for selected student or create if folder missed.

   * @param moment
   *      moment choose/selected for selected student 
   */
  void chooseMoment(String moment) throws IOException;

  /**
   * method for choose data for selected student and moment or create if file missed.

   * @param date
   *      date choose/selected for selected student and moment 
   */
  void chooseDate(String date) throws IOException;

  /**
   * method for add new click (observation) at student, moment and date choose.

   * @param time type
   *      time is a string for the HH:mm:ss of click
   *      type is a string for name of type observation
   */
  void updateObservations(String time, String type) throws IOException;

  /**
   * method for add new type of observation in the first list created.

   * @param type
   *      type is a string for name of type observation
   */
  void addObservationType(String type) throws IOException;

  /**
   * return a copy of list of all observation for selected student, moment and date choose.
   */
  List<String> getDataDayChoose() throws IOException;

  /**
   * return a copy of list for current student and moment.
   * List contain pair: first item is a string with type of observation,
   * second item is number of time the type observed.
   * It can be used for generate graph
   */
  List<Pair<String, Integer>> getDataMomentChoose() throws IOException;

  /**
   * return a copy of list for current student.
   * List contain pair: first item is a string with type of observation,
   * second item is number of time the type is observed. 
   * It can be used for generate graph
   */
  List<Pair<String, Integer>> getDataStudentChoose() throws IOException;

  /**
   * return a copy of list for current student, moment and date.
   * List contain pair: first item is a string with type of observation,
   * second item is number of time the type is observed.
   * It can be used for generate graph and refresh counter types for the day selected.
   */
  List<Pair<String, Integer>> getCounterDayChoose() throws IOException;

  /**
   * return a copy of list for current student and moment.
   * List contain pair: first item is a string with date,
   * second item is number of all observations in this date.
   * It can be used for refresh counter view for date button.
   */
  List<Pair<String, Integer>> getCounterDates() throws IOException;

  /**
   * return a copy of list for selected student.
   * List contain pair: first item is a string with moment,
   * second item is number of all observations in this moment.
   * It can be used for refresh counter view for moment button.
   */
  List<Pair<String, Integer>> getCounterMoments() throws IOException;

  /**
   * return a copy of list for all students.
   * List contain pair: first item is a string with student,
   * second item is number of all observations in this student.
   * It can be used for refresh counter view for student button.
   */
  List<Pair<String, Integer>> getCounterStudents() throws IOException;

}