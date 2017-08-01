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

import com.hm.cal.util.AlmanacConverter;
import org.joda.time.DateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import static com.hm.cal.util.AlmanacConverter.*;

/**
 * Tests {@link com.hm.cal.date.IndianCivilCalendar}.
 */
public class IndianCivilCalendarTest {
  @Test
  public void sameDatesShouldBeEqual() {
    IndianCivilCalendar date1 = new IndianCivilCalendar(1909,2,20);
    IndianCivilCalendar date2 = new IndianCivilCalendar(1909,2,20);
    assertEquals(date1,date2);
  }

  @Test
  public void testSimpleConversion() {
    GregorianCalendar cal = new GregorianCalendar(79,3,22);
    IndianCivilCalendar date1 = new IndianCivilCalendar(1,1,1);
    IndianCivilCalendar date2 = toIndianCivilCalendar(cal);
    assertEquals(date1,date2);

    GregorianCalendar date3 = toGregorianCalendar(date2);
    assertEquals(cal,date3);
  }

  @Test
  public void testLeapYears() {
    assertFalse(IndianCivilCalendar.isLeapYear(1905));
    assertTrue( IndianCivilCalendar.isLeapYear(1906));
    assertFalse(IndianCivilCalendar.isLeapYear(1907));
    assertFalse(IndianCivilCalendar.isLeapYear(1908));
    assertFalse(IndianCivilCalendar.isLeapYear(1909));
    assertTrue( IndianCivilCalendar.isLeapYear(1910));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    // Test with normal year
    int[] days = new int[] { 30, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30};
    IndianCivilCalendar cal = new IndianCivilCalendar(1909,1,1);
    for (int i=0; i<cal.getNumberOfMonthsInYear(); ++i) {
      cal.setMonth(i+1);
      assertEquals(cal.getNumberOfDaysInMonth(),days[i]);
    }

    // Test with leap year
    days[0] = 31;
    cal =new IndianCivilCalendar(1906,1,1);
    for (int i=0; i<cal.getNumberOfMonthsInYear(); ++i) {
      cal.setMonth(i+1);
      System.out.println(i + ": " + cal.getNumberOfDaysInMonth());
      assertEquals(cal.getNumberOfDaysInMonth(),days[i]);
    }
  }

  /*
  @Test
  public void jodaDateTimeShouldConstruct() {
    IndianCivilCalendar date1 =
      new IndianCivilCalendar(new DateTime(1987,3,10,0,1));
    IndianCivilCalendar date2 = new IndianCivilCalendar(1909,2,20);
    assertEquals(true,date1.equals(date2));
  }

  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    IndianCivilCalendar.getMonthName(0); // No "0" month!
  }

  @Test
  public void testIndianCivilRoundTrip() {
    Random r = new Random();
    double min = IndianCivilCalendar.EPOCH.getValue();
    double jday = (new JulianDay()).getValue();
    double rand;
    for (int i=0; i<1000; ++i) {
      rand = r.nextInt((int)(jday-min))+min;
      JulianDay jday1 = new JulianDay(rand);
      IndianCivilCalendar expected = new IndianCivilCalendar(jday1);
      JulianDay jday2 = toJulianDay(expected);
      IndianCivilCalendar actual = toIndianCivilCalendar(jday2);
      assertEquals(expected,actual);
    }
  }

  @Test
  public void testNextDayWorks() {
    IndianCivilCalendar calendar = new IndianCivilCalendar(1909,2,20);
    JulianDay jd = AlmanacConverter.toJulianDay(calendar).atMidnight();
    for (int i=0; i< 385; ++i) {
      calendar.nextDay();
      jd.nextDay();
      JulianDay converted = AlmanacConverter.toJulianDay(calendar);
      assertEquals(jd.getValue(),converted.getValue(),0.00);
    }
  }
  */
}