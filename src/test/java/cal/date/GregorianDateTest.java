package cal.date;

import java.util.Calendar;
import org.joda.time.DateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 * Tests {@link GregorianDate}.
 * @author Chris Engelsma
 * @version 1.1
 * @since 2015-09-23
 */
public class GregorianDateTest {
  @Test
  public void checkProperLeapYearRule() {
    assertEquals( "FAIL: 1900 must be normal year",
                  false,
                  GregorianDate.isLeapYear(1900));
  }

  @Test
  public void sameDatesShouldBeEqual() {
    GregorianDate date1 = new GregorianDate(1986,9,3);
    GregorianDate date2 = new GregorianDate(1986,9,3);
    assertEquals( "FAIL: Dates should be equal",
                  true,
                  date1.equals(date2));
  }

  @Test
  public void datesShouldBeInOrder() {
    GregorianDate date1 = new GregorianDate(1986,9,3);
    GregorianDate date2 = new GregorianDate(1986,9,4);
    assertEquals( "FAIL: Dates should be in chronological order",
                  true,
                  GregorianDate.datesAreChronological(date1,date2));
  }

  @Test 
  public void dateOneShouldBeBeforeDateTwo() {
    GregorianDate date1 = new GregorianDate(1986,9,3);
    GregorianDate date2 = new GregorianDate(1986,9,4);
    assertEquals( "FAIL: Dates should be in chronological order",
                  true,
                  date1.isBefore(date2));
  }

  @Test 
  public void dateOneShouldBeAfterDateTwo() {
    GregorianDate date1 = new GregorianDate(1986,9,3);
    GregorianDate date2 = new GregorianDate(1986,9,4);
    assertEquals( "FAIL: Dates should be in chronological order",
                  true,
                  date2.isAfter(date1));
  }

  @Test
  public void daysPerMonthShouldBeCorrect() {
    int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int year = 2015;
    int[] result = GregorianDate.getDaysPerMonthInYear(year);
    assertArrayEquals("FAIL: Days of month aren't correct",
                      days,
                      result);
  }

  @Test
  public void jodaDateTimeShouldConstruct() {
    GregorianDate date1 = new GregorianDate(new DateTime(1986,9,3,0,1));
    GregorianDate date2 = new GregorianDate(1986,9,3);
    assertEquals( "FAIL: Date should construct from Joda DateTime",
                  true,
                  date1.equals(date2));
  }

  @Test
  public void jdkCalendarShouldConstruct() {
    Calendar cal = Calendar.getInstance();
    cal.set(1986,8,3);
    GregorianDate date1 = new GregorianDate(cal);
    GregorianDate date2 = new GregorianDate(1986,9,3); 
    assertEquals( "FAIL: Date should construct from JDK Calendar",
                  true,
                  date1.equals(date2));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void monthNameShouldBadMonthNumber() {
    String month = GregorianDate.getMonthName(0); // No "0" month!
  }

  
}
