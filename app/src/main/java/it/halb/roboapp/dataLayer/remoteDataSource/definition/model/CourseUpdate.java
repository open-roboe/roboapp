/*
 * roboe backend
 *  ### backend server for the roboapp, connecting roboe with the android clients 
 *
 * The version of the OpenAPI document: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package it.halb.roboapp.dataLayer.remoteDataSource.definition.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * CourseUpdate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-01-31T17:04:24.839244Z[Etc/UTC]")
public class CourseUpdate {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_COMPASS_DEGREES = "compass_degrees";
  @SerializedName(SERIALIZED_NAME_COMPASS_DEGREES)
  private Integer compassDegrees;

  public static final String SERIALIZED_NAME_START_LINE_LEN = "start_line_len";
  @SerializedName(SERIALIZED_NAME_START_LINE_LEN)
  private BigDecimal startLineLen;

  public static final String SERIALIZED_NAME_BREAK_DISTANCE = "break_distance";
  @SerializedName(SERIALIZED_NAME_BREAK_DISTANCE)
  private BigDecimal breakDistance;

  public static final String SERIALIZED_NAME_COURSE_LENGTH = "course_length";
  @SerializedName(SERIALIZED_NAME_COURSE_LENGTH)
  private BigDecimal courseLength;

  public static final String SERIALIZED_NAME_BOTTOM_BUOY = "bottom_buoy";
  @SerializedName(SERIALIZED_NAME_BOTTOM_BUOY)
  private Boolean bottomBuoy;

  public static final String SERIALIZED_NAME_GATE = "gate";
  @SerializedName(SERIALIZED_NAME_GATE)
  private Boolean gate;

  public static final String SERIALIZED_NAME_SECOND_MARK_DISTANCE = "second_mark_distance";
  @SerializedName(SERIALIZED_NAME_SECOND_MARK_DISTANCE)
  private BigDecimal secondMarkDistance;

  public static final String SERIALIZED_NAME_JURY = "jury";
  @SerializedName(SERIALIZED_NAME_JURY)
  private JuryUpdate jury;

  public static final String SERIALIZED_NAME_BUOYS = "buoys";
  @SerializedName(SERIALIZED_NAME_BUOYS)
  private List<BuoyUpdate> buoys = new ArrayList<>();

  public CourseUpdate() {
  }

  public CourseUpdate name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public CourseUpdate type(String type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public CourseUpdate compassDegrees(Integer compassDegrees) {
    
    this.compassDegrees = compassDegrees;
    return this;
  }

   /**
   * Get compassDegrees
   * @return compassDegrees
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getCompassDegrees() {
    return compassDegrees;
  }


  public void setCompassDegrees(Integer compassDegrees) {
    this.compassDegrees = compassDegrees;
  }


  public CourseUpdate startLineLen(BigDecimal startLineLen) {
    
    this.startLineLen = startLineLen;
    return this;
  }

   /**
   * Get startLineLen
   * @return startLineLen
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getStartLineLen() {
    return startLineLen;
  }


  public void setStartLineLen(BigDecimal startLineLen) {
    this.startLineLen = startLineLen;
  }


  public CourseUpdate breakDistance(BigDecimal breakDistance) {
    
    this.breakDistance = breakDistance;
    return this;
  }

   /**
   * Get breakDistance
   * @return breakDistance
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getBreakDistance() {
    return breakDistance;
  }


  public void setBreakDistance(BigDecimal breakDistance) {
    this.breakDistance = breakDistance;
  }


  public CourseUpdate courseLength(BigDecimal courseLength) {
    
    this.courseLength = courseLength;
    return this;
  }

   /**
   * Get courseLength
   * @return courseLength
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getCourseLength() {
    return courseLength;
  }


  public void setCourseLength(BigDecimal courseLength) {
    this.courseLength = courseLength;
  }


  public CourseUpdate bottomBuoy(Boolean bottomBuoy) {
    
    this.bottomBuoy = bottomBuoy;
    return this;
  }

   /**
   * Get bottomBuoy
   * @return bottomBuoy
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getBottomBuoy() {
    return bottomBuoy;
  }


  public void setBottomBuoy(Boolean bottomBuoy) {
    this.bottomBuoy = bottomBuoy;
  }


  public CourseUpdate gate(Boolean gate) {
    
    this.gate = gate;
    return this;
  }

   /**
   * Get gate
   * @return gate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getGate() {
    return gate;
  }


  public void setGate(Boolean gate) {
    this.gate = gate;
  }


  public CourseUpdate secondMarkDistance(BigDecimal secondMarkDistance) {
    
    this.secondMarkDistance = secondMarkDistance;
    return this;
  }

   /**
   * Get secondMarkDistance
   * @return secondMarkDistance
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getSecondMarkDistance() {
    return secondMarkDistance;
  }


  public void setSecondMarkDistance(BigDecimal secondMarkDistance) {
    this.secondMarkDistance = secondMarkDistance;
  }


  public CourseUpdate jury(JuryUpdate jury) {
    
    this.jury = jury;
    return this;
  }

   /**
   * Get jury
   * @return jury
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public JuryUpdate getJury() {
    return jury;
  }


  public void setJury(JuryUpdate jury) {
    this.jury = jury;
  }


  public CourseUpdate buoys(List<BuoyUpdate> buoys) {
    
    this.buoys = buoys;
    return this;
  }

  public CourseUpdate addBuoysItem(BuoyUpdate buoysItem) {
    this.buoys.add(buoysItem);
    return this;
  }

   /**
   * Get buoys
   * @return buoys
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public List<BuoyUpdate> getBuoys() {
    return buoys;
  }


  public void setBuoys(List<BuoyUpdate> buoys) {
    this.buoys = buoys;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseUpdate courseUpdate = (CourseUpdate) o;
    return Objects.equals(this.name, courseUpdate.name) &&
        Objects.equals(this.type, courseUpdate.type) &&
        Objects.equals(this.compassDegrees, courseUpdate.compassDegrees) &&
        Objects.equals(this.startLineLen, courseUpdate.startLineLen) &&
        Objects.equals(this.breakDistance, courseUpdate.breakDistance) &&
        Objects.equals(this.courseLength, courseUpdate.courseLength) &&
        Objects.equals(this.bottomBuoy, courseUpdate.bottomBuoy) &&
        Objects.equals(this.gate, courseUpdate.gate) &&
        Objects.equals(this.secondMarkDistance, courseUpdate.secondMarkDistance) &&
        Objects.equals(this.jury, courseUpdate.jury) &&
        Objects.equals(this.buoys, courseUpdate.buoys);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, compassDegrees, startLineLen, breakDistance, courseLength, bottomBuoy, gate, secondMarkDistance, jury, buoys);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CourseUpdate {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    compassDegrees: ").append(toIndentedString(compassDegrees)).append("\n");
    sb.append("    startLineLen: ").append(toIndentedString(startLineLen)).append("\n");
    sb.append("    breakDistance: ").append(toIndentedString(breakDistance)).append("\n");
    sb.append("    courseLength: ").append(toIndentedString(courseLength)).append("\n");
    sb.append("    bottomBuoy: ").append(toIndentedString(bottomBuoy)).append("\n");
    sb.append("    gate: ").append(toIndentedString(gate)).append("\n");
    sb.append("    secondMarkDistance: ").append(toIndentedString(secondMarkDistance)).append("\n");
    sb.append("    jury: ").append(toIndentedString(jury)).append("\n");
    sb.append("    buoys: ").append(toIndentedString(buoys)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

