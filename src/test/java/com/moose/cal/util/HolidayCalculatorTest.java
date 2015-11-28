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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import com.moose.cal.date.*;

/**
 * Tests {@link com.moose.cal.date.HolidayCalculator}.
 * Calculations are tested against known dates.
 * @author Chris Engelsma
 * @version 2015.11.06
 */
public class HolidayCalculatorTest {

  @Test
  public void easterShouldComputeCorrectly() {
    int n = 10;
    GregorianCalendar[] actual = new GregorianCalendar[n];
    GregorianCalendar[] expected = new GregorianCalendar[] {
      new GregorianCalendar(2000,4,23),
      new GregorianCalendar(2001,4,15),
      new GregorianCalendar(2002,3,31),
      new GregorianCalendar(2003,4,20),
      new GregorianCalendar(2004,4,11),
      new GregorianCalendar(2005,3,27),
      new GregorianCalendar(2006,4,16),
      new GregorianCalendar(2007,4, 8),
      new GregorianCalendar(2008,3,23),
      new GregorianCalendar(2009,4,12)};

    for (int i=0; i<n; ++i) 
      actual[i] = new GregorianCalendar(HolidayCalculator.getEaster(i+2000));
    assertArrayEquals("FAIL: Easter not being computed correctly",
                      expected,
                      actual);
  }
}
