package org.loadx.application.processor.tasks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.loadx.application.constants.CommonConstants;
import org.loadx.application.constants.LoadRequestFields;
import org.loadx.application.constants.LoadTaskFields;
import org.loadx.application.db.LoadPersistent;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadxEntity;
import org.loadx.application.exceptions.MappingException;
import org.loadx.application.util.TimeParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class MappingAndPersistingTask implements Task<Void> {

    private String json;
    private LoadPersistent loadPersistent;

    private MappingAndPersistingTask() {
        // hidden constructor for builder purposes
    }

    static MappingAndPersistingTaskBuilder create() {
        return new MappingAndPersistingTaskBuilder();
    }

    @Override
    public CompletableFuture<Void> execute() {
        return CompletableFuture.runAsync(this::parse);
    }

    private void parse() {
        Map<String, Object> parsedTask = parseJsonToMap(json);

        // TODO: validate parsedTask

        int loadTaskId = loadPersistent.save(mapToLoadTask(parsedTask));
        List<Integer> addedRequestsIds = loadPersistent.save(mapToLoadRequests(parsedTask));
        loadPersistent.persistLoadTaskRequests(loadTaskId, addedRequestsIds);
    }

    private Map<String, Object> parseJsonToMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map;

        try {
            map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new MappingException("Failed to parse input json to map", e);
        }

        return map;
    }

    private LoadTask mapToLoadTask(Map<String, Object> parsedTask) {
        LoadTask loadTask = new LoadTask();
        loadTask.setBaseUrl((String) parsedTask.get(LoadTaskFields.BASE_URL.getValue()));
        int loadingTime = TimeParser.parseTime((String) parsedTask.get(LoadTaskFields.LOADING_TIME.getValue()));
        loadTask.setLoadingTime(loadingTime);
        Integer threshold = (Integer) parsedTask.getOrDefault(
                LoadTaskFields.PARALLEL_THRESHOLD.getValue(),
                CommonConstants.DEFAULT_PARALLEL_THRESHOLD);
        loadTask.setParallelThreshold(threshold);
        return loadTask;
    }

    private List<LoadxEntity> mapToLoadRequests(Map<String, Object> parsedTask) {
        Map<String, Object> requests = (Map<String, Object>) parsedTask.get(LoadTaskFields.REQUESTS.getValue());
        return requests.keySet().stream()
                .map(key -> mapToLoadRequest(key, requests))
                .collect(Collectors.toList());
    }

    private LoadRequest mapToLoadRequest(String requestName, Map<String, Object> requests) {
        LoadRequest request = new LoadRequest();
        Map<String, Object> requestParams = (Map<String, Object>) requests.get(requestName);
        request.setUrl((String) requestParams.get(LoadRequestFields.URL.getValue()));
        request.setType((String) requestParams.get(LoadRequestFields.TYPE.getValue()));
        int timeout = (Integer) requestParams.getOrDefault(
                LoadRequestFields.TIMEOUT.getValue(),
                CommonConstants.DEFAULT_LOAD_REQUEST_TIMEOUT
        );
        request.setTimeout(timeout);
        request.setRequestName(requestName);
        return request;
    }

    static class MappingAndPersistingTaskBuilder {
        private MappingAndPersistingTask task;

        private MappingAndPersistingTaskBuilder() {
            task = new MappingAndPersistingTask();
        }

        MappingAndPersistingTaskBuilder withJson(String json) {
            task.json = json;
            return this;
        }

        MappingAndPersistingTaskBuilder withLoadPersistent(LoadPersistent loadPersistent) {
            task.loadPersistent = loadPersistent;
            return this;
        }

        MappingAndPersistingTask build() {
            return task;
        }
    }

}
