package org.wltea.analyzer.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;

public class IkTest {
	@Test
	public void analyse() {
		//构建IK分词器，使用smart分词模式
		Analyzer analyzer = new IKAnalyzer(true,"threeleveladministrativedivisions.dic","/solr/ik/yxdistrbuted.dic");
		getToken(analyzer);
		//构建IK分词器，使用smart分词模式
		Analyzer analyzer1 = new IKAnalyzer(false,"threeleveladministrativedivisions.dic","/solr/ik/yxdistrbuted.dic");
		getToken(analyzer1);
	}
	public void getToken(Analyzer analyzer) {

		
				//获取Lucene的TokenStream对象
			    TokenStream ts = null;
				try {
					ts = analyzer.tokenStream("myfield", new StringReader(",,a,,武汉市武昌区中国鐘合适的中國壹十个123一个红色的士多啤棃是紅色的ａｚ ＡＺ  ０９•"));
					//获取词元位置属性
				    OffsetAttribute  offset = ts.addAttribute(OffsetAttribute.class); 
				    //获取词元文本属性
				    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
				    //获取词元文本属性
				    TypeAttribute type = ts.addAttribute(TypeAttribute.class);				    				    
				    //重置TokenStream（重置StringReader）
					ts.reset(); 
					//迭代获取分词结果
					while (ts.incrementToken()) {
					  System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
					}
					//关闭TokenStream（关闭StringReader）
					ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					//释放TokenStream的所有资源
					if(ts != null){
				      try {
						ts.close();
				      } catch (IOException e) {
						e.printStackTrace();
				      }
					}
			    }
	
	}
	@Test
	public void getDistributedDivisionTokens() {
		//构建IK分词器，使用smart分词模式
				Analyzer analyzer = new IKAnalyzer(false,"threeleveladministrativedivisions.dic","yxdistrbuted.dic");
				
				//获取Lucene的TokenStream对象
			    TokenStream ts = null;
				try {
					ts = analyzer.tokenStream("myfield", new StringReader(",,a,,武汉市武昌区中国鐘合适的中國壹十个123一个红色的士多啤棃是紅色的ａｚ ＡＺ  ０９•"));
					//获取词元位置属性
				    OffsetAttribute  offset = ts.addAttribute(OffsetAttribute.class); 
				    //获取词元文本属性
				    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
				    //获取词元文本属性
				    TypeAttribute type = ts.addAttribute(TypeAttribute.class);				    				    
				    //重置TokenStream（重置StringReader）
					ts.reset(); 
					//迭代获取分词结果
					while (ts.incrementToken()) {
					  System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
					}
					//关闭TokenStream（关闭StringReader）
					ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					//释放TokenStream的所有资源
					if(ts != null){
				      try {
						ts.close();
				      } catch (IOException e) {
						e.printStackTrace();
				      }
					}
			    }
	}
	@Test
	public void getDivisionTokens() {
		//构建IK分词器，使用smart分词模式
				Analyzer analyzer = new IKAnalyzer(true,"threeleveladministrativedivisions.dic");
				
				//获取Lucene的TokenStream对象
			    TokenStream ts = null;
				try {
					ts = analyzer.tokenStream("myfield", new StringReader(",,a,,武汉市武昌区中国鐘中國壹十个123一个红色的士多啤棃是紅色的ａｚ ＡＺ  ０９•"));
					//获取词元位置属性
				    OffsetAttribute  offset = ts.addAttribute(OffsetAttribute.class); 
				    //获取词元文本属性
				    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
				    //获取词元文本属性
				    TypeAttribute type = ts.addAttribute(TypeAttribute.class);				    				    
				    //重置TokenStream（重置StringReader）
					ts.reset(); 
					//迭代获取分词结果
					while (ts.incrementToken()) {
					  System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
					}
					//关闭TokenStream（关闭StringReader）
					ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					//释放TokenStream的所有资源
					if(ts != null){
				      try {
						ts.close();
				      } catch (IOException e) {
						e.printStackTrace();
				      }
					}
			    }
	}
	@Test
	public void getTokens() {
		//构建IK分词器，使用smart分词模式
				Analyzer analyzer = new IKAnalyzer(true);
				
				//获取Lucene的TokenStream对象
			    TokenStream ts = null;
				try {
					ts = analyzer.tokenStream("myfield", new StringReader(",,a,,中国鐘中國壹十个123一个红色的士多啤棃是紅色的ａｚ ＡＺ  ０９•"));
					//获取词元位置属性
				    OffsetAttribute  offset = ts.addAttribute(OffsetAttribute.class); 
				    //获取词元文本属性
				    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
				    //获取词元文本属性
				    TypeAttribute type = ts.addAttribute(TypeAttribute.class);				    				    
				    //重置TokenStream（重置StringReader）
					ts.reset(); 
					//迭代获取分词结果
					while (ts.incrementToken()) {
					  System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
					}
					//关闭TokenStream（关闭StringReader）
					ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					//释放TokenStream的所有资源
					if(ts != null){
				      try {
						ts.close();
				      } catch (IOException e) {
						e.printStackTrace();
				      }
					}
			    }
	}
}
