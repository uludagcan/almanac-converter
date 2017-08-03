/*****************************************************************************
Copyright 2015 Chris Engelsma

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
package com.hypotemoose.cal.util;

import java.lang.Math;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import org.testng.annotations. Test;

/**
 * Tests {@link com.hypotemoose.cal.util.Util}.
 * @author Chris Engelsma
 * @version 2015.11.03
 */
public class UtilTest {
  @Test
  public void integerShouldConvertToString() {
    int[] input = new int[] { 0, 1, 1986, 1794, 3999};
    String[] expected = new String[] { "0", "1", "1986", "1794", "3999"};
    String[] actual = new String[5];
    for (int i=0; i<5; ++i)
      actual[i] = Util.its(input[i]);
    assertArrayEquals("FAIL: Int-to-String not converting properly",
                      expected,
                      actual);
  }

  @Test
  public void sinDoubleShouldComputeCorrectly() {
    double[] angles = new double[] { 0.0, 45.0, 90.0, 180.0, 360.0 };
    double[] expected = new double[5];
    double[] actual = new double[5];
    expected[0] = 0.0;
    expected[1] = Math.sqrt(2)/2;
    expected[2] = 1.0;
    expected[3] = 0.0;
    expected[4] = 0.0;

    for (int i=0; i<5; ++i) 
      actual[i] = Util.dsin(angles[i]);

    assertArrayEquals("FAIL: Degree-sine computing bad results",
                      expected,
                      actual,
                      1.0E-6);
  }

  @Test
  public void cosDoubleShouldComputeCorrectly() {
    double[] angles = new double[] { 0.0, 45.0, 90.0, 180.0, 360.0 };
    double[] expected = new double[5];
    double[] actual = new double[5];
    expected[0] = 1.0;
    expected[1] = Math.sqrt(2)/2;
    expected[2] = 0.0;
    expected[3] =-1.0;
    expected[4] = 1.0;

    for (int i=0; i<5; ++i) 
      actual[i] = Util.dcos(angles[i]);

    assertArrayEquals("FAIL: Degree-cosine computing bad results",
                      expected,
                      actual,
                      1.0E-6);
  }

  @Test
  public void radiansShouldConvertToDegrees() {
    double pi = Math.PI;
    double[] radians = new double[] { 0.0, pi/4.0, pi/2.0, pi, 2.0*pi };
    double[] expected = new double[5];
    expected[0] = 0.0;
    expected[1] = 45.0;
    expected[2] = 90.0;
    expected[3] = 180.0;
    expected[4] = 360.0;

    double[] actual = new double[5];
    for (int i=0; i<5; ++i) 
      actual[i] = Util.rtd(radians[i]);

    assertArrayEquals("FAIL: Radians not converting to degrees correctly",
                      expected,
                      actual,
                      1.0E-6);
  }

  @Test
  public void degreesShouldConvertToRadians() {
    double pi = Math.PI;
    double[] degrees = new double[] { 0.0, 45.0, 90.0, 180.0, 360.0 };
    double[] expected = new double[5];
    expected[0] = 0.0;
    expected[1] = pi/4.0;
    expected[2] = pi/2.0;
    expected[3] = pi;
    expected[4] = 2.0*pi;

    double[] actual = new double[5];
    for (int i=0; i<5; ++i) 
      actual[i] = Util.dtr(degrees[i]);

    assertArrayEquals("FAIL: Degrees not converting to radians correctly",
                      expected,
                      actual,
                      1.0E-6);
  }

  @Test
  public void angleInDegreesShouldFixBetweenProperRange() {
    double[] degrees = new double[] { 0.0, -45.0, -90.0, 360.0, 900.0 };
    double[] expected = new double[5];
    expected[0] = 0.0;
    expected[1] = 315.0;
    expected[2] = 270.0;
    expected[3] = 0.0;
    expected[4] = 180.0;

    double[] actual = new double[5];
    for (int i=0; i<5; ++i) 
      actual[i] = Util.fixAngle(degrees[i]);

    assertArrayEquals("FAIL: Degrees not fixing bounds correctly",
                      expected,
                      actual,
                      1.0E-6);
  }

  @Test
  public void angleInRadiansShouldFixBetweenProperRange() {
    double pi = Math.PI;
    double[] radians = new double[] { 0.0, -pi/4.0, -pi/2, 2*pi, 5*pi };
    double[] expected = new double[5];
    expected[0] = 0.0;
    expected[1] = pi*7.0/4.0;
    expected[2] = pi*3.0/2.0;
    expected[3] = 0.0;
    expected[4] = pi;

    double[] actual = new double[5];
    for (int i=0; i<5; ++i) 
      actual[i] = Util.fixAngler(radians[i]);

    assertArrayEquals("FAIL: Radians not fixing bounds correctly",
                      expected,
                      actual,
                      1.0E-6);
  }

}
