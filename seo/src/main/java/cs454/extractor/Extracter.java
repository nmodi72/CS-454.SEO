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

	static String startDir = "C://path";
	/*@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		JSONParser parser = new JSONParser();
		 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "metadata.json"));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            String name = (String) jsonObject.get("Name");
            String author = (String) jsonObject.get("Author");
            JSONArray companyList = (JSONArray) jsonObject.get("Company List");
 
            System.out.println("title" + name);
            System.out.println("local file" + author);
         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
		
		
*/		
	}
