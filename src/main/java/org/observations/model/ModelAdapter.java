package org.observations.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Class for adapt data send from view to model.
 * Adapter return map and list request from view for student and moment choose (clicked);
 * create the new folder for student and moment, update file of list of moment if moment is missed;
 * create date file if missed in the folder moment and student choose; update this file with
 * type and time (HH:mm:ss) of click;
 * update file of list of type of observation if user need more item. 
 */
public interface ModelAdapter {

  /**
   * Return the moment list user can choose to observe. 
   */
  List<String> getMomentsListFromFile() throws IOException;

  /**
   * Return the type list of observations user can choose to observe. 
   */
  List<String> getTypesListFromFile() throws IOException;

  /**
   * Create new student folder if missed.

   * @param student
   *        string of new student 
   */
  void createStudent(String student) throws IOException;

  /**
   * Create new moment folder if missed.

   * @param moment
   *        string of new moment 
   */
  void createMoment(String moment) throws IOException;

  /**
   * Create new date file if missed.

   * @param date
   *        string of new date 
   */
  void createDate(String date) throws IOException;

  /**
   * Add new type of observation at list created at start.

   * @param type
   *        string of new type of observation 
   */
  void createObservationsType(String type) throws IOException;

  /**
   * When user click on type of observation send the type,
   * the model update observation list for current date/moment/student.
   * Time is the hour, minutes, second when click is do,
   * use SimpleDateFormat class and Date for format needed.

   * @param type
   *        string of type of observation clicked
   */
  void clickObservation(String type) throws IOException;

  /**
   * return list of all student observed or empty list.
   */
  List<String> getStudentsList() throws IOException;

  /**
   * return map: key is the student choose, value is list of moment observed for the student.

   * @param student
   *      name of student choose
   */
  List<String> getMomentsList(String student) throws IOException;

  /**
   * return map: key is the date, value is map: key type observation, value counter.

   * @param moment
   *      moment choose 
   */
  Map<String, Map<String, Integer>> getDatesAndObservations(String moment) throws IOException;

}