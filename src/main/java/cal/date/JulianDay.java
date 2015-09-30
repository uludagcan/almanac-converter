package cal.date;

import java.lang.Math;
import java.util.Calendar;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;

import cal.util.*;
import static cal.util.Converter.*;

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
public final class JulianDay implements Almanac {
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
  public JulianDay(GregorianCalendar date) {
    this(Converter.toJulianDay(date));
  }

  /**
   * Returns today's date as a string.
   * Convenience static method.
   * @return today's date.
   */
  public static String asToday() {
    return (new JulianDay()).getDate();
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
   * @return This Julian Day.
   */
  public JulianDay setToNoon() {
    _day = Math.ceil(_day-0.5);
    return this;
  }

  /**
   * Sets this Julian Day to midnight.
   * @return This Julian Day.
   */
  public JulianDay setToMidnight() { 
    _day = Math.floor(_day-0.5)+0.5; 
    return this;
  }

  /**
   * Gets this day as a double.
   * @return this day.
   */
  public double getValue() {
    return _day;
  }


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
   * Gets this date.
   */ 
  @Override
  public String getDate() {
    return Double.toString(_day);
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

  /**
   * Prints this Julian Day.
   */
  @Override
  public String toString() {
    return(CALENDAR_NAME+": "+_day);
  }

/////////////////////////////////////////////////////////////////////////////
// private
  
  private double _day;
  
  /*
  private static double _julianFromGregorian(GregorianCalendar date) {
    int month = date.getMonth();
    int year = date.getYear();
    int day = date.getDay();
    if (month==1 || month==2) {
      year--;
      month+=12;
    }
    int a = (int)Math.floor(year/100);
    int b = a/4;
    int c = 2-a+b;
    int e = (int)(365.25*(year+4716));
    int f = (int)(30.6001*(month+1));
    double JDN = c+day+e+f-1524.5;
    return JDN;
  }
  */
}

