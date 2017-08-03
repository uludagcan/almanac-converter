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
package com.hypotemoose.cal.util;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import com.hypotemoose.cal.constants.Holiday;
import org.testng.annotations.Test;

import com.hypotemoose.cal.date.*;

/**
 * Tests {@link com.hypotemoose.cal.util.HolidayCalculator}.
 * Calculations are tested against known dates.
 * @author Chris Engelsma
 * @version 2015.11.06
 */
public class HolidayCalculatorTest {

  @Test
  public void easterShouldComputeCorrectly() {
    test(Holiday.EASTER);
  }

  @Test
  public void ramadanShouldComputeCorrectly() {
    test(Holiday.RAMADAN);
  }

  @Test
  public void eidAlFitrShouldComputeCorrectly() {
    test(Holiday.EID_AL_FITR);
  }

  @Test
  public void eidAlAdhaShouldComputeCorrectly() {
    test(Holiday.EID_AL_ADHA);
  }


  private void test(Holiday h) {
    GregorianCalendar[] expected = _hol[h.getValue()];
    int n = expected.length;
    GregorianCalendar[] actual = new GregorianCalendar[n];
    for (int i=0; i<n; ++i) 
      actual[i] = 
        new GregorianCalendar(HolidayCalculator.get(h,expected[i].getYear()));
    assertArrayEquals(expected,actual);
  }

  final static GregorianCalendar[][] _hol = 
  {
      { // 0  Ash Wednesday
    },{ // 1  Palm Sunday
    },{ // 2  Holy Thursday
    },{ // 3  Good Friday
    },{ // 4  Easter
      new GregorianCalendar(2000,4,23),
      new GregorianCalendar(2001,4,15),
      new GregorianCalendar(2002,3,31),
      new GregorianCalendar(2003,4,20),
      new GregorianCalendar(2004,4,11),
      new GregorianCalendar(2005,3,27),
      new GregorianCalendar(2006,4,16),
      new GregorianCalendar(2007,4, 8),
      new GregorianCalendar(2008,3,23),
      new GregorianCalendar(2009,4,12)
    },{ // 5  Ascension
    },{ // 6  Pentecost
    },{ // 7  Trinity Sunday
    },{ // 8  Advent
    },{ // 9  Ashura
    },{ // 10 Ramadan
      new GregorianCalendar(2000,11,28),
      new GregorianCalendar(2001,11,17),
      new GregorianCalendar(2002,11, 6),
      new GregorianCalendar(2003,10,27),
      new GregorianCalendar(2004,10,15),
      new GregorianCalendar(2005,10, 4),
      new GregorianCalendar(2006, 9,24),
      new GregorianCalendar(2007, 9,13),
      new GregorianCalendar(2008, 9, 2),
      new GregorianCalendar(2009, 8,22)     
    },{ // 12 Eid al-Fitr
      new GregorianCalendar(2000, 1, 8),
      new GregorianCalendar(2001,12,17),
      new GregorianCalendar(2002,12, 6),
      new GregorianCalendar(2003,11,26),
      new GregorianCalendar(2004,11,14),
      new GregorianCalendar(2005,11, 3),
      new GregorianCalendar(2006,10,24),
      new GregorianCalendar(2007,10,13),
      new GregorianCalendar(2008,10, 2),
      new GregorianCalendar(2009, 9,21)
    },{ // 13 Eid al-Adha
      new GregorianCalendar(2000, 3,16),
      new GregorianCalendar(2001, 3, 6),
      new GregorianCalendar(2002, 2,23),
      new GregorianCalendar(2003, 2,12),
      new GregorianCalendar(2004, 2, 2),
      new GregorianCalendar(2005, 1,21),
      new GregorianCalendar(2006, 1,10),
      new GregorianCalendar(2007,12,20),
      new GregorianCalendar(2008,12, 9),
      new GregorianCalendar(2009,11,28)
    },{ // 14 Passover
    },{ // 15 Shauvot
    },{ // 16 Rosh Hashanah
    },{ // 17 Yom Kippur
    },{ // 18 Sukkot
    },{ // 19 Shemini Atzeret
    },{ // 20 New Years Day
    },{ // 21 MLK
    },{ // 22 Washington's birthday
    },{ // 23 Memorial Day
    },{ // 24 Independence Day
    },{ // 25 Labor Day
    },{ // 26 Columbus Day
    },{ // 27 Veterans Day
    },{ // 28 Thanksgiving
    },{ // 29 Christmas
  }};
}
