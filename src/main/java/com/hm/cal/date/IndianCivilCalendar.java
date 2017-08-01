package com.hm.cal.date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import java.util.Date;

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

  public boolean isLeapYear(int year) {
    return (new GregorianCalendar(this)).isLeapYear();
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

  @Override
  public String getDate() {
    return null;
  }

  @Override
  public String getName() {
    return CALENDAR_NAME;
  }

  @Override
  public int getNumberOfDaysInMonth() {
    if (month==0) return (isLeapYear(year)) ? 31 : 30;
    return (month >= 6) ? 30 : 31;
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
    if (!(obj instanceof IslamicCalendar))
      return false;
    if (obj == this)
      return true;

    final IslamicCalendar date = (IslamicCalendar) obj;
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
