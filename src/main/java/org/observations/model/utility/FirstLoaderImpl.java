package org.observations.model.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.observations.model.FirstLoader;
import org.observations.model.Saved;

/**
 * Only one use class, when start first time, create file and folder basic.
 * when launch after first start do nothing.
 */
public class FirstLoaderImpl implements FirstLoader {

  private final List<String> arrayMomentsList = new ArrayList<>();
  private final List<String> arrayTypeList = new ArrayList<>();

  /** 
   * create folder and file required, fill the file list the first time launched.
   */
  public void firstLoad(final String dir, final String students,
      final String moments, final String types, final Saved save) throws IOException {
    if (!new File(dir + students).exists()) {
      save.makeDir(dir + students);
    }
    if (!new File(dir + moments).exists() && !new File(dir + types).exists()) {
      for (final MomentsList item : MomentsList.values()) {
        this.arrayMomentsList.add(item.getText());
      }
      for (final ObservationsList item : ObservationsList.values()) {
        this.arrayTypeList.add(item.getText());
      }
      save.writeList(dir + moments, this.arrayMomentsList);
      save.writeList(dir + types, this.arrayTypeList);
    }
  }

}
