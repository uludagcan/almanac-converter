/****************************************************************************
 * A date in the Gregorian Calendar.
 * Internationally the most widely used civil calendar, the Gregorian 
 * Calendar was introduced by Pope Gregory XIII in 1582.
****************************************************************************/
package cal.date;

import java.lang.Math;
import java.text.DateFormatSymbols;
import java.util.Calendar;

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
 * @version 1.0
 * @since 2015-08-10
 */
public class GregorianDate {

  public int year;
  public int day;
  public int month;

  /**
   * Constructrs a Gregorian Date using today's date.
   */
  public GregorianDate() {
    this(Calendar.getInstance());
  }

  /**
   * Constructs a Gregorian Date given a provided Calendar date.
   * @param A Calendar date.
   */
  public GregorianDate(Calendar date) {
    this(date.get(Calendar.DAY_OF_MONTH),
         date.get(Calendar.MONTH)+1,
         date.get(Calendar.YEAR));
  }

  /**
   * Constructs a Gregorian Date given a Julian Day.
   * @param date A Julian Day.
   */
  public GregorianDate(JulianDay date) {
    this(gregorianFromJulian(date));
  }

  /**
   * Constructs a Gregorian Date given another Gregorian Date.
   * @param date A Gregorian Date.
   */
  public GregorianDate(GregorianDate date) {
    this(date.day,date.month,date.year);
  }

  /**
   * Constructs a Gregorian Date from a given day, month and year.
   * @param day The day.
   * @param month The month.
   * @param year The year.
   */
  public GregorianDate(int day, int month, int year) {
    this.day   = day;
    this.month = month;
    this.year  = year;
  }

  /**
   * Converts Julian Days to a Gregorian Date.
   * Algorithm derived by Richards (2013).
   * @param jday The Julian Day.
   * @return the Gregorian Date.
   */
  public static GregorianDate gregorianFromJulian(JulianDay jday) {
    int J = (int)(jday.day+0.5);
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
    return new GregorianDate(day,month,year);
  }

  /**
   * Prints this date.
   */ 
  public void print() {
    System.out.println("Gregorian Date: " + 
      _monthNames[month-1]+" "+day+", "+year);
  }

  /**
   * Checks if this date is before another Gregorian Date.
   * @param date A Gregorian Date.
   * @return true, if before; false, otherwise.
   */ 
  public boolean isBefore(GregorianDate date) {
    double year = date.year;
    double month = date.month;
    double day = date.day;
    return ((this.year<year) ||
            (this.year==year && this.month<month) ||
            (this.year==year && this.month==month && this.day<day));
  }

  /**
   * Checks if this date is after another Gregorian Date.
   * @param date A Gregorian Date.
   * @return true, if after; false, otherwise.
   */ 
  public boolean isAfter(GregorianDate date) {
    double year = date.year;
    double month = date.month;
    double day = date.day;
    return ((this.year>year) ||
            (this.year==year && this.month>month) ||
            (this.year==year && this.month==month && this.day>day));
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private String[] _monthNames = DateFormatSymbols.getInstance().getMonths();
}
