package cal.date;

public class FrenchRepublicanDateTest {
  public static void main(String[] args) throws Exception {
    GregorianDate date = new GregorianDate(19,9,1794);
    FrenchRepublicanDate frc = new FrenchRepublicanDate(date);
    date.print();
    frc.print();
  }
}
