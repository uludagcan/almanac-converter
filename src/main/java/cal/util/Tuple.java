package cal.util;

/**
 * A simple tuple (pair).
 * @author Chris Engelsma
 * @version 2015.09.03
 */
public class Tuple<X,Y> {
  public final X first;
  public final Y second;
  
  public Tuple(X x, Y y) {
    this.x = x;
    this.y = y;
  }
}
