package cal.util;

import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests {@link RomanNumeralGenerator}.
 */
public class RomanNumeralGeneratorTest {
  @Test
  public void arabicToRomanNumeralsShouldBeCorrect() {
    int[] arabic = { 
         1,    2,    3,    4,    5,    6,    7,    8,    9,   10,
        11,   12,   13,   14,   15,   16,   17,   18,   19,   20 };
    String[] answers = { 
          "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
         "XI","XII","XIII","XIV","XV","XVI","XVII",'XVIII","XIX","XX" };
    String[] results = new String[20];
    for (int i=0; i<numbers.length; ++i) 
      results[i] = RomanNumeralGenerator.toRoman(arabic[i]);
    
    assertArrayEquals("FAIL: Roman numerals should match Arabic numbers",answers,results);
  }
  
  @Test
  public void romanNumeralsToArabicShouldBeCorrect() {
    int[] answers = { 
         1,    2,    3,    4,    5,    6,    7,    8,    9,   10,
        11,   12,   13,   14,   15,   16,   17,   18,   19,   20 };
    String[] roman = { 
          "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
         "XI","XII","XIII","XIV","XV","XVI","XVII",'XVIII","XIX","XX" };
    int[] results = new ing[20];
    for (int i=0; i<numbers.length; ++i) 
      results[i] = RomanNumeralGenerator.toArabic(roman[i]);
    
    assertArrayEquals("FAIL: Roman numerals should match Arabic numbers",answers,results);
  }
  
  @Test(expected = NumberFormatException.class)
  public void nonRomanCharacterShouldThrowException() {
    RomanNumeralGenerator.toArabic("T");
  }
  
  
}
