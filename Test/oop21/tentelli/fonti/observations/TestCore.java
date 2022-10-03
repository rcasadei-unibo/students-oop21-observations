package oop21.tentelli.fonti.observations;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import oop21.tentelli.fonti.observations.core.ModelAdapter;
import oop21.tentelli.fonti.observations.core.ModelCoreImpl;
import oop21.tentelli.fonti.observations.utility.LoaderImpl;

public class TestCore {

	public static final String SEP = File.separator;
	public static final String ROOT = System.getProperty("user.home");
	public static final String SAVE_DIR = ROOT + SEP + "Observations" + SEP + "save";
	
	/* test for new core model class */
	@org.junit.Test
	public void test5UseCore() throws IOException {
		System.out.println("\ntest 5");
		Loader loadTest = new LoaderImpl();
		ArrayList<String> list = loadTest.fillList(SAVE_DIR + SEP + "pippo.txt");
		System.out.println(list);
	}

	/* test for check file/folder in selected folder */
	@org.junit.Test
	public void test6() throws IOException {
		System.out.println("\ntest 6");
		ModelCore mci = new ModelCoreImpl();
		System.out.println("moments list " + mci.getArrayMomentsList() + "\n" + "types list " + mci.getArrayTypeList());
		mci.chooseStudent("pippo");
		mci.chooseStudent("pluto");
		mci.chooseStudent("pippo");
		mci.chooseMoment("prova");
		mci.chooseMoment("prova");
		mci.chooseMoment("trovami");
		mci.chooseMoment("comprami");
		mci.chooseMoment("prova");
		mci.chooseDate("20.09.2022");
		mci.chooseDate("21.09.2022");
		mci.chooseDate("20.09.2022");
		String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		mci.updateObservations(time, "prova");
		time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		mci.updateObservations(time, "asseconda prova");
		mci.addObservationType("stupiscimi");
		mci.addObservationType("stupiscimi");
		mci.chooseDate("21.09.2022");
		time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		mci.updateObservations(time, "provami");
		time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
 		mci.updateObservations(time, "asseconda provati");
		System.out.println("moments list " + mci.getArrayMomentsList() + "\n" + "types list " + mci.getArrayTypeList());
		System.out.println("data student choose" + mci.getDataStudentChoose());
		System.out.println("data moment choose" + mci.getDataMomentChoose());
		System.out.println("data day choose " + mci.getDataDayChoose());
		System.out.println("counter observed day " + mci.getCounterDayChoose());
		System.out.println("counter observed date " + mci.getCounterDates() + " " + mci.getCounterDates().size());
		System.out.println("counter observed moment " + mci.getCounterMoments()+ " " + mci.getCounterMoments().size());
		System.out.println("counter observed student " + mci.getCounterStudents() + " " + mci.getCounterStudents().size());
		assertEquals(2, mci.getCounterStudents().size());
		System.out.println("counter observed moment " + mci.getCounterMoments()+ " " + mci.getCounterMoments().size());
		System.out.println("counter observed date " + mci.getCounterDates() + " " + mci.getCounterDates().size());
		assertEquals(3, mci.getCounterMoments().size());
		assertEquals(2, mci.getCounterDates().size());
		mci.chooseMoment("comprami");
		assertEquals(0, mci.getCounterDates().size());
	}
	
	@org.junit.Test
	public void test7UseAdapter() throws IOException {
		System.out.println("\ntest 7");
		ModelAdapter ma = new ModelAdapter();
		assertEquals(2, ma.getStudentsList().size());
		System.out.println(ma.getStudentsList());
		Map<String, List<String>> map = ma.getMomentsList("pippo");
		assertEquals(3, map.get("pippo").size());
		map = ma.getMomentsList("pluto");
		assertEquals(0, map.get("pluto").size());
		map = ma.getMomentsList("pippo");
		System.out.println(ma.getDatesAndObservations("prova"));

	}
	
}
