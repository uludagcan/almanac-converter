package cal.date;

/**
 * Tests {@link GregorianDate.java}.
 */
public class GregorianDateTest {
  public static void main(String[] args) {
    GregorianDate date = new GregorianDate();
    date.print();
    if (date.isLeapYear()) System.out.println("Leap Year");
    else System.out.println("Normal Year");
  }
}
