package cs454.seo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.jsoup.nodes.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class WebCrawler {
	public static DB db = new DB();
	static ArrayList<tempData> storedLinks = new ArrayList<tempData>();
	static String masterURL = "http://www.google.com";
	static FileWriter fileJSON;

	public static void main(String[] args) throws Exception {
		storedLinks.add(new tempData(masterURL, 0, false));

		fileJSON = new FileWriter("metadata.json");
		WebCrawler p = new WebCrawler();
		// db.runSql2("TRUNCATE Record;");
		p.CRAWL_Recursive(1);
		// p.BFS(masterURL);
		WebCrawler.fileJSON.close();

		// fileJSON.close();

	}

	private String urlPath;
	private int urlDepth;

	@SuppressWarnings("unused")
	public void CRAWL_Recursive(int d) throws SAXException, TikaException,
			SSLHandshakeException, SQLException, Exception {
		if (storedLinks.isEmpty()) {
			return;
		}
		// String urlPath = null;
		// int urlDepth = 0;
		System.out.println("-------");
		for (tempData tempData : storedLinks) {
			System.out.println(tempData.getUrl() + " and Depth is "
					+ tempData.getDepth());
		}
		int var = 0;
		for (int i = 0; i < storedLinks.size(); i++) {
			if ((storedLinks.get(i).isCrawled() == false) && (var <= d)) {
				this.urlPath = storedLinks.get(i).getUrl();
				this.urlDepth = storedLinks.get(i).getDepth();
				storedLinks.set(i, new tempData(urlPath, urlDepth, true));
				break;
			}

		}
		/*
		 * while (storedLinks.get(var).isCrawled() == false && var <= depth) {
		 * path = storedLinks.get(var).getUrl(); currentDepth =
		 * storedLinks.get(var).getDepth(); storedLinks.set(var, new
		 * tempData(true)); var++; }
		 */
		String fileURL = urlPath;
		String saveDir = "C:/path";
		if (urlDepth <= d) {
			URL url;
			try {
				url = new URL(urlPath);
				URI uri = new URI(urlPath);
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

				TeeContentHandler teeHandler = new TeeContentHandler(
						linkhandler, text, toHTMLHandler);

				Metadata metadata = new Metadata();
				ParseContext parsecontext = new ParseContext();
				AutoDetectParser parser = new AutoDetectParser();
				parser.parse(input, teeHandler, metadata, parsecontext);

				for (Link name : linkhandler.getLinks()) {
					String curLink = name.getUri();

					int curDepth = 0;
					if (!(curLink.startsWith("http://")
							|| curLink.startsWith("https://") || curLink
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

					if (curLink.contains(domain)) {
						curDepth = 1;
					} else {
						curDepth = urlDepth + 1;
					}

					if (curDepth <= d) {
						if (!(storedLinks.contains(curLink))) {
							storedLinks.add(new tempData(curLink, curDepth,
									false));
						}

					}

				}
			} catch (Exception e) {

			}

		}
		CRAWL_Recursive(d);

	}

	public void BFS(String path) throws IOException, SAXException,
			TikaException, SQLException {
		Queue queue = new LinkedList();
		queue.add(path);
		String fileURL = path;
		String saveDir = "C:/path";
		try {
			HttpDownloadUtility.downloadFile(fileURL, saveDir);
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		// BFS_Recursive(queue);
		// CRAWL_Recursive(2);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void BFS_Recursive(Queue queue) throws SAXException, TikaException,
			SSLHandshakeException, SQLException {

		if (queue.isEmpty()) {
			return;
		}
		String path = (String) queue.remove();
		String fileURL = path;
		String saveDir = "C:/path";

		for (int i = 0; i == storedLinks.size(); i++) {

			System.out.println(storedLinks.get(i).getUrl());
		}
		System.out.println("-------------------------");

		// check the stored link, if copy founded simply skip
		if (!((storedLinks.contains(path)) || (storedLinks.contains(path
				.substring(0, path.length() - 1))))) {
			// storedLinks.add(new tempData(path, 1));

		} else {
			BFS_Recursive(queue);
		}

		// check if the given URL is already in database
		String sql = "select * from Record where URL = '" + path + "'";
		ResultSet rs = db.runSql(sql);
		if (rs.next()) {

		} else {
			// store the URL to database to avoid parsing again
			sql = "INSERT INTO  `Crawler`.`Record` " + "(`URL`) VALUES "
					+ "(?);";
			PreparedStatement stmt = db.conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, path);
			stmt.execute();
		}

		URL url;
		try {
			url = new URL(path);

			// InputStream input = url.openStream();
			// In adverse sittuation, uncomment upper line and delete below till
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

			for (Link name : linkhandler.getLinks()) {
				String curLink = name.getUri();
				if (!(curLink.startsWith("http://")
						|| curLink.startsWith("https://") || curLink
							.startsWith("ftp://"))) {
					String urlLink = url.toString();
					String absPath = null;
					if (urlLink.endsWith("/")) {
						absPath = urlLink.substring(0, urlLink.length() - 1);
					} else {
						absPath = urlLink + curLink;
					}

					curLink = absPath;

				}
				if (curLink.endsWith("/")) {
					curLink = curLink.substring(0, curLink.length() - 1);

				}
				if ((path.contains(masterURL)) || (masterURL.contains(path))) {

					try {
						HttpDownloadUtility.downloadFile(curLink, saveDir);
					} catch (Exception ex) {
						// ex.printStackTrace();
					}

					queue.add(curLink);
				} else {

					if (!((storedLinks.contains(curLink)) || (storedLinks
							.contains(curLink.substring(0, curLink.length() - 1))))) {

						try {
							HttpDownloadUtility.downloadFile(curLink, saveDir);
						} catch (Exception ex) {
							// ex.printStackTrace();
						}
						// storedLinks.add(new tempData(curLink, 1));
						// storedLinks.add(curLink);

					}

					// check if the given URL is already in database
					String newSql = "select * from Record where URL = '"
							+ curLink + "'";
					ResultSet rs1 = db.runSql(newSql);
					if (rs1.next()) {

					} else {
						// store the URL to database to avoid parsing again
						newSql = "INSERT INTO  `Crawler`.`Record` "
								+ "(`URL`) VALUES " + "(?);";
						PreparedStatement stmt1 = db.conn.prepareStatement(
								newSql, Statement.RETURN_GENERATED_KEYS);
						stmt1.setString(1, path);
						stmt1.execute();
					}

					// storedLinks.add(curLink);
				}

			}

		} catch (Exception e) {

			// e.printStackTrace();
		}
		BFS_Recursive(queue);
	}

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
