package cal.util;

import cal.date.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class ConverterTest {
  @Test
  public void julianDayShouldConvertToGregorianCalendar() {
    GregorianCalendar actual = 
      Converter.toGregorianCalendar(new JulianDay(2446864.5));
    GregorianCalendar expected = new GregorianCalendar(1987,3,10);
    assertEquals( "FAIL: Julian Day -> Gregorian Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToFrenchRepublicanCalendar() {
    FrenchRepublicanCalendar actual =
      Converter.toFrenchRepublicanCalendar(new JulianDay(2446864.5));
    FrenchRepublicanCalendar expected = new FrenchRepublicanCalendar(195,6,2,9);
    assertEquals( "FAIL: Julian Day -> French Republican Calendar is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDateShouldConvertToMayaCalendar() {
    MayaCalendar actual = 
      Converter.toMayaCalendar(new JulianDay(2446864.5));
    MayaCalendar expected = new MayaCalendar(12,18,13,15,2);
    assertEquals( "FAIL: Julian Day -> Maya Calendar is broken",
                  true,
                  actual.equals(expected));
  }
}
