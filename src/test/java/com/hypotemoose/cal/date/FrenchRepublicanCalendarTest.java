/*****************************************************************************
Copyright 2015 Chris Engelsma

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
package com.hypotemoose.cal.date;

import java.util.Random;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import static com.hypotemoose.cal.util.AlmanacConverter.*;

/**
 * Tests {@link com.hypotemoose.cal.date.FrenchRepublicanCalendar}.
 * @author Chris Engelsma
 * @version 2015.10.09
 */
public class FrenchRepublicanCalendarTest {
  @Test
  public void sameDatesShouldBeEqual() {
    FrenchRepublicanCalendar date1 = new FrenchRepublicanCalendar(195,6,2,9);
    FrenchRepublicanCalendar date2 = new FrenchRepublicanCalendar(195,6,2,9);
    assertTrue(date1.equals(date2));
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    String month = FrenchRepublicanCalendar.getMonthName(0); // No "0" month!
  }

  @Test
  public void leapYearShouldComputeCorrectly() {
    int n = 12;
    boolean[] expected = new boolean[n];

    for (int i=0; i<n; ++i) expected[i] = false;
    expected[2] = true; expected[6] = true; expected[10] = true;

    for (int i=0; i<n; ++i) 
      assertEquals(expected[i],FrenchRepublicanCalendar.isLeapYear(i+1));
  }

  @Test
  public void testFrenchRepublicanRoundTrip() {
    Random r = new Random();
    double min = FrenchRepublicanCalendar.EPOCH.getValue();
    double jday = (new JulianDay()).getValue();
    double rand = 0.0;
    for (int i=0; i<1000; ++i) {
      rand = (double)(r.nextInt((int)(jday-min))+min);
      JulianDay jday1 = new JulianDay(rand);
      FrenchRepublicanCalendar a = new FrenchRepublicanCalendar(jday1);
      JulianDay jday2 = toJulianDay(a);
      FrenchRepublicanCalendar b = toFrenchRepublicanCalendar(jday2);
      assertEquals(a,b);
    }
  }
  
}
