package com.virus.pt.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class JackJsonUtils {

    public static String json(String key, Object value) {
        if (value instanceof Number) {
            return "{\"" + key + "\":" + value + "}";
        } else {
            return "{\"" + key + "\":\"" + value + "\"}";
        }
    }

    public static String json(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static HashMap jsonToMap(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, HashMap.class);
    }

    public static JsonNode parse(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(json);
    }

    public static <T> T parseObject(String json, Class<T> tClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, tClass);
    }
}
