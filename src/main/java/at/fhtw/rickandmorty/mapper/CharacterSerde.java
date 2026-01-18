package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.logging.Logger;
import at.fhtw.rickandmorty.series.Character;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class CharacterSerde implements Serde<Character> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Character> deserializeJsonList(String json) {
        try {
            JsonNode results = mapper.readTree(json).get("results");
            return mapper.readerForListOf(Character.class).readValue(results);
        } catch(JsonProcessingException e) {
            Logger.log("ERROR", "Failed to deserialize character list: " + e.getMessage());
            throw new UnsupportedOperationException("Failed to deserialize character list");
        } catch (IOException e) {
            Logger.log("ERROR", "Failed to read character list: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
