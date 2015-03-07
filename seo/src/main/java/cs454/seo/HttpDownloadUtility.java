package cs454.seo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.tika.sax.Link;
import org.jsoup.select.Elements;

import com.mysql.jdbc.IterateBlock;

/*
 * Source: http://www.codejava.net/java-se/networking/use
 * -httpurlconnection-to-download-file-from-an-http-url
 */
public class HttpDownloadUtility {
	private static final int BUFFER_SIZE = 4096;

	@SuppressWarnings("rawtypes")
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

				// always check HTTP response code first
				if (responseCode == HttpURLConnection.HTTP_OK) {
					String uuid = UUID.randomUUID().toString();
					
					String contentType = httpConn.getContentType();
					//int index = fileURL.lastIndexOf("/");
					String extension = null;
					
					
					if(contentType.contains("text/html")){
						extension = ".html";
					}else if(contentType.contains("application/xml")){
						extension = ".xml";
					}else if(contentType.contains("application/xhtml+xml")){
						extension = ".xhtml";
					}else if(contentType.contains("application/pdf")){
						extension = ".pdf";
					}else if(contentType.contains("image/png")){
						extension = ".png";
					}else if(contentType.contains("image/gif")){
						extension = ".gif";
					}else if(contentType.contains("image/jpeg")){
						extension = ".jpg";
					}else if(contentType.contains("application/vnd.openxmlformats-officedocument.presentationml.presentation")){
						extension = ".pptx";
					}else if(contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
						extension = ".docx";
					}else{
						extension = " ";
					}
					
					
					// opens input stream from the HTTP connection
					InputStream inputStream = httpConn.getInputStream();
					
					String saveFilePath = saveDir + "/" + uuid +extension;

					
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
	
	
	@SuppressWarnings({ "resource", "unchecked" })
	public static void downloadContent(String saveFilePath, String fileURL){
		
		if (hashmap.get(fileURL) != null) {
			return;
		} else {
			Elements links = null;
			System.out.println("CONTENT ");
			String title = "null";
			String uuid = UUID.randomUUID().toString();
			//System.out.println("uuid = " + uuid);
			URL website;
			try {
				website = new URL(fileURL);
				String extension = null;
				URLConnection connection = (URLConnection) website.openConnection();
				System.out.println(fileURL);
				System.out.println(connection.getContentType() + "CONTENT");
				String contentType = connection.getContentType();
				
				if(contentType.contains("text/html")){
					extension = ".html";
				}else if(contentType.contains("application/xml")){
					extension = ".xml";
				}else if(contentType.contains("application/xhtml+xml")){
					extension = ".xhtml";
				}else if(contentType.contains("application/pdf")){
					extension = ".pdf";
				}else if(contentType.contains("image/png")){
					extension = ".png";
				}else if(contentType.contains("image/gif")){
					extension = ".gif";
				}else if(contentType.contains("image/jpeg")){
					extension = ".jpg";
				}else if(contentType.contains("application/vnd.openxmlformats-officedocument.presentationml.presentation")){
					extension = ".pptx";
				}else if(contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
					extension = ".docx";
				}else{
					extension = " ";
				}
				
				
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos;
				fos = new FileOutputStream(saveFilePath + "/" +uuid + extension);
				
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				
				
				
				try {
					Storage.toJSON(title, contentType ,fileURL, saveFilePath,
							WebCrawler.findLastModify(saveFilePath), links);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    hashmap.put(fileURL, saveFilePath);
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	public static boolean checkFileExists(String saveFilePath, String fileURL ){

		File file = new File(saveFilePath);
		int var = 1;
		// System.out.println("FILE EXISTS" + file.exists());
		while (file.exists() == true) {

			int extIndex = saveFilePath.lastIndexOf(".");
			String extension = saveFilePath.substring(extIndex,
					saveFilePath.length());
			saveFilePath = saveFilePath.substring(0, extIndex);
			saveFilePath = saveFilePath + var + extension;
			file = new File(saveFilePath);
			var++;
		}

		
		return false;
		
	}
}