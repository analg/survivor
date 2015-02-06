import java.io.File;

public interface SurvivorI {

	/**
	 * Open names file (plain text)
	 */
	public void openFile(File myFile);

	/**
	 * Read file and create a linked list
	 *
	 * @return first node in list that was created
	 */
	public void readFile();

	/**
	 * @param in a name in '(Title) (First) Last (Middle) (Suffix)' format
	 *
	 * @return a name in '(Title) Last (, First (Middle) (,Suffix))' format
	 */
	public String formatName(String in);

	/**
	 * Print out list, line by line
	 */
	public void printList();

	/**
	 * Beginning with the  head of the list, eliminate every nth node in the list until only one node is remaining. Wrap properly (don't reset the count)
	 *
	 * @param n step
	 * @return last remaining name, or null if none in list
	 */
	public String findSurvivor(int n);

}