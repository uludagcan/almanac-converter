package cal.date;

public class FrenchRepublicanDateTest {
  public static void main(String[] args) throws Exception {
    GregorianDate date = new GregorianDate();
    FrenchRepublicanDate frc = new FrenchRepublicanDate(date);
    System.out.println("TODAY");
    date.print();
    frc.print();
    System.out.println();

    date = new GregorianDate(23,10,1793);
    frc = new FrenchRepublicanDate(date);
    System.out.println("REPUBLICAN CALENDAR DECREED");
    date.print();
    frc.print();
    System.out.println();

    date = new GregorianDate(27,7,1794);
    frc = new FrenchRepublicanDate(date);
    System.out.println("FALL OF ROBESPIERRE");
    date.print();
    frc.print();
    System.out.println();

    date = new GregorianDate(9,11,1799);
    frc = new FrenchRepublicanDate(date);
    System.out.println("NAPOLEON BONAPARTE NAMED FIRST CONSUL");
    date.print();
    frc.print();
    System.out.println();

  }
}
