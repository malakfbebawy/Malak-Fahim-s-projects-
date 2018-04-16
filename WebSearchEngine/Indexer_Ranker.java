package WebSearchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.nodes.Document;

public class Indexer_Ranker {
    int numofDocs=0;
    Map<String, Document> dataMap;
    queryManager Q;
    int targetDatabase;
    
    Map<String, ArrayList<String>> pages;
    Map<String, Integer> link_counter;
    Map<String, Double> pg_rank;    
    ArrayList<String> temp_arrList;
    private final double default_rank;
    private double rank_mainPage;
    private final int max_numPages;
    private final double damping_parameter = 0.7;
    private final int MAX_ITERATIONS = 20000;
    
    
    public Indexer_Ranker(Map<String, ArrayList<String>> InLinks, Map<String, Integer> OutDegree,int _max) {
        max_numPages = _max;
        Q = new queryManager();
        pages = InLinks;
        pg_rank = new HashMap<>();
        link_counter = OutDegree;
        temp_arrList = new ArrayList<>();
        default_rank = (1.0 - damping_parameter) * (1.0 / max_numPages);
        
        for (Map.Entry<String, ArrayList<String>> entry : pages.entrySet()) {
            pg_rank.put(entry.getKey(), default_rank);
            temp_arrList = entry.getValue();
            for (int i = 0; i < temp_arrList.size(); i++) {
                pg_rank.put(temp_arrList.get(i), default_rank);
            }
        }
    }

    void setTarget() {
        targetDatabase = (Q.getCountDocument1() == 0) ? 1 : 2;
        System.out.println("DB #" + targetDatabase + " id the chosen for now");
    }

    public void setDataMap(Map<String, Document> m) {
        this.dataMap = m;
    }

    public void Execute_Indexer() {
        PorterStemmer PS = new PorterStemmer();
        long doc_id;

        int progress = 0;
        ComplexInsert stmt = new ComplexInsert(targetDatabase);

        for (Map.Entry<String, Document> entry : dataMap.entrySet()) {            
            Q.insertIntoDocument(entry.getKey(), targetDatabase);
            doc_id = Q.getIdFromDocument(entry.getKey(), targetDatabase);
            ArrayList<String> res;
            ArrayList<String> phrases = new ArrayList<>();
            Q.insertTitle(doc_id,entry.getValue().title(),targetDatabase);
            
            String Text ="";
            if(entry.getValue().body()!= null) 
                Text += entry.getValue().body().text();
            if(entry.getValue().head()!= null)
                Text += ("."+entry.getValue().head().text());
            
            phrases.addAll(Arrays.asList(Text.split("\\.")));
            phrases.add(entry.getValue().title());
            
            res = PS.StemText(Text);
            stmt.add(doc_id, res);
            Q.insert_phrases_into_phrases_table(doc_id, phrases, targetDatabase);
            progress++;
            System.out.println("document#" + (Integer)(progress+(numofDocs*200)) + " has been served!");
        }
        stmt.Execute();
        stmt.clear();
        numofDocs++;
    }
    
    public void Execute_Ranker() {   
        for (int index = 0; index < MAX_ITERATIONS; index++) {
            for (Map.Entry<String, ArrayList<String>> entry : pages.entrySet()) {
                pg_rank.put(entry.getKey(), default_rank);
                temp_arrList = entry.getValue();
                rank_mainPage = pg_rank.get(entry.getKey());
                for (int i = 0; i < temp_arrList.size(); i++) {
                    rank_mainPage += damping_parameter * (pg_rank.get(temp_arrList.get(i)) / link_counter.get(temp_arrList.get(i)));
                }
                pg_rank.put(entry.getKey(), rank_mainPage);
            }
        }
        
        /*saving data to database*/
        long id;
        for (Map.Entry<String, Double> entry : pg_rank.entrySet()) {
            id = Q.getIdFromDocument(entry.getKey(), targetDatabase);
            Q.insertIntoRank(id,entry.getValue(),targetDatabase);
        }
    }

    void finish() {
        System.out.println("Deleting old DB#" + (3 - targetDatabase));
        Q.updateDatabase(targetDatabase);
        Q.deleteDB(3 - targetDatabase);
        System.out.println("Deleting Done");
    }
}
