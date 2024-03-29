package it.halb.roboapp.dataLayer.remoteDataSource.scheme.api;

import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.AuthToken;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.UserCreate;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccountApi {
  /**
   * Get Logged Admin User
   * Returns account info, requires authentication in an admin account. Used to test if the current account is admin
   * @return Call&lt;UserResponse&gt;
   */
  @GET("api/account/admin")
  Call<UserResponse> getLoggedAdminUserApiAccountAdminGet();
    

  /**
   * Get Logged Super Admin User
   * Returns account info, requires authentication in a super admin account. Used to test if the current account is super admin
   * @return Call&lt;UserResponse&gt;
   */
  @GET("api/account/super_admin")
  Call<UserResponse> getLoggedSuperAdminUserApiAccountSuperAdminGet();
    

  /**
   * Get Logged User
   * Returns account info, requires authentication
   * @return Call&lt;UserResponse&gt;
   */
  @GET("api/account/")
  Call<UserResponse> getLoggedUserApiAccountGet();
    

  /**
   * Login
   * 
   * @param username  (required)
   * @param password  (required)
   * @param grantType  (optional)
   * @param scope  (optional, default to )
   * @param clientId  (optional)
   * @param clientSecret  (optional)
   * @return Call&lt;AuthToken&gt;
   */
  @retrofit2.http.FormUrlEncoded
  @POST("api/account/auth")
  Call<AuthToken> loginApiAccountAuthPost(
    @retrofit2.http.Field("username") String username, @retrofit2.http.Field("password") String password, @retrofit2.http.Field("grant_type") String grantType, @retrofit2.http.Field("scope") String scope, @retrofit2.http.Field("client_id") String clientId, @retrofit2.http.Field("client_secret") String clientSecret
  );

  /**
   * Register
   * TODO: require super admin permissions  Register a new user. Requires authentication in a super admin account
   * @param userCreate  (required)
   * @return Call&lt;Object&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/account/register")
  Call<Object> registerApiAccountRegisterPost(
    @retrofit2.http.Body UserCreate userCreate
  );

}
