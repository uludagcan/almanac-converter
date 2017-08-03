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
import org.joda.time.DateTime;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import static com.hypotemoose.cal.util.AlmanacConverter.*;

/**
 * Tests {@link com.hypotemoose.cal.date.JulianCalendar}.
 * @author Chris Engelsma
 * @version 2015.10.06
 */
public class JulianCalendarTest {
  @Test
  public void checkProperLeapYearRule() {
    assertEquals(true,JulianCalendar.isLeapYear(1900));
  }

  @Test
  public void sameDatesShouldBeEqual() {
    JulianCalendar date1 = new JulianCalendar(1986,9,3);
    JulianCalendar date2 = new JulianCalendar(1986,9,3);
    assertEquals(true,date1.equals(date2));
  }

  @Test
  public void datesShouldBeInOrder() {
    JulianCalendar date1 = new JulianCalendar(1986,9,3);
    JulianCalendar date2 = new JulianCalendar(1986,9,4);
    assertEquals(true,JulianCalendar.datesAreChronological(date1,date2));
  }

  @Test 
  public void dateOneShouldBeBeforeDateTwo() {
    JulianCalendar date1 = new JulianCalendar(1986,9,3);
    JulianCalendar date2 = new JulianCalendar(1986,9,4);
    assertEquals(true,date1.isBefore(date2));
  }

  @Test 
  public void dateOneShouldBeAfterDateTwo() {
    JulianCalendar date1 = new JulianCalendar(1986,9,3);
    JulianCalendar date2 = new JulianCalendar(1986,9,4);
    assertEquals(true,date2.isAfter(date1));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int year = 2015;
    int[] result = JulianCalendar.getDaysPerMonthInYear(year);
    assertEquals(days,result);
  }

  @Test
  public void jodaDateTimeShouldConstruct() {
    JulianCalendar date1 = 
      new JulianCalendar(new DateTime(1987,3,10,0,1));
    JulianCalendar date2 = new JulianCalendar(1987,2,25);
    assertEquals(true,date1.equals(date2));
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    String month = JulianCalendar.getMonthName(0); // No "0" month!
  }

  @Test
  public void testJulianRoundTrip() {
    Random r = new Random();
    double min = JulianCalendar.EPOCH.getValue();
    double jday = (new JulianDay()).getValue();
    double rand = 0.0;
    for (int i=0; i<1000; ++i) {
      rand = (double)(r.nextInt((int)(jday-min))+min);
      JulianDay jday1 = new JulianDay(rand);
      JulianCalendar a = new JulianCalendar(jday1);
      JulianDay jday2 = toJulianDay(a);
      JulianCalendar b = toJulianCalendar(jday2);
      assertEquals(a,b);
    }
  }
}
