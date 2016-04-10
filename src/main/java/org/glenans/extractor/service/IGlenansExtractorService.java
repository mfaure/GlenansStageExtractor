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
     * @throws java.lang.InterruptedException
     */
    Collection<Stage> launchExtractor() throws InterruptedException;
    
}
