package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.series.World;

import java.util.List;

public interface Serde<T extends World>{

    public World deserializeJson(String json);

    public List<T> deserializeJsonList(String json);
}
