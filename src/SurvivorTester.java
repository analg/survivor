import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SurvivorTester {

	public static void main(String[] args) {

		SurvivorI survivor = new Survivor();

		// survivor.openFile(new File("names.txt"));
		try {
			survivor.readFile(new Scanner(new File("names.txt")));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(survivor.findSurvivor(6) + " survived.");
	}

}
