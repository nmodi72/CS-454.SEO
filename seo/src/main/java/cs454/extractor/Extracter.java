package cs454.extractor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Extracter {

<<<<<<< HEAD
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void main(String[] args) throws Exception {

		final InputStream in = new FileInputStream("metadata.json");
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();) {
				// System.out.println(it.next());
				LinkedHashMap<String, Object> keyValue = (LinkedHashMap<String, Object>) it
						.next();
				System.out.println(keyValue.get("url"));
			}
		} finally {
			in.close();
		}
	}

/*		final InputStream in = new FileInputStream("metadata.json");
		try {
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(in), Map.class); it
					.hasNext();){
				System.out.println(it.next());
				}
			JSONObject json = new JSONObject();
			
			json = it.next();
			String url = (String) json.get("url");
			//String author = (String) json.get("author");
			
			System.out.println("url" + url);
			JSONArray characters = (JSONArray) json.get("characters");
			Iterator i = characters.iterator();
			System.out.println("characters: ");
			while (i.hasNext()) {
				System.out.println(" " + i.next());
			}

		} finally {
			in.close();
		}
	}*/

}
=======
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
>>>>>>> origin/master
