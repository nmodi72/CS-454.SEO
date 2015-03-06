package cs454.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import java.io.File;

public class Extracter {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		String FileName = "metadata.json";
		
		JSONArray jsonArray = readJsonFile(FileName);
		getFileMetadate(jsonArray);
		
/*		final InputStream in = new FileInputStream("metadata.json");
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();)
				System.out.println(it.next());

		} finally {
			in.close();
		}*/
	}
	/*    try {
    ArrayList<JSONObject> jsons=ReadJSON(new File(FileName),"UTF-8");
} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (ParseException e) {
    e.printStackTrace();
}
}*/

	
	public static JSONArray readJsonFile(String fileName) throws Exception {
		JSONParser parser = new JSONParser();
		Object object = parser.parse(new FileReader(fileName));
		JSONArray jsonArray = (JSONArray) object;
		return jsonArray;
	}


	public static void getFileMetadate(JSONArray jsonArray) throws Exception {
		
		for (Object images : jsonArray) {
			JSONObject imageJsonObject = (JSONObject) images;
			System.out.println(imageJsonObject.get("url"));
			
			/*	extractFileMetadata(path + imageJsonObject.get("newname"),
					imageJsonObject);
			System.out.println(imageJsonObject.toJSONString());
		*/}

	}


	public static void readJson(String file) {
		JSONParser parser = new JSONParser();
		try {
			System.out.println("Reading JSON file from Java program");
			FileReader fileReader = new FileReader(file);
			JSONObject json = (JSONObject) parser.parse(fileReader);
			String url = (String) json.get("url");
			//String author = (String) json.get("author");
			
			System.out.println("url" + url);
			JSONArray characters = (JSONArray) json.get("characters");
			Iterator i = characters.iterator();
			System.out.println("characters: ");
			while (i.hasNext()) {
				System.out.println(" " + i.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static synchronized ArrayList<JSONObject> ReadJSON(File MyFile,
			String Encoding) throws FileNotFoundException, ParseException {
		Scanner scn = new Scanner(MyFile, Encoding);
		ArrayList<JSONObject> json = new ArrayList<JSONObject>();
		// Reading and Parsing Strings to Json

		while (scn.hasNext()) {
			JSONObject obj = (JSONObject) new JSONParser()
					.parse(scn.nextLine());
			json.add(obj);
		}
		// Here Printing Json Objects
		for (JSONObject obj : json) {
			System.out.println((String) obj.get("date last pull:") + " : "
					+ (String) obj.get("local file:") + " : "
					+ (String) obj.get("url:"));
		}
		return json;
	}

}
