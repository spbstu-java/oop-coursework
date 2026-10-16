package lab3;

import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;

public class Dictionary {
	private Map<String, String> translations;

	private static String p1 = "^([^|]+)\\|([^|]+)$";
	private static Pattern pattern = Pattern.compile(p1);
	public Dictionary() {
		translations = new HashMap<String, String>();
	}
	public Dictionary(String path) throws FileReadException, InvalidFileFormatException {
		translations = new HashMap<String, String>();
		try (FileReader reader = new FileReader(path)){
        	var lines = reader.readAllLines();
        	for (int i=0; i<lines.size(); ++i) {
        		var line = lines.get(i);
        		var m = pattern.matcher(line);
        		if (!m.matches()) {
        			throw new InvalidFileFormatException(String.format("Строка %d не соответствует формату: %s",i+1, line));
        		}
        		translations.put(
					m.group(1).strip().toLowerCase(),
					m.group(2).strip().toLowerCase()
				);
        	}
		}
        catch(IOException ex){
        	throw new FileReadException(ex);
        }   
	}
	public boolean hasStart(String key) {
		var iterator = translations.keySet().iterator();
		while (iterator.hasNext()) {
			var t = iterator.next();
			if (t.startsWith(key))
				return true;
		}
		return false;
	}
	public boolean hasKey(String key) {
		return translations.containsKey(key);
	}
	public String get(String key) {
		return translations.get(key);
	}
	@Override public String toString() {
		return translations.toString();
	}
	public String translate(String text) {
		var keys =  translations.keySet();
		String[] arr = new String[keys.size()];
		int i=0;
		for(String str : keys) {
			arr[i++] = str;
		}
	    Comparator<String> cmp = new Comparator<String>() {
	        public int compare(String o1, String o2) {
	            return -Integer.compare(o1.length(), o2.length());
	        }
	    };
		Arrays.sort(arr, cmp);
		for (i=0; i<arr.length; ++i) {
			var txt = arr[i];
			var pattern2 = Pattern.compile(txt, Pattern.CASE_INSENSITIVE);
			var matcher = pattern2.matcher(text);
			text = matcher.replaceAll(translations.get(arr[i]));
		}
		return text;
	}
}
