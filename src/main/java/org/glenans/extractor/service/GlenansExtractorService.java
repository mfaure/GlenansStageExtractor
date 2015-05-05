package org.glenans.extractor.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.glenans.extractor.model.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 *
 * @author meskoj
 */
public class GlenansExtractorService implements IGlenansExtractorService {

    private static Logger LOGGER = Logger.getLogger(GlenansExtractorService.class);
    private static String FIREFOX_PATH = "/opt/firefox/firefox/";
    private static String URL_STAGES_P = "http://www.glenans.asso.fr/fr/pratiquer/stages-de-voile/rechercher-un-stage/moniteurs.html?search=1&fil_id=1&niv_id=9&start_date=Indiff%C3%A9rent&lan_id=1&site_id=0&submit=Rechercher&language=fr-FR&id=0";
            
    @Override
    public Collection<Stage> launchExtractor() throws InterruptedException {
        WebDriver webdriver = new FirefoxDriver(new FirefoxBinary(new File(FIREFOX_PATH)), new FirefoxProfile());
        Collection<Stage> stages = Collections.EMPTY_LIST;
        try {

            webdriver.get(URL_STAGES_P);

            int numberOfStage = webdriver.findElements(By.cssSelector(".top-box")).size();

            stages = new LinkedList<>();
            for (int index = 0; index < numberOfStage; index++) {
                stages.addAll(extractStageFromIndex(webdriver, index));
                webdriver.navigate().back();
                Thread.sleep(2000);
            }
            webdriver.close();
        } catch (WebDriverException wde) {
            System.out.println(wde.getMessage());

        } finally {
            webdriver.close();
            return stages;
        }

    }

    /**
     * 
     * @param webdriver
     * @param index
     * @return
     * @throws InterruptedException
     * @throws WebDriverException 
     */
    private Collection<Stage> extractStageFromIndex(WebDriver webdriver, int index) throws InterruptedException, WebDriverException {
        // click sur Bouton En savoir plus / inscription
        WebElement we = webdriver.findElements(By.cssSelector(".top-box")).get(index);
        we.findElement(By.tagName("form")).submit();
        Thread.sleep(5000);

        Collection<Stage> stagesByType = new ArrayList<>();
        String stageTitle = webdriver.findElement(By.tagName("h2")).getText();
        LOGGER.info("extracting data for stage " + stageTitle);
        // Parse toutes les tabs (ville) et click, puis parse du tableau 
        for (WebElement wec : webdriver.findElements(By.cssSelector(".tab.mlines"))) {
            
            String city = wec.getText();
            LOGGER.info("extracting data of city " + city);
            // click on the panel tab, to update the content of the table of sessions
            wec.click();
            stagesByType.addAll(extractStageFromLine(webdriver, stageTitle, city));
        }
        return stagesByType;

    }

    /**
     * 
     * @param webdriver
     * @param type
     * @param city
     * @return 
     */
    private Collection<Stage> extractStageFromLine(
            WebDriver webdriver, 
            String type, 
            String city) {
        Collection<Stage> stageByTypeAndCity = new ArrayList<>();
        for (WebElement info : webdriver.findElements(By.cssSelector(".tabs-content .table tbody tr"))) {
            if (StringUtils.isNotBlank(info.getText())) {
                StringBuilder strb = new StringBuilder();
                for (WebElement element : info.findElements(By.cssSelector("td"))) {
                    List<WebElement> childImgs = element.findElements(By.cssSelector("img"));
                    // parse all TD
                    if (childImgs.isEmpty() || StringUtils.isNotBlank(element.getText())) {
                        // Grab text inside TD
                        strb.append(element.getText().replaceAll("[\n\r]", " "));
                        strb.append(",");
                    } else {
                        LOGGER.info("img encountered with alt " + childImgs.iterator().next().getAttribute("alt"));
                        // IMG is child so grab ALT
                        strb.append(childImgs.iterator().next().getAttribute("alt").replaceAll("[\n\r]", " "));
                        strb.append(",");
                    }
                }
                String[] stageInfo = strb.toString().split(",");

                Stage stage = new Stage(
                                type,
                                city,
                                stageInfo[Stage.DURATION_INDEX],
                                stageInfo[Stage.FROM_DATE_INDEX],
                                stageInfo[Stage.TO_DATE_INDEX],
                                stageInfo[Stage.HOSTING_INDEX],
                                stageInfo[Stage.FLEET_INDEX],
                                stageInfo[Stage.LANGUAGE_INDEX],
                                stageInfo[Stage.AVAILABILITY_INDEX],
                                stageInfo[Stage.FOOD_INDEX],
                                stageInfo[Stage.PRICE_INDEX]);

                stageByTypeAndCity.add(stage);
            }
        }
        return stageByTypeAndCity;
    }
}
