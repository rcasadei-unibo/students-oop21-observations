package oop21.tentelli.fonti.observations;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import oop21.tentelli.fonti.observations.utility.UpdaterImpl;
import oop21.tentelli.fonti.observations.utility.LoaderImpl;
import oop21.tentelli.fonti.observations.utility.SavedImpl;




public class Test extends TestCore{

	public static final String SEP = File.separator;
	public static final String ROOT = System.getProperty("user.home");
	public static final String SAVE_DIR = ROOT + SEP + "Observations" + SEP + "save";
	
	/* test make dir in user folder */
	@org.junit.Test
	public void test1Save() {
		System.out.println("\ntest 1");
		Saved newTest = new SavedImpl();
		newTest.makeDir(SAVE_DIR);
		File test = new File(SAVE_DIR);		
		System.out.println(test.toString());
		assertFalse(!test.exists());
	}
	
	/* test print all element read in selected directory */
	@org.junit.Test
	public void test2Load() {
		System.out.println("\ntest 2");
		Loader newTest = new LoaderImpl();
		for (String e : newTest.loadFileFolder(new File(SAVE_DIR).getParent())) {
			System.out.println("parent - " + e);
		}
		for (String e : newTest.loadFileFolder(SAVE_DIR)) {
			System.out.println("save dir - " + e);
		}
	}
	
	/* test make file with name choose in selected folder */
	@org.junit.Test
	public void test3CreateFile() throws IOException {
		System.out.println("\ntest 3");
		Saved newTest = new SavedImpl();
		newTest.makeFile(SAVE_DIR + SEP + "pippo.txt");
		File test = new File(SAVE_DIR + SEP, "pippo.txt");	
		System.out.println(test.toString());
		assertFalse(!test.exists());
	}
	
	/* test from add string to file and read is content*/
	@org.junit.Test
	public void test4WriteReadOnFile() throws IOException{
		System.out.println("\ntest 4");
		Loader loader = new LoaderImpl();
		Saved save = new SavedImpl();
		Updater updater = new UpdaterImpl(SAVE_DIR + SEP + "pippo.txt", SEP, SAVE_DIR + SEP, loader);
		String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		updater.updateObservations(SAVE_DIR + SEP + "pippo.txt", "prova" + " - " + time, save);
		time = LocalDate.now().toString();
		updater.updateObservations(SAVE_DIR + SEP + "pippo.txt", "4prova4" + " - " + time, save);
		ArrayList<String> list = loader.fillList(SAVE_DIR + SEP + "pippo.txt");
		System.out.println(list);
	}
	
}
