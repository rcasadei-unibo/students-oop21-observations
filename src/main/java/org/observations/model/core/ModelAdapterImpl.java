package org.observations.model.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.observations.model.ModelAdapter;
import org.observations.model.ModelCore;
import org.observations.model.utility.Pair;

/**
 * Class for adapt data send from view to model.
 * Adapter return map and list request from view for student and moment choose (clicked);
 * create the new folder for student and moment, update file of list of moment if moment is missed;
 * create date file if missed in the folder moment and student choose; update this file with
 * type and time (HH:mm:ss) of click;
 * update file of list of type of observation if user need more item. 
 */
public class ModelAdapterImpl implements ModelAdapter {

  private final ModelCore mc;

  public ModelAdapterImpl() throws IOException {
    super();
    this.mc = new ModelCoreImpl();
  }

  /**
   * Return the moment list user can choose to observe. 
   */
  public List<String> getMomentsListFromFile() throws IOException {
    return this.mc.getMomentsList();
  }

  /**
   * Return the type list of observations user can choose to observe. 
   */
  public List<String> getTypesListFromFile() throws IOException {
    return this.mc.getTypeList();
  }
  
  /**
   * Create new student folder if missed.

   * @param student
   *        string of new student 
   */
  public void createStudent(final String student) throws IOException {
    this.mc.chooseStudent(student);
  }

  /**
   * Create new moment folder if missed.

   * @param moment
   *        string of new moment 
   */
  public void createMoment(final String moment) throws IOException {
    this.mc.chooseMoment(moment);
  }

  /**
   * Create new date file if missed.

   * @param date
   *        string of new date 
   */
  public void createDate(final String date) throws IOException {
    this.mc.chooseDate(date);
  }

  /**
   * Add new type of observation at list created at start.

   * @param type
   *        string of new type of observation 
   */
  public void createObservationsType(final String type) throws IOException {
    this.mc.addObservationType(type);
  }
  
  /**
   * When user click on type of observation send the type,
   * the model update observation list for current date/moment/student.
   * Time is the hour, minutes, second when click is do,
   * use SimpleDateFormat class and Date for format needed.

   * @param type
   *        string of type of observation clicked
   */
  public void clickObservation(final String type) throws IOException {
    final String time = new SimpleDateFormat("HH:mm:ss", Locale.ITALIAN)
        .format(Date.from(Instant.now(Clock.systemDefaultZone())));
    this.mc.updateObservations(time, type);
  }

  /**
   * return list of all student observed or empty list.
   */
  public List<String> getStudentsList() throws IOException {
    return this.mc.getObservedStudents();
  }

  /**
   * return map: key is the student choose, value is list of moment observed for the student.

   * @param student
   *      name of student choose
   */
  public List<String> getMomentsList(final String student) throws IOException {
    this.mc.chooseStudent(student);
    return this.mc.getObservedMoments();
  }

  /**
   * return map: key is the date, value is map: key type observation, value counter.

   * @param moment
   *      moment choose 
   */
  public Map<String, Map<String, Integer>> getDatesAndObservations(final String moment)
      throws IOException {
    final Map<String, Map<String, Integer>> map = new HashMap<>();
    this.mc.chooseMoment(moment);
    final List<String> list = List.copyOf(this.listMaker(this.mc.getCounterDates()));
    for (final String element : list) {
      this.mc.chooseDate(element);
      final Map<String, Integer> mapValue = new HashMap<>();
      for (final Pair<String, Integer> pair : this.mc.getCounterDayChoose()) {
        mapValue.put(pair.getX(), pair.getY());
      }
      map.put(element, mapValue);
    }
    return map;
  }

  /**
   * make list of all string from List of Pair, use only getX() for the string attribute.

   * @param list
   *      need the list of pair to convert
   */
  private List<String> listMaker(final List<Pair<String, Integer>> list) {
    final List<String> fList = new ArrayList<>();
    for (final Pair<String, Integer> pair : list) {
      fList.add(pair.getX());
    }
    return fList;
  }

}
