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

import com.moose.cal.date.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests {@link com.moose.cal.util.Converter}.
 * @author Chris Engelsma
 * @version 2015.10.01
 */
public class ConverterTest {
  @Test
  public void julianDayShouldConvertToGregorianCalendar() {
    GregorianCalendar actual = 
      Converter.toGregorianCalendar(new JulianDay(2446864.5));
    GregorianCalendar expected = new GregorianCalendar(1987,3,10);
    assertEquals( "FAIL: Julian Day -> Gregorian Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void gregorianCalendarShouldConvertToJulianDay() {
    JulianDay actual = 
      Converter.toJulianDay(new GregorianCalendar(1987,3,10));
    JulianDay expected = new JulianDay(2446864.5);
    assertEquals( "FAIL: Gregorian Calendar -> Julian Day is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToJulianCalendar() {
    JulianCalendar actual = 
      Converter.toJulianCalendar(new JulianDay(2446864.5));
    JulianCalendar expected = new JulianCalendar(1987,2,25);
    assertEquals( "FAIL: Julian Day -> Julian Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianCalendarShouldConvertToJulianDay() {
    JulianDay actual = 
      Converter.toJulianDay(new JulianCalendar(1987,2,25));
    JulianDay expected = new JulianDay(2446864.5);
    assertEquals( "FAIL: Julian Calendar -> Julian Day is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToFrenchRepublicanCalendar() {
    FrenchRepublicanCalendar actual =
      Converter.toFrenchRepublicanCalendar(new JulianDay(2446864.5));
    FrenchRepublicanCalendar expected = 
      new FrenchRepublicanCalendar(195,6,2,9);
    assertEquals( "FAIL: Julian Day -> French Republican Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void frenchRepublicanCalendarShouldConvertToJulianDay() {
    JulianDay actual = 
      Converter.toJulianDay(new FrenchRepublicanCalendar(195,6,2,9));
    JulianDay expected = new JulianDay(2446864.5);
    assertEquals( "FAIL: French Republican Calendar -> Julian Day is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToMayaCalendar() {
    MayaCalendar actual = 
      Converter.toMayaCalendar(new JulianDay(2446864.5));
    MayaCalendar expected = new MayaCalendar(12,18,13,15,2);
    assertEquals( "FAIL: Julian Day -> Maya Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void mayaCalendarShouldConvertToJulianDay() {
    JulianDay actual = 
      Converter.toJulianDay(new MayaCalendar(12,18,13,15,2));
    JulianDay expected = new JulianDay(2446864.5);
    assertEquals( "FAIL: Maya Calendar -> Julian Day is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToIslamicCalendar() {
    IslamicCalendar actual = 
      Converter.toIslamicCalendar(new JulianDay(2446864.5));
    IslamicCalendar expected = new IslamicCalendar(1407,7,9);
    assertEquals( "FAIL: Julian Day -> Islamic Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void islamicCalendarShouldConvertToJulianDay() {
    JulianDay actual = 
      Converter.toJulianDay(new IslamicCalendar(1407,7,9));
    JulianDay expected = new JulianDay(2446864.5);
    assertEquals( "FAIL: Islamic Calendar -> Julian Day is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToHebrewCalendar() {
    HebrewCalendar actual = 
      Converter.toHebrewCalendar(new JulianDay(2446864.5));
    HebrewCalendar expected = new HebrewCalendar(5747,12,9);
    assertEquals( "FAIL: Julian Day -> Hebrew Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void hebrewCalendarShouldConvertToJulianDay() {
    JulianDay actual = 
      Converter.toJulianDay(new HebrewCalendar(5747,12,9));
    JulianDay expected = new JulianDay(2446864.5);
    assertEquals( "FAIL: Julian Day -> Hebrew Calendar is broken",
                  true,
                  actual.equals(expected));
  }
}
