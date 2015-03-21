package cs454.privious;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import cs454.privious.Crawler;

public class Extraction {

	File[] list;
	static InputStream input;

	@SuppressWarnings("static-access")
	public static Map<String, String> extract(File f) throws IOException,
			SAXException, TikaException {

		/*
		 * Reference: http://www.rgagnon.com/javadetails/java-0487.html
		 */
		Map<String, String> map = new HashMap<String, String>();
		input = new FileInputStream(new File(f.getAbsolutePath()));

		ContentHandler texthandler = new BodyContentHandler(100000000);
		Metadata metadata = new Metadata();
		AutoDetectParser parser = new AutoDetectParser();

		try {
			parser.parse(input, texthandler, metadata);
		} catch (Exception e) {
		}
		input.close();

		MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
		MimeType jpeg = allTypes.forName("image/jpeg");
		String jpegExt = jpeg.getExtension();

		Crawler.filedata.add(f.getAbsolutePath());
		Crawler.datedata.add(metadata.get(Metadata.TITLE));
		Crawler.authordata.add(metadata.get(Metadata.CONTENT_TYPE));

		return map;
	}

}
