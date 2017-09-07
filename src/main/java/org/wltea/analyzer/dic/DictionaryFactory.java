package org.wltea.analyzer.dic;

import java.util.HashMap;
import java.util.Map;

import org.wltea.analyzer.cfg.Configuration;
/**
 * 工厂类根据配置生成Dictionary
 * @author gaoj
 *
 */
public class DictionaryFactory {
	static Map<Configuration ,Dictionary > dictionaryMap=new HashMap<>();
	/**
	 * 根据配置在zk上创建一个dic节点
	 */
	public void createDicNode(Configuration cfg) {
		
	}
	public static Dictionary getDictionary(Configuration cfg) {
		if(dictionaryMap.containsKey(cfg)) 
			return dictionaryMap.get(cfg);
		Dictionary dictionary=new Dictionary(cfg);
		dictionaryMap.put(cfg, dictionary);
		return dictionary; 
	}
	protected static class DicContext {
		
	}
}
