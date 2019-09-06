package components.datenbank;

public class DatenbankenControl {
	private DatenbankenControl() {}
	
	/*
	  	LIKE				(Vorname LIKE O%)
	  	%:					Platzhalter fuer beliebiege Zeichen (in MS Access: *)
		_:					Platzhalter fuer ein Zeichen (in MS-Access: ?)
		
		IS (NOT) NULL:		prueft, ob ein Attributwert (nicht) undefiniert ist
		IN (..., ..., ...)	vergleicht, ob der Attributwertein Element der Menge ist
	 */
	
	//Content
	public static String selectTableColoumn(String name, boolean multipleOnlyOneTime) {
		if(multipleOnlyOneTime)return " select distinct " + name + " ";
		return " select " + name + " ";
	}
	
	public static String selectAllTableColoumn(boolean multipleOnlyOneTime) {
		if(multipleOnlyOneTime)return " select distinct * ";
		return " select * ";
	}
	
	public static String deleteTableRow() {
		return " delete ";
	}
	
	public static String insertTableRow() {
		return " insert ";
	}
	
	public static String updateTableRow() {
		return " update ";
	}
	
	//Database
	public static String createDatabase(String name) {
		return " create " + name + " ";
	}
	
	
	/*
	  	GRANT			Zugriffsrechte gewaehren
		REVOKE			Zugriffsrechte entziehen
		ALTER TABLE		Tabellenaufbau aendern
	 */
	
	//Table
	public static String createTable(String name) {
		return " create " + name + " ";
	}
	
	public static String deleteTable(String name) {
		return " drop " + name + " ";
	}
	
	public static String renameTable(String name, String newName) { //notsure
		return " rename " + name + " " + newName;
	}
	
	public static String createView() {
		return " create view ";
	}
}
