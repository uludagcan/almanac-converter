package cal.util;

import cal.date.*;
import cal.astro.*;

/**
 * A conversion facilitator between different calendars.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-11
 */
public class Converter {
  public static JulianDay convert(Almanac cal) { 
    if (cal instanceOf GregorianDate)
      return _JD2GD(cal);
   return (JulianDay)cal;
  }
  
  public static GregorianDay convert(Almanac cal) {
    
  }
  
  public static FrenchRepublicanDate convert(Almanac cal) {
    
  }


/////////////////////////////////////////////////////////////////////////////
// private

  private static JulianDay _JD2GD(GregorianDate date) {
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
    return new JulianDay(JDN);
  }
}
