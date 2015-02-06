import java.io.File;

public class SurvivorTester {

	public static void main(String[] args) {

		SurvivorI survivor = new Survivor();

		survivor.openFile(new File("names.txt"));
		survivor.readFile();

		System.out.println(survivor.findSurvivor(1) + " survived.");
	}

}
