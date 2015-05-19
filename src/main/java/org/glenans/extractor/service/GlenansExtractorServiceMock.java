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
 * Implementation of IGlenansExtractorService meant for development purpose
 * It reads all stages directly from a JSON file, thus avoiding the extraction
 * from the website (which can take a few minutes)
 * 
 * @author meskoj
 */
public class GlenansExtractorServiceMock implements IGlenansExtractorService {

    private static Logger LOGGER = Logger.getLogger(GlenansExtractorServiceMock.class);
    private static String JSON_FILE_PATH = "/home/mfaure/Documents/Perso/Perso-0permanent/GITHUB_Glenans_Stage_Extractor/GlenansStageExtractor/src/main/resources/stagesP.json";
            
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
