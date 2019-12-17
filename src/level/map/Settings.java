package level.map;

public enum Settings {
    FACTORY("Factory"),
    GRASS("Grass"),
    MARS("Mars"),
    CREEPY("Creepy"),
    FUTURE("SciFi");

    private String mapName;

    Settings(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}
