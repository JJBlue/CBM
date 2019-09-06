package components.datenbank;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Datenbank {
	private final String benutzer;
	private final String password;
	private final String path;
	private final String ip;
	private final int port;
	
	public Datenbank(String benutzer, String password, String path) {
		this.benutzer = benutzer;
		this.password = password;
		port = -1;
		ip = null;
		this.path = path;
	}
	
	public Datenbank(String ip, int port, String benutzer, String password, String path) {
		this.benutzer = benutzer;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.path = path;
	}
	
	private Datenbanken db;
	private Connection con;
	
	public Connection connect(Datenbanken db) {
		this.db = db;
		con = null;
		
		try {
			switch(db) {
				case DB2:
					//Typ2 Treibers
					//Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
					//oder des Universaltreibers
					Class.forName("com.ibm.db2.jcc.DB2Driver");
					con = DriverManager.getConnection("jdbc:db2:" + path + "?autoReconnect=true", benutzer, password);
					break;
				case Firebird:
					Class.forName("org.firebirdsql.jdbc.FBDriver");
					con = DriverManager.getConnection("jdbc:firebirdsql:" + ip + "/" + port + ":" + path + "?autoReconnect=true", benutzer, password);
					break;
				case H2:
					Class.forName("org.h2.Driver");
					con = DriverManager.getConnection("jdbc:h2:" + path, benutzer + "?autoReconnect=true", password);
					break;
				case HSQLDB:
					Class.forName("org.hsqldb.jdbcDriver");
					con = DriverManager.getConnection("jdbc:hsqldb:file:" + path + ";", benutzer, password);
					break;
				case JavaDB_Derby:
					//Hier wird das Systemverzeichnis fuer die Datenbank angegeben: /.addressbook/
					String userHomeDir = System.getProperty("user.home", ".");
					//Festlegen des Systemverzeichnisses fuer die Datenbank
					System.setProperty("derby.system.home", userHomeDir + "/.addressbook");
					try {
					   con = DriverManager.getConnection("jdbc:derby:" + path + ";user=" + benutzer + ";password=" + password + "?autoReconnect=true");
					} catch (SQLException sqle) {
					   sqle.printStackTrace();
					}
					break;
				case MySQL:
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://" + getAdresse() + "/" + path + "?autoReconnect=true&sslMode=PREFERRED&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", benutzer, password);//autoReConnect=true  sslMode=DISABLED | PREFERRED | REQUIRED | VERIFY_CA | VERIFY_IDENTITY
					break;
				case PostgreSQL:
					Class.forName("org.postgresql.Driver");
					con = DriverManager.getConnection("jdbc:postgresql://" + getAdresse() + "/" + path + "?autoReconnect=true", benutzer, password);
					break;
				case SQLLite:
					//jdbc:sqlite:sample.db
					Class.forName("org.sqlite.JDBC");
					con = DriverManager.getConnection("jdbc:sqlite:" + path);
			}
		} catch (SQLNonTransientConnectionException e) {
			System.out.println("Verbindung mit der Datenbank konnte nicht hergestellt werden");
		} catch (ClassNotFoundException e) {
			System.out.println("API/Klassen fuer die Verbindung mit dieser Datenbank konnte nicht gefunden werden");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("§4Es ist ein Fehler bei der Verbindung mit der Datenbank aufgetreten");
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void setLogger(Datenbanken db, Level level) {
		Logger logger = null;
		
		try {
			switch(db) {
				case DB2:
				case Firebird:
				case H2:
					break;
				case HSQLDB:
					System.setProperty("hsqldb.reconfig_logging", "false");
					Class.forName("org.hsqldb.jdbcDriver");
					logger = Logger.getLogger("hsqldb.db");
					break;
				case JavaDB_Derby:
				case MySQL:
				case PostgreSQL:
				case SQLLite:
					break;
			}
			
			if(logger != null)logger.setLevel(level);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getAdresse() {
		if(port >= 0)return ip + ":" + port;
		else return ip;
	}
	
	public synchronized ResultSet getResult(String query) {
		checkConnection();
		ResultSet rs;
		
		Statement st = getStatement();
		
		if(st != null) {
			try {
				rs = st.executeQuery(query);
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void close(ResultSet result) {
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int setUpdate(String update) {
		checkConnection();
		
		Statement st = getStatement();
		
		if(st != null) {
			try {
				return st.executeUpdate(update);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public synchronized boolean execute(String update) {
		checkConnection();
		
		Statement st = getStatement();
		
		if(st != null) {
			try {
				return st.execute(update);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void setPoolable(boolean value) {
		checkConnection();
		
		Statement st = getStatement();
		
		try {
			st.setPoolable(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setEscapeProcessing(boolean value) {
		checkConnection();
		
		Statement st = getStatement();
		
		try {
			st.setEscapeProcessing(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Statement getStatement() {
		try {
			Statement st = con.createStatement();
			st.closeOnCompletion();
			return st;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}

	public PreparedStatement prepareStatement(String sqlExecute) {
		try {
			return con.prepareStatement(sqlExecute);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PreparedStatement prepareStatementWE(String sqlExecute) throws SQLException {
		return con.prepareStatement(sqlExecute);
	}
	
	public boolean isClosed() {
		try {
			if(con == null || con.isClosed())return true; //st == null || st.isClosed()
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public void checkConnection() {
		try {
			if(isClosed() || !con.isValid(100))
				reconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			reconnect();
		}
	}
	
	public void close() {
		if(con == null) return;
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		con = null;
	}
	
	public void reconnect() {
		System.out.println("Try reconnecting to Database");
		close();
		connect(db);
	}
}
