package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.series.Location;
import at.fhtw.rickandmorty.series.World;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class LocationSerde implements Serde<Location> {

    ObjectMapper mapper = new ObjectMapper();

    public Location deserializeJson(String json) {
        try {
            return mapper.readValue(json, Location.class);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize location");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Location> deserializeJsonList(String json) {
        try {
            JsonNode results = mapper.readTree(json).get("results");
            return mapper.readerForListOf(Location.class).readValue(results);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed to deserialize location list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
