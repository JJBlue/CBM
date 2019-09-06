package components.json;

import java.io.IOException;

public abstract class abstractJSON {
	protected abstract void write(JSONWriter writer, int height, boolean shouldUseSpace) throws IOException;
	
	public String toJSONString() {
		return toJSONString(new StringBuffer()).toString();
	}
	
	protected abstract StringBuffer toJSONString(StringBuffer buffer);
}
