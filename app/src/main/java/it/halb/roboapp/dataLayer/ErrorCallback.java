package it.halb.roboapp.dataLayer;

public interface ErrorCallback {
    /**
     * Handle repository operation errors
     * code 0 is a network error, and its detail string is "network_error"
     * @param code HTTP error response code, or one of the special codes described above
     * @param details error details
     */
    void error(int code, String details);
}
