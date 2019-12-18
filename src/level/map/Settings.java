package level.map;

public enum Settings {
    FACTORY("Factory", false),
    GRASS("Grass", false),
    MARS("Mars", false),
    CREEPY("Creepy", true),
    FUTURE("SciFi", false);

    private String mapName;
    private boolean dark;

    Settings(String mapName, boolean dark) {
        this.mapName = mapName;
        this.dark = dark;
    }

    public String getMapName() {
        return mapName;
    }

    public boolean isDark() {
        return dark;
    }
}
