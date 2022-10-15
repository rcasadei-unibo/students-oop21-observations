package org.observations.model;

import java.io.IOException;
import java.util.List;

/**
 * Updater class for all string passed, create and manage all file in all class.
 * Remember the last hit (student, moment, date) passed for save and manage data.
 * Use @link {ArrayList<>}
 */
public interface Updater {

  /**
   * choose or create the student pass.

   * @param student save loader
   *      student pass: if present is selected else is create with save
   *      save: reference to class for create new folder
   *      loader: reference to class for load file
   */
  void chooseStudent(String student, Saved save, Loader loader) throws IOException;

  /**
   * choose or create the moment pass for last student selected, if moment present select this root.
   * list pass is the list of all moment list file, if new moment is missed add to list e sort.

   * @param moment updateList save loader
   *      moment pass: if present is selected else is create with save
   *      updateList: if moment create is missed in the list add and sort all list
   *      save: reference to class for create new folder
   *      loader: reference to class for load file
   */
  void chooseMoment(String moment, List<String> updateList, Saved save, Loader loader)
      throws IOException;

  /**
   * choose or create the date pass for last moment and student selected.

   * @param date save loader
   *      date pass: if present is selected else is create with save
   *      save: reference to class for create new file
   *      loader: reference to class for load file
   */
  void chooseDate(String date, Saved save, Loader loader) throws IOException;

  /**
   * return a list of all student observed or empty list.
   */
  List<String> getObservedStudents(Loader loader);

  /**
   * return a list of all moment observed for last student selected or empty list.
   */
  List<String> getObservedMoments(Loader loader);

  /**
   * return a list of all dates observed for last moment and student selected or empty list.
   */
  List<String> getObservedDates(Loader loader);

  /**
   * return a list of all observations observed for last date/moment/student selected or empty list.
   */
  List<String> getObservedDay(Loader loader) throws IOException;

  /**
   * add element pass for last date, moment and student selected.

   * @param element save loader
   *      element pass: if present is selected else is create with save
   *      save: reference to class for create new file
   *      loader: reference to class for load file
   */
  void updateObservations(String element, Saved save, Loader loader) throws IOException;

  /**
   * add element pass at path passed, use class save for update file.

   * @param path element save
   *      path pass: path of file need update
   *      element: element to add at file
   *      save: reference to class for create new file
   */
  void updateObservations(String path, String element, Saved save, Loader loader)
      throws IOException;

  /**
   * Full Reset. 
   */
  void reset();
  
  /**
   * Reset date and date root.
   */
  void resetDate();
  
  /**
   * Only for test. 
   */
  void stamp();
}
