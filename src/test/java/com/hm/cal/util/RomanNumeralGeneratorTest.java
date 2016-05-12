/*****************************************************************************
Copyright 2015 Hypotemoose, LLC.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*****************************************************************************/
package com.hm.cal.util;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 * Tests {@link com.hm.cal.util.RomanNumeralGenerator}.
 * @author Chris Engelsma
 * @version 2015.09.03
 */
public class RomanNumeralGeneratorTest {
  @Test
  public void arabicToRomanNumeralsShouldBeCorrect() {
    int[] arabic = { 
         1,    2,    3,    4,    5,    6,    7,    8,    9,   10,
        11,   12,   13,   14,   15,   16,   17,   18,   19,   20 };
    String[] answers = { 
          "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
         "XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX" };

    String[] results = new String[20];
    for (int i=0; i<arabic.length; ++i) 
      results[i] = RomanNumeralGenerator.toRoman(arabic[i]);
    
    assertArrayEquals("FAIL: Roman numerals should match Arabic numbers",
                      answers,
                      results);
  }
  
  @Test
  public void romanNumeralsToArabicShouldBeCorrect() {
    int[] answers = { 
         1,    2,    3,    4,    5,    6,    7,    8,    9,   10,
        11,   12,   13,   14,   15,   16,   17,   18,   19,   20 };

    String[] roman = { 
          "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
         "XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX" };

    int[] results = new int[20];
    for (int i=0; i<roman.length; ++i) 
      results[i] = RomanNumeralGenerator.toArabic(roman[i]);
    
    assertArrayEquals("FAIL: Roman numerals should match Arabic numbers",
                      answers,
                      results);
  }
  
  @Test(expected = NumberFormatException.class)
  public void nonRomanCharacterShouldThrowException() {
    RomanNumeralGenerator.toArabic("T");
  }
  
  
}
