import structures.SimpleNode;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Survivor implements SurvivorI {

	protected File file;
	protected MyLinkedList<String> list;

	public Survivor() {
		list = new MyLinkedList<String>();
		list.restrictIterator(false);
	}

	@Override
	public void openFile(File myFile) {
		file = myFile;
	}

	@Override
	public void readFile(Scanner sc) {
		while (sc.hasNextLine()) {
			list.append(formatName(sc.nextLine()));
		}
		list.loop();
	}

	@Override
	public String formatName(String in) {
		in = in.trim().replaceAll("\\s+", " ");
		Pattern pattern = Pattern.compile("(?:(King|Pope)\\s)?(?:([A-Za-z-]+\\.?)\\s?)(?:([A-Za-z-]+\\.?)\\s)?(?:([A-Za-z-]+\\.?),?\\s?)?(.+)?");
		Matcher matcher = pattern.matcher(in);
		if (matcher.find()) {
			String pre = matcher.group(1);
			String first = matcher.group(2);
			String middle = matcher.group(3);
			String last = matcher.group(4);
			String suf = matcher.group(5);
			String name = "";
			if (pre != null) {
				name += pre + ' ';
			}
			if (last != null) {
				name += last + ", ";
			}
			if (first != null) {
				name += first + ' ';
			}
			if (middle != null) {
				name += middle;
			}
			if (suf != null) {
				name += ", " + suf;
			}
			return name;
		}
		else {
			return in;
		}
	}

	@Override
	public void printList() {
		String output = "";
		for (Object node: list.toArray()) {
			output += (String)(node) + '\n';
		}
		System.out.println(output);
	}

	@Override
	public String findSurvivor(int n) {
		int i = 0;
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (list.size() == 1) {
				break;
			}
			iterator.next();
			if (++i == n) {
				iterator.remove();
				i = 0;
			}
		}
		return list.getHead().getValue();
	}
}
