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
package com.hm.cal.date;

import java.util.Random;
import org.joda.time.DateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import static com.hm.cal.util.AlmanacConverter.*;

/**
 * Tests {@link com.hm.cal.date.GregorianCalendar}.
 * @author Chris Engelsma
 * @version 2015.09.23
 */
public class GregorianCalendarTest {
  @Test
  public void checkProperLeapYearRule() {
    assertEquals( "FAIL: 1900 must be normal year",
                  false,
                  GregorianCalendar.isLeapYear(1900));
  }

  @Test
  public void sameDatesShouldBeEqual() {
    GregorianCalendar date1 = new GregorianCalendar(1986,9,3);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,3);
    assertEquals( "FAIL: Dates should be equal",
                  true,
                  date1.equals(date2));
  }

  @Test
  public void differentDatesShouldNotBeEqual() {
    GregorianCalendar date1 = new GregorianCalendar(1987,3,10);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,3);
    assertEquals( "FAIL: Dates should not be equal",
                  false,
                  date1.equals(date2));
  }

  @Test 
  public void dateOneShouldBeBeforeDateTwo() {
    GregorianCalendar date1 = new GregorianCalendar(1986,9,3);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,4);
    assertEquals( "FAIL: Dates should be in chronological order",
                  true,
                  date1.isBefore(date2));
  }

  @Test 
  public void dateOneShouldBeAfterDateTwo() {
    GregorianCalendar date1 = new GregorianCalendar(1986,9,3);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,4);
    assertEquals( "FAIL: Dates should be in chronological order",
                  true,
                  date2.isAfter(date1));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int year = 2015;
    int[] result = GregorianCalendar.getDaysPerMonthInYear(year);
    assertArrayEquals("FAIL: Days of month aren't correct",
                      days,
                      result);
  }

  @Test
  public void daysPerMonthShouldBeCorrectInLeapYear() {
    int[] days = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int year = 2000;
    int[] result = GregorianCalendar.getDaysPerMonthInYear(year);
    assertArrayEquals("FAIL: Days of month aren't correct",
                      days,
                      result);
  }

  @Test
  public void prolepticLeapYearsShouldDiffer() {
    int year = 1500;
    boolean ypro = GregorianCalendar.isLeapYear(year,true);
    boolean npro = GregorianCalendar.isLeapYear(year,false);
    assertEquals( "FAIL: Proleptic leap years should differ",
                  false,
                  ypro==npro);
  }

  @Test
  public void jodaDateTimeShouldConstruct() {
    GregorianCalendar date1 = 
      new GregorianCalendar(new DateTime(1986,9,3,0,1));
    GregorianCalendar date2 = new GregorianCalendar(1986,9,3);
    assertEquals( "FAIL: Date should construct from Joda DateTime",
                  true,
                  date1.equals(date2));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    String month = GregorianCalendar.getMonthName(0); // No "0" month!
  }

  @Test
  public void testGregorianRoundTrip() {
    Random r = new Random();
    double min = GregorianCalendar.EPOCH.getValue();
    double jday = (new JulianDay()).getValue();
    double rand = 0.0;
    for (int i=0; i<1000; ++i) {
      rand = (double)(r.nextInt((int)(jday-min))+min);
      JulianDay jday1 = new JulianDay(rand);
      GregorianCalendar a = new GregorianCalendar(jday1);
      JulianDay jday2 = toJulianDay(a);
      GregorianCalendar b = toGregorianCalendar(jday2);
      assertEquals( "FAIL: Gregorian Calendar failed round trip",
                    a,
                    b);
    }
  }
 
}
