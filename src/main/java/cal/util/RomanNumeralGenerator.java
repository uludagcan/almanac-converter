package cal.util;

/**
 * A Roman Numeral Generator.
 * <p>
 * The numeric system used in ancient Rome, roman numerals employs
 * combinations of letters from the Latin alphabet to signify values. After
 * the 14th century, Roman numerals began to be replaced in most contexts by
 * Arabic numbers.
 * @author Chris Engelsma
 * @version 1.0
 * @since 2015-08-09
 */
public class RomanNumeralGenerator {


  /**
   * Converts an Arabic number to a Roman numeral.
   * @param an Arabic number.
   * @return a Roman numeral.
   */
  public static String toRoman(int arabic) {
    String roman = "";

    /*
     * The concept of zero and negative numbers wasn't realized until the
     * arabic number system much later in history.
     */
    if (arabic < 1)
      throw new NumberFormatException("Roman numeral must be positive");

    /*
     * Roman Numerals starting at 4000 have a horizontal line above the first
     * numeral denoting "x 1000", and there's no easy way to show that here.
     */
    if (arabic > 3999)
      throw new NumberFormatException("Value must be 3999 or less");

    for (int i=0; i<_numbers.length; ++i) {
      while (arabic >= _numbers[i]) {
        roman += _letters[i];
        arabic -= _numbers[i];
      }
    }
    return roman;
  }

  /**
   * Converts a Roman numeral to an Arabic number.
   * @param roman a Roman numeral.
   * @return an Arabic number.
   */
  public static int toArabic(String roman) {
    int arabic = 0;
    int i = 0;
    if (roman.length() == 0)
      throw new NumberFormatException("Roman numeral string can't be empty");
    roman = roman.toUpperCase();
    while (i<roman.length()) {
      char letter = roman.charAt(i);
      int number = ltn(letter);
      i++;
      if (i==roman.length()) {
        arabic += number;
      } else {
        int next = ltn(roman.charAt(i));
        if (next > number) {
          arabic += (next-number);
          i++;
        } else arabic += number;
      }
    }
    if (arabic > 3999)
      throw new NumberFormatException("Value must be 3999 or less");
    return arabic;
  }

  /**
   * Converts an Arabic number to a Roman numeral.
   * <p>
   * Short-hand method call.
   * @param arabic an Arabic number.
   * @return a Roman numeral.
   */
  public static String itr(int arabic) {
    return toRoman(arabic);
  }

  /**
   * Converts a Roman numeral to an Arabic number.
   * <p>
   * Short-hand method call.
   * @param roman a Roman numeral.
   * @return an Arabic number.
   */
  public static int rti(String roman) {
    return toArabic(roman);
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private static int ltn(char letter) {
    switch (letter) {
      case 'I': return 1;
      case 'V': return 5;
      case 'X': return 10;
      case 'L': return 50;
      case 'C': return 100;
      case 'D': return 500;
      case 'M': return 1000;
      default: throw new NumberFormatException(
        "\""+letter + "\" not an acceptable roman numeral");
    }
  }

  private static int[]    _numbers = { 1000, 900, 500, 400, 100, 90,
                                      50,  40,  10,   9,   5,  4,  1};
  private static String[] _letters = {"M","CM","D","CD","C","XC",
                                      "L","XL","X","IX","V","IV","I"};

}
