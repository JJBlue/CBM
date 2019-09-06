package essentials.utilities;

public class StringUtilities {
	public static String arrayToString(String[] args) {
		StringBuilder builder = new StringBuilder();
		boolean start = true;
		
		for(String string : args) {
			if(start)
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
		
		for(int i = startIndex; i < endIndex; i++) {
			if(start)
				start = false;
			else
				builder.append(' ');
			
			builder.append(args[i]);
		}
		
		return builder.toString();
	}
}
