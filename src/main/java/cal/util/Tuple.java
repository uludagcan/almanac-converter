package cal.util;

/**
 * A generic tuple, or pair of values.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-24
 */
public final class Tuple<K,V> {

  public Tuple(K k, V v) {
    _k = k;
    _v = v;
  }

  public K getFirst()  { return _k; }
  public V getSecond() { return _v; }

/////////////////////////////////////////////////////////////////////////////
// private

  private K _k;
  private V _v;
}
