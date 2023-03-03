package it.halb.roboapp.dataLayer.remoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(response.body());
            onSuccessResponse(call, response);
        }
        else{
            onError(response.code(), decodeErrorDetail(""));
            if(response.code() == 401){
                onAuthError();
            }
        }
    }

        @Override
    public void onFailure(Call<T> call, Throwable t) {
        onNetworkError();
        onError(0, "network_error");
    }

    public abstract void onSuccess(T data);
        //override this method if you need it

    public void onSuccessResponse(Call<T> call, Response<T> response){
        //override this method if you need it
    }

    public void onAuthError(){
        //override this method if you need it
    }

    public void onNetworkError(){
        //override this method if you need it
    }

    /**
     * override this method if you need it.
     * code 0 is a network error, and onNetworkError() will also be called.
     * code 401 is an auth error, and onAuthError will also be called
     * @param code HTTP response code
     * @param detail error details
     */
    public abstract void onError(int code, String detail);


    private String decodeErrorDetail(String errorResponse){
        return errorResponse; //TODO: implement
    }

}
