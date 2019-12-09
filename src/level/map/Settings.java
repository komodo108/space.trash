package level.map;

public enum Settings {
    FACTORY("Factory"),
    GRASS("Grass"),
    MARS("TestMap"), // TODO: Mars
    SPOOPY("SpoopyScary"),
    FUTURE("SciFi");

    private String mapName;

    Settings(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}
