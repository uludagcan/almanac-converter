package cal.date;

/**
 * Tests {@link FrenchRepublicanDate}.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-07
 */
public class FrenchRepublicanDateTest {
  public static void main(String[] args) throws Exception {
    GregorianDate date = new GregorianDate();
    JulianDay jd = new JulianDay(date);
    FrenchRepublicanDate frc = new FrenchRepublicanDate(date);
    System.out.println("TODAY");
    date.print();
    jd.print();
    frc.print();
    System.out.println();

    date = new GregorianDate(24,10,1793);
    jd = new JulianDay(date);
    frc = new FrenchRepublicanDate(date);
    System.out.println("REPUBLICAN CALENDAR DECREED");
    date.print();
    jd.print();
    frc.print();
    System.out.println();

    date = new GregorianDate(27,7,1794);
    jd = new JulianDay(date);
    frc = new FrenchRepublicanDate(date);
    System.out.println("FALL OF ROBESPIERRE");
    date.print();
    jd.print();
    frc.print();
    System.out.println();

    date = new GregorianDate(9,11,1799);
    jd = new JulianDay(date);
    frc = new FrenchRepublicanDate(date);
    System.out.println("NAPOLEON BONAPARTE NAMED FIRST CONSUL");
    date.print();
    jd.print();
    frc.print();
    System.out.println();

  }
}
