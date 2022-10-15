package org.observations.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.observations.model.Loader;
import org.observations.model.ModelAdapter;
import org.observations.model.ModelCore;
import org.observations.model.Saved;
import org.observations.model.Updater;
import org.observations.model.core.ModelAdapterImpl;
import org.observations.model.core.ModelCoreImpl;
import org.observations.model.utility.LoaderImpl;
import org.observations.model.utility.SavedImpl;
import org.observations.model.utility.UpdaterImpl;

/** class test JUnit 5 - Jupiter.*/
class TestJupiter {

  private static final String PIPPO_TXT = "pippo.txt";
  private static final String TIME_STAMP = "HH:mm:ss";
  private static final String STUPISCIMI = "stupiscimi";
  private static final String COMPRAMI = "comprami";
  private static final String TROVAMI = "trovami";
  private static final String PLUTO = "pluto";
  private static final String PROVA = "prova";
  private static final String PIPPO = "pippo";
  public static final String SEP = File.separator;
  public static final String ROOT = System.getProperty("user.home");
  public static final String SAVE_DIR = ROOT + SEP + "Observations" + SEP + "save";

  /** test make dir in user folder. */
  @Test
  void test() {
    System.out.println("\ntest 1");
    final Saved newTest = new SavedImpl();
    newTest.makeDir(SAVE_DIR);
    final File test = new File(SAVE_DIR);
    System.out.println(test.toString());
    assertTrue(test.exists());
  }

  /** test print all element read in selected directory. */
  @Test
  void test2() {
    System.out.println("\ntest 2");
    final Loader newTest = new LoaderImpl();
    for (final String e : newTest.loadFileFolder(new File(SAVE_DIR).getParent())) {
      System.out.println("parent - " + e);
    }
    for (final String e : newTest.loadFileFolder(SAVE_DIR)) {
      System.out.println("save dir - " + e);
    }
  }

  /** test make file with name choose in selected folder. */
  @Test
  void test3() throws IOException {
    System.out.println("\ntest 3");
    final Saved newTest = new SavedImpl();
    newTest.makeFile(SAVE_DIR + SEP + PIPPO_TXT);
    final File test = new File(SAVE_DIR + SEP, PIPPO_TXT);
    System.out.println(test.toString());
    assertTrue(test.exists());
  }

  /** test from add string to file and read is content. */
  @Test
  void test4() throws IOException {
    System.out.println("\ntest 4");
    final Loader loader = new LoaderImpl();
    final Saved save = new SavedImpl();
    final Updater upd = new UpdaterImpl(SAVE_DIR + SEP + PIPPO_TXT, SEP, SAVE_DIR + SEP);
    String time = new SimpleDateFormat("HH:mm:ss", Locale.ITALIAN)
        .format(Date.from(Instant.now(Clock.systemDefaultZone())));
    System.out.println("time " + time);
    upd.updateObservations(SAVE_DIR + SEP + PIPPO_TXT, "prova" + " - " + time, save, loader);
    time = LocalDate.now().toString();
    upd.updateObservations(SAVE_DIR + SEP + PIPPO_TXT, "4prova4" + " - " + time, save, loader);
    final List<String> list = loader.fillList(SAVE_DIR + SEP + PIPPO_TXT);
    System.out.println(list);
  }

  /** test for new core model class. */
  @Test
  void test5() throws IOException {
    System.out.println("\ntest 5");
    final Loader loadTest = new LoaderImpl();
    final List<String> list = loadTest.fillList(SAVE_DIR + SEP + "pippo.txt");
    System.out.println(list);
  }

  /** test for check file/folder in selected folder with model core. */
  @Test
  void test6() throws IOException {
    System.out.println("\ntest 6");
    final ModelCore mci = new ModelCoreImpl();
    System.out.println("moments list " + mci.getMomentsList() + "\n"
        + "types list " + mci.getTypeList());
    mci.chooseStudent(PIPPO);
    mci.chooseStudent(PLUTO);
    mci.chooseStudent(PIPPO);
    mci.chooseMoment(PROVA);
    mci.chooseMoment(PROVA);
    mci.chooseMoment(TROVAMI);
    mci.chooseMoment(COMPRAMI);
    mci.chooseMoment(PROVA);
    mci.chooseDate("20.09.2022");
    mci.chooseDate("21.09.2022");
    mci.chooseDate("20.09.2022");
    String time = new SimpleDateFormat(TIME_STAMP, Locale.ITALIAN)
        .format(Date.from(Instant.now(Clock.systemDefaultZone())));
    mci.updateObservations(time, PROVA);
    time = new SimpleDateFormat(TIME_STAMP, Locale.ITALIAN)
        .format(Date.from(Instant.now(Clock.systemDefaultZone())));
    mci.updateObservations(time, "asseconda prova");
    mci.addObservationType(STUPISCIMI);
    mci.addObservationType(STUPISCIMI);
    mci.chooseDate("21.09.2022");
    time = new SimpleDateFormat(TIME_STAMP, Locale.ITALIAN)
        .format(Date.from(Instant.now(Clock.systemDefaultZone())));
    mci.updateObservations(time, "provami");
    time = new SimpleDateFormat(TIME_STAMP, Locale.ITALIAN)
        .format(Date.from(Instant.now(Clock.systemDefaultZone())));
    mci.updateObservations(time, "asseconda provati");
    System.out.println("moments list " + mci.getMomentsList() + "\n"
        + "types list " + mci.getTypeList());
    System.out.println("data student choose" + mci.getDataStudentChoose());
    System.out.println("data moment choose" + mci.getDataMomentChoose());
    System.out.println("data day choose " + mci.getDataDayChoose());
    System.out.println("counter observed day " + mci.getCounterDayChoose());
    System.out.println("counter observed date " + mci.getCounterDates() + " "
        + mci.getCounterDates().size());
    System.out.println("counter observed moment " + mci.getCounterMoments() + " "
        + mci.getCounterMoments().size());
    System.out.println("counter observed students " + mci.getCounterStudents() + " "
        + mci.getCounterStudents().size());
    System.out.println("counter observed moment " + mci.getCounterMoments() + " "
        + mci.getCounterMoments().size());
    System.out.println("counter observed date " + mci.getCounterDates() + " "
        + mci.getCounterDates().size());
    System.out.println(mci.getCounterDates());
    System.out.println(mci.getCounterDates());
    System.out.println(mci.getCounterDayChoose());
    System.out.println(mci.getCounterMoments());
    System.out.println(mci.getCounterStudents());
    System.out.println(mci.getCounterDates());
    System.out.println(mci.getCounterDates());
    mci.chooseStudent(PIPPO);
    assertEquals(3, mci.getCounterMoments().size());
    mci.chooseStudent(PIPPO);
    mci.chooseMoment(PROVA);
    assertEquals(2, mci.getCounterDates().size());
    mci.chooseMoment(COMPRAMI);
    mci.stamp();
    assertEquals(0, mci.getCounterDates().size());
    mci.chooseStudent(PLUTO);
    mci.stamp();
  }

  /** test for test class model adapter. */
  @Test
  void test7() throws IOException {
    System.out.println("\ntest 7");
    final ModelAdapter ma = new ModelAdapterImpl();
    ma.createStudent(PIPPO + "1");
    ma.createStudent(PLUTO + "1");
    ma.createStudent(PIPPO + "1");
    ma.createMoment(TROVAMI);
    ma.createStudent(PLUTO + "1");
    assertEquals(1, ma.getMomentsList(PIPPO + "1").size());
    assertEquals(0, ma.getMomentsList(PLUTO + "1").size());
    assertEquals(1, ma.getMomentsList(PIPPO + "1").size());
    assertEquals(0, ma.getMomentsList(PLUTO + "1").size());
    System.out.println(ma.getMomentsList(PIPPO + "1"));
    System.out.println(ma.getMomentsList(PLUTO + "1"));
    System.out.println(ma.getMomentsList(PIPPO + "1"));
    ma.createStudent(PIPPO + "1");
    ma.createMoment(TROVAMI);
    ma.createDate("oggi");
    ma.createDate("oggi");
    ma.clickObservation("ciaociao");
    ma.clickObservation("ciaociao");
    System.out.println("date " + ma.getDatesAndObservations(TROVAMI));
  }
  
}
