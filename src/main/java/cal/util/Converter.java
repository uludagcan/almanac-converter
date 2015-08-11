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
    if (cal instanceof GregorianDate) {
      return _GD2JD(cal);
    } else if (cal instanceof FrenchRepublicanDate) {
      // TODO
      return (JulianDay)cal;
    }      

   return (JulianDay)cal;
  }
  
  public static GregorianDay convert(Almanac cal) {
    
  }
  
  public static FrenchRepublicanDate convert(Almanac cal) {
    if (cal instanceof JulianDay) {
      return _JD2FRD((JulianDay)cal);
    } else if (cal instanceof GregorianDate) {
     // return _GD2FRD((GregorianDate)cal);
    }
    return (FrenchRepublicanDate)cal;
  }


/////////////////////////////////////////////////////////////////////////////
// private

  private static JulianDay _GD2JD(GregorianDate date) {
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


//private static FrenchRepublicanDate _GD2FRD(GregorianDate date) {
  
//}

  private static FrenchRepublicanDate _JD2FRD(JulianDay jd) {
    double jday = Math.floor(jd.day)+0.5;
    double[] adr = anneeDeLaRevolution(new JulianDay(jday));
    double an = (int)adr[0];
    double equinoxe = adr[1];
    double mois = (int)(Math.floor((jd.day-equinoxe)/30)+1);
    double djour = (jd.day-equinoxe) % 30;
    double decade = (int)(Math.floor(djour/10)+1);
    double jour = (int)((djour % 10)+1);
    if (mois>12) jour+=11;
    
    // Adjust for Sans-culottides.
    if (jour>10) {
      jour -= 12;
      decade = 1;
      mois = 13;
    }
    if (mois==13) {
      decade = 1;
      if (jour>6) {
        jour = 1;
      }
    }
    
    return new FrenchRepublicanDate(an,mois,decade,jour)
  }

  private static double[] anneeDeLaRevolution(JulianDay julday) {
    int guess = (new GregorianDate(julday)).year-2;
    double nexteq,lasteq,jd;
    jd = julday.day;
    lasteq = parisEquinox(guess);
    while (lasteq>jd) {
      guess--;
      lasteq = parisEquinox(guess);
    }
    nexteq = lasteq-1;
    while (!((lasteq <= jd) && (jd < nexteq))) {
      lasteq = nexteq;
      guess++;
      nexteq = parisEquinox(guess);
    }
    double adr = (lasteq - FRENCH_REVOLUTION_EPOCH.day);
    adr /= Meeus.TROPICAL_YEAR;
    adr += 1;
    return new double[] {Math.round(adr), lasteq};
  }

  private static double parisEquinox(int year) {
    double eqJED = Meeus.equinox(year,Season.AUTUMN);
    double eqJD = eqJED - Meeus.deltat(year)/(24.0*60.0*60.0);
    double eqAPP = eqJD + Meeus.equationOfTime(eqJED);
    double dtParis = (2.0+(20.0/60.0)+(15.0/(60.0*60.0)))/360.0;
    double eqParis = eqAPP + dtParis;
    eqParis = Math.floor(eqParis-0.5)+0.5;
    return eqParis;
  }

  private JulianDay FRENCH_REVOLUTION_EPOCH = new JulianDay(2375839.5);
