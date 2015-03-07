package cs454.extractor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import cs454.extractor.*;
import cs454.seo.WebCrawler;

public class Storage {
	
	public static void save()
			throws IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = new PrintWriter("extraction.txt", "UTF-8");
		FileWriter file = new FileWriter("extraction.json");
		
		for(int i = 0;  i < Crawler.filedata.size(); i++){
			writer.println("File:"	+ Crawler.filedata.get(i));
			writer.println("Title:"	+ Crawler.datedata.get(i));
			writer.println("Type:"	+ Crawler.authordata.get(i));
			writer.println("-----------------------------------------------------------------------");
			
			JSONObject obj = new JSONObject();
			obj.put("File"	, Crawler.filedata.get(i));
			obj.put("Title"	, Crawler.datedata.get(i));
			obj.put("Type"	, Crawler.authordata.get(i));
			
			
			
			String jsonfield = obj.toJSONString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonfield);
			String prettyJsonString = gson.toJson(je);
			
			file.write(prettyJsonString);
			file.flush();
			
			
			
			
			
		}
		System.out.println("done");
		writer.close();
		
		file.flush();
		file.close();
	}


}
