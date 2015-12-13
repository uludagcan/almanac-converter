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
package com.moose.cal.date;

import java.util.Calendar;
import java.util.Random;
import org.joda.time.DateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import static com.moose.cal.util.Converter.*;

/**
 * Tests {@link com.moose.cal.date.HebrewCalendar}.
 * @author Chris Engelsma
 * @version 2015.12.10
 */
public class HebrewCalendarTest {
  @Test
  public void sameDatesShouldBeEqual() {
    HebrewCalendar date1 = new HebrewCalendar(5747,12,9);
    HebrewCalendar date2 = new HebrewCalendar(5747,12,9);
    assertEquals( "FAIL: Dates should be equal",
                  true,
                  date1.equals(date2));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    int[] days = new int[] { 30, 29, 30, 29, 30, 29, 30, 30, 30, 29, 30, 29};
    int year = 5747;
    int[] result = HebrewCalendar.getDaysPerMonthInYear(year);
    assertArrayEquals("FAIL: Days of month aren't correct",
                      days,
                      result);
  }

  @Test
  public void jodaDateTimeShouldConstruct() {
    HebrewCalendar date1 = 
      new HebrewCalendar(new DateTime(1987,3,10,0,1));
    HebrewCalendar date2 = new HebrewCalendar(5747,12,9);
    assertEquals( "FAIL: Date should construct from Joda DateTime",
                  true,
                  date1.equals(date2));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    String month = HebrewCalendar.getMonthName(0); // No "0" month!
  }

  @Test
  public void testHebrewRoundTrip() {
    Random r = new Random();
    double min = HebrewCalendar.EPOCH.getValue();
    double jday = (new JulianDay()).getValue();
    double rand = 0.0;
    for (int i=0; i<1000; ++i) {
      rand = (double)(r.nextInt((int)(jday-min))+min);
      JulianDay jday1 = new JulianDay(rand);
      HebrewCalendar a = new HebrewCalendar(jday1);
      JulianDay jday2 = toJulianDay(a);
      HebrewCalendar b = toHebrewCalendar(jday2);
      assertEquals( "FAIL: French Republican Calendar failed round trip",
                    a,
                    b);
    }
  }

}
