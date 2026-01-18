package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.logging.Logger;
import at.fhtw.rickandmorty.network.PageData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PageDataSerde {

    private final ObjectMapper mapper = new ObjectMapper();

    public PageData deserializePageData(String json) {
        try {
            JsonNode result = mapper.readTree(json).get("info");
            return mapper.treeToValue(result, PageData.class);
        } catch (JsonProcessingException e) {
            Logger.log("ERROR", "Failed to deserialize page data: " + e.getMessage());
            throw new RuntimeException("Failed to deserialize page data", e);
        } catch (IOException e) {
            Logger.log("ERROR", "IO error during deserialization: " + e.getMessage());
            throw new RuntimeException("IO error during deserialization", e);
        }
    }
}
