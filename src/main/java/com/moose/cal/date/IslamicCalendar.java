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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;

import static com.moose.cal.util.Converter.*;

public class IslamicCalendar implements Almanac {
  public static final String CALENDAR_NAME = "Islamic Calendar";
  public static final JulianDay EPOCH = new JulianDay(1948439.5);

  /**
   * Constructs an Islamic date using today's date.
   */
  public IslamicCalendar() {
    this(new JulianDay());
  }

  /**
   * Constructs an Islamic date using an existing Almanac.
   * @param a an Almanac.
   */
  public IslamicCalendar(Almanac a) {
    this(toIslamicCalendar(a));
  }

  /**
   * Constructs an Islamic date using an existing Islamic Calendar.
   * @param date an Islamic date.
   */
  public IslamicCalendar(IslamicCalendar date) {
    this(date.getYear(),
         date.getMonth(),
         date.getDay());
  }

  /**
   * Constructs an Islamic date using a Joda DateTime.
   * @param dt a Joda DateTime.
   */
  public IslamicCalendar(DateTime dt) {
    this(toIslamicCalendar(new GregorianCalendar(dt)));
  }

  /**
   * Constructs an Islamic date using an existing year, month and day in the
   * Islamic calendar.
   * @param year the Islamic calendar year.
   * @param month the Islamic calendar month.
   * @param day the Islamic calendar day.
   */
  public IslamicCalendar(int year, int month, int day) {
    _day = day;
    _month = month;
    _year = year;
  }

  /**
   * Gets the year.
   * @return the year.
   */
  public int getYear() {
    return _year;
  }

  /**
   * Gets the month.
   * @return the month number
   */
  public int getMonth() {
    return _month;
  }

  /**
   * Gets the day.
   * @return the day
   */
  public int getDay() {
    return _day;
  }

  /**
   * Gets this month's name.
   * @return this month's name.
   */
  public String getMonthName() {
    return _monthNames[_month-1];
  }

  /** 
   * Gets the name of a given month.
   * @param month the month number [1 - 12].
   * @throws IndexOutOfBoundsException
   */
  public static String getMonthName(int month) 
    throws IndexOutOfBoundsException
  {
    return IslamicCalendar.getMonthNames()[month-1];
  }

  /**
   * Gets the names of the months.
   * @return the names of the months.
   */
  public static String[] getMonthNames() {
    return _monthNames;
  }

  @Override
  public String toString() {
    return(CALENDAR_NAME+": "+
           _day+" "+
           getMonthName()+", "+
           _year);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IslamicCalendar))
      return false;
    if (obj == this)
      return true;

    final IslamicCalendar date = (IslamicCalendar) obj;
    return new EqualsBuilder()
      .append(_day,date.getDay())
      .append(_month,date.getMonth())
      .append(_year,date.getYear())
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(_day)
      .append(_month)
      .append(_year)
      .toHashCode();
  }

//////////////////////////////////////////////////////////////////////////////
// private

  private int _day;
  private int _month;
  private int _year;
  
  private static final String[] _dayNames = 
  {
    "al-Ahad", 
    "al-Ithnayn",
    "ath-Thalatha",
    "al-Arbia",
    "al-Khamis",
    "al-Jumu'ah",
    "as-Sabt"
  };

  private static final String[] _monthNames =
  {
    "Muharram",
    "Safar",
    "Rabi' al-Awwal",
    "Rabi' ath-Thani",
    "Jumada al-Ula",
    "Jumada ath-Thaniyah",
    "Rajab",
    "Sha'ban",
    "Ramadan",
    "Shawwal",
    "Dhu al-Qa'dah",
    "Dhu al-Hijjah"
  };
}
