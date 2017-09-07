package org.apache.lucene.analysis.ik;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.lucene.IKTokenizer;

/**
 * @author <a href="mailto:su.eugene@gmail.com">Eugene Su</a>
 */
public class IKTokenizerFactory extends TokenizerFactory{
  private boolean useSmart;
	
  public boolean useSmart() {
	return useSmart;
  }
	
  public void setUseSmart(boolean useSmart) {
    this.useSmart = useSmart;
  }
	
  public IKTokenizerFactory(Map<String,String> args) {
    super(args);
    useSmart = getBoolean(args, "useSmart", false); 
  }
  
  @Override
  public Tokenizer create(AttributeFactory factory) {
    Tokenizer _IKTokenizer = new IKTokenizer(factory , this.useSmart);
    return _IKTokenizer;
  }
}