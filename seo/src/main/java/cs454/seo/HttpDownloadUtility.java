package cs454.seo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tika.sax.Link;
import org.jsoup.select.Elements;

import com.mysql.jdbc.IterateBlock;

/*
 * Source: http://www.codejava.net/java-se/networking/use
 * -httpurlconnection-to-download-file-from-an-http-url
 */
public class HttpDownloadUtility {
	private static final int BUFFER_SIZE = 4096;

	static HashMap hashmap = new HashMap();

	@SuppressWarnings("unchecked")
	public static void downloadFile(int pass, String title,String type, String fileURL, String saveDir, Elements links)
			throws Exception {

		// if key is available then please return..!!!
		if (hashmap.get(fileURL) != null) {
			return;
		} else {

			try {
				URL url = new URL(fileURL);
				HttpURLConnection httpConn = (HttpURLConnection) url
						.openConnection();
				int responseCode = httpConn.getResponseCode();
				//System.out.println("----------------" + responseCode);

				// always check HTTP response code first
				if (responseCode == HttpURLConnection.HTTP_OK) {

					/*
					 * String disposition = httpConn
					 * .getHeaderField("Content-Disposition");
					 */
					String contentType = httpConn.getContentType();
					/* int contentLength = httpConn.getContentLength(); */
					int index = fileURL.lastIndexOf("/");
					String extension = null;
					if (index == fileURL.length()) {
						fileURL = fileURL.substring(1, index);
					}
					index = fileURL.lastIndexOf("/") + 1;
					String name = fileURL.substring(index, fileURL.length());
					if (contentType.contains("text/html")) {
						extension = ".html";
						name = name + extension;
					}
					/*
					 * System.out.println("Content-Type = " + contentType);
					 * System.out.println("Content-Length = " + contentLength);
					 */

					// opens input stream from the HTTP connection
					InputStream inputStream = httpConn.getInputStream();

					String saveFilePath = saveDir + "/" + name;

					File file = new File(saveFilePath);
					int var = 1;
					// System.out.println("FILE EXISTS" + file.exists());
					while (file.exists() == true) {

						int extIndex = saveFilePath.lastIndexOf(".");
						extension = saveFilePath.substring(extIndex,
								saveFilePath.length());
						saveFilePath = saveFilePath.substring(0, extIndex);
						saveFilePath = saveFilePath + var + extension;
						file = new File(saveFilePath);
						var++;
					}
					//System.out.println(saveFilePath + "SAVED");
					// opens an output stream to save into file
					FileOutputStream outputStream = new FileOutputStream(
							saveFilePath);

					int bytesRead = -1;
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
					}

					outputStream.close();
					inputStream.close();
					hashmap.put(fileURL, saveFilePath);
					
					/*System.out.println(fileURL + saveFilePath +
							WebCrawler.findLastModify(saveFilePath));*/
					
					if(pass == 1){
					Storage.toJSON(title, type ,fileURL, saveFilePath,
							WebCrawler.findLastModify(saveFilePath), links);
					System.out.println("File downloaded");
					}

				} else {
					//System.out.println("No file to download. Server replied HTTP code: "+ responseCode);
				}
				httpConn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

}