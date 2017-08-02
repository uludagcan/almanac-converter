package com.hm.cal.date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;
import java.util.Calendar;

import static com.hm.cal.constants.CalendarConstants.IndianCivilCalendarConstants.monthNames;
import static com.hm.cal.util.AlmanacConverter.toIndianCivilCalendar;

/**
 */
public class IndianCivilCalendar extends Almanac {

  public static final String CALENDAR_NAME = "Indian Civil Calendar";
  public static final JulianDay EPOCH = new JulianDay(1749994.5);

  public IndianCivilCalendar() {
    this(new DateTime());
  }

  public IndianCivilCalendar(Calendar cal) {
    this(new GregorianCalendar(cal));
  }

  public IndianCivilCalendar(DateTime dt) {
    this(toIndianCivilCalendar(new GregorianCalendar(dt)));
  }

  public IndianCivilCalendar(Almanac a) {
    this(toIndianCivilCalendar(a));
  }

  public IndianCivilCalendar(IndianCivilCalendar date) {
    this(date.getYear(), date.getMonth(), date.getDay());
  }

  public IndianCivilCalendar(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public static boolean isLeapYear(int year) {
    return GregorianCalendar.isLeapYear(year + 78, false);
  }

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
