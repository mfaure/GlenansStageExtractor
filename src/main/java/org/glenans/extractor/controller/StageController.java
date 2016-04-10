/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glenans.extractor.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.glenans.extractor.model.Stage;
import org.glenans.extractor.service.GlenansExtractorService;
import org.glenans.extractor.service.IGlenansExtractorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StageController {

    private static Logger LOGGER = Logger.getLogger(StageController.class);
    private Collection<Stage> stageList = Collections.EMPTY_LIST;
    // List of stages : Mockup via file (for development)
//    private IGlenansExtractorService glenansExtractor = new GlenansExtractorServiceMock();
    // List of stage : real extraction
    private IGlenansExtractorService glenansExtractor = new GlenansExtractorService();

    public StageController() {
        try {
            launchExtraction();
            LOGGER.info(stageList.size() + " stages loaded on constructor");
        } catch (InterruptedException ignored) {
        }
    }

    @RequestMapping("/stagesP")
    public Collection<Stage> stagesP(
            @RequestParam(required = false) final Collection<String> names,
            @RequestParam(required = false) final Collection<String> city,
            @RequestParam(required = false) final Integer duration) {
        return filterStage(new StagePredicate(names, city, duration));
    }

    @RequestMapping("/launchExtraction")
    public final String launchExtraction() throws InterruptedException {
        stageList = glenansExtractor.launchExtractor();
        return "OK";
    }

    /**
     * @param predicate : a predicate which can be one or many picked from: city, names, duration
     * @return
     */
    private Collection<Stage> filterStage(Predicate predicate) {
        return filterStage(stageList, predicate);
    }

    /**
     * @param stages
     * @param predicate : a predicate which can be one or many picked from: city, names, duration
     * @return
     */
    private Collection<Stage> filterStage(Collection<Stage> stages, Predicate predicate) {
        // copy all stages in a list because CollectionUtils.filter modifies the list
        Collection<Stage> stagesCopy = new ArrayList<>();
        stagesCopy.addAll(stages);
        CollectionUtils.filter(stagesCopy, predicate);
        return stagesCopy;
    }

    private class StagePredicate implements Predicate {

        private int duration = -1;
        private Collection<String> cities = Collections.EMPTY_LIST;
        private Collection<String> names = Collections.EMPTY_LIST;

        public StagePredicate(Integer duration) {
            this.duration = duration;
        }

        public StagePredicate(
                Collection<String> names,
                Collection<String> cities,
                Integer duration) {
            if (duration != null) {
                this.duration = duration;
            }
            this.names = names;
            this.cities = cities;
        }

        @Override
        public boolean evaluate(Object o) {
            Stage stage = ((Stage) o);
            return evaluateCity(stage)
                    && evaluateName(stage)
                    && evaluateDuration(stage);
        }

        private boolean evaluateCity(Stage stage) {
            return CollectionUtils.isEmpty(cities)
                    || CollectionUtils.containsAny(cities, Arrays.asList(stage.getCity()));
        }

        private boolean evaluateName(Stage stage) {
            return CollectionUtils.isEmpty(names)
                    || CollectionUtils.containsAny(names, Arrays.asList(stage.getName()));
        }

        private boolean evaluateDuration(Stage stage) {
            return duration == -1
                    || stage.getDuration() == duration;
        }
    }

}
