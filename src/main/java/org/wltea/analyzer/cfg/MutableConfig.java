package org.wltea.analyzer.cfg;

import java.util.ArrayList;
import java.util.List;

public class MutableConfig implements Configuration{

	public List<String> ExtDictionarys=new ArrayList<>();
	private boolean useSmart;
	private static final String PATH_DIC_QUANTIFIER = "org/wltea/analyzer/dic/quantifier.dic";
	public MutableConfig(String dicFiles) {
		super();
		//使用;分割多个扩展字典配置
		String[] filePaths = dicFiles.split(";");
		if(filePaths != null){
			for(String filePath : filePaths){
				if(filePath != null && !"".equals(filePath.trim())){
					ExtDictionarys.add(filePath.trim());
				}
			}
		}
	}

	@Override
	public boolean useSmart() {
		// TODO Auto-generated method stub
		return this.useSmart;
	}

	@Override
	public void setUseSmart(boolean useSmart) {
		// TODO Auto-generated method stub
		this.useSmart=useSmart;
	}
	@Override
	public String getMainDictionary() {
		// TODO Auto-generated method stub
		return "null.dic";
	}

	@Override
	public String getQuantifierDicionary() {
		// TODO Auto-generated method stub
		return PATH_DIC_QUANTIFIER;
	}

	@Override
	public List<String> getExtDictionarys() {
		// TODO Auto-generated method stub
		return this.ExtDictionarys;
	}

	@Override
	public List<String> getExtStopWordDictionarys() {
		// TODO Auto-generated method stub
		return null;
	}


}
