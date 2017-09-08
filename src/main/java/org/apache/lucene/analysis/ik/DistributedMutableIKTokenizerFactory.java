package org.apache.lucene.analysis.ik;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.lucene.IKTokenizer;

/**
 * @author <a href="mailto:su.eugene@gmail.com">Eugene Su</a>
 */
public class DistributedMutableIKTokenizerFactory extends TokenizerFactory {
	private boolean useSmart;
	private String dicFiles;
	private String distributedDicPath;

	public boolean useSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	public DistributedMutableIKTokenizerFactory(Map<String, String> args) {
		super(args);
		useSmart = getBoolean(args, "useSmart", false);
		dicFiles = get(args, "dicFiles");
		distributedDicPath = get(args, "distributedDicPath");
	}

	@Override
	public Tokenizer create(AttributeFactory factory) {
		Tokenizer _IKTokenizer = new IKTokenizer(factory, this.useSmart, dicFiles, distributedDicPath);
		return _IKTokenizer;
	}
}