package org.observations.model;

import java.io.IOException;
import java.util.List;

/**
 * Simple class for load file and folder from root,
 * return a list file or single file in the selected directory. 
 */
public interface Loader {

  /** return string list of file and/or folder from directory path selected.

   * @param path
   *      directory path for reading file
   */
  List<String> loadFileFolder(String path);

  /** import list file and return List String.

   * @param path
   *      directory path for reading file
   */
  List<String> fillList(String path) throws IOException;

}