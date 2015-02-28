package cs454.seo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.Link;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Storage {

	@SuppressWarnings("unchecked")
	public static void toJSON(String title,String type, String url, String loc, String date, List<Link> list)
			throws Exception {
		
		ArrayList<String> links1 = new ArrayList<String>();
		ArrayList<String> links2 = new ArrayList<String>();
		
		// TODO Auto-generated method stub
		for (Link name : list) {
			String curLink = name.getUri();
			
			if (!(curLink.startsWith("http://") || curLink.startsWith("https://") || curLink
					.startsWith("ftp://"))) {

				if (curLink.startsWith("/")) {
					curLink = url + curLink;
				} else {
					curLink = url + "/" + curLink;
				}

			}
			
			links1.add(curLink);
		}
		//System.out.println(url + "DHHHHHHHHHHHHHHHHHHH");
		/*url = url.replace("/", "\\");
		loc = loc.replace("/", "\\");
		date = date.replace("/", "\\");
		*/
		JSONObject obj = new JSONObject();
		obj.put("title", title);
		obj.put("type", type);
		obj.put("url", url);
		obj.put("date last pull", date);
		obj.put("local file", loc);
		obj.put("links", links1);
		
		
		
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

	public static void storelinks(List<Link> links) {
		// TODO Auto-generated method stub
		
	}

}
