package org.wltea.analyzer.dic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.consts.ConfigConsts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 工厂类根据配置生成Dictionary
 * 
 * @author gaoj
 *
 */
public class DistributedDictionaryFactory {
	private static DistributedDictionaryFactory instance;
	final static String CONF_ROOT="/solr/ik";
	ZkClient zkClient = new ZkClient(ConfigConsts.getInstance().getZkHost(), 10000, 10000, new SerializableSerializer());
	static Map<Configuration, Dictionary> dictionaryMap = new HashMap<>();
	// 每个config对应的词典仓库(会有冗余，后期优化)
	Map<Integer, Set<String>> dicRepo = new HashMap<>();
	Map<String, Set<String>> pullDicCache = new HashMap<>();// 提交缓存

	private DistributedDictionaryFactory() {
		init();
	}

	public static DistributedDictionaryFactory getInstance() {
		if (instance == null) {
			synchronized (DistributedDictionaryFactory.class) {
				if (instance == null) {
					instance = new DistributedDictionaryFactory();
				}
			}
		}
		return instance;
	}

	private void init() {
		// 如果节点不存在，则创建
		if (!zkClient.exists(CONF_ROOT)) {
			zkClient.createPersistent(CONF_ROOT, true);
		}
		zkClient.subscribeChildChanges(CONF_ROOT, new IZkChildListener() {

			@Override
			public void handleChildChange(String arg0, List<String> arg1) throws Exception {
				// 监听远程词典，同步变化
				System.out.println(arg0 + "词库更新！" + arg1);
			}

		});
	}

	// 要删除的词库=repoDic-pullDicCache
	public void reomveWords(Configuration cfg, Set<String> repoDic, Set<String> pullDicCache) {
		Set<String> differenceSet = new HashSet<String>();
		for (String ele : repoDic) {
			if (!pullDicCache.contains(ele))
				differenceSet.add(ele);
			if (differenceSet.size() > 1000) {
				dictionaryMap.get(cfg).disableWords(differenceSet);
				differenceSet.clear();
			}
		}
		dictionaryMap.get(cfg).disableWords(differenceSet);
	}

	// 要增加的词库=pullDicCache-repoDic
	public void addWords(Configuration cfg, Set<String> repoDic, Set<String> pullDicCache) {
		Set<String> differenceSet = new HashSet<String>();
		for (String ele : pullDicCache) {
			if (!repoDic.contains(ele))
				differenceSet.add(ele);
			if (differenceSet.size() > 1000) {
				dictionaryMap.get(cfg).addWords(differenceSet);
				differenceSet.clear();
			}
		}
		dictionaryMap.get(cfg).addWords(differenceSet);
	}

	@Deprecated
	public Set<String> getWordsNeedToBeRemoved(Set<String> repoDic, Set<String> pullDicCache) {
		Set<String> differenceSet = new HashSet<String>();
		for (String ele : pullDicCache) {
			if (!repoDic.contains(ele))
				repoDic.add(ele);
			if (repoDic.size() > 1000)
				;

		}
		repoDic.removeAll(pullDicCache);
		return repoDic;
	}

	@Deprecated
	public void getWordsNeedToBeAdded(String distributedDicPath, Set<String> pullDicCache) {

	}

	public void reloadDistributedDic(Configuration cfg, Object dicData) {
		String dicDataStr=(String) dicData;
		String[] lines=dicDataStr.split("\r\n");
		Set<String> pullDicCache = new HashSet<String>();
		for (int i = 0; i <= (lines.length - 1); i++) {
			String word = lines[i];
			pullDicCache.add(word);
		}
		if (!dicRepo.containsKey(cfg.hashCode()))
			dicRepo.put(cfg.hashCode(), new HashSet<String>());
		Set<String> repoDic = dicRepo.get(cfg.hashCode());
		reomveWords(cfg, repoDic, pullDicCache);
		addWords(cfg, repoDic, pullDicCache);
		// 将仓库指针指向提交
		repoDic = null;
		dicRepo.put(cfg.hashCode(), pullDicCache);
		pullDicCache = null;
	}

	/**
	 * 根据配置在zk上监听一个dic节点
	 */
	private void createDicNode(Configuration cfg) {
		String path = cfg.getDistributedDic();
		// 如果节点不存在，则创建
		if (!zkClient.exists(path)) {
			zkClient.createPersistent(path, "[]");
		} else {
			Object data = zkClient.readData(path);
			reloadDistributedDic(cfg, data);
		}
		/**
		 * 监听节点数据的变化，子节点数据变化不会监听到
		 */
		zkClient.subscribeDataChanges(path, new IZkDataListener() {
			// 数据变化时触发
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println(dataPath + ":" + data);
				reloadDistributedDic(cfg, data);
			}

			// 节点删除时触发
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {

			}
		});
	}

	public Dictionary getDictionary(Configuration cfg) {
		if (dictionaryMap.containsKey(cfg))
			return dictionaryMap.get(cfg);
		Dictionary dictionary = new Dictionary(cfg);
		dictionaryMap.put(cfg, dictionary);
		if (null != (cfg.getDistributedDic()))
			createDicNode(cfg);
		return dictionary;
	}
}
