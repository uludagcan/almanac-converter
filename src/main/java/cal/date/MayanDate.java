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
  
  public int getKin()    { return (int)_kin;    }
  public int getWinal()  { return (int)_winal;  }
  public int getTun()    { return (int)_tun;    }
  public int getKatun()  { return (int)_katun;  }
  public int getBaktun() { return (int)_baktun; }
  
  
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
  
  /**
   * Prints the "Long Form" Mayan Calendar date.
   * @return The "Long Form" Mayan Calendar date.
   */
  @Override
  public void print() {
    System.out.println(CALENDAR_NAME+": "+getDate());
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private void _julianDayToMayanCount(JulianDay jday) {
    float day = (float)(jday.setToNoon()).getDay();
    float d = day - EPOCH;
    _baktun = (float)Math.floor(d/_lbaktun);
    d = d % _lbaktun;
    _katun = (float)Math.floor(d/_lkatun);
    d = d % _lkatun;
    _tun = (float)Math.floor(d / _ltun);
    d = d % _ltun;
    _winal = (float)Math.floor(d / _lwinal);
    _kin = d % _lwinal;
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
