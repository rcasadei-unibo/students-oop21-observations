package org.observations.model.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.observations.model.Saved;

/**
 * Simple class for create folder from root, return a file path string. 
 */
public class SavedImpl implements Saved {

  /**
   * Create a folder and subfolder require for the path requested. 

   * @param dir
   *      absolute path for create folder
   */
  public void makeDir(final String dir) {
    final File createFolder = new File(dir);
    createFolder.mkdirs();
  }

  /**
   * Create a file request in the path selected.

   * @param name
   *      absolute path for create file
   */
  public void makeFile(final String name) {
    final File createFile = new File(name);
    try {
      createFile.createNewFile();
    } catch (IOException e) {
      System.out.println("Impossible create file\n");
      e.printStackTrace();
    }
  }

  /**
   * Simple writer with try catch and print error.

   * @param fw item
   *      file writer
   *      item to add at file
   */
  private void updateList(final FileWriter fw, final String item) throws IOException {
    try {
      fw.write(item + "\n");
    } catch (IOException e) {
      System.out.println("Impossible add item to list");
      e.printStackTrace();
    }
  }

  /** 
   * Create file and write all the file in the list.

   * @param path list
   *      path absolute path for create file if missed
   *      list of all file to copy
   */
  @SuppressWarnings("PMD.CloseResource")
  public void writeList(final String path, final List<String> list) throws IOException {
    this.makeFile(path);
    final FileWriter fw = new FileWriter(new File(path));
    for (final String item : list) {
      this.updateList(fw, item);
    }
    fw.flush();
    fw.close();
  }
}
