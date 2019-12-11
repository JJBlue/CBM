package components.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter {

	private StringBuilder builder;
	private Writer output;

	public JSONWriter(StringBuilder builder) {
		this.builder = builder;
	}

	public JSONWriter(Writer writer) {
		this.output = writer;
	}

	public static String getJSONString(abstractJSON json) throws IOException {
		StringBuilder builder = new StringBuilder();
		json.write(new JSONWriter(builder), 0, true);
		return builder.toString();
	}

	public static void writeOut(abstractJSON json, File file) throws IOException {
		file.getParentFile().mkdirs();
		JSONWriter writer = new JSONWriter(new FileWriter(file));
		json.write(writer, 0, true);
		writer.close();
	}

	private synchronized void close() {
		if (output != null) {
			try {
				output.flush();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			output = null;
		}
	}

	public void newLine() throws IOException {
		if (builder != null) builder.append('\n');
		else if (output != null) output.write('\n');
	}

	public void writeOut(Writer writer) {
		output = writer;
	}

	protected void write(String string, int height) throws IOException {
		spaces(height);

		if (builder != null)
			builder.append(string);
		else if (output != null)
			output.write(string);
	}

	protected void write(char c, int height) throws IOException {
		spaces(height);

		if (builder != null)
			builder.append(c);
		else if (output != null)
			output.write(c);
	}

	protected void spaces(int height) throws IOException {
		if (builder != null)
			for (int i = height; i > 0; i--) builder.append("  ");
		else if (output != null)
			for (int i = height; i > 0; i--) output.write("  ");
	}
}
