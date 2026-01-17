package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.series.Episode;
import at.fhtw.rickandmorty.series.World;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class EpisodeSerde implements Serde<Episode> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Episode deserializeJson(String json) {
        try {
            return mapper.readValue(json, Episode.class);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize episode");
            }
    }

    @Override
    public List<Episode> deserializeJsonList(String json) {
        try {
            JsonNode results = mapper.readTree(json).get("results");
            return mapper.readerForListOf(Episode.class).readValue(results);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize episode list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
