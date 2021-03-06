package main;

/**
 * Static class for constant variables and static functions
 */
public final class Constants {

    // Number of levels
    public static final int LEVELS = 30;
    public static final int SPECIAL_LEVEL = 8;
    public static final int MID_LEVEL = 9;      // Mid level > Special level
    public static final int HELPER_GONE_START = 23;
    public static final int HELPER_GONE_END = 24;
    public static final float SLEEP_FACTOR = 1.35f;

    // Python constants
    /**
     * This string should be one which a user isn't allowed to type in with ANY of the parsers
     */
    public static final String KEY = "exec";
    public static final int SHORT_SLEEP_TIME = 10;
    public static final int LONG_SLEEP_TIME = 50;

    // Window constants
    public static final String NAME = "Space.trash";
    public static final int WIDTH = 1504;   // Note: Images are made for 1504
    public static final int HEIGHT = 912;   // Note: Images are made for 912
    public static final float VOLUME = 0.25f;

    // GUI constants
    public static final int EDITOR_WIDTH = WIDTH / 4;
    public static final int EDITOR_HEIGHT = HEIGHT - 100;
    public static final int UNEDIT_HEIGHT = (EDITOR_HEIGHT - 80) / 2;
    public static final int CONSOLE_TIME = 100;
    public static final int MAX_LENGTH = 100;
    public static final int NEW_LENGTH = 10;
    public static final String IMAGE_DIR = "images/";
    public static final String AUDIO_DIR = "audio/";

    // Physics parameters
    public static final float MOVE_RADIUS = 1.75f;
    public static final float TARGET_RADIUS = 3f;
    public static final float ATTACK_RADIUS = 2.5f;
    public static final float SLOW_RADIUS = 1f;
    public static final float MAX_ACCELERATION = 0.2f;
    public static final float MAX_SPEED = 1.35f;
    public static final float ATTACK_TIME = 0.37f;
    public static final float PLAYER_MAX_SPEED = 0.69f;
    public static final double ORIENTATION_BASE = Math.PI / 32;
    public static final float DAMPING = 1f;
    public static final float PUSHING_AMOUNT = 1.5f;

    // Rendering parameters
    public static final float EYE_FACTOR = 3f;
    public static final int EYE_OFFSET = 4;
    public static final int SMALL_EYE_OFFSET = 3;

    // Map parameters
    public static final int TILE_SIZE = 16;
    public static final int MAP_WIDTH = WIDTH / TILE_SIZE;
    public static final int MAP_HEIGHT = HEIGHT / TILE_SIZE;
}
