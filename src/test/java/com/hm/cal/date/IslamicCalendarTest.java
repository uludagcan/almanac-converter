/****************************************************************************
 * Copyright (c) 2016, Hypotemoose, Inc. - All Rights Reserved.
 * Unauthorized copying of this file via any medium is strictly prohibited.
 * Proprietary and confidential.
 ****************************************************************************/
package com.hm.cal.date;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests {@link com.hm.cal.date.IslamicCalendar}.
 *
 * @author Chris Engelsma
 * @since 2016.05.19
 */
public class IslamicCalendarTest {

  @Test
  public void leapYearRuleShouldWorkForWestIslamic() {
    int[] leapYears = { 2, 5, 7, 10, 13, 16, 18, 21, 24, 26, 29 };
    for (int i=1; i<31; ++i) {
      int actual = IslamicCalendar.getNumberOfDaysInYear(i, IslamicCalendar.LeapYearRule.WEST_ISLAMIC);
      if (ArrayUtils.contains(leapYears, i)) {
        assertEquals(355, actual);
      } else {
        assertEquals(354, actual);
      }
    }
  }

  @Test
  public void daysPerMonthShouldBeCorrectEastIslamic() {
    int[] leapYears = { 2, 5, 7, 10, 13, 15, 18, 21, 24, 26, 29 };
    for (int i=1; i<31; ++i) {
      int actual = IslamicCalendar.getNumberOfDaysInYear(i, IslamicCalendar.LeapYearRule.EAST_ISLAMIC);
      if (ArrayUtils.contains(leapYears, i)) {
        assertEquals(355, actual);
      } else {
        assertEquals(354, actual);
      }
    }
  }

  @Test
  public void daysPerMonthShouldBeCorrectFatimid() {
    int[] leapYears = { 2, 5, 8, 10, 13, 16, 19, 21, 24, 27, 29 };
    for (int i=1; i<31; ++i) {
      int actual = IslamicCalendar.getNumberOfDaysInYear(i, IslamicCalendar.LeapYearRule.TAIYABI_ISMAILI);
      if (ArrayUtils.contains(leapYears, i)) {
        assertEquals(355, actual);
      } else {
        assertEquals(354, actual);
      }
    }
  }

  @Test
  public void daysPerMonthShouldBeCorrectHabashAlHasib() {
    int[] leapYears = { 2, 5, 8, 11, 13, 16, 19, 21, 24, 27, 30 };
    for (int i=1; i<31; ++i) {
      int actual = IslamicCalendar.getNumberOfDaysInYear(i, IslamicCalendar.LeapYearRule.HABASH_AL_HASIB);
      if (ArrayUtils.contains(leapYears, i)) {
        assertEquals(355, actual);
      } else {
        assertEquals(354, actual);
      }
    }
  }
}
