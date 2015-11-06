/*****************************************************************************
Copyright 2015 Hypotemoose, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*****************************************************************************/
package com.moose.cal.util;

import java.lang.Math;

/**
 * A collection of convenience utilities.
 * @author Chris Engelsma
 * @version 2015.10.07
 */
public final class Util {

  /**
   * Converts an integer to string.
   * @param val an integer.
   * @return the integer as a string.
   */
  public static String its(int val) {
    return Integer.toString(val);
  }

  /**
   * The sine of an angle in degrees.
   * @param deg an angle in degrees.
   * @return the sine of the angle.
   */  
  public static double dsin(double deg) {
    return Math.sin(dtr(deg));
  }

  /**
   * The cosine of an angle in degrees.
   * @param deg an angle in degrees.
   * @return the cosine of the angle.
   */  
  public static double dcos(double deg) {
    return Math.cos(dtr(deg));
  }

  /**
   * Converts radians to degrees.
   * @param rad an angle in radians.
   * @return the angle in degrees.
   */  
  public static double rtd(double rad) {
    return Math.toDegrees(rad);
  }

  /**
   * Converts degrees to radians.
   * @param deg an angle in degrees.
   * @return the angle in radians.
   */  
  public static double dtr(double deg) {
    return Math.toRadians(deg);
  }

  /**
   * Fixes an angle between range [0-360).
   * @param a an angle in degrees.
   * @return the fixed angle.
   */  
  public static double fixAngle(double a) {
    return (a - 360.0 * (Math.floor(a/360.0)));
  }

  /**
   * Fixes an angle between range [0-2*PI).
   * @param a an angle in radians.
   * @return the fixed angle.
   */  
  public static double fixAngler(double rad) {
    return rad - (2*Math.PI)*(Math.floor(rad/(2*Math.PI)));
  }

}
