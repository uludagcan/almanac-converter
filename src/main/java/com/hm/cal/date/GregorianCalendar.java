/*****************************************************************************
 * Copyright 2015 Hypotemoose, Inc.
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
package com.hm.cal.date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import java.util.Calendar;

import static com.hm.cal.constants.CalendarConstants.GregorianCalendarConstants.monthNames;
import static com.hm.cal.constants.CalendarConstants.GregorianCalendarConstants.weekDayNames;
import static com.hm.cal.util.AlmanacConverter.toGregorianCalendar;

/**
 * A Gregorian Calendar Date.
 * <p>
 * The Gregorian Calendar is internationally the most widely used civil
 * calendar. It was named for Pope Gregory XIII who introduced it on
 * October 15, 1582. The calendar was a refinement to the Julian Calendar,
 * with the motivation of setting the Easter holiday to a specific date
 * instead of the spring equinox, which naturally drifted dates.
 * <p>
 * Each year is divided into 12 months, with a varied number of days per
 * month. To account for the drift in seasons, a leap year occurs which
 * introduces an additional day in February. These leap years happen every
 * year that's divisible by 4, except years that are divisible by 100 but
 * not divisible by 400. For example: 1700, 1800 and 1900 are NOT leap
 * years, but 2000 is a leap year.
 *
 * @author Chris Engelsma
 * @version 2015.11.05
 */
public final class GregorianCalendar extends Almanac {

  public static final String CALENDAR_NAME = "Gregorian Calendar";
  public static final JulianDay EPOCH = new JulianDay(2299160.5);

  /**
   * Constructs a Gregorian Calendar using today's date.
   */
  public GregorianCalendar() {
    this(Calendar.getInstance());
  }

  /**
   * Constructs a Greogrian Calendar using a Java calendar date.
   *
   * @param cal a java util calendar.
   */
  public GregorianCalendar(Calendar cal) {
    this(cal.get(Calendar.YEAR),
      cal.get(Calendar.MONTH) + 1,
      cal.get(Calendar.DAY_OF_MONTH));
  }

  /**
   * Constructs a Gregorian Calendar from another Almanac.
   *
   * @param date another Almanac
   */
  public GregorianCalendar(Almanac date) {
    this(toGregorianCalendar(date));
  }

  /**
   * Constructs a Gregorian Calendar given another Gregorian Calendar.
   *
   * @param date A Gregorian Calendar.
   */
  public GregorianCalendar(GregorianCalendar date) {
    this(date.getYear(),
      date.getMonth(),
      date.getDay());
  }

  /**
   * Constructs a Gregorian Calendar given a Joda DateTime.
   *
   * @param dt a Joda DateTime.
   */
  public GregorianCalendar(DateTime dt) {
    this(dt.getYear(),
      dt.getMonthOfYear(),
      dt.getDayOfMonth());
  }

  /**
   * Constructs a Gregorian Calendar from a given day, month and year.
   *
   * @param year  The year.
   * @param month The month.
   * @param day   The day.
   */
  public GregorianCalendar(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  /**
   * Returns today's date as a string.
   * Convenience static method.
   *
   * @return today's date.
   */
  public static String asToday() {
    return (new GregorianCalendar()).toString();
  }

  /**
   * Determines whether a given year is a leap year.
   * <p>
   * This method optionally determines whether the current date exists
   * before or after the creation of the Gregorian calendar, and follows
   * that calendar's leap year rule. As a result, there are "missing dates"
   * (October 4 - 14, 1582).
   *
   * @param year         a given year.
   * @param useProleptic true, if using a proleptic calendar; false,
   *                     otherwise.
   * @return true, if year is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year, boolean useProleptic) {
    GregorianCalendar epoch = new GregorianCalendar(EPOCH);
    GregorianCalendar date = new GregorianCalendar(year, 1, 1);

    if (useProleptic && datesAreChronological(date, epoch))
      return JulianCalendar.isLeapYear(year);

    if (Math.abs(year) % 4 == 0) {
      if (Math.abs(year) % 400 == 0) return true;
      if (Math.abs(year) % 100 == 0) return false;
    }
    return false;
  }

  /**
   * Determines whether a given year is a leap year.
   *
   * @param year a given year.
   * @return true, if is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    return GregorianCalendar.isLeapYear(year, true);
  }

  /**
   * Gets a month name.
   *
   * @param month the month number [1-12].
   * @return the name of the month.
   * @throws IndexOutOfBoundsException
   */
  public static String getMonthName(int month) throws IndexOutOfBoundsException {
    return monthNames[month - 1];
  }

  /**
   * Gets the total number of days in a given month and year.
   *
   * @param month the month.
   * @param year  the year.
   * @return the number of days in the month.
   */
  public static int getNumberOfDaysInMonth(int year, int month) {
    if (month == 4 || month == 6 || month == 9 || month == 11)
      return 30;
    if (month == 2) {
      if (!GregorianCalendar.isLeapYear(year)) return 28;
      else return 29;
    }
    return 31;
  }

  /**
   * Gets an array of month-lengths for a given year.
   *
   * @param year a year.
   * @return an array[12] of month-lengths for a given year.
   */
  public static int[] getDaysPerMonthInYear(int year) {
    int[] days = new int[12];
    for (int i = 0; i < 12; ++i)
      days[i] = GregorianCalendar.getNumberOfDaysInMonth(year, i + 1);
    return days;
  }

  /**
   * Determines whether this date's year is a leap year.
   * <p>
   * This method will assume that the leap year is proleptic, meaning it will
   * back-project the Gregorian Calendar to before it was implemented, making
   * it not historically accurate prior to October 15, 1582.
   *
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return GregorianCalendar.isLeapYear(this.year, true);
  }

  /**
   * Determines whether this date's year is a leap year.
   * <p>
   * This method requires the user to specify whether to consider a proleptic
   * calendar or not.
   *
   * @param useProleptic true, if making this calendar proleptic; false,
   *                     otherwise.
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear(boolean useProleptic) {
    return GregorianCalendar.isLeapYear(this.year, useProleptic);
  }

  /**
   * Gets the month name.
   *
   * @return the name of the month.
   */
  public String getMonthName() {
    return GregorianCalendar.getMonthName(this.month);
  }

  /**
   * Gets the total number of days in a given month for this year.
   *
   * @param month the month.
   * @return the number of days in a month of this year.
   */
  public int getNumberOfDaysInMonth(int month) {
    return GregorianCalendar.getNumberOfDaysInMonth(this.year, this.month);
  }

  /**
   * Gets the months.
   *
   * @return the months.
   */
  public String[] getMonths() {
    return monthNames;
  }

  /**
   * Gets the weekdays.
   *
   * @return the weekdays.
   */
  public String[] getWeekDays() {
    return weekDayNames;
  }

  /**
   * Sets this calendar.
   *
   * @param a an almanac.
   */
  @Override
  public void set(Almanac a) {
    GregorianCalendar cal = toGregorianCalendar(a);
    this.year = cal.getYear();
    this.month = cal.getMonth();
    this.day = cal.getDay();
  }

  /**
   * Gets the weekday name for this date.
   *
   * @return the weekday name.
   */
  @Override
  public String getWeekDay() {
    return weekDayNames[getWeekDayNumber()];
  }

  /**
   * Gets the total number of days for this month and year.
   *
   * @return the number of days in this month and year.
   */
  @Override
  public int getNumberOfDaysInMonth() {
    return GregorianCalendar.getNumberOfDaysInMonth(this.year, this.month);
  }

  /**
   * Gets the number of days in a week.
   *
   * @return the number of days in a week.
   */
  @Override
  public int getNumberOfDaysInWeek() {
    return 7;
  }

  /**
   * Gets the number of months in a year.
   *
   * @return the number of months in a year.
   */
  @Override
  public int getNumberOfMonthsInYear() {
    return 12;
  }

  /**
   * Gets an array of month-lengths for this year.
   *
   * @return an array[12] of month-lengths for this year.
   */
  public int[] getDaysPerMonthInYear() {
    return GregorianCalendar.getDaysPerMonthInYear(getYear());
  }

  /**
   * Gets the date.
   *
   * @return the date.
   */
  @Override
  public String getDate() {
    return new String(getMonthName() + " " +
      getDay() + ", " +
      getYear());
  }

  @Override
  public String toString() {
    return new String(CALENDAR_NAME + ": " + getDate());
  }

  @Override
  public String getName() {
    return CALENDAR_NAME;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GregorianCalendar))
      return false;
    if (obj == this)
      return true;

    final GregorianCalendar date = (GregorianCalendar) obj;
    return new EqualsBuilder()
      .append(this.year, date.getYear())
      .append(this.month, date.getMonth())
      .append(this.day, date.getDay())
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(this.year)
      .append(this.month)
      .append(this.day)
      .toHashCode();
  }
}
