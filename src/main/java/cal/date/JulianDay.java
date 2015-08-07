/****************************************************************************
 * A Julian Day.
 * The Julian Day is the continuous count of days since the beginning of the
 * Julian Period, a number primarily used by astronomers.
****************************************************************************/
package cal.date;

import java.lang.Math;
import java.util.Calendar;

public class JulianDay {

  public double day;

  public JulianDay(JulianDay jd) {
    this(jd.day);
  }

  public JulianDay(double i) {
    day = i;
  }

  public JulianDay(int i) {
    this((double)i);
  }

  public JulianDay() {
    this(new GregorianDate());
  }

  public JulianDay(GregorianDate date) {
    day = julianFromGregorian(date);
  }

  public JulianDay setToMidnight() { 
    day = Math.floor(day-0.5)+0.5; 
    return this;
  }

  public JulianDay plusEquals(JulianDay val) {
    return plusEquals(val.day);
  }

  public JulianDay minusEquals(JulianDay val) {
    return minusEquals(val.day);
  }

  public JulianDay timesEquals(JulianDay val) {
    return timesEquals(val.day);
  }

  public JulianDay divideEquals(JulianDay val) {
    return divideEquals(val.day);
  }

  public JulianDay plus(JulianDay val) {
    return plus(val.day);
  }

  public JulianDay minus(JulianDay val) {
    return minus(val.day);
  }

  public JulianDay times(JulianDay val) {
    return times(val.day);
  }

  public JulianDay divide(JulianDay val) {
    return divide(val.day);
  }

  public JulianDay plusEquals(double val)   { 
    day += val; 
    return this;
  }

  public JulianDay minusEquals(double val)  { 
    day -= val; 
    return this;
  }

  public JulianDay divideEquals(double val) { 
    day /= val; 
    return this;
  }

  public JulianDay timesEquals(double val)  { 
    day *= val; 
    return this;
  }

  public JulianDay plus(double right) {
    return new JulianDay(this).plusEquals(right);
  }

  public JulianDay minus(double right) {
    return new JulianDay(this).minusEquals(right);
  }

  public JulianDay times(double right) {
    return new JulianDay(this).timesEquals(right);
  }

  public JulianDay divide(double right) {
    return new JulianDay(this).divideEquals(right);
  }

  public boolean isAfter(JulianDay jd) {
    return (this.day>jd.day);
  }

  public boolean isBefore(JulianDay jd) {
    return (this.day<jd.day);
  }

  public boolean isAfterOrOn(JulianDay jd) {
    return (this.day>=jd.day);
  }

  public boolean isBeforeOrOn(JulianDay jd) {
    return (this.day<=jd.day);
  }

  public static double julianFromGregorian(GregorianDate date) {
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

  public void print() {
    System.out.println("Julian Day: "+day);
  }
}
