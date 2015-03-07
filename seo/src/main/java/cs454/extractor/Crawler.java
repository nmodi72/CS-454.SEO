package cs454.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import thredds.inventory.bdb.MetadataManager.KeyValue;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import cs454.extractor.*;

public class Crawler {

	File[] list;
	static ArrayList<String> filedata = new ArrayList<String>();
	static ArrayList<String> datedata = new ArrayList<String>();
	static ArrayList<String> authordata = new ArrayList<String>();

	// static List <File> tempList = new ArrayList <File>();

	public void walk(String path) throws IOException, SAXException,
			TikaException {

		String path1 = Extracter.startDir;
		File root = new File(path1);
		list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath());
			} else {
				String relatedPath = f.getAbsoluteFile().toString();
				if (!(relatedPath.contains(".zip"))) {
					System.out.println(relatedPath);
					Map<String, String> metadata = Extraction.extract(f);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, SAXException,
			TikaException {

		String sub = "";
		final InputStream in = new FileInputStream("metadata.json");
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();) {
				// System.out.println(it.next());
				LinkedHashMap<String, Object> keyValue = (LinkedHashMap<String, Object>) it
						.next();

				System.out.println(keyValue.get("local file"));
				String str = (String) keyValue.get("local file");
				int count = str.lastIndexOf('/');

				sub = str.substring(0, count);
			
			}
			//FileUtils.cleanDirectory(sub); 
			
			new Crawler().walk(sub);
		} finally {
			in.close();
		}

		Storage.save();

	}

}
