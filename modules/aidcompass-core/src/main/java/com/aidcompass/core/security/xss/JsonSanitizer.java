package com.aidcompass.core.security.xss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;


@Log4j2
public class JsonSanitizer {

    public static String sanitize(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            sanitizingPart(rootNode);
            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            log.warn("Failed to process JSON: {}", e.getMessage());
            return null;
        }
    }

    private static void sanitizingPart(JsonNode node) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                if (entry.getValue().isTextual()) {
                    String safeValue = Encode.forHtml(entry.getValue().asText());
                    ((ObjectNode) node).put(entry.getKey(), safeValue);
                } else {
                    sanitizingPart(entry.getValue());
                }
            });
        } else if (node.isArray()) {
            for (JsonNode arrayItem : node) {
                sanitizingPart(arrayItem);
            }
        }
    }

}
