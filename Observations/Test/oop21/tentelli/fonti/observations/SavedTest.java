package oop21.tentelli.fonti.observations;

public class SavedTest {

	public static void main(String[] args) {

		Saved test1 = new Saved();
		System.out.println(Saved.ROOT);
		System.out.println(Saved.SEP);
		boolean test = test1.makeDir();
		System.out.println(test);
		System.out.println(test1.makeDir());
	}

}
