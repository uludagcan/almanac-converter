/****************************************************************************
 * Copyright (c) 2016, Hypotemoose, Inc. - All Rights Reserved.
 * Unauthorized copying of this file via any medium is strictly prohibited.
 * Proprietary and confidential.
 ****************************************************************************/
package com.hm.cal.date;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link com.hm.cal.date.IslamicCalendar}.
 *
 * @author Chris Engelsma
 * @since 2016.05.19
 */
public class IslamicCalendarTest {

  @Test
  public void leapYearRuleShouldWorkForBase16() {
    int[] leapYears = { 2, 5, 7, 10, 13, 16, 18, 21, 24, 26, 29 };
    for (int i=1; i<31; ++i) {
      int actual = IslamicCalendar.getNumberOfDaysInYear(i, IslamicCalendar.LeapYearRule.BASE_16);
      if (ArrayUtils.contains(leapYears, i)) {
        assertEquals(355, actual);
      } else {
        assertEquals(354, actual);
      }
    }
  }

  @Test
  public void daysPerMonthShouldBeCorrectBase15() {
    int[] leapYears = { 2, 5, 7, 10, 13, 15, 18, 21, 24, 26, 29 };
    for (int i=1; i<31; ++i) {
      int actual = IslamicCalendar.getNumberOfDaysInYear(i, IslamicCalendar.LeapYearRule.BASE_15);
      if (ArrayUtils.contains(leapYears, i)) {
        assertEquals(355, actual);
      } else {
        assertEquals(354, actual);
      }
    }
  }
}
