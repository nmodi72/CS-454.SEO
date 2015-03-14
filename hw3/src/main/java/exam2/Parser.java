package exam2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Parser {

	public static HashMap<String, String> parsing(ArrayList<String> allTexts) {
		
		ArrayList<String> words = new ArrayList<String>();
		
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("E:\\520\\workspace\\hw3\\words.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				words.add(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
		

		HashMap<String, String> last = new HashMap<String, String>();

		for (int i = 0; i < allTexts.size(); i++) {

			HashMap<String, Integer> wc = new HashMap<String, Integer>();

			String str = allTexts.get(i);

			String[] parts = str.split(" ");

			for (int j = 0; j < parts.length; j++) {

				parts[j] = parts[j].replaceAll("[^a-zA-Z0-9]", "");
				parts[j] = parts[j].toLowerCase();

				if (!words.contains(parts[j])) {
					if (wc.containsKey(parts[j])) {

						wc.put(parts[j], wc.get(parts[j]) + 1);

					} else {

						wc.put(parts[j], 1);

					}

				}
			}

			Set set = wc.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();

				/*System.out.println("Doc: " + (i + 1) + " word: "
						+ mentry.getKey() + " count: " + mentry.getValue());
*/
				Random r = new Random();
				int ri = r.nextInt(100);
				if (last.containsKey(mentry.getKey())) {

					last.put((String) mentry.getKey(),
							last.get((String) mentry.getKey()) + " [" + (i + 1)
									+ ":" + ri + "]");

				} else {

					last.put((String) mentry.getKey(), " [" + (i + 1) + ":"
							+ ri + "]");
				}
			}

		}
		return last;
	}
}
