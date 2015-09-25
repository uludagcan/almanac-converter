package cal.util;

import cal.date.*;
import cal.astro.*;

/**
 * A mechanism to convert between various calendars.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-27
 */
public class Converter {

  /**
   * Converts an Almanac to a Julian day.
   * @param a an Almanac.
   * @return the Julian day.
   */
  public static JulianDay toJulianDay(Almanac a) {
    JulianDay b = new JulianDay();
    if (a instanceof JulianDay) {
      b = (JulianDay)a;
    } else {
      if (a instanceof GregorianDate)
        b = _g2jd((GregorianDate)a);
      if (a instanceof FrenchRepublicanDate)
        b = _frc2jd((FrenchRepublicanDate)a);
      if (a instanceof MayaDate)
        b = _m2jd((MayaDate)a);
    }
    return b;
  }

  /**
   * Converts an Almanac to a Gregorian date.
   * @param a an Almanac.
   * @return the Gregorian date.
   */
  public static GregorianDate toGregorianDate(Almanac a) {
    if (a instanceof GregorianDate)
      return (GregorianDate)a;
    else 
      return _jd2g(toJulianDay(a));
  }

  /**
   * Converts an Almanac to a French Republican date.
   * @param a an Almanac.
   * @return the French Republican date.
   */
  public static FrenchRepublicanDate toFrenchRepublicanDate(Almanac a) {
    if (a instanceof FrenchRepublicanDate)
      return (FrenchRepublicanDate)a;
    else
      return _jd2frc(toJulianDay(a));
  }

  /**
   * Converts an Almanac to a Maya date.
   * @param a an Almanac.
   * @return the Maya date.
   */
  public static MayaDate toMayaDate(Almanac a) {
    if (a instanceof MayaDate) 
      return (MayaDate)a;
    else 
      return _jd2m(toJulianDay(a));
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private static JulianDay _g2jd(GregorianDate date) {
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
    return new JulianDay(JDN);
  }

  private static JulianDay _frc2jd(FrenchRepublicanDate date) {
    // TODO
    return new JulianDay();
  }

  private static JulianDay _m2jd(MayaDate date) {
    // TODO
    return new JulianDay();
  }

  private static GregorianDate _jd2g(JulianDay jd) {
    int J = (int)(jd.getValue()+0.5);
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
    return new GregorianDate(year,month,day);
  }

  private static FrenchRepublicanDate _jd2frc(JulianDay jd) {
    int year, month, week, day;
    double dd = jd.getValue();
    double jday = Math.floor(dd)+0.5;
    double[] adr = anneeDeLaRevolution(new JulianDay(jd));
    year = (int)adr[0];
    double equinoxe = adr[1];
    month = (int)(Math.floor((dd-equinoxe)/30)+1);
    double djour = (dd-equinoxe) % 30;
    week = (int)(Math.floor(djour/10)+1);
    day = (int)((djour % 10)+1);
    if (month>12) day+=11;

    // Adjust for Sans-culottides
    if (day>10) {
      day -= 12;
      week = 1;
      month = 13;
    }
    if (month==13) {
      week = 1;
      if (day>6) {
        day = 1;
      }
    }

    return new FrenchRepublicanDate(year,month,week,day);
  }

  private static MayaDate _jd2m(JulianDay jd) {
    int baktun, katun, tun, uinal, kin;
    double day = (jd.atMidnight()).getValue();
    double d = day - MayaDate.EPOCH.getValue();
    baktun = (int)Math.floor(d/_lbaktun);
    d = d % _lbaktun;
    katun = (int)Math.floor(d/_lkatun);
    d = d % _lkatun;
    tun = (int)Math.floor(d / _ltun);
    d = d % _ltun;
    uinal = (int)Math.floor(d / _luinal);
    kin = (int)(d % _luinal);
    return new MayaDate(baktun,katun,tun,uinal,kin);
  }

  private static double[] anneeDeLaRevolution(JulianDay julday) {
    int guess = (new GregorianDate(julday)).getYear()-2;
    double nexteq,lasteq,jd;
    jd = julday.getValue();
    lasteq = _parisEquinox(guess);
    while (lasteq>jd) {
      guess--;
      lasteq = _parisEquinox(guess);
    }
    nexteq = lasteq-1;
    while (!((lasteq <= jd) && (jd < nexteq))) {
      lasteq = nexteq;
      guess++;
      nexteq = _parisEquinox(guess);
    }
    double adr = (lasteq - FrenchRepublicanDate.EPOCH.getValue());
    adr /= Meeus.TROPICAL_YEAR;
    adr += 1;
    return new double[] {Math.round(adr), lasteq};
  }

  private static double _parisEquinox(int year) {
    double eqJED = Meeus.equinox(year,Season.AUTUMN);
    double eqJD = eqJED - Meeus.deltat(year)/(24.0*60.0*60.0);
    double eqAPP = eqJD + Meeus.equationOfTime(eqJED);
    double dtParis = (2.0+(20.0/60.0)+(15.0/(60.0*60.0)))/360.0;
    double eqParis = eqAPP + dtParis;
    eqParis = Math.floor(eqParis-0.5)+0.5;
    return eqParis;
  }

  /* Constants for Maya Calendar */
  private static final double _luinal      = 20.0;
  private static final double _ltun        = 360.0;
  private static final double _lkatun      = 7200.0;
  private static final double _lbaktun     = 144000.0;
  private static final double _lpiktun     = 2880000.0;
  private static final double _lkalabtun   = 57600000.0;
  private static final double _lkinchiltun = 1152000000.0;
  private static final double _lalautun    = 23040000000.0;

}
