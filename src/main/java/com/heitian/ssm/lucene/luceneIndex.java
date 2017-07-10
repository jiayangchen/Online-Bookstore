package com.heitian.ssm.lucene;

/**
 * Created by dl on 2017/6/15.
 */
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class luceneIndex {

    private Directory dir=null;
    public luceneIndex(){}
    public luceneIndex(List<Book> books){
        try {
            IndexWriter writer=getWriter();
            writer.deleteAll();
            writer.close();
        for(int i=0;i<books.size();i++) {
            System.out.print(books.get(i));
            addIndex(books.get(i));
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private IndexWriter getWriter()throws Exception{

        dir= FSDirectory.open(Paths.get("D://lucene"));
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(dir, iwc);
        return writer;
    }
    public void addIndex(Book book)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(book.getBid()), Field.Store.YES));
        doc.add(new TextField("bookname", book.getbName(), Field.Store.YES));
        doc.add(new TextField("description",book.getbDiscr(), Field.Store.YES));
        doc.add(new TextField("bookauthor", book.getbAuthor(), Field.Store.YES));
        doc.add(new TextField("bookcate", book.getbCategory(), Field.Store.YES));
        doc.add(new TextField("bookprice", String.valueOf(book.getbPrice()), Field.Store.YES));
        doc.add(new TextField("bookquan", String.valueOf(book.getbQuantity()), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }
    public void updateIndex(Book book)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(book.getBid()), Field.Store.YES));
        doc.add(new TextField("bookname", book.getbName(), Field.Store.YES));
        doc.add(new TextField("bookauthor", book.getbAuthor(), Field.Store.YES));
        doc.add(new TextField("bookcate", book.getbCategory(), Field.Store.YES));
        doc.add(new TextField("bookprice", String.valueOf(book.getbPrice()), Field.Store.YES));
        doc.add(new TextField("description",book.getbDiscr(), Field.Store.YES));
        doc.add(new TextField("bookquan", String.valueOf(book.getbQuantity()), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(book.getBid())), doc);
        writer.close();
    }
    public void deleteIndex(String id)throws Exception{
        IndexWriter writer=getWriter();
        writer.deleteDocuments(new Term("id", id));
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }
    public List<Book> search(String q)throws Exception{
        dir= FSDirectory.open(Paths.get("D://lucene"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is=new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        QueryParser parser=new QueryParser("description",analyzer);
        Query query=parser.parse(q);
        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        TopDocs hits=is.search(booleanQuery.build(), 100);
        QueryScorer scorer=new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);

        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='black'>","</font></b>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<Book> bookList=new LinkedList<>();
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            Book book=new Book();
            book.setBid((long) Integer.parseInt(doc.get(("id"))));
            book.setbDiscr(doc.get(("description")));
            book.setbName(doc.get("bookname"));
            book.setbAuthor(doc.get("bookauthor"));
            book.setbCategory(doc.get("bookcate"));
            book.setbQuantity(Integer.parseInt(doc.get("bookquan")));
            book.setbPrice(Double.parseDouble(doc.get("bookprice")));
            String description=doc.get("description");
            if(description!=null){
                TokenStream tokenStream = analyzer.tokenStream("description", new StringReader(description));
                String hContent=highlighter.getBestFragment(tokenStream, description);
                book.setbDiscr(hContent);
            }
            bookList.add(book);
        }
        reader.close();
        return bookList;
    }
}

