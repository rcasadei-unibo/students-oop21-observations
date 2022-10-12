package org.observations.model.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.observations.model.Loader;
import org.observations.model.Saved;
import org.observations.model.Updater;

/**
 * Updater class for all string passed, create and manage all file in all class.
 * Remember the last hit (student, moment, date) passed for save and manage data.
 * Use @link {ArrayList<>}
 */
public class UpdaterImpl implements Updater {

  private final String pathMomentList;
  private final String sep;

  private String moment;
  private String date;
  private final String studentRoot;
  private String momentRoot;
  private String dateRoot;
  
  /**
   * Constructor for this class.

   * @param pathMomentList sep startPath
   *      path for moment list
   *      file separator for folder
   *      start path for student folder
   */
  public UpdaterImpl(final String pathMomentList, final String sep, final String startPath) {
    super();
    this.pathMomentList = pathMomentList;
    this.sep = sep;
    this.studentRoot = startPath;
  }

  /**
   * Return list of String for the selected path.

   * @param path loader
   *     path selected for load all file
   *     loader: reference to class for load file
   */
  private List<String> check(final String path, final Loader loader) {
    return loader.loadFileFolder(path);
  }

  /**
   * Choose or create the student pass.

   * @param student save loader
   *      student pass: if present is selected else is create with save
   *      save: reference to class for create new folder
   *      loader: reference to class for load file
   */
  public void chooseStudent(final String student, final Saved save, final Loader loader)
      throws IOException {
    final String studentChoose = this.studentRoot + student + this.sep;
    if (!loader.loadFileFolder(this.studentRoot).contains(student)) {
      save.makeDir(studentChoose);
    } else {
      this.momentRoot = studentChoose;
    }
  }

  /**
   * Choose or create the moment pass for last student selected,
   * if moment already present select this root.
   * pass the list of all moment list file create, if new moment is missed add to list e sort

   * @param moment updateList save loader
   *      moment pass: if present is selected else is create with save
   *      updateList: if moment create is missed in the list add and sort the list
   *      save: reference to class for make new folder
   *      loader: reference to class for load file
   */
  public void chooseMoment(final String moment, final List<String> updateList,
      final Saved save, final Loader loader) throws IOException {
    this.moment = this.momentRoot + moment + this.sep;
    if (!loader.loadFileFolder(this.momentRoot).contains(moment) && !moment.isBlank()) {
      save.makeDir(this.moment);
      if (!updateList.contains(moment)) {
        updateList.add(moment);
        updateList.sort((a, b) -> a.compareTo(b));
        save.writeList(this.pathMomentList, updateList);
      }
    } else {
      this.dateRoot = this.moment;
    }
  }

  /**
   * Choose or create the date pass for last moment and student selected.

   * @param date save loader
   *      date pass: if present is selected else is create with save
   *      save: reference to class for create new file
   *      loader: reference to class for load file
   */
  public void chooseDate(final String date, final Saved save, final Loader loader)
      throws IOException {
    this.date = this.dateRoot + date;
    if (!loader.loadFileFolder(this.moment).contains(date)) {
      save.makeFile(this.date);
    }
  }

  /**
   * Return a list of all student observed or empty list,
   * private method observed is a control for item present.
   */
  public List<String> getObservedStudents(final Loader loader) {
    return this.observed(this.studentRoot, this.check(this.studentRoot, loader));
  }

  /**
   * Return a list of all moment observed for last student selected or empty list,
   * private method observed is a control for item present.
   */
  public List<String> getObservedMoments(final Loader loader) {
    return this.observed(this.momentRoot, this.check(this.momentRoot, loader));
  }

  /**
   * Return a list of all dates observed for last moment and student selected or empty list,
   * private method observed is a control for item present.
   */
  public List<String> getObservedDates(final Loader loader) {
    return this.observed(this.dateRoot, this.check(this.dateRoot, loader));
  }

  /**
   * Return a list of all observations observed for current date/moment/student or empty list,
   * private method observed is a control for item present.
   */
  public List<String> getObservedDay(final Loader loader) throws IOException {
    return this.observed(this.date, loader.fillList(this.date));
  }

  /**
   * Add element pass for the current date, moment and student.

   * @param element save loader
   *      element pass: if present is selected else is create with save
   *      save: reference to class for create new file
   *      loader: reference to class for load file
   */
  public void updateObservations(final String element, final Saved save, final Loader loader)
      throws IOException {
    this.updateObservations(this.date, element, save, loader);
  }

  /**
   * Add element pass at path passed, use class save for update file.

   * @param path element save loader
   *      path pass: path of file need update
   *      element: element to add at file
   *      save: reference to class for create new file
   *      loader: reference to class for load file
   */
  public void updateObservations(final String path, final String element,
      final Saved save, final Loader loader)
      throws IOException {
    final List<String> list = loader.fillList(path);
    list.add(element);
    list.sort((a, b) -> a.compareTo(b));
    save.writeList(path, list);
  }

  /**
   * Private method for control for item present, return empty list or list.
   */
  private List<String> observed(final String observed, final List<String> returnOk) {
    if (observed.isBlank()) {
      return new ArrayList<>();
    }
    return returnOk;
  }
}
