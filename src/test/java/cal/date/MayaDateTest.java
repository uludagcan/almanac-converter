package cal.date;

/**
 * Tests {@link MayaDate}
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-24
 */
public class MayaDateTest {
  public static void main(String[] args) {
    JulianDay jd = new JulianDay();
    MayaDate mayaDate = new MayaDate(jd);
    jd.print();
    mayaDate.print();
  }
}
