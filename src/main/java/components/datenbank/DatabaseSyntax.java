package components.datenbank;

public class DatabaseSyntax {
	public static String select(String... table) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT ");
		
		boolean first = true;
		for(String s : table)
			if(first) {
				first = false;
				builder.append(" ").append(s);
			} else
				builder.append(" ,").append(s);
		
		return builder.toString();
	}
	
	public static String from(String table) {
		return "FROM " + table;
	}
	
	public static String update(String table) {
		return "UPDATE " + table;
	}
	
	public static String SetColoum(String... coloum) {
		return setKeywordWithCondition("SET", coloum);
	}
	
	public static String where(String... where) {
		return setKeywordWithCondition("WHERE", where);
	}
	
	public static String setKeywordWithCondition(String keyword, String... condition) {
		StringBuilder builder = new StringBuilder();
		builder.append(keyword);

		boolean first = true;
		for(String s : condition)
			if(first) {
				first = false;
				builder.append(" ").append(s).append(" = ?");
			} else
				builder.append(" ,").append(s).append(" = ?");
		
		return builder.toString();
	}
	
	public static String updateSetWhere(String table, String coloum, String where) {
		return "Update " + table + " Set " + coloum + " = ? Where " + where + " = ?";
	}
	
	public static String updateSetWhere(String table, String coloum, String... where) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Update ").append(table).append(" Set ").append(coloum).append(" = ? Where");
		
		boolean first = true;
		for(String s : where)
			if(first) {
				first = false;
				builder.append(" ").append(s).append(" = ?");
			} else
				builder.append(" ,").append(s).append(" = ?");
		
		return builder.toString();
	}
	
	public static String selectFrom(String select, String from) {
		return "SELECT " + select + " FROM " + from;
	}
	
	public static String selectFromWhere(String select, String from, String... where) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT ").append(select).append(" FROM ").append(from).append(" Where");
		
		boolean first = true;
		for(String s : where)
			if(first) {
				first = false;
				builder.append(" ").append(s).append(" = ?");
			} else
				builder.append(" ,").append(s).append(" = ?");
		
		return builder.toString();
	}
}
