/****************************************************************************
 * A date in the Gregorian Calendar.
 * Internationally the most widely used civil calendar, the Gregorian 
 * Calendar was introduced by Pope Gregory XIII in 1582.
****************************************************************************/
package cal.date;

import java.lang.Math;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class GregorianDate {
  public GregorianDate(Calendar date) {
    this(date.get(Calendar.DAY_OF_MONTH),
         date.get(Calendar.MONTH)+1,
         date.get(Calendar.YEAR));
  }

  public GregorianDate(JulianDay date) {
    GregorianDate gd = gregorianFromJulian(date);
    _day   = gd.getDay();
    _month = gd.getMonth();
    _year  = gd.getYear();
  }

  public GregorianDate(int day, int month, int year) {
    _day   = day;
    _month = month;
    _year  = year;
  }

  /**
   * Converts Julian Days to a Gregorian Date.
   * Algorithm derived by Richards (2013).
   * @param jday The Julian Day.
   * @return the Gregorian Date.
   */
  public static GregorianDate gregorianFromJulian(JulianDay jday) {
    int J = (int)(jday.getDay()+0.5);
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

  public void print() {
    System.out.println("Gregorian Date: " + 
      _monthNames[_month-1]+" "+_day+", "+_year);
  }

  public boolean isBefore(GregorianDate date) {
    double year = date.getYear();
    double month = date.getMonth();
    double day = date.getDay();
    return ((_year<year) ||
            (_year==year && _month<month) ||
            (_year==year && _month==month && _day<day));
  }

  public boolean isAfter(GregorianDate date) {
    double year = date.getYear();
    double month = date.getMonth();
    double day = date.getDay();
    return ((_year>year) ||
            (_year==year && _month>month) ||
            (_year==year && _month==month && _day>day));
  }

  public int getDay()   { return _day; }
  public int getMonth() { return _month; }
  public int getYear()  { return _year; }

/////////////////////////////////////////////////////////////////////////////
// private

  private int _year;
  private int _day;
  private int _month;

  private String[] _monthNames = DateFormatSymbols.getInstance().getMonths();
}
