package org.wltea.analyzer.consts;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigConsts {
	String zkHost;
	private static ConfigConsts instance;
	private ConfigConsts() {
		init();
	}

	public static ConfigConsts getInstance() {
		if (instance == null) {
			synchronized (ConfigConsts.class) {
				if (instance == null) {
					instance = new ConfigConsts();
				}
			}
		}
		return instance;
	}

	private void init() {
		Properties prop = new Properties();
        
        InputStream in = ConfigConsts.class.getClassLoader().getResourceAsStream(
                "context.properties");
        if(null==in)
        	in=ConfigConsts.class.getClassLoader().getResourceAsStream(
                    "standalone-context.properties");
        try {
			prop.load(in);
			zkHost=prop.getProperty("zk.hosts");
			System.out.println("zkHost地址："+zkHost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getZkHost() {
		return zkHost;
	}
	
}
