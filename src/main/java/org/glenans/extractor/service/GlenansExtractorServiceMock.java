package org.glenans.extractor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.glenans.extractor.model.Stage;

/**
 *
 * @author meskoj
 */
public class GlenansExtractorServiceMock implements IGlenansExtractorService {

    private static Logger LOGGER = Logger.getLogger(GlenansExtractorServiceMock.class);
    private static String JSON_FILE_PATH = "$My_Path";
            
    @Override
    public Collection<Stage> launchExtractor() throws InterruptedException {
        Collection<Stage> stages = new ArrayList<Stage>();
        try {
            String json = FileUtils.readFileToString(new File(JSON_FILE_PATH));
            ObjectMapper objectMapper = new ObjectMapper();
            stages = objectMapper.readValue(json, new TypeReference<ArrayList<Stage>>(){});
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GlenansExtractorServiceMock.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return stages;
        }
    }

}
