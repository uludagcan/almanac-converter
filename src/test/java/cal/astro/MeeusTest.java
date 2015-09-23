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
    double expected = 2457288.847222;
    double result = Meeus.equinox(2015,Season.AUTUMN);
    assertEquals( expected,
                  result,
                  0.0001);
  }

  @Test
  public void springEquinoxShouldBeCorrect() {
    double expected = 2457101.688194;
    double result = Meeus.equinox(2015,Season.SPRING);
    assertEquals( expected,
                  result,
                  0.0001);
  }

  @Test
  public void summerSolsticeShouldBeCorrect() {
    double expected = 2457195.193750;
    double result = Meeus.equinox(2015,Season.SUMMER);
    assertEquals( expected,
                  result,
                  0.0001);
  }

  @Test
  public void winterSolsticeShouldBeCorrect() {
    double expected = 2457378.700694;
    double result = Meeus.equinox(2015,Season.WINTER);
    assertEquals( expected,
                  result,
                  0.0001);
  }
}
