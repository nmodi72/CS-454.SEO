package cs454.seo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

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

public class PDFExtract {
	public static DB db = new DB();
	static ArrayList<String> storedLinks = new ArrayList<String>();
	static String masterURL = "https://www.calstatela.edu";
	
	public static void main(String[] args) throws IOException, SAXException,
			TikaException, SQLException {
		PDFExtract p = new PDFExtract();
		db.runSql2("TRUNCATE Record;");
		https://www.facebook.com/careers/?ref=pf/careers#locations
		p.BFS(masterURL);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void BFS(String path) throws IOException, SAXException,
			TikaException, SQLException {
		Queue queue = new LinkedList();
		queue.add(path);
		BFS_Recursive(queue);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void BFS_Recursive(Queue queue) throws SAXException, TikaException,
			SSLHandshakeException, SQLException {

		/*	SSLContext sslContext = SSLContext.getInstance("SSL");

		// set up a TrustManager that trusts everything
		sslContext.init(null, new TrustManager[] { new X509TrustManager() {
		            public X509Certificate[] getAcceptedIssuers() {
		                    System.out.println("getAcceptedIssuers =============");
		                    return null;
		            }

		            public void checkClientTrusted(X509Certificate[] certs,
		                            String authType) {
		                    System.out.println("checkClientTrusted =============");
		            }

		            public void checkServerTrusted(X509Certificate[] certs,
		                            String authType) {
		                    System.out.println("checkServerTrusted =============");
		            }
		} }, new SecureRandom());

		SSLSocketFactory sf = new SSLSocketFactory(sslContext);
		Scheme httpsScheme = new Scheme("https", 443, sf);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(httpsScheme);

		// apache HttpClient version >4.2 should use BasicClientConnectionManager
		ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm);
		*/
		if (queue.isEmpty()) {
			return;
		}
		String path = (String) queue.remove();
		//System.out.println(path);
		for (String str : storedLinks){
			System.out.println(str);
		}
		System.out.println("-------------------------");
		
		if(!((storedLinks.contains(path)) || (storedLinks.contains(path.substring(0, path.length() - 1))))){
			storedLinks.add(path);	
		}else{
			BFS_Recursive(queue);
		}
		
		//check if the given URL is already in database
		String sql = "select * from Record where URL = '"+path+"'";
		ResultSet rs = db.runSql(sql);
		if(rs.next()){
 
		}else{
			//store the URL to database to avoid parsing again
			sql = "INSERT INTO  `Crawler`.`Record` " + "(`URL`) VALUES " + "(?);";
			PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
						absPath = url + curLink;
					}

					curLink = absPath;
				}
				
				if((path.contains(masterURL)) || (masterURL.contains(path))){
					queue.add(curLink);
				}else{
					
					if(!((storedLinks.contains(curLink)) || (storedLinks.contains(curLink.substring(0, curLink.length() - 1))))){
						storedLinks.add(curLink);	
					}else{
						//BFS_Recursive(queue);
					}
					
					//check if the given URL is already in database
					String newSql = "select * from Record where URL = '"+curLink+"'";
					ResultSet rs1 = db.runSql(newSql);
					if(rs1.next()){
			 
					}else{
						//store the URL to database to avoid parsing again
						newSql = "INSERT INTO  `Crawler`.`Record` " + "(`URL`) VALUES " + "(?);";
						PreparedStatement stmt1 = db.conn.prepareStatement(newSql, Statement.RETURN_GENERATED_KEYS);
						stmt1.setString(1, path);
						stmt1.execute();
					}
					
					
					//storedLinks.add(curLink);
				}
				
			}

/*			Iterator iterator = queue.iterator();
			while (iterator.hasNext()) {
				String element = (String) iterator.next();
				// System.out.println(element);
				storedLinks.add(element);
			}
*/
			/*
			 * for (String a : storedLinks) { System.out.println(a); }
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		BFS_Recursive(queue);
	}

}
