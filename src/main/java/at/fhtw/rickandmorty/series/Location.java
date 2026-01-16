package at.fhtw.rickandmorty.series;

import java.util.List;

public class Location extends World {
    private String type;
    private String dimension;
    private List<String> residents;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<String> getResidents() {
        return residents;
    }

    public void setResidents(List<String> residents) {
        this.residents = residents;
    }
}
