package exam2;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class Main {


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

/*		String url = "http://www.calstatela.edu";
		
		ArrayList<String> visitedLinks = new ArrayList<String>();

		visitedLinks.add(url);
		
		int numLinks = AttLinks.numLinks(url);
		ArrayList<String> LinkNames = AttLinks.getLinks(url);

		for (int i = 0; i < numLinks; i++) {

			ArrayList<String> LinkCheck = AttLinks.getLinks(LinkNames.get(i));
			for (int j = 0; j < LinkCheck.size(); j++) {
				if (!visitedLinks.contains(LinkCheck.get(j))) {
					visitedLinks.add(LinkCheck.get(j));
				}
			}

		}
	
		Collections.sort(visitedLinks);
*/		
		
		ArrayList<String> visitedLinks = new ArrayList<String>();

		String sub = "";
		final InputStream in = new FileInputStream("E://520//workspace//seo//metadata.json");
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();) {
				// System.out.println(it.next());
				LinkedHashMap<String, Object> keyValue = (LinkedHashMap<String, Object>) it
						.next();
				String link = (String) keyValue.get("url");
				System.out.println(link);
				visitedLinks.add(link);
			}
			//FileUtils.cleanDirectory(sub); 
			
		} finally {
			in.close();
		}

		
		
		ArrayList<String> allTexts = new ArrayList<String>();
		for (int i = 0; i < visitedLinks.size(); i++) {
			String html = AttLinks.texts(visitedLinks.get(i));
			Document doc = Jsoup.parse(html);
			Elements paragraphs = doc.select("p");
			for (Element p : paragraphs)
				if (!p.text().contains("document"))
					allTexts.add(p.text());
		}

		HashMap<String, String> wc = Parser.parsing(allTexts);
		System.out.println();
		System.out.println("Index Table");
		System.out.println();
		Set set = wc.entrySet();
		Iterator iterator = set.iterator();
		
		
		FileWriter file = new FileWriter("index.json");
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();

			 System.out.print(mentry.getKey());
			 System.out.println(mentry.getValue());
			 
			 JSONObject obj = new JSONObject();
				obj.put("Word"	, mentry.getKey());
				obj.put("index"	, mentry.getValue());
				
				String jsonfield = obj.toJSONString();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonParser jp = new JsonParser();
				JsonElement je = jp.parse(jsonfield);
				String prettyJsonString = gson.toJson(je);
				file.write(prettyJsonString);
				file.flush();

		}

		file.flush();
		file.close();
		System.out.println();
		System.out.println();
		System.out.println();
		
/*		HashMap<String, String> urldoc = new HashMap<String, String>();
		HashMap<String, Integer> urlnum = new HashMap<String, Integer>();
		HashMap<String, ArrayList<String>> urllinks = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> allLinks = PageRank.getConnectedLinks(url);
		allLinks.add(url);
		Collections.sort(allLinks);
				
		
		for(int i = 0; i < allLinks.size(); i++){
		
			System.out.println("URL: " + allLinks.get(i));
			System.out.println("		Total Links: "+ PageRank.getnum(allLinks.get(i)));
			urlnum.put(allLinks.get(i), PageRank.getnum(allLinks.get(i)));
			//urldoc.put(allLinks.get(i), PageRank.getdoc(allLinks.get(i)));
			urllinks.put(allLinks.get(i), PageRank.getlist(allLinks.get(i)));
			System.out.println("		and the links are : ");
			ArrayList<String> newprint = PageRank.getlist(allLinks.get(i));
		
			for(String s: newprint){
				System.out.println("                              "+s);
			}
		System.out.println();
		}
		
		OneMore.first(urlnum, urllinks);
*/	}

}
