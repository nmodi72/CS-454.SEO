package cs454.seo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;

import java.net.URI;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import javax.net.ssl.SSLHandshakeException;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class WebCrawler {
	public static DB db = new DB();
	static ArrayList<tempData> storedLinks = new ArrayList<tempData>();
	public static String masterURL;
	static String saveDir = "C://path";
	public static int masterD;
	static FileWriter fileJSON;

	public static void main(String[] args) throws Exception {
		WebCrawler p = new WebCrawler();

		p.passArgs(args);

		File root = new File(saveDir);
		for(File file: root.listFiles()){ file.delete();}
		
		
		
		fileJSON = new FileWriter("metadata.json");
		storedLinks.add(new tempData(masterURL, 0, false, true));
	
		//p.CRAWL_Recursive(2);

		p.CRAWL_Recursive(masterD);

		fileJSON.close();
		
		
		
		// p.BFS(masterURL);

	}

	public void passArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
		
			if (args[i].equals("-d")) {
				this.masterD = Integer.parseInt(args[i + 1]);
				continue;
			}
			if (args[i].equals("-u")) {
				this.masterURL = args[i + 1];
				continue;
			}
		}

	}

	private String urlPath;
	private int urlDepth;
	private boolean urlSameDomain;

	@SuppressWarnings("unused")
	public void CRAWL_Recursive(int d) throws SAXException, TikaException,
			SSLHandshakeException, SQLException, Exception {
		if (storedLinks.isEmpty()) {
			return;
		}
		boolean check = false;
		for (int i = 0; i < storedLinks.size(); i++) {

			if (storedLinks.get(i).isCrawled() == true) {
				check = true;
			} else {
				check = false;
			}
		}
		if (check == true) {
			return;
		}

		System.out.println("-------");
		int var = 0;

		for (tempData tempData : storedLinks) {

			System.out.println(var + " At Depth" + tempData.getDepth() + " "
					+ tempData.getUrl());
			var++;
		}

		System.out.println();

		for (int i = 0; i < storedLinks.size(); i++) {

			if ((storedLinks.get(i).isCrawled() == false)/* && (i <= d) */) {
				this.urlPath = storedLinks.get(i).getUrl();
				this.urlDepth = storedLinks.get(i).getDepth();
				this.urlSameDomain = storedLinks.get(i).isSameDomain();

				storedLinks.set(i, new tempData(urlPath, urlDepth, true,
						urlSameDomain));
				break;
			}

		}
		System.out.println("Parent " + urlPath + " ------- " + urlDepth);

/*		URL url;
		try {
			url = new URL(urlPath);
			URI uri = new URI(masterURL);
			String domain = uri.getHost();
			System.out.println(domain + "Domain Name");

			// InputStream input = url.openStream();
			// In adverse sittuation, uncomment upper line and delete below
			// till
			// checkpt1 - implemented to remove IOEXception - nirav
			URLConnection uc;
			StringBuilder parsedContentFromUrl = new StringBuilder();
			uc = url.openConnection();
			uc.connect();

			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();

			BufferedInputStream input = new BufferedInputStream(
					uc.getInputStream());

			// *** checkpt1

			LinkContentHandler linkhandler = new LinkContentHandler();
			ContentHandler text = new BodyContentHandler();
			ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();

			TeeContentHandler teeHandler = new TeeContentHandler(linkhandler,
					text, toHTMLHandler);

			Metadata metadata = new Metadata();
			ParseContext parsecontext = new ParseContext();
			AutoDetectParser parser = new AutoDetectParser();
			parser.parse(input, teeHandler, metadata, parsecontext);
			String title = metadata.get(Metadata.TITLE);
			String type = metadata.get(Metadata.CONTENT_TYPE);*/
			urlDepth = urlDepth + 1;
			
			//JSOup
			
			
			Document doc;
			try {
		 
				// need http protocol
				doc = Jsoup.connect(urlPath).get();
				URI uri = new URI(masterURL);
				String domain = uri.getHost();
				System.out.println(domain + "Domain Name");

				// get page title
				String title = doc.title();
				//System.out.println("title : " + title);
				String text = doc.body().text();
				// get all links
				Elements links = doc.select("a[href]");
				
				Connection.Response res = Jsoup.connect(urlPath).timeout(10*1000).execute();

				String contentType=res.contentType();
				
				
				try {
					HttpDownloadUtility.downloadFile(1,title,contentType,urlPath, saveDir, links);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				for (Element link : links) {
					Element image = doc.select("img").first();
					String imageurl = image.absUrl("src");
					System.out.println(imageurl + "IMAGE");
					
					String curLink = link.absUrl("href");
					
					int curDepth = urlDepth;
					try {
						HttpDownloadUtility.downloadContent(saveDir, imageurl);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
<<<<<<< HEAD
=======
				int count = 0;
>>>>>>> origin/master
				for (Element link : links) {
					Element image = doc.select("img").first();
					String imageurl = image.absUrl("src");
					//System.out.println(imageurl + "IMAGE");
					
					String curLink = link.absUrl("href");
					
					int curDepth = urlDepth;
					try {
<<<<<<< HEAD
						HttpDownloadUtility.downloadContent(saveDir, curLink);
=======
						count++;
						if(count == 2){
							break;
						}
						HttpDownloadUtility.downloadContent(saveDir, imageurl);
						
>>>>>>> origin/master
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				for (Element link : links) {
					Element image = doc.select("img").first();
					String imageurl = image.absUrl("src");
					System.out.println(imageurl + "IMAGE");
					
					String curLink = link.absUrl("href");
					
					int curDepth = urlDepth;

					forEachCrawledLinks(curLink, domain, d, curDepth, urlSameDomain);
					
				}
				
				
			/*	try {
					HttpDownloadUtility.downloadFile(title,type,urlPath, saveDir, linkhandler.getLinks());
				} catch (Exception ex) {
					// ex.printStackTrace();
				}

					//Storage.storelinks(linkhandler.getLinks());
				for (Link name : linkhandler.getLinks()) {
					int curDepth = urlDepth;

					String curLink = name.getUri();
					forEachCrawledLinks(curLink, domain, d, curDepth, urlSameDomain);
				}
			*/
			
			
			
			
			
			

		/*	try {
				HttpDownloadUtility.downloadFile(title,type,urlPath, saveDir, linkhandler.getLinks());
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

				//Storage.storelinks(linkhandler.getLinks());
			for (Link name : linkhandler.getLinks()) {
				int curDepth = urlDepth;

				String curLink = name.getUri();
				forEachCrawledLinks(curLink, domain, d, curDepth, urlSameDomain);
			}*/
		} catch (Exception e) {

		}

		CRAWL_Recursive(d);

	}

	public void forEachCrawledLinks(String curLink, String domain, int d,
			int curDepth, boolean urlSameDomain) throws Exception {
		// int curDepth = urlDepth;
		boolean isCurSameDomain = false;

		if (!(curLink.startsWith("http://") || curLink.startsWith("https://") || curLink
				.startsWith("ftp://"))) {

			if (curLink.startsWith("/")) {
				curLink = urlPath + curLink;
			} else {
				curLink = urlPath + "/" + curLink;
			}

		}

		if (curLink.endsWith("/")) {
			curLink = curLink.substring(0, curLink.length() - 1);
		}

		URI newUri = new URI(curLink);
		String newDomain = newUri.getHost();

		if (urlSameDomain == true) {

			if (newDomain.equals(domain)) {
				isCurSameDomain = true;
				// curDepth = 1;

			} else {
				isCurSameDomain = false;
				// curDepth = urlDepth + 1;
			}
		} else {
			isCurSameDomain = false;
		}
		boolean isPresent = true;
		for (tempData temp : storedLinks) {
			if (!(curLink.equals(temp.getUrl()))) {
				isPresent = false;
			} else {
				isPresent = true;
				break;
			}
		}

	
		if (isPresent == false) {

			if ((isCurSameDomain == true)
					|| (isCurSameDomain == false && curDepth <= d)) {

				storedLinks.add(new tempData(curLink, curDepth, false,
						isCurSameDomain));
		
				return;
			} else {
				System.out.println("Error");
			}

		}

	}


/*
		URL url;
		try {
			url = new URL(urlPath);
			URI uri = new URI(masterURL);
			String domain = uri.getHost();
			System.out.println(domain + "Domain Name");

			// InputStream input = url.openStream();
			// In adverse sittuation, uncomment upper line and delete below
			// till
			// checkpt1 - implemented to remove IOEXception - nirav
			URLConnection uc;
			StringBuilder parsedContentFromUrl = new StringBuilder();
			uc = url.openConnection();
			uc.connect();

			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();

			BufferedInputStream input = new BufferedInputStream(
					uc.getInputStream());

			// *** checkpt1

			LinkContentHandler linkhandler = new LinkContentHandler();
			ContentHandler text = new BodyContentHandler();
			ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();

			TeeContentHandler teeHandler = new TeeContentHandler(linkhandler,
					text, toHTMLHandler);

			Metadata metadata = new Metadata();
			ParseContext parsecontext = new ParseContext();
			AutoDetectParser parser = new AutoDetectParser();
			parser.parse(input, teeHandler, metadata, parsecontext);
			String title = metadata.get(Metadata.TITLE);
			String type = metadata.get(Metadata.CONTENT_TYPE);
*/
	public static void findMetadata(String path) throws Exception {

		File f = new File(path);
		InputStream in = new FileInputStream(new File(f.getAbsolutePath()));
		ContentHandler texthandler = new BodyContentHandler(100000000);
		Metadata metadata = new Metadata();
		// TikaMetadata tika = new TikaMetadata();
		AutoDetectParser parser = new AutoDetectParser();

		// String mimeType = tika.detect(input);
		// metadata.set(Metadata.CONTENT_TYPE, mimeType);
		try {
			parser.parse(in, texthandler, metadata);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		in.close();
		System.out.println(metadata.getDate(Metadata.DATE));
	}

	public static String findLastModify(String path) {
		File file = new File(path);

		// System.out.println("Before Format : " + file.lastModified());

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		System.out.println("After Format : " + sdf.format(file.lastModified()));
		return sdf.format(file.lastModified());
	}
	
}
