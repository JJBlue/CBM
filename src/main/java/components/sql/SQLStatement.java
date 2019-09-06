package components.sql;

import java.math.BigDecimal;

/*
 * Normalerweise kann man java.sql.PreparedStatement verwenden, aber Android ist die Klasse gesperrt und die Alternative nicht verwendbar
 */
public class SQLStatement {
	
	private final String inputSQL;
	private StatementHelper helper;
	private Object[] indexes;
	
	public SQLStatement(String sql) {
		inputSQL = sql;
		load(sql);
	}
	
	private void load(String sql) {
		if(sql == null || sql.isEmpty()) return;
		
		int countIndexes = 0;
		boolean anfuerungszeichen = false;
		boolean doubleAnfuerungszeichen = false;
		
		StringBuilder builder = new StringBuilder();
		StatementHelper currentHelper = null;
		
		for(char c : sql.toCharArray()) {
			switch(c) {
				case '\"':
					doubleAnfuerungszeichen = !doubleAnfuerungszeichen;
					builder.append(c);
					break;
				case '\'':
					anfuerungszeichen = !anfuerungszeichen;
					builder.append(c);
					break;
				case '?':
					if(!anfuerungszeichen && !doubleAnfuerungszeichen) {
						countIndexes++;
						
						String value = builder.toString();
						if(value != null && !value.isEmpty()) {
							currentHelper = addIndex(currentHelper, value);
							builder = new StringBuilder();
						}
						
						currentHelper = addIndex(currentHelper, null);
						break;
					}
				default:
					builder.append(c);
			}
		}
		
		if(doubleAnfuerungszeichen)
			builder.append("\"");
		
		if(anfuerungszeichen)
			builder.append("\'");
		
		String value = builder.toString();
		if(value != null && !value.isEmpty())
			currentHelper = addIndex(currentHelper, value);
		
		if(countIndexes > 0)
			indexes = new Object[countIndexes];
	}
	
	private StatementHelper addIndex(StatementHelper current, String value) {
		StatementHelper myhelper = new StatementHelper();
		myhelper.value = value;
		
		if(current != null)
			current.next = myhelper;
		else
			helper = myhelper;
		
		return myhelper;
	}
	
	public void setBoolean(int index, boolean value) {
		setObjectIndex(index, value);
	}
	
	public void setByte(int index, byte value) {
		setObjectIndex(index, value);
	}
	
	public void setShort(int index, short value) {
		setObjectIndex(index, value);
	}

	public void setInt(int index, int value) {
		setObjectIndex(index, value);
	}
	
	public void setLong(int index, long value) {
		setObjectIndex(index, value);
	}
	
	public void setFloat(int index, float value) {
		setObjectIndex(index, value);
	}
	
	public void setDouble(int index, double value) {
		setObjectIndex(index, value);
	}
	
	public void setBigDecimal(int index, BigDecimal value) {
		setString(index, value.toString());
	}
	
	public void setObject(int index, Object object) {
		if(object == null) return;
		setString(index, object.toString());
	}
	
	public void setString(int index, String value) {
		if(value == null || value.isEmpty())return;
		
		boolean anfuerungszeichen = false;
		
		StringBuilder builder = new StringBuilder();
		
		for(char c : value.toCharArray()) {
			if(c == '\'') {
				anfuerungszeichen = !anfuerungszeichen;
			} else if(anfuerungszeichen) {
				anfuerungszeichen = false;
				builder.append("'");
			}
			
			builder.append(c);
		}
		
		if(anfuerungszeichen)
			builder.append("\'");
		
		setObjectIndex(index, "'" + builder.toString() + "'");
	}
	
	private void setObjectIndex(int index, Object obj) {
		if(index < 1 || index > indexes.length) return;
		indexes[index - 1] = obj;
	}
	
	public void clearParameters() {
		for(int i = 0; i < indexes.length; i++)
			indexes[i] = null;
	}

	public String getSQL() {
		StatementHelper current = helper;
		StringBuilder builder = new StringBuilder();
		int counter = 0;
		
		while(current != null) {
			if(current.value == null) {
				if(counter < indexes.length)
					builder.append(indexes[counter++]);
			} else
				builder.append(current.value);
			
			current = current.next;
		}
		
		return builder.toString();
	}
	
	public String getInputSQL() {
		return inputSQL;
	}
	
	static class StatementHelper {
		StatementHelper next;
		String value;
	}
}
