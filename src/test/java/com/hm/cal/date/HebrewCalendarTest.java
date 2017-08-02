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
package com.hm.cal.date;

import java.util.Random;

import com.hm.cal.util.AlmanacConverter;
import org.joda.time.DateTime;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import static com.hm.cal.util.AlmanacConverter.*;

/**
 * Tests {@link com.hm.cal.date.HebrewCalendar}.
 * @author Chris Engelsma
 * @version 2015.12.10
 */
public class HebrewCalendarTest {
  @Test
  public void sameDatesShouldBeEqual() {
    HebrewCalendar date1 = new HebrewCalendar(5747,12,9);
    HebrewCalendar date2 = new HebrewCalendar(5747,12,9);
    assertEquals(true,date1.equals(date2));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    int[] days = new int[] { 30, 29, 30, 29, 30, 29, 30, 30, 30, 29, 30, 29};
    HebrewCalendar hebrewCalendar = new HebrewCalendar(5747,1,1);
    for (int i=0; i<hebrewCalendar.getNumberOfMonthsInYear(); ++i) {
      hebrewCalendar.setMonth(i+1);
      assertEquals(hebrewCalendar.getNumberOfDaysInMonth(),days[i]);
    }
  }

  @Test
  public void jodaDateTimeShouldConstruct() {
    HebrewCalendar date1 = 
      new HebrewCalendar(new DateTime(1987,3,10,0,1));
    HebrewCalendar date2 = new HebrewCalendar(5747,12,9);
    assertEquals(true,date1.equals(date2));
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    HebrewCalendar.getMonthName(0); // No "0" month!
  }

  @Test
  public void testHebrewRoundTrip() {
    Random r = new Random();
    double min = HebrewCalendar.EPOCH.getValue();
    double jday = (new JulianDay()).getValue();
    double rand;
    for (int i=0; i<1000; ++i) {
      rand = r.nextInt((int)(jday-min))+min;
      JulianDay jday1 = new JulianDay(rand);
      HebrewCalendar expected = new HebrewCalendar(jday1);
      JulianDay jday2 = toJulianDay(expected);
      HebrewCalendar actual = toHebrewCalendar(jday2);
      assertEquals(expected,actual);
    }
  }

  @Test
  public void testNextDayWorks() {
    HebrewCalendar calendar = new HebrewCalendar(5776,1,1);
    JulianDay jd = AlmanacConverter.toJulianDay(calendar).atMidnight();
    for (int i=0; i< 385; ++i) {
      calendar.nextDay();
      jd.nextDay();
      JulianDay converted = AlmanacConverter.toJulianDay(calendar);
      assertEquals(jd.getValue(),converted.getValue(),0.00);
    }
  }
}