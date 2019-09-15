package components.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class JSONArray extends abstractJSON {
	private List<?> array;

	public JSONArray() {}

	public JSONArray(List<?> array) {
		this.array = array;
	}

	public void setArray(List<?> array) {
		this.array = array;
	}

	public void setList(List<?> array) {
		this.array = array;
	}

	public Object get(int index) {
		return array.get(index);
	}

	public int length() {
		return array.size();
	}

	public List<?> getList() {
		return array;
	}

	public Object[] toArray() {
		return array.toArray();
	}

	public int size() {
		return array.size();
	}

	@Override
	protected void write(JSONWriter writer, int height, boolean shouldUseSpace) throws IOException {
		if (array == null || array.isEmpty()) {
			writer.write("[]", 0);
			return;
		}

		writer.write('[', height);
		writer.newLine();

		if (array != null && !array.isEmpty()) {
			Iterator<?> it = array.iterator();
			boolean first = true;
			while (it.hasNext()) {
				if (!first) {
					writer.write(",", 0);
					writer.newLine();
				} else first = false;
				JSONValue.getabstractJSON(it.next()).write(writer, height + 1, true);
			}
		}

		writer.newLine();
		writer.write(']', height);
	}

	@Override
	protected StringBuffer toJSONString(StringBuffer buffer) {
		if (array == null || array.isEmpty()) {
			buffer.append("[]");
			return buffer;
		}

		buffer.append('[');

		if (array != null && !array.isEmpty()) {
			Iterator<?> it = array.iterator();
			boolean first = true;
			while (it.hasNext()) {
				if (!first)
					buffer.append(",");
				else first = false;
				JSONValue.getabstractJSON(it.next()).toJSONString(buffer);
			}
		}

		buffer.append(']');
		return buffer;
	}
}
