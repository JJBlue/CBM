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
}
