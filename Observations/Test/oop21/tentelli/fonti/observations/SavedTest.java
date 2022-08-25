package oop21.tentelli.fonti.observations;

public class SavedTest {

	public static void main(String[] args) {

		String name = "Observations" + Saved.SEP + "save";
		Saved test1 = new Saved();
		System.out.println(Saved.ROOT);
		System.out.println(Saved.SEP);
		String test = test1.makeDir(name);
		System.out.println(name);
		System.out.println(test);
		System.out.println(test1.makeDir(name));
	}

}
