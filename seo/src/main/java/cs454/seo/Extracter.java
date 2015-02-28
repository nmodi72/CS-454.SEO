package cs454.seo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import javax.json.JsonReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;

public class Extracter {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		
			    String FileName="metadata.json";
			    try {
			        ArrayList<JSONObject> jsons=ReadJSON(new File(FileName),"UTF-8");
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (ParseException e) {
			        e.printStackTrace();
			    }
			}
			public static synchronized ArrayList<JSONObject> ReadJSON(File MyFile,String Encoding) throws FileNotFoundException, ParseException {
			    Scanner scn=new Scanner(MyFile,Encoding);
			    ArrayList<JSONObject> json=new ArrayList<JSONObject>();
			//Reading and Parsing Strings to Json
			    
			    while(scn.hasNext()){
			        JSONObject obj= (JSONObject) new JSONParser().parse(scn.nextLine());
			        json.add(obj);
			    }
			//Here Printing Json Objects
			    for(JSONObject obj : json){
			        System.out.println((String)obj.get("date last pull:")+" : "+(String)obj.get("local file:")+" : "+(String)obj.get("url:"));
			    }
			    return json;
			}

			
		
		
		
	
	
}
