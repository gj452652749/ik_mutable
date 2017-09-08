package org.wltea.analyzer.dic;

import java.util.ArrayList;
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
	String zkHosts = "127.0.0.1:2181";
	ZkClient zkClient = new ZkClient(zkHosts, 10000, 10000, new SerializableSerializer());
	static Map<Configuration, Dictionary> dictionaryMap = new HashMap<>();
	static String REMOTE_DIC_ROOT = "/solr/ik";
	Map<String, Set<String>> dicRepo = new HashMap<>();// 词典仓库
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
		zkClient.subscribeChildChanges("/solr/ik", new IZkChildListener() {

			@Override
			public void handleChildChange(String arg0, List<String> arg1) throws Exception {
				// 监听远程词典，同步变化
				System.out.println(arg0 + "词库更新！" + arg1);
			}

		});
	}
	public void reomveWords(Configuration cfg, Set<String> pullDicCache) {
		String distributedDicPath=REMOTE_DIC_ROOT + "/" + cfg.getDistributedDic();
		Set<String> repoDic=dicRepo.get(distributedDicPath);
		Set<String> differenceSet=new HashSet<String>();
		for(String ele:pullDicCache) {
			if(!repoDic.contains(ele))
				differenceSet.add(ele);
			if(differenceSet.size()>1000) {
				dictionaryMap.get(cfg).disableWords(differenceSet);
				differenceSet.clear();
			}
		}
		dictionaryMap.get(cfg).disableWords(repoDic);
	}
	public void addWords(Configuration cfg, Set<String> pullDicCache) {
		String distributedDicPath=REMOTE_DIC_ROOT + "/" + cfg.getDistributedDic();
		Set<String> repoDic=dicRepo.get(distributedDicPath);
		Set<String> differenceSet=new HashSet<String>();
		for(String ele:repoDic) {
			if(!pullDicCache.contains(ele))
				differenceSet.add(ele);
			if(differenceSet.size()>1000) {
				dictionaryMap.get(cfg).disableWords(differenceSet);
				differenceSet.clear();
			}
		}
		dictionaryMap.get(cfg).disableWords(repoDic);
	}
	@Deprecated
	public Set<String> getWordsNeedToBeRemoved(String distributedDicPath, Set<String> pullDicCache) {
		Set<String> repoDic=dicRepo.get(distributedDicPath);
		Set<String> differenceSet=new HashSet<String>();
		for(String ele:pullDicCache) {
			if(!repoDic.contains(ele))
				repoDic.add(ele);
			if(repoDic.size()>1000);
				
		}
		repoDic.removeAll(pullDicCache);
		return repoDic;
	}
	@Deprecated
	public void getWordsNeedToBeAdded(String distributedDicPath, Set<String> pullDicCache) {
		
	}
	public void reloadDistributedDic(Configuration cfg, Object dicData) {
		JSONObject jsonObj = JSON.parseObject((String) dicData);
		JSONArray jsonArray = jsonObj.getJSONArray("data");
		Set<String> pullDicCache =new HashSet<String>();
		List<String> list = new ArrayList<>();
		for (int i = 0; i <= (jsonArray.size() - 1); i++) {
			String word = jsonArray.getString(i);
			pullDicCache.add(word);
			list.add(word);
			// 每1000个词批量添加一次
			if (list.size() > 1000) {
				dictionaryMap.get(cfg).addWords(list);
				list.clear();
			}
		}
		dictionaryMap.get(cfg).addWords(list);
	}

	/**
	 * 根据配置在zk上监听一个dic节点
	 */
	private void createDicNode(Configuration cfg) {
		String path = REMOTE_DIC_ROOT + "/" + cfg.getDistributedDic();
		// 如果节点不存在，则创建
		if (!zkClient.exists(path)) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("cfgCode", cfg.hashCode());
			jsonObj.put("data", "[]");
			zkClient.createPersistent(path, jsonObj.toJSONString());
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
