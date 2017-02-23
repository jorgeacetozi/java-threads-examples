package webcrawler.forkjoin;

/**
 * The class that will start the process of crawling the web.
 */

public class App {

    public static void main(String[] args) {
        WebLink webLink = new WebLink("http://www.google.com");
        webLink.startCrawling();
    }
}