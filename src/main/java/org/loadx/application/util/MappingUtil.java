package org.loadx.application.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.loadx.application.exceptions.MappingException;

import java.io.IOException;
import java.util.Map;

public final class MappingUtil {

    private MappingUtil() {
        // hidden constructor for util class
    }

    /**
     * Parses input json to Map.
     *
     * @param json String with JSON to be parsed.
     * @return Map representing passed json.
     */
    public static Map<String, Object> parseJsonToMap(String json) {
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

}
