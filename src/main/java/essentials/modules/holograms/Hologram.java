package essentials.modules.holograms;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;

public class Hologram {
	protected List<HologramLine> lines;
	protected Location location;
	
	public Hologram(Location location) {
		this.location = location;
		lines = new LinkedList<>();
	}
	
	public Hologram(Location location, List<HologramLine> lines) {
		this.lines = lines;
		this.location = location;
	}
	
	public void setText(int line, String text) {
		if(line < lines.size()) {
			HologramLine hologramLine = lines.get(line);
			hologramLine.setText(text);
		} else {
			
			do {
				addText("");
			} while(line > lines.size());
			
			addText(text);
		}
	}
	
	public void addText(String text) {
		int position = lines.size();
		HologramLine line = new HologramLine(getLocation(position));
		line.setPosition(position);
		line.setText(text);
		lines.add(line);
	}
	
	public void removeText() {
		HologramLine line = lines.get(lines.size() - 1);
		lines.remove(line);
		line.destroy();
	}
	
	public void removeLine(int line) {
		HologramLine hologramLine = lines.get(lines.size() - 1);
		lines.remove(hologramLine);
		hologramLine.destroy();
		
		//TODO reposition other lines
	}
	
	protected Location getLocation(int line) {
		return null;
	}
	
	public void moveHologram(Location location) {
		this.location = location;
		//TODO update information HologramManager?
		//TODO move
	}
	
	public void clear() {
		for(HologramLine line : lines)
			line.destroy();
		
		lines.clear();
	}
}
