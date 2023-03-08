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

}
