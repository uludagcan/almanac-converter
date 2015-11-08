/*****************************************************************************
Copyright 2015 Hypotemoose, Inc.

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
import java.util.Calendar;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;

import com.moose.cal.util.*;
import static com.moose.cal.util.Converter.*;

/**
 * A Julian Day.
 * <p>
 * The Julian Day is the continuous count of days since the beginning of the
 * Julian Period used primarily by astronomers. The Julian Period is a
 * chronological interval of 7980 years beginning in 4713 BC, and has been
 * used since 1583 to convert between different calendars. The next Julian 
 * Period begins in the year 3268 AD.
 * @author Chris Engelsma
 * @version 2015.08.07
 */
public final class JulianDay extends Almanac {

  public static final String CALENDAR_NAME = "Julian Day";
  public static JulianDay EPOCH = new JulianDay(2400000.5);

  /**
   * A Julian Day.
   * Day number is set using a given Julian Day.
   * @param jd A Julian Day.
   */
  public JulianDay(JulianDay jd) {
    this(jd.getValue());
  }

  /**
   * A Julian Day.
   * Day number is set to a given value.
   * @param jd A Julian Day.
   */
  public JulianDay(double jd) {
    _day = jd;
  }

  /**
   * Constructs a Julian Day that is set to today's date.
   */
  public JulianDay() {
    this(new GregorianCalendar());
  }
  
  /**
   * Constructs a Julian Day from a given integer.
   * Note: Day will be set to midnight.
   * @param jd A Julian Day.
   */
  public JulianDay(int jd) {
    this((double)jd);
  }

  /**
   * Constructs a Julian Day from a Joda DateTime.
   * @param dt a Joda DateTime object.
   */
  public JulianDay(DateTime dt) {
    this(new GregorianCalendar(dt));
  }

  /**
   * Constructs a Julian Day using a provided Gregorian Date.
   * @param date A Gregorian Date.
   */
  public JulianDay(Almanac date) {
    this(Converter.toJulianDay(date));
  }

  @Override 
  public int getNumberOfDaysInMonth() { return 0; }

  /**
   * Returns this calendar's name.
   * @return this calendar's name.
   */
  @Override
  public String getName() {
    return CALENDAR_NAME;
  }

  /**
   * Returns today's date as a string.
   * Convenience static method.
   * @return today's date.
   */
  public static String asToday() {
    return (new JulianDay()).toString();
  }

  /**
   * Returns this Julian Day set to noon.
   * @return This Julian Day at noon.
   */
  public JulianDay atNoon() {
    return new JulianDay(Math.ceil(_day-0.5));
  }

  /**
   * Returns this Julian Day set to midnight.
   * @return This Julian Day at midnight.
   */
  public JulianDay atMidnight() {
    return new JulianDay(Math.floor(_day-0.5)+0.5);
  }

  /**
   * Sets this Julian Day to noon.
   */
  public void setToNoon() {
    _day = (this.atNoon()).getValue();
  }

  /**
   * Sets this Julian Day to midnight.
   */
  public void setToMidnight() { 
    _day = (this.atMidnight()).getValue();
  }

  /**
   * Gets this day as a double.
   * @return this day.
   */
  public double getValue() {
    return _day;
  }

  /**
   * Subtracts days from this Julian day.
   * @param days.
   */
  public JulianDay minus(int days) {
    _day -= days;
    return this;
  }

  /**
   * Adds days to this Julian day.
   */ 
  public JulianDay plus(int days) {
    _day += days;
    return this;
  }

  @Override
  public void nextDay() { _day += 1; }

  @Override
  public void prevDay() { _day -= 1; }

  /**
   * Gets the Modified Julian Day (MJD).
   * The Modified Julian Day is an adjusted version of the Julian
   * Day by setting the Epoch to midnight, November 17, 1858.
   * @return The Modified Julian Day.
   */ 
  public double getModified() {
    return _day-EPOCH.getValue();
  }

  /**
   * Prints the date.
   * @param the date.
   */  
  @Override
  public String getDate() {
    return Double.toString(getValue());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof JulianDay))
      return false;
    if (obj == this)
      return true;
      
    final JulianDay date = (JulianDay) obj;
    return new EqualsBuilder()
      .append(_day, date.getValue())
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(_day)
      .toHashCode();
  }

  @Override
  public String toString() {
    return(CALENDAR_NAME+": "+getDate());
  }

/////////////////////////////////////////////////////////////////////////////
// private
  
  private double _day;
  
}
