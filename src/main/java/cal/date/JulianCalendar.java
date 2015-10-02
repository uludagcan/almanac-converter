package cal.date;

import java.lang.Math;
import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;

import cal.util.*;
import static cal.util.Converter.*;

/**
 * A Julian Calendar Date.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-09-30
 */
public final class JulianCalendar implements Almanac {
  public static final String CALENDAR_NAME = "Julian Calendar";
  public static final JulianDay EPOCH = new JulianDay(2299160.5);

  /**
   * Constructrs a Julian Date using today's date.
   */
  public JulianCalendar() {
    this(new DateTime());
  }

  /**
   * Constructs a Julian Date from another Almanac.
   * @param date another Almanac
   */
  public JulianCalendar(Almanac date) {
    //this(toGregorianDate(date));
  }

  /**
   * Constructs a Julian Date given another Julian Date.
   * @param date A Julian Date.
   */
  public JulianCalendar(JulianCalendar date) {
    this(date.getYear(),
         date.getMonth(),
         date.getDay());
  }

  /**
   * Constructs a Julian Date given a Joda DateTime.
   * @param dt a Joda DateTime.
   */
  public JulianCalendar(DateTime dt) {
    this(dt.getYear(),
         dt.getMonthOfYear(),
         dt.getDayOfMonth());
  }

  /**
   * Constructs a Julian Date from a given day, month and year.
   * @param year The year.
   * @param month The month.
   * @param day The day.
   */
  public JulianCalendar(int year, int month, int day) {
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
    return (new JulianCalendar()).getDate();
  }

  /**
   * Determines whether this date's year is a leap year.
   * <p>
   * This method determines whether the the current date exists during the
   * Julian calendar or the Gregorian Calendar. The only difference between
   * the two is that for the Gregorian Calendar leap years must be evenly
   * divisible by 4, unless the year is divisible by 100 - with the exception
   * of years evenly divisble by 400.
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return JulianCalendar.isLeapYear(_year);
  }

  /**
   * Determines whether a given year is a leap year.
   * @param year a given year.
   * @return true, if is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    JulianCalendar epoch = new JulianCalendar(EPOCH);
    JulianCalendar date = new JulianCalendar(year,1,1);
    if (Math.abs(year) % 4 == 0) {
      if (JulianCalendar.datesAreChronological(date,epoch)) return true;
      else {
        if (Math.abs(year)%400==0) return true;
        if (Math.abs(year)%100==0) return false;
      }
    }
    return false;
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
    return JulianCalendar.getMonthNames()[month-1];
  }

  /**
   * Gets the month name.
   * @param month the month number [1-12].
   * @return the name of the month.
   */
  public String getMonthName() {
    return JulianCalendar.getMonthName(_month);
  }

  /**
   * Gets the month names.
   * @return the month names.
   */
  public static String[] getMonthNames() {
    return _monthNames;
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
      if (!JulianCalendar.isLeapYear(year)) return 28;
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
    return JulianCalendar.getDaysInMonth(month,getYear());
  }

  /**
   * Gets the total number of days for this month and year.
   * @return the number of days in this month and year.
   */
  public int getDaysInMonth() {
    return JulianCalendar.getDaysInMonth(getMonth(),getYear());
  }

  /**
   * Gets an array of month-lengths for a given year.
   * @param year a year.
   * @return an array[12] of month-lengths for a given year.
   */
  public static int[] getDaysPerMonthInYear(int year) {
    int[] days = new int[12];
    for (int i=0; i<12; ++i) 
      days[i] = JulianCalendar.getDaysInMonth(i+1,year);
    return days;
  }

  /**
   * Gets an array of month-lengths for this year.
   * @return an array[12] of month-lengths for this year.
   */
  public int[] getDaysPerMonthInYear() {
    return JulianCalendar.getDaysPerMonthInYear(getYear());
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
   */ 
  @Override
  public String toString() {
    return(CALENDAR_NAME+": " + 
           getMonthName()+" "+
           getDay()+", "+
           getYear());
  }

  /**
   * Checks if this date is before another Julian Date.
   * @param date A Julian Calendar Date.
   * @return true, if before; false, otherwise.
   */ 
  public boolean isBefore(JulianCalendar date) {
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
    JulianCalendar firstDate, JulianCalendar secondDate) 
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
   * Checks if this date is after another Julian Date.
   * @param date A Julian Date.
   * @return true, if after; false, otherwise.
   */ 
  public boolean isAfter(JulianCalendar date) {
    double year = date.getYear();
    double month = date.getMonth();
    double day = date.getDay();
    return ((_year>year) ||
            (_year==year && _month>month) ||
            (_year==year && _month==month && _day>day));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof JulianCalendar))
      return false;
    if (obj == this)
      return true;
      
    final JulianCalendar date = (JulianCalendar) obj;
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
    "IANVARIVS",
    "FEBRVARIVSs",
    "MARTIVS",
    "APRILLIS",
    "MAIVS",
    "IVNIVS",
    "IVLIVS",
    "AVGVSTVS",
    "SEPTEMBER",
    "OCTOBER",
    "NOVEMBER",
    "DECEMBER"
  };

}
