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
package com.moose.cal.date;

import com.moose.cal.astro.*;
import static com.moose.cal.astro.Meeus.*;

/**
 * A holiday calculator.
 * @author Chris Engelsma
 * @version 2015.11.02
 */
public class HolidayCalculator {

  public static JulianDay get(Holiday holiday, int year) {
    JulianDay jday = new JulianDay();
    
    switch (holiday) {
      case EASTER: // First Sunday after full moon on or after spring equinox
        jday = getEaster(year);
        break;
      case GOOD_FRIDAY: // Two days before Easter
        jday = getEaster(year).minus(2);
        break;
      case HOLY_THURSDAY: // Three days before Easter
        jday = getEaster(year).minus(3);
        break;
      case PALM_SUNDAY: // Sunday before Easter
        jday = getEaster(year).minus(7);
        break;
      case ASH_WEDNESDAY: // 46 days before Easter.
        jday = getEaster(year).minus(46); 
        break;
      default: 
    }

    return jday;
  }

//////////////////////////////////////////////////////////////////////////////
// private
  
  private static JulianDay getEaster(int year) {
    double eq = equinox(year,Season.SPRING);
    double full = eq;
    GregorianCalendar cal = new GregorianCalendar(new JulianDay(eq));
    int month = cal.getMonth();
    int day = cal.getDay();
    while (full <= eq) 
      full = getMoonQuarters(year,month++,day)[2];
    cal = new GregorianCalendar(new JulianDay(full));
    if (cal.getWeekDay()==0) cal.nextDay();
    while (cal.getWeekDay()>0) cal.nextDay();
    return new JulianDay(cal);
  }
}
