package org.observations.model;

import java.io.IOException;

/**
 * Only one use class, when start first time, create file and folder basic.
 * when launch after first start do nothing
 */
public interface FirstLoader {

  /** 
   * create folder and file required, fill the file list the first time launched.
   */
  void firstLoad(String dir, String students, String moments, String types, Saved save)
      throws IOException;

}