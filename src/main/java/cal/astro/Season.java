package cal.astro;

public enum Season {
  SPRING(0),
  SUMMER(1),
  AUTUMN(2),
  WINTER(3);

  private final int id;
  Season(int id) { this.id = id; }
  public int getValue() { return id; }
}
