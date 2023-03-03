package it.halb.roboapp.dataLayer.remoteDataSource.definition.api;


import java.util.List;

import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.ApiCourse;
import it.halb.roboapp.dataLayer.remoteDataSource.definition.model.RoboaGet;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface CourseApi {
  /**
   * Add Course
   * Add a course, with its jury and buoys.  Courses are the same thing as Regattas. It&#39;s just a synonym
   * @param apiCourse  (required)
   * @return Call&lt;ApiCourse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/course/")
  Call<ApiCourse> addCourseApiCoursePost(
    @retrofit2.http.Body ApiCourse apiCourse
  );

  /**
   * Buoy Assign Roboa
   * assign a roboa to a buoy. As of now it is not possible to assign a roboa to the jury buoy  This operation will not cause the roboa to move! If you want to move the roboa use the roboa/move endpoint
   * @param courseName  (required)
   * @param buoyId  (required)
   * @param roboaGet  (required)
   * @return Call&lt;Object&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/course/{course_name}/buoy/{buoy_id}/assign_roboa")
  Call<Object> buoyAssignRoboaApiCourseCourseNameBuoyBuoyIdAssignRoboaPost(
    @retrofit2.http.Path("course_name") String courseName, @retrofit2.http.Path("buoy_id") Integer buoyId, @retrofit2.http.Body RoboaGet roboaGet
  );

  /**
   * Get All Courses
   * get all courses
   * @return Call&lt;List&lt;ApiCourse&gt;&gt;
   */
  @GET("api/course/")
  Call<List<ApiCourse>> getAllCoursesApiCourseGet();
    

  /**
   * Update Course
   * 
   * @param courseName  (required)
   * @return Call&lt;Object&gt;
   */
  @DELETE("api/course/{course_name}")
  Call<Object> updateCourseApiCourseCourseNameDelete(
    @retrofit2.http.Path("course_name") String courseName
  );

  /**
   * Update Course
   * update a course  note: course.name, jury.id and buoy.id are identifiers and cannot be changed
   * @param apiCourse  (required)
   * @return Call&lt;ApiCourse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("api/course/")
  Call<ApiCourse> updateCourseApiCoursePut(
    @retrofit2.http.Body ApiCourse apiCourse
  );

}
