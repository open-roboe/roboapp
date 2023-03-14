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

    public static final int GateMarkDx = 7;
    public static final int GateMarkSx = 6;
    public static final int BottomMark = 5;
    public static final int BreakMark = 4;
    public static final int SecondUpMark = 3;
    public static final int UpMark = 2;
    public static final int StartMark = 1;

    public static final int MidLineStart = 0;

    public static final int TriangleMark = 10;

    public static final int[] allBouy = {TriangleMark,StartMark,UpMark,SecondUpMark,BreakMark,
            BottomMark,GateMarkSx,GateMarkDx};



}
