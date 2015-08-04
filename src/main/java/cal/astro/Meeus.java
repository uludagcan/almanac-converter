/****************************************************************************
 * Astronomical Equations derived by Belgian Astronomer Jean Meeus.
****************************************************************************/
package cal.astro;

import java.util.*;
import java.lang.Math;
import cal.date.*;
import static cal.date.GregorianDate.*;

public class Meeus {

  public static JulianDay equinox(int year, Season season) {
    JulianDay jdeo = new JulianDay(mean(year,season));
    double jd = jdeo.getDay();
    double t = (jd-2451545.0)/36525.0;
    double w = (35999.373*t)-2.47;
    double lam = 1+0.0334*Math.cos(w)+0.0007*Math.cos(2*w);
    double s = 0;
    for (int i=0; i<tblC.length; ++i) {
      s += tblC[i][0] * Math.cos(tblC[i][1]+(tblC[i][2]*t));
    }
    s = Math.floor(s);
    jdeo.plusEquals((0.00001*s)/lam);
    jdeo.roundToMidnight();
    return jdeo;
  }

/////////////////////////////////////////////////////////////////////////////
//protected

  protected static double calculateJdeo(double[] series, double y) {
    return (series[0] + 
           (series[1] * y) +
           (series[2] * Math.pow(y,2)) + 
           (series[3] * Math.pow(y,3)) + 
           (series[4] * Math.pow(y,4)));
  }

//
/////////////////////////////////////////////////////////////////////////////
// private

  private static double mean(int year, Season season) {
    double y,jdeo;
    if (year>3000 || year<-1000) {

    }
    if (year>=1000) {
      y = (year-2000.0)/1000.0;
      jdeo = calculateJdeo(tblB[season.getValue()],y);
    } else {
      y = year/1000.0;
      jdeo = calculateJdeo(tblA[season.getValue()],y);
    }
//    print("Y: "+y);
//    print("jdeo: "+jdeo);

    return jdeo;
  }

  /* For years -1000 to +1000 */
  private static double[][] tblA = 
    { 
      { 1721139.29189, 365242.13740, 0.06134, 0.00111,-0.00071},
      { 1721233.25401, 365241.72562,-0.05323, 0.00907,-0.00025},
      { 1721325.70455, 365242.49558,-0.11677,-0.00297, 0.00074},
      { 1721414.39987, 365242.88257,-0.00769,-0.00933,-0.00006}
    };
  /* For years +1000 to +3000 */
  private static double[][] tblB = 
    { 
      { 2451623.80984, 365242.37404, 0.05169,-0.00411,-0.00057},
      { 2451716.56767, 365241.62603,-0.00325, 0.00888,-0.00030},
      { 2451810.21715, 365242.01767,-0.11575,-0.00337, 0.00078},
      { 2451900.05952, 365242.74049,-0.06223, 0.00823, 0.00032}
    };

  private static double[][] tblC = 
    {
      {485.0, 324.96,   1934.136},
      {203.0, 337.23,  32964.467},
      {199.0, 342.08,     20.186},
      {182.0,  27.85, 445267.112},
      {156.0,  73.14,  45036.886},
      {136.0, 171.52,  22518.443},
      { 77.0, 222.54,  65928.934},
      { 74.0, 296.72,   3034.906},
      { 70.0, 243.58,   9037.513},
      { 58.0, 119.81,  33718.147},
      { 52.0, 297.17,    150.678},
      { 50.0,  21.02,   2281.226},
      { 45.0, 247.54,  29929.562},
      { 44.0, 325.15,  31555.956},
      { 29.0,  60.93,   4443.417},
      { 18.0, 155.12,  67555.328},
      { 17.0, 288.79,   4562.452},
      { 16.0, 198.04,  62894.029},
      { 14.0, 199.76,  31436.921},
      { 12.0,  95.39,  14577.848},
      { 12.0, 287.11,  31931.756},
      { 12.0, 320.81,  34777.259},
      {  9.0, 227.73,   1222.114},
      {  8.0,  15.45,  16859.074}
    };

    private static void print(Object o) {
      System.out.println(o);
    }
}
