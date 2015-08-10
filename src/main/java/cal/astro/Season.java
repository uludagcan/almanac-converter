package cal.astro;

/**
 * An astronomical season.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-10
 */ 
public enum Season {
  SPRING(0),
  SUMMER(1),
  AUTUMN(2),
  WINTER(3);

  private final int id;
  Season(int id) { this.id = id; }
  public int getValue() { return id; }
}
