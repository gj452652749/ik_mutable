package org.wltea.analyzer.dic;

import java.io.IOException;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.junit.Test;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.MutableConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DistributedDictionaryFactoryTest {
	String zkHosts = "127.0.0.1:2181";
	ZkClient zkClient = new ZkClient(zkHosts, 10000, 10000, new SerializableSerializer());

	@Test
	public void addWords() {
		Configuration conf = new MutableConfig("yxmain.dic", "yxdistrbuted.dic");
		String path = DistributedDictionaryFactory.REMOTE_DIC_ROOT + "/" + conf.getDistributedDic();
		zkClient.delete(path);
		// 如果节点不存在，则创建
		if (!zkClient.exists(path)) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("cfgCode", "sss");
			jsonObj.put("data", "[]");
			zkClient.createPersistent(path, jsonObj.toJSONString());
		}

		Object data = zkClient.readData(path);
		JSONObject jsonObj = JSON.parseObject((String) data);
		JSONArray jsonArray = jsonObj.getJSONArray("data");
		jsonArray.add("境地");
		jsonArray.add("let's");
		jsonArray.add("合适的");
		jsonObj.put("data", jsonArray.toJSONString());
		zkClient.writeData(path, jsonObj.toJSONString());
	}
	@Test
	public void get() {
		Configuration conf = new MutableConfig("yxmain.dic", "yxdistrbuted.dic");
		String path = DistributedDictionaryFactory.REMOTE_DIC_ROOT + "/" + conf.getDistributedDic();
		// 如果节点不存在，则创建
//		if (!zkClient.exists(path)) {
//			JSONObject jsonObj = new JSONObject();
//			jsonObj.put("cfgCode", "sss");
//			jsonObj.put("data", "[]");
//			zkClient.createPersistent(path, jsonObj.toJSONString());
//		}

		Object data = zkClient.readData(path);
		System.out.println(data);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration conf = new MutableConfig("yxmain.dic", "yxdistrbuted.dic");
		DistributedDictionaryFactory.getInstance().getDictionary(conf);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
