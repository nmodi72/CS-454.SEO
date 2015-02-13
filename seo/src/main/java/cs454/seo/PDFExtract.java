package cs454.seo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.net.ssl.SSLHandshakeException;
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

	static ArrayList<String> storedLinks = new ArrayList<String>();

	public static void main(String[] args) throws IOException, SAXException,
			TikaException {
		PDFExtract p = new PDFExtract();

		String url = "http://www.calstatela.edu";
		p.BFS(url);

		// System.out.println("title:\n" + metadata.get("title"));
		// System.out.println("links:\n" + linkhandler.getLinks());
		// System.out.println("text:\n" + text.toString());
		// System.out.println("html:\n" + toHTMLHandler.toString());

		/*
		 * Parser parser = new AutoDetectParser(); BodyContentHandler handler =
		 * new BodyContentHandler(10000000); Metadata metadata = new Metadata();
		 * InputStream input = PDFExtract.class
		 * .getResourceAsStream("/Resume.pdf"); parser.parse(input, handler,
		 * metadata, new ParseContext());
		 * 
		 * for (String name : metadata.names()) { System.out.println(name +
		 * ":\t" + metadata.get(name)); }
		 */
	}

	public void BFS(String path) throws IOException, SAXException,
			TikaException {
		Queue queue = new LinkedList();
		queue.add(path);
		BFS_Recursive(queue);
	}

	public void BFS_Recursive(Queue queue) throws IOException, SAXException,
			TikaException, SSLHandshakeException {
		

		if (queue.isEmpty()) {
			return;
		}
		String path = (String) queue.remove();
		System.out.println(path);
		storedLinks.add(path);

		URL url = new URL(path);
		InputStream input = url.openStream();
		LinkContentHandler linkhandler = new LinkContentHandler();
		ContentHandler text = new BodyContentHandler();
		ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();

		TeeContentHandler teeHandler = new TeeContentHandler(linkhandler, text,
				toHTMLHandler);

		Metadata metadata = new Metadata();
		ParseContext parsecontext = new ParseContext();
		AutoDetectParser parser = new AutoDetectParser();
		parser.parse(input, teeHandler, metadata, parsecontext);

		// boolean[] V = new boolean[linkhandler.getLinks().size()];

		for (Link name : linkhandler.getLinks()) {
			String curLink = name.getUri();
			if (!(curLink.contains("http"))) {
				String absPath = url + curLink;
				curLink = absPath;
			}
			queue.add(curLink);

			// System.out.println(curLink + "\t");
		}

		Iterator iterator = queue.iterator();
		while (iterator.hasNext()) {
			String element = (String) iterator.next();
			//System.out.println(element);
			storedLinks.add(element);
		}

		/*
		 * for (String a : storedLinks) { System.out.println(a); }
		 */
		BFS_Recursive(queue);
	}

	
}
