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
  
  public MayanDate() {
    this(new JulianDay());
  }
  
  public MayanDate(JulianDay jday) {
    // TODO
  }
  
  @Override
  public String getDate() {
    return "Not yet implemented";
  }
  
  @Override
  public void print() {
    System.out.println("Mayan Calendar: Not yet implemented");
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private int _kin;
  private int _uinal;
  private int _tun;
  private int _katun;
  private int _baktun;
  private int _pictun;
  private int _calabtun;
  private int _kinchiltun;
  private int _alautun;
}
