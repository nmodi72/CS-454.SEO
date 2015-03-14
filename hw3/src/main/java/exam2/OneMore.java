package exam2;

import java.util.ArrayList;
import java.util.HashMap;

public class OneMore {

	public static void first(HashMap<String, Integer> urlnum,
			HashMap<String, ArrayList<String>> urllinks) {

		HashMap<String, ArrayList<String>> reverse = new HashMap<String, ArrayList<String>>();

		for ( String key : urllinks.keySet() ) {

			ArrayList<String> values = urllinks.get(key);

			if(values.contains(key)){
				//reverse.put(key, value)
			}
		}

	}
}
