/*****************************************************************************
Copyright 2015 Hypotemoose, LLC.

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
 * @version 2015.09.30
 */
public final class Util {

  public static String its(int val) {
    return Integer.toString(val);
  }

  public static double dsin(double deg) {
    return Math.sin(dtr(deg));
  }

  public static double dcos(double deg) {
    return Math.cos(dtr(deg));
  }

  public static double rtd(double rad) {
    return Math.toDegrees(rad);
  }

  public static double dtr(double deg) {
    return Math.toRadians(deg);
  }

  public static double fixAngle(double a) {
    return (a - 360.0 * (Math.floor(a/360.0)));
  }

  public static double fixAngler(double rad) {
    return rad - (2*Math.PI)*(Math.floor(rad/(2*Math.PI)));
  }
}
