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

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import static com.hypotemoose.cal.util.AlmanacConverter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests {@link com.hypotemoose.cal.date.GregorianCalendar}.
 *
 * @author Chris Engelsma
 * @since 2015.09.23
 */
public class GregorianCalendarTest {
  @Test
  public void checkProperLeapYearRule() {
    assertEquals(false, GregorianCalendar.isLeapYear(1900));
  }

  @Test
  public void sameDatesShouldBeEqual() {
    GregorianCalendar date1 = new GregorianCalendar(1986,9,3);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,3);
    assertEquals(true,date1.equals(date2));
  }

  @Test
  public void differentDatesShouldNotBeEqual() {
    GregorianCalendar date1 = new GregorianCalendar(1987,3,10);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,3);
    assertEquals(false,date1.equals(date2));
  }

  @Test 
  public void dateOneShouldBeBeforeDateTwo() {
    GregorianCalendar date1 = new GregorianCalendar(1986,9,3);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,4);
    assertEquals(true, date1.isBefore(date2));
  }

  @Test 
  public void dateOneShouldBeAfterDateTwo() {
    GregorianCalendar date1 = new GregorianCalendar(1986,9,3);
    GregorianCalendar date2 = new GregorianCalendar(1986,9,4);
    assertEquals(true, date2.isAfter(date1));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int year = 2015;
    int[] result = GregorianCalendar.getDaysPerMonthInYear(year);
    assertEquals(days,result);
  }

  @Test
  public void daysPerMonthShouldBeCorrectInLeapYear() {
    int[] days = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int year = 2000;
    int[] result = GregorianCalendar.getDaysPerMonthInYear(year);
    assertEquals(days,result);
  }

  @Test
  public void testSimpleLeapYears() {
    assertFalse(GregorianCalendar.isLeapYear(1900));
    assertFalse(GregorianCalendar.isLeapYear(1901));
    assertFalse(GregorianCalendar.isLeapYear(1902));
    assertFalse(GregorianCalendar.isLeapYear(1903));
    assertTrue(GregorianCalendar.isLeapYear(1904));
    assertTrue(GregorianCalendar.isLeapYear(1908));
    assertTrue(GregorianCalendar.isLeapYear(1912));
    assertTrue(GregorianCalendar.isLeapYear(2000));
  }

  @Test
  public void prolepticLeapYearsShouldDiffer() {
    int year = 1500;
    boolean ypro = GregorianCalendar.isLeapYear(year,true);
    boolean npro = GregorianCalendar.isLeapYear(year,false);
    assertEquals(false,ypro==npro);
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
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
      assertEquals(a,b);
    }
  }
 
}
