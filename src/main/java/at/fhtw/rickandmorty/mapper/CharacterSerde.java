package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.series.Character;
import at.fhtw.rickandmorty.series.World;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class CharacterSerde implements Serde<Character> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Character deserializeJson(String json) {
        try {
            return mapper.readValue(json, Character.class);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize character");
        }
    }

    @Override
    public List<Character> deserializeJsonList(String json) {
        try {
            JsonNode results = mapper.readTree(json).get("results");
            return mapper.readerForListOf(Character.class).readValue(results);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize character list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
