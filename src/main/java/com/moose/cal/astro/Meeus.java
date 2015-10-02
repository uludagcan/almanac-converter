/*****************************************************************************
Copyright 2015 Hypotemoose, LLC.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*****************************************************************************/
package com.moose.cal.astro;

import java.util.*;
import java.lang.Math;

import static com.moose.cal.util.Util.*;

/**
 * Astronomical equations derived by Belgian Astronomer Jean Meeus.
 * Algorithms also inspired by Fourmilab.
 * @author Chris Engelsma
 * @version 2015.09.23
 */
public class Meeus {

  public static final double J2000 = 2451545.0;
  public static final double JULIAN_CENTURY = 36525.0;
  public static final double JULIAN_MILLENIUM = (JULIAN_CENTURY * 10);
  public static final double ASTRONOMICAL_UNIT = 149597870.0;
  public static final double TROPICAL_YEAR = 365.24219878;

  /**
   * The Julian Ephemeris day of an equinox or solstice.
   * @param year The year.
   * @param season The season.
   * @return The Julian Day of the equinox.
   */
  public static double equinox(int year, Season season) {
    double jde = 0;
    double jdeo,t,w,lam,s;

    jdeo = mean(year,season);
    t = (jdeo-2451545.0)/36525.0;
    w = (35999.373*t)-2.47;
    lam = 1+(0.0334*dcos(w))+(0.0007*dcos(2*w));

    // Sum periodic terms for time t.
    s = 0;
    for (int i=0; i<tblC.length; ++i)
      s += tblC[i][0] * dcos(tblC[i][1]+(tblC[i][2]*t));

    jde = jdeo+((0.00001*s)/lam);
    return jde;
  }

  /**
   * The difference in seconds between Dynamical Time and Universal time.
   * @param year The year.
   * @return The difference in time in seconds between Dynamical Time and
   * Universal Time.
   */
  public static double deltat(int year) {
    double dt, f, t;
    int i;

    if ((year >= 1620) && (year <= 2000)) {
        i = (int)(Math.floor((year - 1620) / 2.0));
        f = ((year - 1620) / 2.0) - i;  /* Fractional part of year */
        dt = deltaTtab[i] + ((deltaTtab[i + 1] - deltaTtab[i]) * f);
    } else {
        t = (year - 2000) / 100.0;
        if (year < 948) {
            dt = 2177 + (497 * t) + (44.1 * t * t);
        } else {
            dt = 102 + (102 * t) + (25.3 * t * t);
            if ((year > 2000) && (year < 2100)) {
                dt += 0.37 * (year - 2100);
            }
        }
    }
    return dt;
  }

  /**
   * The Equation of Time.
   * <p>
   * The equation of time describes the discrepency between two kinds of solar
   * time: apparent solar time and mean solar time.
   * @param jd The Julian Day.
   * @return The equation of time as a fraction of a day.
   */
  public static double equationOfTime(double jd) {
    double tau,l0,alpha,dPsi,epsilon,e;

    tau = (jd-J2000)/JULIAN_MILLENIUM;
    l0 = 280.4664567+(360007.6982779*tau) +
                (0.03032028*tau*tau) +
                ((tau*tau*tau)/49931.0) -
                (Math.pow(tau,4)/15300.0) -
                (Math.pow(tau,5)/2000000.0);

    l0 = fixAngle(l0);
    alpha = sunPosition(jd)[10];
    dPsi = nutation(jd)[0];
    epsilon = obliquityEquation(jd)+nutation(jd)[1];

    e = l0 + (-0.0057183) - alpha + (dPsi*dcos(epsilon));
    e -= 20.0*(Math.floor(e/20.0));
    e /= (24*60);
    return e;
  }

  /**
   * The obliquity of the ecliptic.
   * <p>
   * Calculates the obliquity of the ecliptic for a given Julian Day. This
   * uses Laskar's 10th degree polynomial fit (J. Laskar, Astronomy and
   * Astrophysics, Vol 157. page 68 [1986]).
   * @param jd The Julian Day
   * @return the obliquity of the ecliptic.
   */
  public static double obliquityEquation(double jd) {
    double eps = 0.0;
    double u,v;
    v = (jd-J2000)/(JULIAN_CENTURY*100);
    u = v;
    if (Math.abs(u)<1.0) {
      for (int i=0; i<10; ++i) {
        eps += (oterms[i]/3600.0)*v;
        v *= u;
      }
    }
    return eps;
  }

  /**
   * The Sun's position.
   * <p>
   * Computes a number of parameters regarding the Sun's position at a given
   * Julian Day.
   * @param jd A Julian Day.
   * @return An array containing parameters of the Sun's position.
   */
  public static double[] sunPosition(double jd) {
    double t, t2, l0, m, e, c, sunLong, sunAnomaly, sunRadius,
           w, lam, epsilon, epsilon0, alpha, delta,
           alphaApp, deltaApp;

    t = (jd - J2000) / JULIAN_CENTURY;
    t2 = t * t;
    l0 = fixAngle(280.46646 + (36000.76983 * t) + ( 0.0003032 * t2));
    m  = fixAngle(357.52911 + (35999.05029 * t) + (-0.0001537 * t2));
    e  = 0.016708634 + (-0.000042037 * t) + (-0.0000001267 * t2);
    c  = ((1.914602 + (-0.004817 * t) + (-0.000014 * t2)) * dsin(m)) +
         ((0.019993 - (0.000101 * t)) * dsin(2 * m)) +
         (0.000289 * dsin(3 * m));
    sunLong = l0 + c;
    sunAnomaly = m + c;
    sunRadius = (1.000001018*(1 - (e * e))) / (1 + (e * dcos(sunAnomaly)));
    w = 125.04 - (1934.136 * t);
    lam = sunLong + (-0.00569) + (-0.00478 * dsin(w));
    epsilon0 = obliquityEquation(jd);
    epsilon = epsilon0 + (0.00256 * dcos(w));
    alpha = rtd(Math.atan2(dcos(epsilon0)*dsin(sunLong),dcos(sunLong)));
    alpha = fixAngle(alpha);
    delta = rtd(Math.asin(dsin(epsilon0) * dsin(sunLong)));
    alphaApp = rtd(Math.atan2(dcos(epsilon) * dsin(lam), dcos(lam)));
    alphaApp = fixAngle(alphaApp);
    deltaApp = rtd(Math.asin(dsin(epsilon) * dsin(lam)));
    return new double[] {
      l0,          //  [0] Geometric mean longitude of the Sun
      m,           //  [1] Mean anomaly of the Sun
      e,           //  [2] Eccentricity of the Earth's orbit
      c,           //  [3] Sun's equation of the center
      sunLong,     //  [4] Sun's true longitude
      sunAnomaly,  //  [5] Sun's true anomaly
      sunRadius,   //  [6] Sun's radius vector in AU
      lam,         //  [7] Sun's apparent longitude at true equinox of date
      alpha,       //  [8] Sun's true right ascension
      delta,       //  [9] Sun's true declination
      alphaApp,    // [10] Sun's apparent right ascension
      deltaApp     // [11] Sun's apparent declination
    };
  }

  /**
   * The nutation in longitude (delta-Psi) and obliquity (delta-Epsilon) for a
   * given Julian Date.
   * @return the nutation.
   */
  public static double[] nutation(double jd) {
    double dPsi, dEpsilon;
    double t, t2, t3, to10, dp = 0.0, de = 0.0, ang;
    double[] ta = new double[5];

    t = (jd-2451545.0)/(36525.0);
    t2 = t*t;
    t3 = t2*t;

    ta[0] = dtr(297.850363 + 445267.111480*t - 0.0019142*t2 + t3/189474.0);
    ta[1] = dtr(357.527720 +  35999.050340*t - 0.0001603*t2 - t3/300000.0);
    ta[2] = dtr(134.962980 + 477198.867398*t + 0.0086972*t2 +  t3/56250.0);
    ta[3] = dtr( 93.271910 + 483202.017538*t - 0.0036825*t2 + t3/327170.0);
    ta[4] = dtr(125.044520 -   1934.136261*t + 0.0020708*t2 + t3/450000.0);

    for (int i=0; i<5; ++i) fixAngler(ta[i]);

    to10 = t/10.0;
    for (int i = 0; i<63; ++i) {
      ang = 0.0;
      for (int j=0; j<5; ++j) {
        int k = i*5+j;
        if (nutArgMult[k]!=0) ang += nutArgMult[k]*ta[j];
      }
      dp += (nutArgCoeff[(i*4)+0] + nutArgCoeff[(i*4)+1]*to10)*Math.sin(ang);
      de += (nutArgCoeff[(i*4)+2] + nutArgCoeff[(i*4)+3]*to10)*Math.cos(ang);
    }

    dPsi     = dp/(3600.0*10000.0);
    dEpsilon = de/(3600.0*10000.0);

    return new double[] {dPsi,dEpsilon};
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private static double calculateJdeo(double[] series, double y) {
    return (series[0] + 
           (series[1] * y) +
           (series[2] * Math.pow(y,2)) + 
           (series[3] * Math.pow(y,3)) + 
           (series[4] * Math.pow(y,4)));
  }


  private static double mean(int year, Season season) {
    double y,jde0;
    if (year<1000) {
      y = year/1000.0;
      jde0 = calculateJdeo(tblA[season.getValue()],y);
    } else {
      y = (year-2000.0)/1000.0;
      jde0 = calculateJdeo(tblB[season.getValue()],y);
    }

    return jde0;
  }

  /* For years -1000 to +1000 */
  private static final double[][] tblA = 
    { 
      { 1721139.29189, 365242.13740, 0.06134, 0.00111,-0.00071},
      { 1721233.25401, 365241.72562,-0.05323, 0.00907, 0.00025},
      { 1721325.70455, 365242.49558,-0.11677,-0.00297, 0.00074},
      { 1721414.39987, 365242.88257,-0.00769,-0.00933,-0.00006}
    };

  /* For years +1000 to +3000 */
  private static final double[][] tblB = 
    { 
      { 2451623.80984, 365242.37404, 0.05169,-0.00411,-0.00057},
      { 2451716.56767, 365241.62603, 0.00325, 0.00888,-0.00030},
      { 2451810.21715, 365242.01767,-0.11575, 0.00337, 0.00078},
      { 2451900.05952, 365242.74049,-0.06223,-0.00823, 0.00032}
    };

  private static final double[][] tblC = 
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

    private static final double[] deltaTtab = {
      121.0, 112.0, 103.0,  95.0,  88.0,  82.0,  77.0,  72.0,  68.0,  63.0,
       60.0,  56.0,  53.0,  51.0,  48.0,  46.0,  44.0,  42.0,  40.0,  38.0,
       35.0,  33.0,  31.0,  29.0,  26.0,  24.0,  22.0,  20.0,  18.0,  16.0,
       14.0,  12.0,  11.0,  10.0,   9.0,   8.0,   7.0,   7.0,   7.0,   7.0,
        7.0,   7.0,   8.0,   8.0,   9.0,   9.0,   9.0,   9.0,   9.0,  10.0,
       10.0,  10.0,  10.0,  10.0,  10.0,  10.0,  10.0,  11.0,  11.0,  11.0,
       11.0,  11.0,  12.0,  12.0,  12.0,  12.0,  13.0,  13.0,  13.0,  14.0,
       14.0,  14.0,  14.0,  15.0,  15.0,  15.0,  15.0,  15.0,  16.0,  16.0,
       16.0,  16.0,  16.0,  16.0,  16.0,  16.0,  15.0,  15.0,  14.0,  13.0,
       13.1,  12.5,  12.2,  12.0,  12.0,  12.0,  12.0,  12.0,  12.0,  11.9,
       11.6,  11.0,  10.2,   9.2,   8.2,   7.1,   6.2,   5.6,   5.4,   5.3,
        5.4,   5.6,   5.9,   6.2,   6.5,   6.8,   7.1,   7.3,   7.5,   7.6,
        7.7,   7.3,   6.2,   5.2,   2.7,   1.4,  -1.2,  -2.8,  -3.8,  -4.8,
       -5.5,  -5.3,  -5.6,  -5.7,  -5.9,  -6.0,  -6.3,  -6.5,  -6.2,  -4.7,
       -2.8,  -0.1,   2.6,   5.3,   7.7,  10.4,  13.3,  16.0,  18.2,  20.2,
       21.1,  22.4,  23.5,  23.8,  24.3,  24.0,  23.9,  23.9,  23.7,  24.0,
       24.3,  25.3,  26.2,  27.3,  28.2,  29.1,  30.0,  30.7,  31.4,  32.2,
       33.1,  34.0,  35.0,  36.5,  38.3,  40.2,  42.2,  44.5,  46.5,  48.5,
       50.5,  52.2,  53.8,  54.9,  55.8,  56.9,  58.3,  60.0,  61.6,  63.0,
       65.0,  66.6 
    };

    // Constants for computing obliquity of the ecliptic.
    private static final double[] oterms = {
      -4680.93,
         -1.55,
       1999.25,
        -51.38,
       -249.67,
        -39.05,
          7.12,
         27.87,
          5.79,
          2.45
    };
    
    private static final double[] nutArgMult = {
       0.0, 0.0, 0.0, 0.0, 1.0, 
      -2.0, 0.0, 0.0, 2.0, 2.0, 
       0.0, 0.0, 0.0, 2.0, 2.0, 
       0.0, 0.0, 0.0, 0.0, 2.0, 
       0.0, 1.0, 0.0, 0.0, 0.0, 
       0.0, 0.0, 1.0, 0.0, 0.0, 
      -2.0, 1.0, 0.0, 2.0, 2.0, 
       0.0, 0.0, 0.0, 2.0, 1.0, 
       0.0, 0.0, 1.0, 2.0, 2.0, 
      -2.0, 1.0, 0.0, 2.0, 2.0, 
      -2.0, 0.0, 1.0, 0.0, 0.0, 
      -2.0, 0.0, 0.0, 2.0, 1.0, 
       0.0, 0.0, 1.0, 2.0, 2.0, 
       2.0, 0.0, 0.0, 0.0, 0.0, 
       0.0, 0.0, 1.0, 0.0, 1.0, 
       2.0, 0.0, 1.0, 2.0, 2.0, 
       0.0, 0.0, 1.0, 0.0, 1.0, 
       0.0, 0.0, 1.0, 2.0, 1.0, 
      -2.0, 0.0, 2.0, 0.0, 0.0, 
       0.0, 0.0, 2.0, 2.0, 1.0, 
       2.0, 0.0, 0.0, 2.0, 2.0, 
       0.0, 0.0, 2.0, 2.0, 2.0, 
       0.0, 0.0, 2.0, 0.0, 0.0, 
      -2.0, 0.0, 1.0, 2.0, 2.0, 
       0.0, 0.0, 0.0, 2.0, 0.0, 
      -2.0, 0.0, 0.0, 2.0, 0.0, 
       0.0, 0.0, 1.0, 2.0, 1.0, 
       0.0, 2.0, 0.0, 0.0, 0.0, 
       2.0, 0.0, 1.0, 0.0, 1.0, 
      -2.0, 2.0, 0.0, 2.0, 2.0, 
       0.0, 1.0, 0.0, 0.0, 1.0, 
      -2.0, 0.0, 1.0, 0.0, 1.0, 
       0.0, 1.0, 0.0, 0.0, 1.0, 
       0.0, 0.0, 2.0, 2.0, 0.0, 
       2.0, 0.0, 1.0, 2.0, 1.0, 
       2.0, 0.0, 1.0, 2.0, 2.0, 
       0.0, 1.0, 0.0, 2.0, 2.0, 
      -2.0, 1.0, 1.0, 0.0, 0.0, 
       0.0, 1.0, 0.0, 2.0, 2.0, 
       2.0, 0.0, 0.0, 2.0, 1.0, 
       2.0, 0.0, 1.0, 0.0, 0.0, 
      -2.0, 0.0, 2.0, 2.0, 2.0, 
      -2.0, 0.0, 1.0, 2.0, 1.0, 
       2.0, 0.0, 2.0, 0.0, 1.0, 
       2.0, 0.0, 0.0, 0.0, 1.0, 
       0.0, 1.0, 1.0, 0.0, 0.0, 
      -2.0, 1.0, 0.0, 2.0, 1.0, 
      -2.0, 0.0, 0.0, 0.0, 1.0, 
       0.0, 0.0, 2.0, 2.0, 1.0, 
      -2.0, 0.0, 2.0, 0.0, 1.0, 
      -2.0, 1.0, 0.0, 2.0, 1.0, 
       0.0, 0.0, 1.0, 2.0, 0.0, 
      -1.0, 0.0, 1.0, 0.0, 0.0, 
      -2.0, 1.0, 0.0, 0.0, 0.0, 
       1.0, 0.0, 0.0, 0.0, 0.0, 
       0.0, 0.0, 1.0, 2.0, 0.0, 
      -1.0, 1.0, 1.0, 0.0, 0.0, 
       0.0, 1.0, 1.0, 0.0, 0.0, 
       0.0, 1.0, 1.0, 2.0, 2.0, 
       2.0, 1.0, 1.0, 2.0, 2.0, 
       0.0, 0.0, 2.0, 2.0, 2.0, 
       0.0, 0.0, 3.0, 2.0, 2.0, 
       2.0, 1.0, 0.0, 2.0, 2.0
    };

    private static final double[] nutArgCoeff = {
      -171996.0, -1742.0, 92095.0,    89.0,        /*  0,  0,  0,  0,  1 */
       -13187.0,   -16.0,  5736.0,   -31.0,        /* -2,  0,  0,  2,  2 */
        -2274.0,    -2.0,   977.0,    -5.0,        /*  0,  0,  0,  2,  2 */
         2062.0,     2.0,  -895.0,     5.0,        /*  0,  0,  0,  0,  2 */
         1426.0,   -34.0,    54.0,    -1.0,        /*  0,  1,  0,  0,  0 */
          712.0,     1.0,    -7.0,     0.0,        /*  0,  0,  1,  0,  0 */
         -517.0,    12.0,   224.0,    -6.0,        /* -2,  1,  0,  2,  2 */
         -386.0,    -4.0,   200.0,     0.0,        /*  0,  0,  0,  2,  1 */
         -301.0,     0.0,   129.0,    -1.0,        /*  0,  0,  1,  2,  2 */
          217.0,    -5.0,   -95.0,     3.0,        /* -2, -1,  0,  2,  2 */
         -158.0,     0.0,     0.0,     0.0,        /* -2,  0,  1,  0,  0 */
          129.0,     1.0,   -70.0,     0.0,        /* -2,  0,  0,  2,  1 */
          123.0,     0.0,   -53.0,     0.0,        /*  0,  0, -1,  2,  2 */
           63.0,     0.0,     0.0,     0.0,        /*  2,  0,  0,  0,  0 */
           63.0,     1.0,   -33.0,     0.0,        /*  0,  0,  1,  0,  1 */
          -59.0,     0.0,    26.0,     0.0,        /*  2,  0, -1,  2,  2 */
          -58.0,    -1.0,    32.0,     0.0,        /*  0,  0, -1,  0,  1 */
          -51.0,     0.0,    27.0,     0.0,        /*  0,  0,  1,  2,  1 */
           48.0,     0.0,     0.0,     0.0,        /* -2,  0,  2,  0,  0 */
           46.0,     0.0,   -24.0,     0.0,        /*  0,  0, -2,  2,  1 */
          -38.0,     0.0,    16.0,     0.0,        /*  2,  0,  0,  2,  2 */
          -31.0,     0.0,    13.0,     0.0,        /*  0,  0,  2,  2,  2 */
           29.0,     0.0,     0.0,     0.0,        /*  0,  0,  2,  0,  0 */
           29.0,     0.0,   -12.0,     0.0,        /* -2,  0,  1,  2,  2 */
           26.0,     0.0,     0.0,     0.0,        /*  0,  0,  0,  2,  0 */
          -22.0,     0.0,     0.0,     0.0,        /* -2,  0,  0,  2,  0 */
           21.0,     0.0,   -10.0,     0.0,        /*  0,  0, -1,  2,  1 */
           17.0,    -1.0,     0.0,     0.0,        /*  0,  2,  0,  0,  0 */
           16.0,     0.0,    -8.0,     0.0,        /*  2,  0, -1,  0,  1 */
          -16.0,     1.0,     7.0,     0.0,        /* -2,  2,  0,  2,  2 */
          -15.0,     0.0,     9.0,     0.0,        /*  0,  1,  0,  0,  1 */
          -13.0,     0.0,     7.0,     0.0,        /* -2,  0,  1,  0,  1 */
          -12.0,     0.0,     6.0,     0.0,        /*  0, -1,  0,  0,  1 */
           11.0,     0.0,     0.0,     0.0,        /*  0,  0,  2, -2,  0 */
          -10.0,     0.0,     5.0,     0.0,        /*  2,  0, -1,  2,  1 */
           -8.0,     0.0,     3.0,     0.0,        /*  2,  0,  1,  2,  2 */
            7.0,     0.0,    -3.0,     0.0,        /*  0,  1,  0,  2,  2 */
           -7.0,     0.0,     0.0,     0.0,        /* -2,  1,  1,  0,  0 */
           -7.0,     0.0,     3.0,     0.0,        /*  0, -1,  0,  2,  2 */
           -7.0,     0.0,     3.0,     0.0,        /*  2,  0,  0,  2,  1 */
            6.0,     0.0,     0.0,     0.0,        /*  2,  0,  1,  0,  0 */
            6.0,     0.0,    -3.0,     0.0,        /* -2,  0,  2,  2,  2 */
            6.0,     0.0,    -3.0,     0.0,        /* -2,  0,  1,  2,  1 */
           -6.0,     0.0,     3.0,     0.0,        /*  2,  0, -2,  0,  1 */
           -6.0,     0.0,     3.0,     0.0,        /*  2,  0,  0,  0,  1 */
            5.0,     0.0,     0.0,     0.0,        /*  0, -1,  1,  0,  0 */
           -5.0,     0.0,     3.0,     0.0,        /* -2, -1,  0,  2,  1 */
           -5.0,     0.0,     3.0,     0.0,        /* -2,  0,  0,  0,  1 */
           -5.0,     0.0,     3.0,     0.0,        /*  0,  0,  2,  2,  1 */
            4.0,     0.0,     0.0,     0.0,        /* -2,  0,  2,  0,  1 */
            4.0,     0.0,     0.0,     0.0,        /* -2,  1,  0,  2,  1 */
            4.0,     0.0,     0.0,     0.0,        /*  0,  0,  1, -2,  0 */
           -4.0,     0.0,     0.0,     0.0,        /* -1,  0,  1,  0,  0 */
           -4.0,     0.0,     0.0,     0.0,        /* -2,  1,  0,  0,  0 */
           -4.0,     0.0,     0.0,     0.0,        /*  1,  0,  0,  0,  0 */
            3.0,     0.0,     0.0,     0.0,        /*  0,  0,  1,  2,  0 */
           -3.0,     0.0,     0.0,     0.0,        /* -1, -1,  1,  0,  0 */
           -3.0,     0.0,     0.0,     0.0,        /*  0,  1,  1,  0,  0 */
           -3.0,     0.0,     0.0,     0.0,        /*  0, -1,  1,  2,  2 */
           -3.0,     0.0,     0.0,     0.0,        /*  2, -1, -1,  2,  2 */
           -3.0,     0.0,     0.0,     0.0,        /*  0,  0, -2,  2,  2 */
           -3.0,     0.0,     0.0,     0.0,        /*  0,  0,  3,  2,  2 */
           -3.0,     0.0,     0.0,     0.0         /*  2, -1,  0,  2,  2 */
    };

    private static void print(Object o) {
      System.out.println(o);
    }
}
