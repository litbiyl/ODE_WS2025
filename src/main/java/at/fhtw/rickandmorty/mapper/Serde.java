package at.fhtw.rickandmorty.mapper;

import at.fhtw.rickandmorty.series.World;

import java.util.List;

public interface Serde<T extends World>{

    List<T> deserializeJsonList(String json);
}
