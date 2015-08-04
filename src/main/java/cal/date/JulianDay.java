/****************************************************************************
 * A Julian Day.
 * The Julian Day is the continuous count of days since the beginning of the
 * Julian Period, a number primarily used by astronomers.
****************************************************************************/
package cal.date;

import java.lang.Math;
import java.util.Calendar;

public class JulianDay {
  public JulianDay(double i) {
    _day = i;
  }

  public JulianDay(GregorianDate date) {
    _day = julianFromGregorian(date);
  }

  public double getExactDay() { return _day; }
  public double getDay() { return Math.floor(_day)+0.5; }
  public void setDay(double day) { _day = day; }
  public void roundToMidnight() { _day = Math.floor(_day)+0.5; }

  public void plusEquals(double val)   { _day += val; }
  public void minusEquals(double val)  { _day -= val; }
  public void divideEquals(double val) { _day /= val; }
  public void timesEquals(double val)  { _day *= val; }

  public static double julianFromGregorian(GregorianDate date) {
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

  public void print() {
    System.out.println("Julian Day: "+_day);
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private double _day;
}
