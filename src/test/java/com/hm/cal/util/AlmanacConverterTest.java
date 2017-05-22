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
package com.hm.cal.util;

import com.hm.cal.date.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Tests {@link AlmanacConverter}.
 * @author Chris Engelsma
 * @version 2016.05.17
 */
public class AlmanacConverterTest {

  private static final JulianDay EXPECTED = new JulianDay(2446864.5);


  @Test
  public void julianDayShouldConvertToGregorianCalendar() {
    GregorianCalendar actual = AlmanacConverter.toGregorianCalendar(EXPECTED);
    GregorianCalendar expected = new GregorianCalendar(1987,3,10);
    assertTrue(actual.equals(expected));
  }

  @Test
  public void gregorianCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new GregorianCalendar(1987,3,10));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToJulianCalendar() {
    JulianCalendar actual = AlmanacConverter.toJulianCalendar(EXPECTED);
    JulianCalendar expected = new JulianCalendar(1987,2,25);
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new JulianCalendar(1987,2,25));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToFrenchRepublicanCalendar() {
    FrenchRepublicanCalendar actual = AlmanacConverter.toFrenchRepublicanCalendar(EXPECTED);
    FrenchRepublicanCalendar expected = new FrenchRepublicanCalendar(195,6,2,9);
    assertTrue(actual.equals(expected));
  }

  @Test
  public void frenchRepublicanCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new FrenchRepublicanCalendar(195,6,2,9));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToMayaCalendar() {
    MayaCalendar actual = AlmanacConverter.toMayaCalendar(EXPECTED);
    MayaCalendar expected = new MayaCalendar(12,18,13,15,2);
    assertTrue(actual.equals(expected));
  }

  @Test
  public void mayaCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new MayaCalendar(12,18,13,15,2));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToIslamicCalendar() {
    IslamicCalendar actual = AlmanacConverter.toIslamicCalendar(EXPECTED);
    IslamicCalendar expected = new IslamicCalendar(1407,7,9);
    assertTrue(actual.equals(expected));
  }

  @Test
  public void islamicCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new IslamicCalendar(1407,7,9));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToHebrewCalendar() {
    HebrewCalendar actual = AlmanacConverter.toHebrewCalendar(EXPECTED);
    HebrewCalendar expected = new HebrewCalendar(5747,12,9);
    assertTrue(actual.equals(expected));
  }

  @Test
  public void hebrewCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new HebrewCalendar(5747,12,9));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void persianCalendarShouldConvertToJulianDay() {
    JulianDay actual = AlmanacConverter.toJulianDay(new PersianCalendar(1365,12,19));
    JulianDay expected = EXPECTED;
    assertTrue(actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToPersianCalendar() {
    PersianCalendar actual = AlmanacConverter.toPersianCalendar(EXPECTED);
    PersianCalendar expected = new PersianCalendar(1365,12,19);
    assertTrue(actual.equals(expected));
  }

}
