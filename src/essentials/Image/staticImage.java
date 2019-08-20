package essentials.Image;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class staticImage {
	private static HashMap<String, Image> images = new HashMap<>();
	
	public static Image getImage(String pfad, String nameWithEnding){
		if(images.get(pfad + nameWithEnding) == null){
			Image img = null;
			try{
				img = ImageIO.read(new File(pfad, nameWithEnding));
			}catch(Exception e2){}
			
			if(img != null){
				images.put(pfad + nameWithEnding, img);
				return img;
			}
		} else
			return images.get(pfad + nameWithEnding);
		
		return null;
	}
}
