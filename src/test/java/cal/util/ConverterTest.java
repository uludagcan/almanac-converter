package cal.util;

import cal.date.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class ConverterTest {
  @Test
  public void julianDayShouldConvertToGregorianDate() {
    GregorianDate actual = 
      Converter.toGregorianDate(new JulianDay(2446864.5));
    GregorianDate expected = new GregorianDate(1987,3,10);
    assertEquals( "FAIL: Julian Day -> Gregorian Date is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDayShouldConvertToFrenchRepublicanDate() {
    FrenchRepublicanDate actual =
      Converter.toFrenchRepublicanDate(new JulianDay(2446864.5));
    FrenchRepublicanDate expected = new FrenchRepublicanDate(195,6,2,9);
    assertEquals( "FAIL: Julian Day -> French Republican Date is broken",
                  true,
                  actual.equals(expected));
  }

  @Test
  public void julianDateShouldConvertToMayaDate() {
    MayaDate actual = 
      Converter.toMayaDate(new JulianDay(2446864.5));
    MayaDate expected = new MayaDate(12,18,13,15,2);
    assertEquals( "FAIL: Julian Day -> Maya Date is broken",
                  true,
                  actual.equals(expected));
  }
}
