package org.wltea.analyzer.cfg;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {
	static Map<String ,Configuration > confMap=new HashMap<>();
	public static Configuration getConfig(boolean useSmart,String dicFiles) {
		String key=dicFiles+":"+useSmart;
		if(confMap.containsKey(key)) {
			return confMap.get(key);
		}
		Configuration conf=new MutableConfig(dicFiles);
		conf.setUseSmart(useSmart);
		confMap.put(key, conf);
		return conf; 
	}

}
