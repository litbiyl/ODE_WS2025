package at.fhtw.rickandmorty.mapping;

import at.fhtw.rickandmorty.series.Episode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class EpisodeSerde {

    ObjectMapper mapper = new ObjectMapper();

    public Episode deserializeEpisode(String json) {
        try {
            return mapper.readValue(json, Episode.class);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize episode");
            }
    }

    public List<Episode> deserializeEpisodeList(String json) {
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
