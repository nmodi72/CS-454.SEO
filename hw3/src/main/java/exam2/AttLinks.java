package exam2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AttLinks {

	public static ArrayList<String> getLinks(String url)
			throws MalformedURLException, IOException {
		ArrayList<String> Links = new ArrayList<String>();
		System.out.println(url);
		Document doc = Jsoup.parse(new URL(url), 2000);
		//System.out.println(url);
		Elements resultLinks = doc.select("a");
		//System.out.println("number of links: " + resultLinks.size());
		for (Element link : resultLinks) {

			String href = link.attr("href");
			String test = link.absUrl("href");
			/* System.out.println("Title: " + link.text());*/
			 //System.out.println("		has a link to - " + href);
			Links.add(test);

		}
		return Links;
	}

	public static int numLinks(String url) throws MalformedURLException,
			IOException {

		Document doc = Jsoup.parse(new URL(url), 2000);

		Elements resultLinks = doc.select("a");

		return resultLinks.size();
	}

	public static String texts(String url) throws Exception {
		   StringBuilder sb = new StringBuilder();
		   System.out.println(url);
		   for(Scanner sc = new Scanner(new URL(url).openStream()); sc.hasNext(); )
		      sb.append(sc.nextLine()).append('\n');
		   return sb.toString();
		}
}
