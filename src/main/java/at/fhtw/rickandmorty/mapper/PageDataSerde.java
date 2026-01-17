package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.network.PageData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PageDataSerde {

    ObjectMapper mapper = new ObjectMapper();

    public PageData deserializePageData(String json) {
        try {
            JsonNode result = mapper.readTree(json).get("info");
            return mapper.treeToValue(result, PageData.class);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize page data");
        }
    }
}
