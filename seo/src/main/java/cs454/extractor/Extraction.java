package cs454.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Extraction {
	static FileWriter fileJSON;

	File[] list;
	static ArrayList<String> filedata = new ArrayList<String>();

	public void walk(Parser parser, String path) throws IOException,
			SAXException, TikaException {
		File root = new File(path);
		list = root.listFiles();
		if (list == null)
			return;
		for (File f : list) {
			if (f.isDirectory()) {
				walk(parser, f.getAbsolutePath());
			} else {
				String relatedPath = f.getAbsoluteFile().toString();
				if (!filedata.contains(relatedPath)) {
					if (!(relatedPath.contains(".zip"))) {
						System.out.println(relatedPath);
						extractFromFile(parser, relatedPath);
						filedata.add(relatedPath);
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void main(final String[] args) throws IOException,
			SAXException, TikaException {
		Parser parser = new AutoDetectParser();
		fileJSON = new FileWriter("extraction.json");
		String filename = args[1];
		String sub = "";
		final InputStream in = new FileInputStream(filename);
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();) {
				// System.out.println(it.next());
				LinkedHashMap<String, Object> keyValue = (LinkedHashMap<String, Object>) it
						.next();
				// System.out.println(keyValue.get("local file"));
				String str = (String) keyValue.get("local file");
				int count = str.lastIndexOf('/');
				sub = str.substring(0, count);
				new Extraction().walk(parser, sub);
			}
		} finally {
			in.close();
			fileJSON.close();
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static void extractFromFile(final Parser parser,
			final String fileName) throws IOException, SAXException,
			TikaException {
		long start = System.currentTimeMillis();
		BodyContentHandler handler = new BodyContentHandler(10000000);
		Metadata metadata = new Metadata();
		InputStream content = new FileInputStream(new File(fileName));
		/*
		 * InputStream content =
		 * AutoDetectionExample.class.getResourceAsStream(fileName);
		 */
		parser.parse(content, handler, metadata, new ParseContext());

		JSONObject obj = new JSONObject();
		obj.put("File Name", fileName);
		//System.out.println("HELLO" +handler.toString());
		for (String name : metadata.names()) {
			obj.put(name, metadata.get(name));

			System.out.println(name + ":\t" + metadata.get(name));
		}
		String jsonfield = obj.toJSONString();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonfield);
		String prettyJsonString = gson.toJson(je);
		Extraction.fileJSON.write(prettyJsonString);
		Extraction.fileJSON.flush();

		System.out.println(String.format(
				"------------ Processing took %s millis\n\n",
				System.currentTimeMillis() - start));
	}

}
