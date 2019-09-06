package components.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLParser {
	private SQLParser() {}
	
	public static String getResource(InputStream inputStream) {
		StringBuilder builder = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		try {
			String line;
			boolean first = true;
			while((line = reader.readLine()) != null) {
				if(first)first = false;
				else builder.append('\n');
				
				builder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			reader.close();
		} catch (IOException e) {}
		
		return builder.toString();
	}
	
	public static String getResource(String path, Class<?> classy) {
		return getResource(classy.getResourceAsStream(path));
	}
	
	public static String getResource(String path) {
		return getResource(SQLParser.class.getResourceAsStream(path));
	}
	
	public static String[] getResources(InputStream inputStream) {
		return getResource(inputStream).split(";");
	}
	
	public static String[] getResources(String path, Class<?> classy) {
		return getResource(classy.getResourceAsStream(path)).split(";");
	}
	
	public static String[] getResources(String path) {
		return getResource(path).split(";");
	}
}
