package webcrawler.forkjoin;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

/**
 * Class that represents a web link that will eventually be visited by a {@link LinkVisitor}. It uses the Fork/Join Framework
 * to divide the work of visiting the links between a pool of threads, eventually merging all the results into a single one if we
 * wanted to.
 * 
 * PS: Since we are crawling the entire web, the program will virtually never stop running =)
 */

public final class WebLink {
    private static final Collection<WebLink> VISITED_LINKS = Collections.synchronizedSet(new HashSet<>());
    private final String url;
    private ForkJoinPool forkJoinPool;
    
    public WebLink(String url) {
        this.url = url;
        this.forkJoinPool = new ForkJoinPool();
    }
    
    public void startCrawling() {
    	// Here it's where all start, the LinkVisitor is a task that can be invoked by the ForkJoinPool.
        this.forkJoinPool.invoke(new LinkVisitor(this));
    }
    
    public String getUrl() {
        return url;
    }
    
    public boolean isVisited() {
        return VISITED_LINKS.contains(this);
    }
    
    public void markVisited() {
        VISITED_LINKS.add(this);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof WebLink))
            return false;
        WebLink other = (WebLink) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.url;
    }
}