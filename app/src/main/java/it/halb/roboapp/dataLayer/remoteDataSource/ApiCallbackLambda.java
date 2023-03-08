package it.halb.roboapp.dataLayer.remoteDataSource;

/**
 * Communicates responses from an api request.
 * This is the same as ApiCallback, but with lambda expressions
 * instead of callback methods. You can use this to write more concise api callbacks.
 *
 * This has one big downside: In case of runtime exceptions the stack trace will be much harder
 * to follow. Especially when there is a heavy usage of these lambda callbacks
 *
 * @param <T> The data returned in case of success
 */
public class ApiCallbackLambda<T> extends ApiCallback<T> {

    @FunctionalInterface
    public interface Success<T>{
        void success(T data);
    }

    @FunctionalInterface
    public interface Error{
        void error(int code, String detail);
    }

    @FunctionalInterface
    public interface AuthError{
        void authError();
    }

    private final Success<T> success;
    private final Error error;
    private final AuthError authError;

    /**
     * Use this only if you don't need any feedback from the api request
     *
     * Nothing will happen when the api call terminates, regardless of success state.
     */
    public ApiCallbackLambda(){
        this(
                t->{},
                (code, detail)->{},
                ()->{}
        );
    }

    /**
     * Use this only if you don't need to handle api errors.
     * Note that in case of api errors the success callback will never be called.
     *
     * @param success callback that will be executed in case of success
     */
    public ApiCallbackLambda(Success<T> success){
        this(
                success,
                (code, detail)->{},
                ()->{}
        );
    }

    /**
     *
     * @param success callback that will be executed in case of success
     * @param error callback that will be executed in case of api errors. Including auth errors.
     */
    public ApiCallbackLambda(Success<T> success, Error error){
        this(
                success,
                error,
                ()->{}
        );
    }

    /**
     * Note that in case of auth errors both the authError callback and tha error callback will be
     * executed. the error callback will have a code of 401
     *
     * @param success callback that will be executed in case of success
     * @param error callback that will be executed in case of api errors. Including auth errors.
     * @param authError callback that will be executed in case of api auth errors.
     */
    public ApiCallbackLambda(Success<T> success, Error error, AuthError authError){
        this.success = success;
        this.error = error;
        this.authError = authError;
    }

    @Override
    public void onSuccess(T data) {
        success.success(data);
    }

    @Override
    public void onError(int code, String detail) {
        error.error(code, detail);
    }

    @Override
    public void onAuthError() {
        authError.authError();
    }

}
