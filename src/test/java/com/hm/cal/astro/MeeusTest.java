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
package com.hm.cal.astro;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 * Tests {@link com.hm.cal.astro.Meeus}.
 * @author Chris Engelsma
 * @version 2015.10.01
 */
public class MeeusTest {
  @Test
  public void autumnalEquinoxShouldBeCorrect() {
    // Autumnal Equinox 2015 = 9/23 @ 08:21:33.9 UTC
    int year = 2015;
    Season season = Season.AUTUMN;
    double expected = 2457288.848309;
    double result = Meeus.equinox(year,season);
    assertEquals( expected,
                  result,
                  1.0E-6);
  }

  @Test
  public void springEquinoxShouldBeCorrect() {
    // Spring Equinox 2015 = 3/20 @ 22:46:20.6 UTC
    double expected = 2457102.448850;
    double result = Meeus.equinox(2015,Season.SPRING);
    assertEquals( expected,
                  result,
                  1.0E-6);
  }

  @Test
  public void summerSolsticeShouldBeCorrect() {
    // Summer Solstice 2015 = 6/21 @ 16:39:00.5 UTC
    double expected = 2457195.193756;
    double result = Meeus.equinox(2015,Season.SUMMER);
    assertEquals( expected,
                  result,
                  1.0E-6);
  }

  @Test
  public void winterSolsticeShouldBeCorrect() {
    // Winter Solstice 2015 = 12/22 @ 04:49:20.3 UTC
    double expected = 2457378.700929;
    double result = Meeus.equinox(2015,Season.WINTER);
    assertEquals( expected,
                  result,
                  1.0E-6);
  }

  @Test
  public void lunarPhaseShouldComputeCorrectly() {
    // TODO
  }
}
