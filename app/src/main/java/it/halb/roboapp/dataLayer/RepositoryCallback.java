package it.halb.roboapp.dataLayer;

public interface RepositoryCallback<T>{

    /**
     * override this method to handle request success
     * @param data The response data returned by the successful operation
     */
    void onSuccess(T data);

    /**
     * override this method to handle request failure
     * code 0 is a network error, and its detail string is "network_error"
     * @param code HTTP response code
     * @param detail error details
     */
    void onError(int code, String detail);

}
