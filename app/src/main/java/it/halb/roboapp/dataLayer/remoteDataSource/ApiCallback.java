package it.halb.roboapp.dataLayer.remoteDataSource;


import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    @Override
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(response.body());
        }
        else{
            onError(response.code(), decodeErrorDetail(""));
            if(response.code() == 401){
                onAuthError();
            }
        }
    }


    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onError(0, "network_error");
    }

    /**
     * override this method to handle request success
     * @param data The response data returned by the succesfful operation
     */
    public abstract void onSuccess(T data);


    /**
     * override this method to handle request failure
     * code 0 is a network error, and its detail string is "network_error"
     * @param code HTTP response code
     * @param detail error details
     */
    public abstract void onError(int code, String detail);

    /**
     * Override this method to handle authentication issues
     */
    public abstract void onAuthError();

    private String decodeErrorDetail(String errorResponse){
        return errorResponse; //TODO: implement
    }
}
