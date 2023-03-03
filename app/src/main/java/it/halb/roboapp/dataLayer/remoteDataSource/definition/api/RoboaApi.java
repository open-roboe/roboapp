package it.halb.roboapp.dataLayer.remoteDataSource.definition.api;

import java.util.List;

import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.RoboaCreate;
import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.RoboaResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RoboaApi {
  /**
   * Add Roboa
   * Register a new roboa in the system
   * @param roboaCreate  (required)
   * @return Call&lt;RoboaResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/roboa/")
  Call<RoboaResponse> addRoboaApiRoboaPost(
    @retrofit2.http.Body RoboaCreate roboaCreate
  );

  /**
   * Delete Roboa
   * TOIMPLEMENT delete a roboa
   * @param name  (required)
   * @return Call&lt;Object&gt;
   */
  @DELETE("api/roboa/{name}")
  Call<Object> deleteRoboaApiRoboaNameDelete(
    @retrofit2.http.Path("name") String name
  );

  /**
   * Get All Roboas
   * get all roboas
   * @return Call&lt;List&lt;RoboaResponse&gt;&gt;
   */
  @GET("api/roboa/")
  Call<List<RoboaResponse>> getAllRoboasApiRoboaGet();
    

  /**
   * Move To Assigned Buoy
   * TOIMPLEMENT set the target coordinates of the specified roboa, causing it to move to the location
   * @param name  (required)
   * @return Call&lt;Object&gt;
   */
  @PUT("api/roboa/{name}/move_to_buoy")
  Call<Object> moveToAssignedBuoyApiRoboaNameMoveToBuoyPut(
    @retrofit2.http.Path("name") String name
  );

}
