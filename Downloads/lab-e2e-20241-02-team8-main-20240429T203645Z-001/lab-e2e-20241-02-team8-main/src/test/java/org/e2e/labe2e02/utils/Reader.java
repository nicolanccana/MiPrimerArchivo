package org.e2e.labe2e02.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class Reader {
    @Autowired
    ObjectMapper mapper;

    public static String readJsonFile(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        byte[] byteArray = Files.readAllBytes(resource.toPath());
        return new String(byteArray);
    }

    public String updateId(String json, String key, Long id) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);

        ((ObjectNode) jsonNode).put(key, id);

        return mapper.writeValueAsString(jsonNode);
    }
}
