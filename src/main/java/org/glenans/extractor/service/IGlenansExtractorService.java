package org.glenans.extractor.service;

import java.util.Collection;
import org.glenans.extractor.model.Stage;

/**
 *
 * @author meskoj
 */
public interface IGlenansExtractorService {
    
          
    /**
     * 
     * @return 
     * @throws java.lang.InterruptedException 
     */
    public Collection<Stage> launchExtractor() throws InterruptedException;
    
}
