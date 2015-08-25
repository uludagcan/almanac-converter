package cal.date;

import cal.util.*;
import cal.astro.*;

/**
 * A date in the Maya calendar.
 * <p>
 * The Maya calendar is not one calendar per se, but rather a system of
 * calendars employed by pre-Columbian Mesoamerica. In many modern communities
 * in Guatemala and Mexico, this calendar is still used today.
 * <p>
 * The Maya calendar consists of several cycles (or counts) of different
 * lengths. The 260-day calendar, or <em>Tzolk'in</em>, was combined with
 * the 365-day calendar, or <em>Haab'</em> to form a synchronized cycle
 * lasting for 52 Haab' called the Calendar Round. To measure periods longer
 * than this, the Maya used the Long Count calendar.
 * <p>
 * The Long Count calendar is a vigesimal (base-20) and base-18 calendar.
 * Conversion between Maya count units are as follows:
 * <code>
 * Long Count Unit  |  Long Count Period  | Days
 * 1 K'in           |                     | 1
 * 1 Uinal          | 20 K'in             | 20
 * 1 Tun            | 18 Uinal            | 360
 * 1 K'atun         | 20 Tun              | 7,200
 * 1 B'ak'tun       | 20 K'atun           | 144,000
 * 1 Piktun         | 20 B'ak'tun         | 2,880,000
 * 1 Kalabtun       | 20 Piktun           | 57,600,000
 * 1 K'inchiltun    | 20 Kalabtun         | 1,152,000,000
 * 1 Alautun        | 20 K'inchiltun      | 23,040,000,000
 * </code>
 * <p>
 * Note: It is a common misconception to use the term "Mayan" calendar, when
 * this term is actually an Indo-European invention. It is unlikely that this
 * would cause anyone to be upset, and in our contemporary vernacular these
 * words have become interchangeable.
 * @author Chris Engelsma
 * @version 1.1
 * @since 2015.08.24
 */ 
public class MayaDate implements Almanac {
  public static final String CALENDAR_NAME = "Maya Calendar";
  public static final float EPOCH = 584282.5f;
  
  /**
   * Constructs a date in the Maya calendar.
   * The calendar will default to today's date.
   */
  public MayaDate() {
    this(new JulianDay());
  }
  
  /**
   * Constructs a date in the Maya calendar with a given Julian Day.
   * @param jday A Julian Day.
   */
  public MayaDate(JulianDay jday) {
    _julianDayToMayaCount(jday);
  }
  
  /**
   * Gets this K'in.
   * The K'in is the smallest unit of Maya calendar time. It is equal to 1
   * day.
   * @return This K'in.
   */
  public int getKin() { 
    return _kin;    
  }

  /**
   * Gets this Uinal.
   * A Uinal is equal to 20 K'in, which is equal to 20 days.
   * @return This Uinal.
   */
  public int getUinal() { 
    return _uinal;  
  }

  /**
   * Gets this Tun.
   * A Tun is equal to 18 Uinal, which is equal to 360 days.
   * @return This Tun.
   */ 
  public int getTun()    { return _tun;    }

  /**
   * Gets this K'atun.
   * A K'atun is equal to 20 Tun, which is equal to 7,200 days.
   * @return This K'atun.
   */
  public int getKatun()  { return _katun;  }

  /**
   * Gets this B'aktun.
   * A B'aktun is equal to 20 K'atun which is equal to 144,000 days.
   * @return This B'aktun.
   */
  public int getBaktun() { return _baktun; }
  
  /**
   * Gets the "Long Count" Maya Date.
   * @return The long count mayan date.
   */
  @Override
  public String getDate() {
    return new String(_baktun+"."+ _katun+"."+ _tun+"."+ _uinal+"."+ _kin);
  }
  
  /**
   * Prints the "Long Form" Maya Calendar date.
   * @return The "Long Form" Maya Calendar date.
   */
  @Override
  public void print() {
    System.out.println(CALENDAR_NAME+": "+getDate());
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private void _julianDayToMayaCount(JulianDay jday) {
    float day = (float)(jday.atMidnight()).getDay();
    float d = day - EPOCH;
    _baktun = (int)Math.floor(d/_lbaktun);
    d = d % _lbaktun;
    _katun = (int)Math.floor(d/_lkatun);
    d = d % _lkatun;
    _tun = (int)Math.floor(d / _ltun);
    d = d % _ltun;
    _uinal = (int)Math.floor(d / _luinal);
    _kin = (int)(d % _luinal);
  }

  private int _kin;
  private int _uinal;
  private int _tun;
  private int _katun;
  private int _baktun;
  private int _piktun;
  private int _kalabtun;
  private int _kinchiltun;
  private int _alautun;

  private float _luinal      = 20f;
  private float _ltun        = 360f;
  private float _lkatun      = 7200f;
  private float _lbaktun     = 144000f;
  private float _lpiktun     = 2880000f;
  private float _lkalabtun   = 57600000f;
  private float _lkinchiltun = 1152000000f;
  private float _lalautun    = 23040000000f;

  private String[] _haabMonths = {
    "Pop",
    "Wo'",
    "Sip",
    "Sotz'",
    "Sek",
    "Xul",
    "Yaxk'in'",
    "Mol",
    "Ch'en",
    "Yax",
    "Sak'",
    "Keh",
    "Mak",
    "K'ank'in",
    "Muwan'",
    "Pax",
    "K'ayab",
    "Kumk'u",
    "Wayeb"
  };

  private String[] _tzolkinDayNames = {
    "Imix'",
    "Ik'",
    "Ak'b'al",
    "K'an",
    "Chikchan",
    "Kimi",
    "Manik'",
    "Lamat",
    "Muluk",
    "Ok",
    "Chuwen",
    "Eb'",
    "B'en",
    "Ix",
    "Men",
    "Kib'",
    "Kab'an",
    "Etz'nab'",
    "Kawak",
    "Ajaw"
  };
}
