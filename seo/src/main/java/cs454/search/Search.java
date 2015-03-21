package cs454.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Search {
	static HashMap<String, String> mainList = new HashMap<String, String>();
	static HashMap<Integer, String> stringList = new HashMap<Integer, String>();

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public static void main(String[] args) throws JsonParseException,
			JsonProcessingException, IOException {
		// read the json

		final InputStream in = new FileInputStream("extraction.json");
		int varI = 0;
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();) {
				varI++;
				LinkedHashMap<String, Object> keyValue = (LinkedHashMap<String, Object>) it
						.next();

				String filePath = (String) keyValue.get("File Name");
				try {
					Search.contentRead(filePath, varI);
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TikaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// new Extraction().walk(parser, sub);
			}
		} finally {
			in.close();
		}

		// access file with tika

		// read content

		// seperate all words

		// store as index file

		//
		Set s = Search.mainList.entrySet();
		// Get an iterator
		Iterator i = s.iterator();
		// Display elements
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue());
		}
		System.out.println("----");

	}

	@SuppressWarnings("rawtypes")
	public static void contentRead(String filePath, int varI)
			throws IOException, SAXException, TikaException {

		File file = new File(filePath);
		InputStream input = new FileInputStream(file);
		System.out.println(file.getPath());

		Metadata metadata = new Metadata();

		BodyContentHandler handler = new BodyContentHandler(10 * 1024 * 1024);
		AutoDetectParser parser = new AutoDetectParser();

		parser.parse(input, handler, metadata);
		String dataContent = handler.toString();
		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		dataContent = dataContent.replaceAll("[^a-zA-Z]+", " ");
		dataContent = dataContent.toLowerCase();

		String[] wordArray = dataContent.split(" ");
		String newString = "";
		for (String str : wordArray) {
			if (str.equals("the") || str.equals("a") || str.equals("is")
					|| str.equals("") || str.equals(" ")) {
				// No Action Please.
			} else {
				newString = newString + " " + str;

				if (hashmap.containsKey(str)) {
					int var = hashmap.get(str);
					hashmap.put(str, var + 1);
				} else {
					hashmap.put(str, 1);
				}
			}
		}
		Search.stringList.put(varI, newString);

		Set set = hashmap.entrySet();
		Iterator iter = set.iterator();
		// Display Each elements

		while (iter.hasNext()) {
			Map.Entry me = (Map.Entry) iter.next();
			String key = (String) me.getKey();
			if (Search.mainList.containsKey(key)) {
				String value = Search.mainList.get(key) + " [" + (varI) + ":"
						+ me.getValue() + "]";
				Search.mainList.put(key, value);

			} else {
				String value = "[" + (varI) + ":" + me.getValue() + "]";
				Search.mainList.put(key, value);

			}
		}

	}

}
