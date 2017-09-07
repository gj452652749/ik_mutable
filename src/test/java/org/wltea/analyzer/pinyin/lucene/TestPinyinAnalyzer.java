package org.wltea.analyzer.pinyin.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.pinyin.lucene.PinyinAnalyzer;
import org.wltea.analyzer.utils.AnalyzerUtils;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/24 19:25
 * 测试拼音分词器
 */
public class TestPinyinAnalyzer {
    public static void main(String[] args) throws IOException {
        String text = "金诚抉择？";
        Analyzer analyzer = new PinyinAnalyzer();
        AnalyzerUtils.displayTokens(analyzer, text);
    }
}
