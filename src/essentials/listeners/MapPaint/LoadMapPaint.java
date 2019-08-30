package essentials.listeners.MapPaint;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import components.datenbank.Datenbank;
import components.sql.SQLParser;
import essentials.Image.staticImage;
import essentials.database.Databases;

public class LoadMapPaint {
	
	public static void load() {
		Datenbank database = Databases.getWorldDatabase();
		
		for(String s : SQLParser.getResources("sql/create.sql", LoadMapPaint.class))
			database.execute(s);
	}
	
	public static Image getMapPaint(int id) {
		MPInformation mpi = getMpInformation(id);
		if(mpi == null) return null;
		
		Image img = staticImage.getImage(mpi.getConvertedPath(), mpi.filename);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimg.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		
		int x = mpi.startX;
		int y = mpi.startY;
		if(x < 0) x *= -1;
		if(y < 0) y *= -1;
		
		int w = width - x;
		if(w > 128) w = 128;
		int h = height - y;
		if(h > 128) h = 128;
		
		bimg = bimg.getSubimage(x, y, w, h);
		
		return bimg;
	}
	
	public static MPInformation getMpInformation(int id) {
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/getPaintInformation.sql", LoadMapPaint.class));
		
		try {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) return null;
			
			MPInformation mpInformation = new MPInformation();
			mpInformation.mapID = id;
			mpInformation.path = resultSet.getString("filePath");
			mpInformation.filename = resultSet.getString("fileName");
			mpInformation.startX = resultSet.getInt("startX");
			mpInformation.startY = resultSet.getInt("startY");
			
			return mpInformation;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int get(String pfad, String filename, int x, int y) {
		if(pfad == null || filename == null) return -1;
		
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/getMapID.sql", LoadMapPaint.class));
		
		try {
			preparedStatement.setInt(1, x);
			preparedStatement.setInt(2, y);
			preparedStatement.setString(3, pfad);
			preparedStatement.setString(4, filename);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) return resultSet.getInt("mapID");
			
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static void setMapPaint(int id, String pfad, String filename, int x, int y) {
		if(pfad == null || filename == null) return;
		
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/addFile.sql", LoadMapPaint.class));
		
		try {
			preparedStatement.setString(1, pfad);
			preparedStatement.setString(2, filename);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/setPaint.sql", LoadMapPaint.class));
		
		try {
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, x);
			preparedStatement.setInt(3, y);
			
			preparedStatement.setString(4, pfad);
			preparedStatement.setString(5, filename);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeID(int id) {
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/deletePaint.sql", LoadMapPaint.class));
		
		try {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean contains(int id) {
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/getPaintInformation.sql", LoadMapPaint.class));
		
		try {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
