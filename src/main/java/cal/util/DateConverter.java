package cal.util;

import cal.date.*;

public class DateConverter {

  public static JulianDay toJulian(Almanac date) {
    return (JulianDay)date;
  }

  public static FrenchRepublicanDate toFrenchRepublicanDate(Almanac date) {
    return (FrenchRepublicanDate)date;
  }

  public static GregorianDate toGregorianDate(Almanac date) {
    return (GregorianDate)date;
  }

  public static MayaDate toMayaDate(Almanac date) {
    return (MayaDate)date;
  }
}
