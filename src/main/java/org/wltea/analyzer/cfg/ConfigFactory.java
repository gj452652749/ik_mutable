package org.wltea.analyzer.cfg;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {
	static Map<String ,Configuration > confMap=new HashMap<>();
	public static Configuration getConfig(String dicFiles) {
		if(confMap.containsKey(dicFiles)) 
			return confMap.get(dicFiles);
		Configuration conf=new MutableConfig(dicFiles);
		confMap.put(dicFiles, conf);
		return conf; 
	}

}
