package cs454.privious;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Extracter {
	static String startDir = "C://path";

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void main(String[] args) throws Exception {

		final InputStream in = new FileInputStream("metadata.json");
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();) {
				// System.out.println(it.next());
				LinkedHashMap<String, Object> keyValue = (LinkedHashMap<String, Object>) it
						.next();
				System.out.println(keyValue.get("url"));
			}
		} finally {
			in.close();
		}
	}

}
