package cal.astro;

import java.util.Calendar;
import cal.date.*;

public class MeeusTest {
  public static void main(String[] args) {
    JulianDay eqjd = Meeus.equinox(2015,Season.AUTUMN);
    GregorianDate eqgd = new GregorianDate(eqjd);

    GregorianDate today = new GregorianDate(Calendar.getInstance());
    JulianDay todayjd = new JulianDay(today);

    System.out.println("Autumnal Equinox: ");
    eqjd.print();
    eqgd.print();
    System.out.println("Today: ");
    todayjd.print();
    today.print();
  }
}
