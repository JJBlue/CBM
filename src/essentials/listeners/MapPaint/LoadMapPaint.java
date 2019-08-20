package essentials.listeners.MapPaint;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import essentials.Image.staticImage;

public class LoadMapPaint {
	private static File file = new File("plugins/Allgemein", "MapPaint.yml");
	private static FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
	
	private static boolean tested = false;
	
	public static Image getMapPaint(int id){
		String pfad = fileConf.getString(id + ".Pfad");
		String filename = fileConf.getString(id + ".Filename");
		
		if(pfad == null || pfad.isEmpty()) return null;
		if(filename == null || filename.isEmpty()) return null;
		
		Image img = staticImage.getImage(pfad, filename);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimg.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		
		int x = getX(id);
		int y = getY(id);
		if(x < 0) x *= -1;
		if(y < 0) y *= -1;
		
		int w = width - x;
		if(w > 128) w = 128;
		int h = height - y;
		if(h > 128) h = 128;
		
		bimg = bimg.getSubimage(x, y, w, h);
		
		return bimg;
	}
	
	private static int getX(int id){
		int x = fileConf.getInt(id + ".X");
		
		return x;
	}
	
	private static int getY(int id){
		int y = fileConf.getInt(id + ".Y");
		
		return y;
	}
	
	public static int get(String pfad, String filename, int x, int y) {
		List<Integer> list = getIDs();
		if(list == null) return -1;
		
		for(int id : list){
			String pfad2 = fileConf.getString(id + ".Pfad");
			if(pfad2 == null || !pfad2.equals(pfad)) continue;
			
			String f2 = fileConf.getString(id + ".Filename");
			if(f2 == null || !f2.equals(filename)) continue;
			
			if(fileConf.getInt(id + ".X") == x || fileConf.getInt(id + ".X") == (x * -1)) {
				if(fileConf.getInt(id + ".Y") == y || fileConf.getInt(id + ".Y") == (y * -1)) {
					return id;
				}
			}
		}
		
		return -1;
	}
	
	public static void setMapPaint(int id, String pfad, String filename, int x, int y){
		fileConf.set(id + ".Pfad", pfad);
		fileConf.set(id + ".Filename", filename);
		fileConf.set(id + ".X", x);
		fileConf.set(id + ".Y", y);
		
		addIDs(id);
	}
	
	public static boolean contains(int id) {
		List<Integer> list = getIDs();
		if(list == null) return false;
		return list.contains(id);
	}
	
	public static void addIDs(int id){
		List<Integer> list = getIDs();
		if(list == null)list = new LinkedList<>();
		
		if(list.contains(id)) return;
		
		list.add((int) id);
		fileConf.set("IDs", list);
		
		try {
			fileConf.save(file);
		} catch (IOException e) {}
	}
	
	public static void removeID(int id){
		List<Integer> list = getIDs();
		if(list == null || !list.contains(id)) return;
		
		list.remove(id);
		fileConf.set("IDs", list);
		fileConf.set(id + "", null);
		
		try {
			fileConf.save(file);
		} catch (IOException e) {}
	}
	
	public static List<Integer> getIDs(){
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>) fileConf.getList("IDs");
		
		if(!tested){
			tested = true;
			list = shouldRemove(list);
		}
		
		if(list != null){
			return list;
		}
		
		return null;
	}
	
	private static List<Integer> shouldRemove(List<Integer> list){
		if(list != null){
			ArrayList<Integer> copyList = new ArrayList<>(list);
			boolean change = false;
			
			for(int i = 0; i < list.size(); i++){
				int id = list.get(i);
				
				String pfad = fileConf.getString(id + ".Pfad");
				String filename = fileConf.getString(id + ".Filename");
				
				if(pfad == null || pfad.equals("")){
					change = true;
					Integer i1 = i;
					copyList.remove(i1);
					fileConf.set(id + "", null);
				}else if(filename == null || filename.equals("")){
					change = true;
					Integer i1 = i;
					copyList.remove(i1);
					fileConf.set(id + "", null);
				}else{
					File file = new File(pfad, filename);
					if(!file.exists()){
						change = true;
						Integer i1 = i;
						copyList.remove(i1);
						fileConf.set(id + "", null);
					}
				}
			}
			
			if(change){
				fileConf.set("IDs", copyList);
				
				try {
					fileConf.save(file);
				} catch (IOException e) {
				}
			}
			
			return copyList;
		}
		
		return list;
	}
}
