/**
 * The four seasons, marked by their corresponding equinoxes/solstices.
 */

package cal.astro;

public enum Season {
  SPRING(0), // Spring equinox
  SUMMER(1), // Summer solstice
  AUTUMN(2), // Autumnal equinox
  WINTER(3); // Winter solstice

  private final int id;
  Season(int id) { this.id = id; }
  public int getValue() { return id; }
}
