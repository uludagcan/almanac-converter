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
   * This method determines whether the the current date exists during the
   * Julian calendar or the Gregorian Calendar. The only difference between
   * the two is that for the Gregorian Calendar leap years must be evenly
   * divisible by 4, unless the year is divisible by 100 - with the exception
   * of years evenly divisble by 400.
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return GregorianCalendar.isLeapYear(_year);
  }

  /**
   * Determines whether a given year is a leap year.
   * @param year a given year.
   * @return true, if is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    GregorianCalendar epoch = new GregorianCalendar(EPOCH);
    GregorianCalendar date = new GregorianCalendar(year,1,1);
    if (Math.abs(year) % 4 == 0) {
      if (GregorianCalendar.datesAreChronological(date,epoch)) return true;
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

  // TODO
  private int _hour;
  private int _minute;
  private int _second;
  
  private static final Tuple<String,String>[] _monthNames = 
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

  /**
   * Converts Julian Days to a Gregorian Date.
   * Algorithm derived by Richards (2013).
   * @param jday The Julian Day.
   * @return the Gregorian Date.
   */
   /*
  private static GregorianCalendar _gregorianFromJulian(JulianDay jday) {
    int J = (int)(jday.getValue()+0.5);
    int y = 4716; int j = 1401;   int m = 2;
    int n = 12;   int r = 4;      int p = 1461;
    int v = 3;    int u = 5;      int s = 153;
    int w = 2;    int B = 274277; int C = -38;
    int f = J+j+(((4*J+B)/146097)*3)/4+C;
    int e = r*f+v;
    int g = (e%p)/r;
    int h = u*g+w;
    int day = (h%s)/u+1;
    int month = (h/s+m)%n+1;
    int year = (e/p)-y+(n+m-month)/n;
    return new GregorianCalendar(year,month,day);
  }
  */
}
