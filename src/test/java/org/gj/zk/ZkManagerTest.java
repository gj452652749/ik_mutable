package org.gj.zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class ZkManagerTest {
	String zkHosts="10.0.34.10:2181";
	ZkClient zkClient = new ZkClient(zkHosts,10000,10000,new SerializableSerializer());
	@Test
	public void createNode() {
		String path = "/solr1/zk";  
		JSONArray dic=new JSONArray();
		dic.add("有象");
		dic.add("视频");
		//true:父节点不存在则创建
        zkClient.createPersistent(path, true);
        zkClient.writeData(path, dic.toJSONString());  
	}
	@Test
	public void deleteNode() {
		String path = "/solr/ik/thireedic12.dic";  
		zkClient.delete(path);
	}
	@Test
	public void appendData() {
		String path = "/solr/ik/thireedic12.dic";  
		Object data=zkClient.readData(path);
		JSONArray dic=JSONArray.parseArray((String) data);
		dic.add("合适的");
		dic.add("let's");
        zkClient.writeData(path, dic.toJSONString());  
	}
	@Test
	public void getNodeData() {
		String path = "/tokenizer/dic/1/synonym/goods-syn.dic";  
		Object data=zkClient.readData(path);
		System.out.println("get data:"+data);
		path = "/tokenizer/dic/1/common/goods";  
		data=zkClient.readData(path);
		System.out.println("get data:"+data);
	}

}
