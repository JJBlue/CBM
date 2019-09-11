package essentials.utilities.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class imageManger {
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
