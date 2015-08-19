package cal.date;

import java.lang.Math;
import java.util.Calendar;

/**
 * A Julian Day.
 * <p>
 * The Julian Day is the continuous count of days since the beginning of the
 * Julian Period used primarily by astronomers. The Julian Period is a
 * chronological interval of 7980 years beginning in 4713 BC, and has been
 * used since 1583 to convert between different calendars. The next Julian 
 * Period begins in the year 3268 AD.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-07
 */
public class JulianDay implements Almanac {
  public static final String CALENDAR_NAME = "Julian Day";

  public double day;
  public static double EPOCH_MODIFIED_JULIAN_DAY = 2400000.5;

  /**
   * A Julian Day.
   * Day number is set using a given Julian Day.
   * @param jd A Julian Day.
   */
  public JulianDay(JulianDay jd) {
    this(jd.day);
  }

  /**
   * A Julian Day.
   * Day number is set to a given value.
   * @param jd A Julian Day.
   */
  public JulianDay(double jd) {
    day = jd;
  }

  /**
   * Constructs a Julian Day that is set to today's date.
   */
  public JulianDay() {
    this(new GregorianDate());
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
   * Constructs a Julian Day from a Java Calendar instance.
   * @param cal A Java Calendar instance.
   */ 
  public JulianDay(Calendar cal) {
    this(new GregorianDate(cal));
  }
  /**
   * Constructs a Julian Day using a provided Gregorian Date.
   * @param date A Gregorian Date.
   */
  public JulianDay(GregorianDate date) {
    day = _julianFromGregorian(date);
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
    return new JulianDay(Math.ceil(day-0.5));
  }

  /**
   * Returns this Julian Day set to midnight.
   * @return This Julian Day at midnight.
   */
  public JulianDay atMidnight() {
    return new JulianDay(Math.floor(day-0.5)+0.5);
  }

  /**
   * Sets this Julian Day to noon.
   * @return This Julian Day.
   */
  public JulianDay setToNoon() {
    day = Math.ceil(day-0.5);
    return this;
  }

  /**
   * Sets this Julian Day to midnight.
   * @return This Julian Day.
   */
  public JulianDay setToMidnight() { 
    day = Math.floor(day-0.5)+0.5; 
    return this;
  }

  /**
   * Gets the Modified Julian Day (MJD).
   * The Modified Julian Day is an adjusted version of the Julian
   * Day by setting the Epoch to midnight, November 17, 1858.
   * @return The Modified Julian Day.
   */ 
  public double getModified() {
    return this.day-EPOCH_MODIFIED_JULIAN_DAY;
  }

  /**
   * Gets this date.
   */ 
  @Override
  public String getDate() {
    return Double.toString(this.day);
  }
  
  /**
   * Prints this Julian Day.
   */
  @Override
  public void print() {
    System.out.println("Julian Day: "+day);
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private static double _julianFromGregorian(GregorianDate date) {
    int month = date.month;
    int year = date.year;
    int day = date.day;
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
}
