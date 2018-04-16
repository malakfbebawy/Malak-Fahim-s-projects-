package WebSearchEngine;

import java.util.Scanner;

public class WebSearchEngineMain {

    public static void main(String[] args) {
        System.out.print("Enter number of threads: ");
        Scanner S = new Scanner(System.in);
        int threads = S.nextInt();
        Program crawler_and_indexer = new Program(threads);
        crawler_and_indexer.runSearchEngine();
    }
}