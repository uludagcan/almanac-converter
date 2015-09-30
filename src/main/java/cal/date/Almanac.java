package cal.date;

/**
 * An almanac interface.
 * @author Chris Engelsma
 * @version 2015.08.11
 */
public interface Almanac {
  /*
   * Calendar Constants
   */
  public static final JulianDay EPOCH = new JulianDay();
  public static final String CALENDAR_NAME = "";
  public String getDate();
}
