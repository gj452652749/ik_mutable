# ik_mutable

兼容旧版，可以同时存在不同词库的多个ik分词器  
配置示例：
```
<fieldType name="text_ik" class="solr.TextField">
        <analyzer type="index">
            <tokenizer class="org.apache.lucene.analysis.ik.IKTokenizerFactory" useSmart="false"/>
            <filter class="com.pycredit.solr.filter.PYSynonymFilterFactory" useSynonym="true"/>
        </analyzer>
        <analyzer type="query">
            <tokenizer class="org.apache.lucene.analysis.ik.IKTokenizerFactory" useSmart="true"/>
        </analyzer>
    </fieldType>
	<fieldType name="text_ik_mutable" class="solr.TextField">
        <analyzer type="index">
            <tokenizer class="org.apache.lucene.analysis.ik.MutableIKTokenizerFactory" useSmart="false" dicFiles="threeleveladministrativedivisions.dic"/>
            <filter class="com.pycredit.solr.filter.IgnoreCharFilterFactory" ignoreCharCase="1"/>
        </analyzer>
        <analyzer type="query">
            <tokenizer class="org.apache.lucene.analysis.ik.MutableIKTokenizerFactory" useSmart="true" dicFiles="threeleveladministrativedivisions.dic"/>
			<filter class="com.pycredit.solr.filter.IgnoreCharFilterFactory" ignoreCharCase="1"/>
        </analyzer>
    </fieldType>
```
