package it.halb.roboapp.dataLayer;

/**
 * Communicates successful responses from an asynchronous RegattaRepositoryMock operation
 */
@FunctionalInterface
public interface SuccessCallback<T> {
    /**
     * Handle repository operation success.
     * @param data The data returned by the successful operation
     */
    void success(T data);
}
