package cal.util;

/**
 * Tests {@link RomanNumeralGenerator}.
 */
public class RomanNumeralGeneratorTest {
  public static void main(String[] args) {
    int[] numbers = { 1,  2,  3,  4,  5,  6,  7,  8,  9, 10,
                     11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                     1793, 2015, 1945, 1865, 1776, 1986};
    for (int i=0; i<numbers.length; ++i) {
      String roman = RomanNumeralGenerator.toRoman(numbers[i]);
      int arabic = RomanNumeralGenerator.toArabic(roman);
      System.out.println(roman+" -> "+arabic);
    }
  }
}
