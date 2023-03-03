package it.halb.roboapp.dataLayer.remoteDataSource;

public interface LoginCallback{

    /**
     * override this method if you need it.
     *
     * if this method is called the login was successful, which means that
     * future api calls to authenticated endpoints will work
     */
    void onSuccess();

    /**
     * override this method if you need it.
     *
     * code 0 is a network error
     * @param code HTTP response code
     * @param detail error details
     */
    void onError(int code, String detail);

}
