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
import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Storage {

	@SuppressWarnings("unchecked")
	public static void toJSON(String title, String type, String url,
			String loc, String date, Elements links) throws Exception {

		ArrayList<String> links1 = new ArrayList<String>();
		if(links != null){
		for (Element link : links) {

			String Link = link.absUrl("href");
			links1.add(Link);

		}
		}
		JSONObject obj = new JSONObject();
		obj.put("title", title);
		obj.put("type", type);
		obj.put("url", url);
		obj.put("date last pull", date);
		obj.put("local file", loc);
		if(links != null){
		obj.put("list", links1);
		}
		String jsonfield = obj.toJSONString();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonfield);
		String prettyJsonString = gson.toJson(je);

		WebCrawler.fileJSON.write(prettyJsonString);
		WebCrawler.fileJSON.flush();

	}

}
