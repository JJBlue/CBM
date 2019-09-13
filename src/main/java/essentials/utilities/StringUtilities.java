package essentials.utilities;

import java.util.List;

public class StringUtilities {
	public static String arrayToString(String[] args) {
		StringBuilder builder = new StringBuilder();
		boolean start = true;

		for (String string : args) {
			if (start)
				start = false;
			else
				builder.append(' ');

			builder.append(string);
		}

		return builder.toString();
	}

	public static String arrayToStringRange(String[] args, int startIndex, int endIndex) {
		StringBuilder builder = new StringBuilder();
		boolean start = true;

		for (int i = startIndex; i < endIndex; i++) {
			if (start)
				start = false;
			else
				builder.append(' ');

			builder.append(args[i]);
		}

		return builder.toString();
	}

	public static String listToListingString(List<String> list) {
		if (list == null) return null;

		StringBuilder builder = new StringBuilder();
		boolean start = true;

		for (String s : list) {
			if (start)
				start = false;
			else
				builder.append(", ");

			builder.append(s);
		}

		return builder.toString();
	}
}
