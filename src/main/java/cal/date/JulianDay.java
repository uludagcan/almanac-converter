package cal.date;

import java.lang.Math;
import java.util.Calendar;

/****************************************************************************
 * A Julian Day.
 * <p>
 * The Julian Day is the continuous count of days since the beginning of the
 * Julian Period, a number primarily used by astronomers.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-07
****************************************************************************/
public class JulianDay {

  public double day;

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
   * A Julian Day.
   * Day number is set to a given value.
   * @param jd A Julian Day.
   */
  public JulianDay(int jd) {
    this((double)jd);
  }

  /**
   * A Julian Day.
   * Julian Day is set to the current day.
   */
  public JulianDay() {
    this(new GregorianDate());
  }

  /**
   * A Julian Day.
   * Day number is set using a given Gregorian Date.
   * @param date A Gregorian Date.
   */
  public JulianDay(GregorianDate date) {
    day = _julianFromGregorian(date);
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

  public double getModified() {
    return 0.0; // TODO
  }
  
  /**
   * Prints this Julian Day.
   */
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
