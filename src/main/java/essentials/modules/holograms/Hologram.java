package essentials.modules.holograms;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;

public class Hologram {
	protected List<HologramLine> lines;
	protected Location location;
	public final static double distanceBetween = 0.3;
	protected String ID;
	
	public Hologram(Location location) {
		this.ID = System.currentTimeMillis() + "";
		this.location = location.clone();
		lines = new LinkedList<>();
	}
	
	public Hologram(Location location, String ID) {
		this.ID = ID;
		this.location = location.clone();
		lines = new LinkedList<>();
	}
	
	protected void setHologramLine(HologramLine hologramLine) {
		int line = hologramLine.getPosition();
		
		if(line < lines.size())
			lines.set(line, hologramLine);
		else {
			
			while(line > lines.size()) {
				lines.add(null);
			}
			
			lines.add(hologramLine);
		}
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
		HologramLine line = new HologramLine(getLocation(position), ID);
		line.setID(ID);
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
		
		for(int i = line; i < lines.size(); i++)
			hologramLine.setPosition(i);
		
		refreshPosition(location);
	}
	
	protected Location getLocation(int line) {
		Location location = this.location.clone();
		return location.add(0, -distanceBetween * line, 0);
	}
	
	public void moveHologram(Location location) {
		this.location = location;
		refreshPosition(location);
	}
	
	public void refreshPosition(Location location) {
		Location currentLocation = location.clone();
		
		for(HologramLine line : lines) {
			line.teleport(currentLocation);
			currentLocation.add(0, -distanceBetween, 0);
		}
	}
	
	public void clear() {
		for(HologramLine line : lines)
			line.destroy();
		
		lines.clear();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
		
		for(HologramLine line : lines)
			line.setID(ID);
	}

	public List<HologramLine> getLines() {
		return lines;
	}

	public Location getLocation() {
		return location;
	}
}
