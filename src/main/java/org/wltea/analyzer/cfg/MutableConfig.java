package org.wltea.analyzer.cfg;

import java.util.ArrayList;
import java.util.List;

public class MutableConfig implements Configuration{

	public List<String> ExtDictionarys=new ArrayList<>();
	public String distributedDicPath=null;
	private boolean useSmart;
	private static final String PATH_DIC_QUANTIFIER = "org/wltea/analyzer/dic/quantifier.dic";
	String extDicFiles;
	public MutableConfig(String extDicFiles) {
		super();
		initExtDic(extDicFiles);
	}
	public MutableConfig(String extDicFiles,String distributedDicPath) {
		super();
		initExtDic(extDicFiles);
		this.distributedDicPath=distributedDicPath;
	}
	private void initExtDic(String extDicFiles) {
		if(null==extDicFiles)
			return;
		this.extDicFiles=extDicFiles;
		//使用;分割多个扩展字典配置
		String[] filePaths = extDicFiles.split(";");
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
		return "null.dic";
	}

	@Override
	public List<String> getExtDictionarys() {
		// TODO Auto-generated method stub
		return ExtDictionarys;
	}

	@Override
	public List<String> getExtStopWordDictionarys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (this.extDicFiles+":"+this.distributedDicPath+":"+this.useSmart).hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.extDicFiles.replaceAll("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]", "_")+"_"+this.useSmart;
	}
	@Override
	public String getDistributedDic() {
		// TODO Auto-generated method stub
		return distributedDicPath;
	}


}
