package cal.date;

import cal.util.*;
import cal.astro.*;

/**
 * A date in the Mayan calendar.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015.08.19
 */ 
public class MayanDate implements Almanac {
  public static final String CALENDAR_NAME = "Mayan Calendar";
  public static final float EPOCH = 584282.5f;
  
  public MayanDate() {
    this(new JulianDay());
  }
  
  public MayanDate(JulianDay jday) {
    _julianDayToMayanCount(jday);
  }
  
  /**
   * Gets the "Long Count" Mayan Date.
   * @return The long count mayan date.
   */
  @Override
  public String getDate() {
    return new String(_baktun+"."+
                      _katun+"."+
                      _tun+"."+
                      _winal+"."+
                      _kin);
  }
  
  @Override
  /**
   * Prints the "Long Form" Mayan Calendar date.
   * @return The "Long Form" Mayan Calendar date.
   */
  public void print() {
    System.out.println(CALENDAR_NAME+": "+getDate());
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private void _julianDayToMayanCount(JulianDay jday) {
    float day = (jday.setToNoon()).getDay();
    float d = day - EPOCH;
    _baktun = Math.floor(d/144000);
    d = d % 144000;
    _katun = Math.floor(d/7200);
    d = d % 7200;
    _tun = Math.floor(d / 360);
    d = d % 360;
    _winal = Math.floor(d / 20);
    _kin = d % 20;
  }

  /**
   * Conversion between Mayan count units are as follows:
   * Long Count Unit  |  Long Count Period  | Days
   * 1 K'in           |                     | 1
   * 1 Winal          | 20 K'in             | 20
   * 1 Tun            | 18 Winal            | 360
   * 1 K'atun         | 20 Tun              | 7,200
   * 1 B'ak'tun       | 20 K'atun           | 144,000
   * 1 Piktun         | 20 B'ak'tun         | 2,880,000
   * 1 Kalabtun       | 20 Piktun           | 57,600,000
   * 1 K'inchiltun    | 20 Kalabtun         | 1,152,000,000
   * 1 Alautun        | 20 K'inchiltun      | 23,040,000,000
   */ 
  private float _kin,        _lkin = 1f;
  private float _winal,      _lwinal = 20f;
  private float _tun,        _ltun = 360f;
  private float _katun,      _lkatun = 7200f;
  private float _baktun,     _lbaktun = 144000f;
  private float _piktun,     _lpiktun = 2880000f;
  private float _kalabtun,   _lkalabtun = 57600000f;
  private float _kinchiltun, _lkinchiltun = 1152000000f;
  private float _alautun,    _lalautun = 23040000000f;
}
