package org.wltea.analyzer.cfg;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {
	static Map<String ,Configuration > confMap=new HashMap<>();
	static Map<Boolean ,Configuration > defaultConfMap=new HashMap<>();
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
	public static Configuration getDistributedConfig(boolean useSmart,String dicFiles,String distributedDicPath) {
		String key=dicFiles+":"+useSmart+":"+distributedDicPath;
		if(confMap.containsKey(key)) {
			return confMap.get(key);
		}
		Configuration conf=new MutableConfig(dicFiles,distributedDicPath);
		conf.setUseSmart(useSmart);
		confMap.put(key, conf);
		return conf; 
	}
	/**
	 * 兼容旧版不可变词库的模式
	 * @param useSmart
	 * @param dicFiles
	 * @return
	 */
	public static Configuration getDefaultConfig(boolean useSmart) {
		boolean key=useSmart;
		if(defaultConfMap.containsKey(key)) {
			return defaultConfMap.get(key);
		}
		Configuration conf=DefaultConfig.getInstance();
		conf.setUseSmart(useSmart);
		defaultConfMap.put(key, conf);
		return conf; 
	}

}
