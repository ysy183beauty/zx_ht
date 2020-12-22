package com.szbase.credit.util;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class lucenesearch {
    static Analyzer analyzer = null;
    static Directory directory = null;
    // lucene版本
    private static Version version = Version.LUCENE_30;
    // 词库(maven 下载的jar包中不包含词库)
  //  private static String path = "C:\\Users\\Administrator\\Desktop\\lucene demo\\mmseg4j-1.8.3\\data";

    public static void main(String[] args) throws Exception {
    	List<NameValuePair> params1 = new ArrayList<NameValuePair>();

		params1.add(new BasicNameValuePair("param_xxlb", "G"));
		params1.add(new BasicNameValuePair("start", "0"));
		params1.add(new BasicNameValuePair("limit", "50"));
		List<NameValuePair> params2 = new ArrayList<NameValuePair>();

		params2.add(new BasicNameValuePair("param_xxlb", "R"));
		params2.add(new BasicNameValuePair("start", "0"));
		params2.add(new BasicNameValuePair("limit", "50"));
		List<NameValuePair> params3 = new ArrayList<NameValuePair>();

		params3.add(new BasicNameValuePair("param_xxlb", "B"));
		params3.add(new BasicNameValuePair("start", "0"));
		params3.add(new BasicNameValuePair("limit", "50"));
		String str1 =	HttpClientUtil.readInterfacePost("http://172.16.146.21:8080/service/api/credit/hhbxxPublishHandler/getHhbxxPageList", params1);
		String str2 =	HttpClientUtil.readInterfacePost("http://172.16.146.21:8080/service/api/credit/hhbxxPublishHandler/getHhbxxPageList", params2);
     	String str3 =	HttpClientUtil.readInterfacePost("http://172.16.146.21:8080/service/api/credit/hhbxxPublishHandler/getHhbxxPageList", params3);
//        analyzer = new ComplexAnalyzer();
        // 默认读取jar包中的词库
//        analyzer = new ComplexAnalyzer("path");
     	System.out.println(str1);
     	System.out.println(str2);
     	System.out.println(str3);
	    JSONObject json1 = new  JSONObject(str1);
	    JSONArray JSON1 = json1.getJSONArray("list");
	    JSONObject json2 = new  JSONObject(str2);
	    JSONArray JSON2 = json2.getJSONArray("list");
	    JSONObject json3 = new  JSONObject(str3);
	    JSONArray JSON3 = json3.getJSONArray("list");
      //  analyzer = new MMSegAnalyzer(path);
        directory =  new SimpleFSDirectory(new File("D:\\6565\\lucene"));
        write(JSON1,JSON2,JSON3);
        System.out.println(search("企业"));
    }

    /**
     * 简单分词
     *
     * @throws Exception
     */
    public static void write(JSONArray JSON_G ,JSONArray JSON_R ,JSONArray JSON_B ) throws Exception {
     //  IndexWriterConfig iwConfig = new IndexWriterConfig(version, analyzer);
      //  iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter iwriter = new IndexWriter(directory, new MMSegAnalyzer(), true, IndexWriter.MaxFieldLength.LIMITED);
        //清空索引
        iwriter.deleteAll();
        
        for(int i=0;i<JSON_G.length();i++){
            Document doc = new Document();
            String url = "/credit/credit_publity/creditPublityView?paramId="+JSON_G.getJSONObject(i).getString("id")+"&start=0&limit=20&type=2&first=true";
            doc.add(new Field("xxlywbj", JSON_G.getJSONObject(i).getString("xxlywbj"), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("xxmc", JSON_G.getJSONObject(i).getString("xxmc"), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("fwcs", JSON_G.getJSONObject(i).get("visits").toString(), Field.Store.YES, Field.Index.ANALYZED));
            Date d = new Date((Long) JSON_G.getJSONObject(i).get("fbsj") );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            doc.add(new Field("ssxm", "公示信息", Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("fbsj", sdf.format(d), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("URL", url, Field.Store.YES, Field.Index.ANALYZED));
            iwriter.addDocument(doc);
           } 
        for(int i=0;i<JSON_R.length();i++){
            Document doc = new Document();
            String url = "/credit/credit_publity/creditPublityView?paramId="+JSON_R.getJSONObject(i).getString("id")+"&start=0&limit=20&type=2&first=true";
            doc.add(new Field("xxlywbj", JSON_R.getJSONObject(i).getString("xxlywbj"), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("xxmc", JSON_R.getJSONObject(i).getString("xxmc"), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("fwcs", JSON_R.getJSONObject(i).get("visits").toString(), Field.Store.YES, Field.Index.ANALYZED));
            Date d = new Date((Long) JSON_R.getJSONObject(i).get("fbsj") );
            doc.add(new Field("ssxm", "红榜信息", Field.Store.YES, Field.Index.ANALYZED));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            doc.add(new Field("fbsj", sdf.format(d), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("URL", url, Field.Store.YES, Field.Index.ANALYZED));
            iwriter.addDocument(doc);
           }
       for(int i=0;i<JSON_B.length();i++){
        Document doc = new Document();
        String url = "/credit/credit_publity/creditPublityView?paramId="+JSON_B.getJSONObject(i).getString("id")+"&start=0&limit=20&type=2&first=true";
        doc.add(new Field("xxlywbj", JSON_B.getJSONObject(i).getString("xxlywbj"), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("xxmc", JSON_B.getJSONObject(i).getString("xxmc"), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("fwcs", JSON_B.getJSONObject(i).get("visits").toString(), Field.Store.YES, Field.Index.ANALYZED));
        Date d = new Date((Long) JSON_B.getJSONObject(i).get("fbsj") );
        doc.add(new Field("ssxm", "黑榜信息", Field.Store.YES, Field.Index.ANALYZED));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        doc.add(new Field("fbsj", sdf.format(d), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("URL", url, Field.Store.YES, Field.Index.ANALYZED));
        iwriter.addDocument(doc);
       }
        iwriter.commit();
        iwriter.close();
    }

    public static String search(String querystr) throws Exception {
    	 directory =  new SimpleFSDirectory(new File("D:\\6565\\lucene"));
        IndexReader ireader = IndexReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(ireader);
        QueryParser qp = new QueryParser(version, "xxmc", new MMSegAnalyzer());
        Query q = qp.parse(querystr);
        System.out.println("q="+q);
        TopDocs tds = searcher.search(q, 10);
        List<Object> list = new ArrayList<Object>();
        System.out.println("======size:" + tds.totalHits + "========");
        for (ScoreDoc sd : tds.scoreDocs) {
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("score", sd.score);
        	map.put("xxlywbj", searcher.doc(sd.doc).get("xxlywbj"));
        	map.put("xxmc", searcher.doc(sd.doc).get("xxmc"));
        	map.put("fwcs", searcher.doc(sd.doc).get("fwcs"));
        	map.put("fbsj", searcher.doc(sd.doc).get("fbsj"));
        	map.put("ssxm", searcher.doc(sd.doc).get("ssxm"));
        	map.put("URL", searcher.doc(sd.doc).get("URL"));
        	list.add(map);
            System.out.println(sd.score);
            System.out.println(searcher.doc(sd.doc).get("xxlywbj"));
            System.out.println(searcher.doc(sd.doc).get("xxmc"));
            System.out.println(searcher.doc(sd.doc).get("fwcs"));
            System.out.println(searcher.doc(sd.doc).get("fbsj"));
            System.out.println(searcher.doc(sd.doc).get("ssxm"));
            System.out.println(searcher.doc(sd.doc).get("URL"));
        }
        if(list.isEmpty()){
        	return null;
        }
      return  com.alibaba.fastjson.JSONObject.toJSONString(list);
    }
}