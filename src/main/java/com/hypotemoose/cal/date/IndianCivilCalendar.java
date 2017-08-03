/*****************************************************************************
 * Copyright 2017 Chris Engelsma
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/
package com.hypotemoose.cal.date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;
import java.util.Calendar;

import static com.hypotemoose.cal.constants.CalendarConstants.IndianCivilCalendarConstants.monthNames;
import static com.hypotemoose.cal.util.AlmanacConverter.toIndianCivilCalendar;

/**
 * An Indian Civil Calendar.
 * <p>
 *
 * @author Chris Engelsma
 * @since 2017.08.03
 */
public class IndianCivilCalendar extends Almanac {

  public static final String CALENDAR_NAME = "Indian Civil Calendar";
  public static final JulianDay EPOCH = new JulianDay(1749994.5);

  /**
   * Constructs a new Indian Civil Calendar for today's date.
   */
  public IndianCivilCalendar() {
    this(new DateTime());
  }

  /**
   * Constructs a new Indian Civil Calendar using a {@link java.util.Calendar}.
   * @param cal a calendar.
   */
  public IndianCivilCalendar(Calendar cal) {
    this(new GregorianCalendar(cal));
  }

  /**
   * Constructs a new Indian Civil Calendar using a {@link org.joda.time.DateTime}.
   * @param dt a {@link org.joda.time.DateTime}.
   */
  public IndianCivilCalendar(DateTime dt) {
    this(toIndianCivilCalendar(new GregorianCalendar(dt)));
  }

  /**
   * Constructs a new Indian Civil Calendar from another Almanac.
   * @param a an almanac.
   */
  public IndianCivilCalendar(Almanac a) {
    this(toIndianCivilCalendar(a));
  }

  /**
   * Constructs a new Indian Civil Calendar from another Indian Civil Calendar.
   * @param date a calendar.
   */
  public IndianCivilCalendar(IndianCivilCalendar date) {
    this(date.getYear(), date.getMonth(), date.getDay());
  }

  /**
   * Constructs a new Indian Civil calendar.
   * @param year  a year.
   * @param month a month [1 - 12].
   * @param day   a day.
   */
  public IndianCivilCalendar(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  /**
   * Determines if a given Indian Civil year is a leap year.
   * @param year  a year in the Indian Civil Calendar.
   * @return      true, if is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    return GregorianCalendar.isLeapYear(year + 78, false);
  }

  /**
   * Determines if this calendar falls in a leap year.
   * @return true, if this calendar falls in a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return isLeapYear(year);
  }

  /**
   * Gets the name of a given month.
   *
   * @param month the month number [1-12].
   * @return the name of the month.
   * @throws IndexOutOfBoundsException
   */
  public static String getMonthName(int month)
    throws IndexOutOfBoundsException {
    return monthNames[month - 1];
  }

  /**
   * Gets the name of this calendar's month.
   * @return the name of this calendar's month.
   */
  public String getMonthName() {
    return IndianCivilCalendar.getMonthName(this.month);
  }

  @Override
  public String getDate() {
    return getDay() + " " + getMonthName() + ", " + getYear();
  }

  @Override
  public String getName() {
    return CALENDAR_NAME;
  }

  @Override
  public int getNumberOfDaysInMonth() {
    if (this.month==1) return this.isLeapYear() ? 31 : 30;
    return (this.month >= 7) ? 30 : 31;
  }

  @Override
  public int getNumberOfDaysInWeek() {
    return 7;
  }

  @Override
  public int getNumberOfMonthsInYear() {
    return 12;
  }

  @Override
  public void set(Almanac a) {
    IndianCivilCalendar cal = toIndianCivilCalendar(a);
    this.year = cal.getYear();
    this.month = cal.getMonth();
    this.day = cal.getDay();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IndianCivilCalendar))
      return false;
    if (obj == this)
      return true;

    final IndianCivilCalendar date = (IndianCivilCalendar) obj;
    return new EqualsBuilder()
      .append(this.day, date.getDay())
      .append(this.month, date.getMonth())
      .append(this.year, date.getYear())
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(this.day)
      .append(this.month)
      .append(this.year)
      .toHashCode();
  }

}
