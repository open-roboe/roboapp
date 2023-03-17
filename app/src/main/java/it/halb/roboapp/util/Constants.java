package it.halb.roboapp.util;

public class Constants {

    // sharedPreferences
    public static final String SHARED_PREFERENCES_FILENAME = "it_halb_roboapp_preferences";
    public static final String KEY_API_BASE_URL = "api_base_url";
    public static final String DEFAULT_API_BASE_URL = "https://roboapp.halb.it/";

    // Room database
    public static final String ROBOAPP_DATABASE_NAME = "roboapp_database";
    public static final int ROBOAPP_DATABASE_VERSION = 1;

    // foreground service
    public static final String NOTIFICATION_CHANNEL_ID = "roboapp_foreground_channel";
    public static final int NOTIFICATION_ID = 42;

    public static final int POLLING_DELAY_MILLIS = 5 * 1000;

    //Buoys Type

    public static final String GateMarkDx = "GATE MARK DX";
    public static final String GateMarkSx = "GATE MARK SX";
    public static final String BottomMark = "BOTTOM MARK";
    public static final String BreakMark = "BREAK MARK";
    public static final String SecondUpMark = "SECOND UP WIND MARK";
    public static final String UpMark = "UP WIND MARK";
    public static final String StartMark = "START MARK";

    public static final String MidLineStart = "MID LINE START";

    public static final String TriangleMark = "TRIANGLE MARK";




}
