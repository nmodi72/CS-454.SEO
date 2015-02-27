package cs454.seo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.json.simple.JSONObject;

public class Storage {

	public static void toJSON(String url, String loc, String date)
			throws Exception {
		// TODO Auto-generated method stub
		
		//System.out.println(url + "DHHHHHHHHHHHHHHHHHHH");
		/*url = url.replace("/", "\\");
		loc = loc.replace("/", "\\");
		date = date.replace("/", "\\");
		*/
		JSONObject obj = new JSONObject();
		obj.put("url:", url);
		obj.put("date last pull:", date);
		obj.put("local file:", loc);
		WebCrawler.fileJSON.write(obj.toJSONString());
		WebCrawler.fileJSON.flush();
		
		
		
		/*PrintWriter writer = new PrintWriter("metadata.txt", "UTF-8");
		FileWriter file = new FileWriter("metadata.json");

		for (int i = 0; i < Crawler.printData.size(); i = i + 3) {
			JSONObject obj = new JSONObject();
			obj.put("File", Crawler.printData.get(i));
			obj.put("Date", Crawler.printData.get(i + 1));
			obj.put("Author", Crawler.printData.get(i + 2));
			file.write(obj.toJSONString());

		}
		file.flush();
		file.close();

		for (int i = 0; i < Crawler.printData.size(); i = i + 3) {
			writer.println("File" + Crawler.printData.get(i));
			writer.println("Date" + Crawler.printData.get(i + 1));
			writer.println("Author" + Crawler.printData.get(i + 2));
		}
		writer.close();
*/
	}

}
