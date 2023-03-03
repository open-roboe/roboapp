package it.halb.roboapp.dataLayer.remoteDataSource.definition.api;


import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.PollResponse;
import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.PollRoboaUpdate;
import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.PollUserUpdate;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PollingApi {
  /**
   * Poll
   * Polling endpoint, called repeatedly by an authenticated user to get data updates. This endpoint does not update user metrics
   * @return Call&lt;PollResponse&gt;
   */
  @GET("api/polling/")
  Call<PollResponse> pollApiPollingGet();
    

  /**
   * Poll Roboa Update
   * Polling endpoint, called repeatedly by an authenticated roboa to send its metrics, and receive back commands  This endpoint updates the roboa last_update timestamp
   * @param pollRoboaUpdate  (required)
   * @return Call&lt;Object&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/polling/roboa")
  Call<Object> pollRoboaUpdateApiPollingRoboaPost(
    @retrofit2.http.Body PollRoboaUpdate pollRoboaUpdate
  );

  /**
   * Poll User Update
   * Polling endpoint, called repeatedly by an authenticated user to send its metrics and get data updates  This endpoint updates the user last_update timestamp
   * @param pollUserUpdate  (required)
   * @return Call&lt;PollResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/polling/user")
  Call<PollResponse> pollUserUpdateApiPollingUserPost(
    @retrofit2.http.Body PollUserUpdate pollUserUpdate
  );

}
