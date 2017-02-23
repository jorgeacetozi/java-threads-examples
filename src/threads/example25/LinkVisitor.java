package webcrawler.forkjoin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that will dig into urls inside the given {@link WebLink} object. It extends {@link RecursiveAction}, a class that can
 * be processes by {@link ForkJoinPool} and will delegate a portion of its work to another thread if it not matches a specified
 * threshold. It will eventually await the result of the delegated part of its work to be completed before proceeding. 
 */

public class LinkVisitor extends RecursiveAction {
    private static final long serialVersionUID = 1L;
    
    private static final Set<WebLink> CACHE_FOR_LINKS = Collections.synchronizedSet(new HashSet<>());
    private final WebLink webLink;
    
    public LinkVisitor(WebLink webLink) {
        this.webLink = webLink;
    }

    @Override
    public void compute() {
        if(!this.webLink.isVisited()) {
            List<LinkVisitor> newLinkVisitors = new ArrayList<>();
            
            String currentUrl = this.webLink.getUrl();
            List<String> foundUrlsInsideTheLink = this.getLinksFromUrl(currentUrl);
            
            foundUrlsInsideTheLink.forEach(url -> {
            	WebLink newLink = new WebLink(url.trim());
            	
            	if(!newLink.isVisited() && !CACHE_FOR_LINKS.contains(newLink)) {
            		newLinkVisitors.add(new LinkVisitor(newLink));
            		CACHE_FOR_LINKS.add(newLink);
            		System.out.println("Website has been found with URL: " + url);
            	}
            });

            this.webLink.markVisited();
            invokeAll(newLinkVisitors);
        }
    }
    
    private List<String> getLinksFromUrl(String url) {
    	String rawHtml = this.extractHtmlFrom(url);
    	
    	Matcher matcher = Pattern.compile("http://(\\w+\\.)*(\\w+)").matcher(rawHtml);
    	List<String> urls = new ArrayList<>();
    	
    	while(matcher.find()) {
    		urls.add(matcher.group());
    	}
    	
    	return urls;
    }

    private String extractHtmlFrom(String url) {
        StringBuilder rawHtml = new StringBuilder();

        try {
            URL site = new URL(url);
            
            try(BufferedReader bf = new BufferedReader(new InputStreamReader(site.openStream()))) {
                String input = "";

                while((input = bf.readLine()) != null) {
                    rawHtml.append(input);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage()); // NEVER do this in real life!!!!!
        }

        return rawHtml.toString();
    }
}