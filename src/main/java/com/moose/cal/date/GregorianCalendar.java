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
package com.moose.cal.date;

import java.lang.Math;
import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;

import com.moose.cal.util.*;
import static com.moose.cal.util.Converter.*;

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
 * @author Chris Engelsma
 * @version 2015.09.24
 */
public final class GregorianCalendar implements Almanac {
  public static final String CALENDAR_NAME = "Gregorian Calendar";
  public static final JulianDay EPOCH = new JulianDay(2299160.5);

  /**
   * Constructrs a Gregorian Date using today's date.
   */
  public GregorianCalendar() {
    this(new DateTime());
  }

  /**
   * Constructs a Gregorian Date from another Almanac.
   * @param date another Almanac
   */
  public GregorianCalendar(Almanac date) {
    this(toGregorianCalendar(date));
  }

  /**
   * Constructs a Gregorian Date given another Gregorian Date.
   * @param date A Gregorian Date.
   */
  public GregorianCalendar(GregorianCalendar date) {
    this(date.getYear(),
         date.getMonth(),
         date.getDay());
  }

  /**
   * Constructs a Gregorian Date given a Joda DateTime.
   * @param dt a Joda DateTime.
   */
  public GregorianCalendar(DateTime dt) {
    this(dt.getYear(),
         dt.getMonthOfYear(),
         dt.getDayOfMonth());
  }

  /**
   * Constructs a Gregorian Date from a given day, month and year.
   * @param year The year.
   * @param month The month.
   * @param day The day.
   */
  public GregorianCalendar(int year, int month, int day) {
    _day   = day;
    _month = month;
    _year  = year;
  }

  /**
   * Returns today's date as a string.
   * Convenience static method.
   * @return today's date.
   */
  public static String asToday() {
    return (new GregorianCalendar()).getDate();
  }

  /**
   * Determines whether this date's year is a leap year.
   * <p>
   * This method will assume that the leap year is proleptic, meaning it will
   * back-project the Gregorian Calendar to before it was implemented, making
   * it not historically accurate prior to October 15, 1582.
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return GregorianCalendar.isLeapYear(_year,true);
  }

  /**
   * Determines whether this date's year is a leap year.
   * <p>
   * This method requires the user to specify whether to consider a proleptic
   * calendar or not.
   * @param useProleptic true, if making this calendar proleptic; false,
   * otherwise.
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear(boolean useProleptic) {
    return GregorianCalendar.isLeapYear(_year,useProleptic);
  }

  /**
   * Determines whether a given year is a leap year.
   * <p>
   * This method optionally determines whether the current date exists 
   * before or after the creation of the Gregorian calendar, and follows 
   * that calendar's leap year rule. As a result, there are "missing dates" 
   * (October 4 - 14, 1582).
   * @param year a given year.
   * @param useProleptic true, if using a proleptic calendar; false,
   * otherwise.
   * @return true, if year is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year, boolean useProleptic) {
    GregorianCalendar epoch = new GregorianCalendar(EPOCH);
    GregorianCalendar date = new GregorianCalendar(year,1,1);

    if (useProleptic && GregorianCalendar.datesAreChronological(date,epoch)) 
      return JulianCalendar.isLeapYear(year);

    if (Math.abs(year)%4==0) {
      if (Math.abs(year)%400==0) return true;
      if (Math.abs(year)%100==0) return false;
    }
    return false;
  }

  /**
   * Determines whether a given year is a leap year.
   * @param year a given year.
   * @return true, if is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    return GregorianCalendar.isLeapYear(year,true);
  }

  /**
   * Gets a month name.
   * @param month the month number [1-12].
   * @throws IndexOutOfBoundsException
   * @return the name of the month.
   */  
  public static String getMonthName(int month) 
    throws IndexOutOfBoundsException 
  {
    return GregorianCalendar.getMonthNames()[month-1];
  }

  /**
   * Gets the month name.
   * @param month the month number [1-12].
   * @return the name of the month.
   */
  public String getMonthName() {
    return GregorianCalendar.getMonthName(_month);
  }

  /**
   * Gets the month names.
   * @return the month names.
   */
  public static String[] getMonthNames() {
    String[] months = new String[12];
    for (int i=0; i<12; ++i)
      months[i] = _monthNames[2*i];
    return months;
  }

  /**
   * Gets the short month names.
   * @return the short month names.
   */
  public static String[] getShortMonthNames() {
    String[] months = new String[12];
    for (int i=0; i<12; ++i)
      months[i] = _monthNames[2*i+1];
    return months;
  }

  /**
   * Gets the total number of days in a given month and year.
   * @param month the month.
   * @param year the year. 
   * @return the number of days in the month.
   */
  public static int getDaysInMonth(int month, int year) {
    if (month==4  || month==6  || month==9  || month==11) 
      return 30;
    if (month==2) {
      if (!GregorianCalendar.isLeapYear(year)) return 28;
      else return 29;
    }
    return 31;
  }

  /**
   * Gets the total number of days in a given month for this year.
   * @param month the month.
   * @return the number of days in a month of this year.
   */
  public int getDaysInMonth(int month) {
    return GregorianCalendar.getDaysInMonth(month,getYear());
  }

  /**
   * Gets the total number of days for this month and year.
   * @return the number of days in this month and year.
   */
  public int getDaysInMonth() {
    return GregorianCalendar.getDaysInMonth(getMonth(),getYear());
  }

  /**
   * Gets an array of month-lengths for a given year.
   * @param year a year.
   * @return an array[12] of month-lengths for a given year.
   */
  public static int[] getDaysPerMonthInYear(int year) {
    int[] days = new int[12];
    for (int i=0; i<12; ++i) 
      days[i] = GregorianCalendar.getDaysInMonth(i+1,year);
    return days;
  }

  /**
   * Gets an array of month-lengths for this year.
   * @return an array[12] of month-lengths for this year.
   */
  public int[] getDaysPerMonthInYear() {
    return GregorianCalendar.getDaysPerMonthInYear(getYear());
  }

  /**
   * Gets the day.
   * @return the day.
   */ 
  public int getDay() { return _day; }
   
  /**
    * Gets the month.
    * @return the month.
   */
  public int getMonth() { return _month; }  
  
  /**
   * Gets the day.
   * @return the day.
   */
  public int getYear() { return _year; }
  
  /**
   * Gets this date with a specified format.
   * @param format an output format.
   * @return the formatted date.
   */
  public String getDate(String format) {
    return getDate(new AlmanacFormat(format));
  }

  /**
   * Returns the date with a specified format.
   * <p>
   * Note: Not yet implemented.
   * @param format an output format.
   * @return the formatted date.
   */
  public String getDate(AlmanacFormat format) {
    return format.format(this);
  }

  /**
   * Gets this date.
   * @return the date.
   */
  @Override
  public String getDate() {
    return getDate("M-dd-yyyy");
  }

  /**
   * Prints this date with a simple pre-defined format.
   * @return this calendar as a string.
   */ 
  @Override
  public String toString() {
    return(CALENDAR_NAME+": " + 
           getMonthName()+" "+
           getDay()+", "+
           getYear());
  }

  /**
   * Checks if this date is before another Gregorian Date.
   * @param date A Gregorian Date.
   * @return true, if before; false, otherwise.
   */ 
  public boolean isBefore(GregorianCalendar date) {
    double year = date.getYear();
    double month = date.getMonth();
    double day = date.getDay();
    return ((_year<year) ||
            (_year==year && _month<month) ||
            (_year==year && _month==month && _day<day));
  }

  /**
   * Checks if two dates are in chronological order.
   * @param firstDate the first date.
   * @param secondDate the second date.
   * @return true, if firstDate comes before secondDate; false, otherwise.
   */
  public static boolean datesAreChronological(
    GregorianCalendar firstDate, GregorianCalendar secondDate) 
  {
    double year1  = firstDate.getYear();
    double month1 = firstDate.getMonth();
    double day1   = firstDate.getDay();

    double year2  = secondDate.getYear();
    double month2 = secondDate.getMonth();
    double day2   = secondDate.getDay();

    return ((year1<year2) ||
            (year1==year2 && month1<month2) ||
            (year1==year2 && month1==month2 && day1<day2));
  }

  /**
   * Checks if this date is after another Gregorian Date.
   * @param date A Gregorian Date.
   * @return true, if after; false, otherwise.
   */ 
  public boolean isAfter(GregorianCalendar date) {
    double year = date.getYear();
    double month = date.getMonth();
    double day = date.getDay();
    return ((_year>year) ||
            (_year==year && _month>month) ||
            (_year==year && _month==month && _day>day));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GregorianCalendar))
      return false;
    if (obj == this)
      return true;
      
    final GregorianCalendar date = (GregorianCalendar) obj;
    return new EqualsBuilder()
      .append(_year, date.getYear())
      .append(_month, date.getMonth())
      .append(_day, date.getDay())
      .isEquals();
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(_year)
      .append(_month)
      .append(_day)
      .toHashCode();
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private int _year;
  private int _day;
  private int _month;

  private static final String[] _monthNames = 
  {
    "January",   "Jan",
    "February",  "Feb",
    "March",     "Mar",
    "April",     "Apr",
    "May",       "May",
    "June",      "Jun",
    "July",      "Jul",
    "August",    "Aug",
    "September", "Sept",
    "October",   "Oct",
    "November",  "Nov",
    "December",  "Dec"
  };

  private static final String[] _eras = 
  {
    "BC","BCE",
    "AD","CE"
  };
}
