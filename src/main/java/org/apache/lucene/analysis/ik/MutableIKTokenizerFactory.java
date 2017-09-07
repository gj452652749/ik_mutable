package org.apache.lucene.analysis.ik;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.lucene.IKTokenizer;

/**
 * @author <a href="mailto:su.eugene@gmail.com">Eugene Su</a>
 */
public class MutableIKTokenizerFactory extends TokenizerFactory{
  private boolean useSmart;
  private String dicFiles;
  public boolean useSmart() {
	return useSmart;
  }
	
  public void setUseSmart(boolean useSmart) {
    this.useSmart = useSmart;
  }
	
  public MutableIKTokenizerFactory(Map<String,String> args) {
    super(args);
    useSmart = getBoolean(args, "useSmart", false); 
    dicFiles = get(args,"dicFiles");
  }
  
  @Override
  public Tokenizer create(AttributeFactory factory) {
    Tokenizer _IKTokenizer = new IKTokenizer(factory , this.useSmart,dicFiles);
    return _IKTokenizer;
  }
}