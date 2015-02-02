package cs454.seo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;

import java.net.URL;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class PDFExtract {
	public static void main(String[] args) throws IOException,
			SAXException, TikaException {
		URL url = new URL("http://www.calstatela.edu");
		InputStream input = url.openStream();
		LinkContentHandler linkhandler = new LinkContentHandler();
		ContentHandler text = new BodyContentHandler();
		ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
		TeeContentHandler teeHandler = new TeeContentHandler(linkhandler, text, toHTMLHandler);
		
		Metadata metadata = new Metadata();
		ParseContext parsecontext = new ParseContext();
		HtmlParser parser = new HtmlParser();
		parser.parse(input, teeHandler, metadata, parsecontext);
		
		//System.out.println("title:\n" + metadata.get("title"));
		//System.out.println("links:\n" + linkhandler.getLinks());
		//System.out.println("text:\n" + text.toString());
        System.out.println("html:\n" + toHTMLHandler.toString());
		
/*		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(10000000);
		Metadata metadata = new Metadata();
		InputStream input = PDFExtract.class
				.getResourceAsStream("/Resume.pdf");
		parser.parse(input, handler, metadata, new ParseContext());

		for (String name : metadata.names()) {
			System.out.println(name + ":\t" + metadata.get(name));
		}
*/
	}
}
