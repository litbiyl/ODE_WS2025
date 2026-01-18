package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.logging.Logger;
import at.fhtw.rickandmorty.series.Episode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class EpisodeSerde implements Serde<Episode> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Episode> deserializeJsonList(String json) {
        try {
            JsonNode results = mapper.readTree(json).get("results");
            return mapper.readerForListOf(Episode.class).readValue(results);
        } catch (JsonProcessingException e) {
            Logger.log("ERROR", "Failed to deserialize episode list: " + e.getMessage());
            throw new RuntimeException("Failed to deserialize episode list", e);
        } catch (IOException e) {
            Logger.log("ERROR", "Failed to read episode list: " + e.getMessage());
            throw new RuntimeException("Failed to read episode list", e);
        }
    }
}
