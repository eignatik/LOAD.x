package org.loadx.application.processor.tasks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.loadx.application.constants.CommonConstants;
import org.loadx.application.constants.LoadRequestFields;
import org.loadx.application.constants.LoadTaskFields;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.exceptions.MappingException;
import org.loadx.application.util.TimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MappingAndPersistingTask implements Task {

    private String json;

    /**
     * hidden constructor for builder purposes
     */
    private MappingAndPersistingTask(String json) {
        this.json = json;
    }

    public static MappingAndPersistingTaskBuilder createWithJson(String json) {
        return new MappingAndPersistingTaskBuilder(json);
    }

    @Override
    public void execute() {
        Map<String, Object> parsedTask = parse(json);

        // TODO: validate parsedTask

        // TODO: map to suitable objects
        LoadTask loadTask = new LoadTask();
        loadTask.setBaseUrl((String) parsedTask.get(LoadTaskFields.BASE_URL.getValue()));
        int loadingTime = TimeParser.parseTime((String) parsedTask.get(LoadTaskFields.LOADING_TIME.getValue()));
        loadTask.setLoadingTime(loadingTime);
        Integer threshold = (Integer) parsedTask.getOrDefault(
                LoadTaskFields.PARALLEL_THRESHOLD.getValue(),
                CommonConstants.DEFAULT_PARALLEL_THRESHOLD);
        loadTask.setParallelThreshold(threshold);

        Object requests = parsedTask.get(LoadTaskFields.REQUESTS.getValue());
        List<Object> parsedRequests = new ArrayList<>();
        if (requests instanceof List) {
            parsedRequests = (List<Object>) requests;
        }

        List<LoadRequest> loadRequests = parsedRequests.stream()
                .map(request -> {
                    Map<String, Object> requestParams = new HashMap<>();
                    if (request instanceof Map) {
                        requestParams = (Map<String, Object>) request;
                    }
                    return requestParams;
                })
                .map(request -> {
                    LoadRequest loadRequest = new LoadRequest();
                    loadRequest.setType((String) request.get(LoadRequestFields.TYPE.getValue()));
                    loadRequest.setTimeout((Integer) request.getOrDefault(
                            LoadRequestFields.TYPE.getValue(),
                            CommonConstants.DEFAULT_LOAD_REQUEST_TIMEOUT));
                    loadRequest.setUrl((String) request.get(LoadRequestFields.URL.getValue()));
                    return loadRequest;
                })
                .collect(Collectors.toList());


        // TODO: write to the database
    }

    private Map<String, Object> parse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map;

        try {
            map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new MappingException("", e);
        }

        return map;
    }

    public static class MappingAndPersistingTaskBuilder {
        private MappingAndPersistingTask task;

        private MappingAndPersistingTaskBuilder(String json) {
            task = new MappingAndPersistingTask(json);
        }

        public MappingAndPersistingTask build() {
            return task;
        }
    }

}
