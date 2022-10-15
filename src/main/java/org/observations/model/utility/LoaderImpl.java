package org.observations.model.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.observations.model.Loader;

/**
 * Simple class for load file and folder from root.
 * Return a list file or single file for the selected directory. 
 */
public class LoaderImpl implements Loader {

  /**
   * return string list of file and/or folder from directory path selected. 

   * @param dir
   *      directory path for reading file
   */
  public List<String> loadFileFolder(final String dir) {
    final List<String> listFileFolder = new ArrayList<>();
    if (!dir.isBlank() && new File(dir).exists()) {
      for (final String e : new File(dir).list()) {
        listFileFolder.add(e);
      }
      listFileFolder.sort((a, b) -> a.compareTo(b));
    }
    return listFileFolder;
  }

  /**
   * Read file and return buffered reader.

   * @param path
   *      directory path for reading file 
   */
  private BufferedReader readFile(final String path) throws FileNotFoundException {
    if (!new File(path).exists() && !path.isBlank()) {
      final File file = new File(path);
      try {
        file.createNewFile();
      } catch (IOException e) {
        System.out.println("Impossible create file " + path);
        e.printStackTrace();
      }
    }
    return new BufferedReader(new FileReader(new File(path)));
  }

  /**
   * Import list file and return String list.

   * @param path
   *      directory path for reading file
   */
  public List<String> fillList(final String path) throws IOException {
    final List<String> list = new ArrayList<>();
    @SuppressWarnings("PMD.CloseResource")
    final BufferedReader reader = this.readFile(path);
    String item = reader.readLine();
    while (item != null) {
      list.add(item);
      item = reader.readLine();
    }
    list.sort((a, b) -> a.compareTo(b));
    reader.close();
    return list;
  }

}
