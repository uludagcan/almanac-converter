package cal.astro;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import cal.date.*;

/**
 * Tests {@link Meeus.java}.
 */
public class MeeusTest {
  @Test
  public void autumnalEquinoxShouldBeCorrect() {
    // Autumnal Equinox 2015 = 9/23 @ 08:21:33.9 UTC
    int year = 2015;
    Season season = Season.AUTUMN;
    double expected = 2457288.848309;
    double result = Meeus.equinox(year,season);
    assertEquals( expected,
                  result,
                  0.0001);
  }

  @Test
  public void springEquinoxShouldBeCorrect() {
    // Spring Equinox 2015 = 3/20 @ 22:46:20.6 UTC
    double expected = 2457102.448850;
    double result = Meeus.equinox(2015,Season.SPRING);
    assertEquals( expected,
                  result,
                  0.0001);
  }

  @Test
  public void summerSolsticeShouldBeCorrect() {
    // Summer Solstice 2015 = 6/21 @ 16:39:00.5 UTC
    double expected = 2457195.193756;
    double result = Meeus.equinox(2015,Season.SUMMER);
    assertEquals( expected,
                  result,
                  0.0001);
  }

  @Test
  public void winterSolsticeShouldBeCorrect() {
    // Winter Solstice 2015 = 12/22 @ 04:49:20.3 UTC
    double expected = 2457378.700929;
    double result = Meeus.equinox(2015,Season.WINTER);
    assertEquals( expected,
                  result,
                  0.0001);
  }
}
