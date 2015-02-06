import structures.SimpleNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Survivor implements SurvivorI {

	protected Scanner nameDescriptor;
	protected MyLinkedList<String> list;

	public static final List<String> suffices = new ArrayList<String>();
	public static final List<String> titles = new ArrayList<String>();
	public static final List<String> deathMethods = new ArrayList<String>();

	static {
		try {
			Scanner scanner = new Scanner(new File("suffix.txt"));
			while (scanner.hasNextLine()) {
				suffices.add(scanner.nextLine().trim());
			}
			scanner = new Scanner(new File("prefix.txt"));
			while (scanner.hasNextLine()) {
				titles.add(scanner.nextLine().trim());
			}
			scanner = new Scanner(new File("MuWaysToDie.txt"));
			while (scanner.hasNextLine()) {
				deathMethods.add(scanner.nextLine().trim());
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create new Survivor instance.
	 */
	public Survivor() {
		list = new MyLinkedList<String>();
		list.restrictIterator(false);
	}

	/**
	 * Open names list file.
	 *
	 * @param myFile to open
	 */
	@Override
	public void openFile(File myFile) {
		try {
			nameDescriptor = new Scanner(myFile);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read names from list file.
	 */
	@Override
	public void readFile() {
		ArrayList<String> sorted = new ArrayList<String>();
		while (nameDescriptor.hasNextLine()) {
			sorted.add(formatName(nameDescriptor.nextLine()));
		}
		Collections.sort(sorted);
		for (String s: sorted) {
			list.append(s);
		}
		list.loop();
	}

	/**
	 * Parse names into regular format.
	 *
	 * @param in to parse
	 */
	@Override
	public String formatName(String in) {
		// normalize whitespace
		in = in.trim().replaceAll("\\s+", " ");
		String pre = null;
		String suf = null;
		// cut prefices and suffices out if they exist
		for (String s: titles) {
			if (in.startsWith(s) && (pre == null || pre.length() < s.length())) {
				pre = s;
			}
		}
		if (pre != null) {
			in = in.substring(pre.length());
		}
		for (String s: suffices) {
			if (in.endsWith(s) && (suf == null || suf.length() < s.length())) {
				suf = s;
			}
		}
		if (suf != null) {
			in = in.substring(0, in.length() - suf.length());
		}
		in = in.trim();
		// regex capture first, middle, and last name groups
		Pattern pattern = Pattern.compile("(?:([A-Za-z-]+\\.?)\\s?)(?:([A-Za-z-]+\\.?)\\s)?(?:([A-Za-z-]+\\.?))?");
		Matcher matcher = pattern.matcher(in);
		if (matcher.find()) {
			String first = matcher.group(1);
			String middle = matcher.group(2);
			String last = matcher.group(3);
			String name = "";

			boolean predefined = pre != null;
			boolean lastdefined = last != null;
			boolean firstdefined = first != null;
			boolean middledefined = middle != null;
			boolean sufdefined = suf != null;

			/*
			if (predefined) {
				name += pre + ' ';
			}
			*/

			if (lastdefined) {
				name += last.substring(0, 1).toUpperCase() + last.substring(1);
				if (firstdefined || middledefined) {
					name += ", ";
				}
			}

			if (firstdefined) {
				name += first.substring(0, 1).toUpperCase() + first.substring(1);
			}

			if (middledefined) {
				if (firstdefined) {
					name += ' ';
				}
				name += middle;
			}

			if (sufdefined) {
				name += ", " + suf;
			}

			return name.trim();
		}
		else {
			return in;
		}
	}

	/**
	 * Output list of names to stdout.
	 */
	@Override
	public void printList() {
		String output = "";
		for (Object node: list.toArray()) {
			output += (String)(node) + '\n';
		}
		System.out.println(output);
	}

	/**
	 * Find survivor from selection seed.
	 *
	 * @param n death interval
	 *
	 * @return name of survivor
	 */
	@Override
	public String findSurvivor(int n) {
		int i = 0;
		Random r = new Random();
		MyLinkedList<String> transientList = new MyLinkedList<String>();
		// copy list to temporary list
		list.restrictIterator(true);
		for (String s: list) {
			transientList.append(s);
		}
		// loop and cyclify list
		transientList.loop();
		transientList.restrictIterator(false);
		Iterator<String> iterator = transientList.iterator();
		while (iterator.hasNext()) {
			if (transientList.size() == 1) {
				break;
			}
			String s = iterator.next();
			if (++i == n) {
				// remove and announce death
				iterator.remove();
				System.out.println(s + " " + deathMethods.get(r.nextInt(deathMethods.size())));
				i = 0;
			}
		}

		// head of list contains last value
		return transientList.getHead().getValue();
	}
}
