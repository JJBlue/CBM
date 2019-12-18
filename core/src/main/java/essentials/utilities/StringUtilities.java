package essentials.utilities;

import java.util.LinkedList;
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
	
	public static String setArgs(String text, String... args) {
		for (int i = args.length; i > 0; i--)
			text = text.replace("$" + i, args[i - 1] == null ? "" : args[i - 1]);
		return text;
	}
	
	public static void append(StringBuilder builder, String s) {
		if(s == null || builder == null) return;
		builder.append(s);
	}

	public static List<String> parseQuotionMarks(String input) {
		List<String> list = new LinkedList<>();
		StringBuilder builder = null;
		
		boolean inArg = false;
		boolean backspace = false;
		
		for(char c : input.toCharArray()) {
			if(builder == null) {
				builder = new StringBuilder();
			}
			
			if(!inArg && Character.isWhitespace(c)) {
				if(builder.length() > 0) {
					list.add(builder.toString());
					builder = null;
				}
				continue;
			}
			
			switch (c) {
				case '\\':
					
					if(inArg) {
						if(backspace) {
							builder.append(c);
						}
						
						backspace = !backspace;
					} else {
						builder.append(c);
					}
					
					break;
				case '\"':
					
					if(inArg) {
						if(backspace) {
							builder.append(c);
							backspace = false;
						} else {
							inArg = false;
						}
					} else {
						inArg = true;
					}
					
					break;
				default: {
					builder.append(c);
					backspace = false;
				}
			}
		}
		
		if(builder != null && builder.length() > 0) {
			list.add(builder.toString());
		}
		
		return list;
	}
	
	public static String[] toArray(List<String> list) {
		String[] array = new String[list.size()];
		return list.toArray(array);
	}
	
	public static String nextLetterString(String ID) {
		if(ID == null || ID.isEmpty()) return "a";
		
		char[] idarray = ID.toCharArray();
		int pos = ID.length() - 1;
		boolean add = false;
		
		while(pos >= 0) {
			char c = idarray[pos];
			
			if(c != 'z') {
				idarray[pos] = nextLetter(idarray[pos]);
				break;
			} else {
				add = true;
				idarray[pos] = nextLetter(idarray[pos]);
			}
			
			pos--;
		}
		
		if(add)
			return new String(idarray) + "a";
		return new String(idarray);
	}
	
	public static char nextLetter(char c) {
		if(c == 'z')
			return 'a';
		return ++c;
	}
}
