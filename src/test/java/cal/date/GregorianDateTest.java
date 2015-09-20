package cal.date;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests {@link GregorianDate.java}.
 */
public class GregorianDateTest {
  @Test
  public void leapYearsShouldBeIdentified() {
    assertEquals( "1900 must be normal year",
                  false,
                  GregorianDate.isLeapYear(1900));
    assertEquals( "2000 must be a leap year",
                  true,
                  GregorianDate.isLeapYear(2000));
  }
}
