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
package com.moose.cal.util;

import com.moose.cal.date.*;

/**
 * An Almanac formatter.
 * @author Chris Engelsma
 * @version 2015.09.30
 */ 
public class AlmanacFormat {

  public AlmanacFormat(String pattern) {
    _pattern = pattern;
  }

  public String format(Almanac almanac) {
    StringBuffer buffer = new StringBuffer();
    String date = parseDate(almanac,buffer).toString();
    return date;
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private String _pattern = "";
  private static final String _patternChars = "GyMdkHmsSEDFwWahKzZ";
  private final static int _TAG_QUOTE_ASCII_CHAR = 100;
  private final static int _TAG_QUOTE_CHARS = 101;
  transient private char[] compiledPattern;

  private StringBuffer parseDate(Almanac almanac, StringBuffer buffer) {
    compiledPattern = compile();
    System.out.println(new String(compiledPattern));

    for (int i=0; i<compiledPattern.length; ) {
      int tag   = compiledPattern[i] >>> 8;
      int count = compiledPattern[i++] & 0xff;
      if (count==255) {
        count = compiledPattern[i++] << 16;
        count |= compiledPattern[i++];
      }
      switch(tag) {
        case _TAG_QUOTE_ASCII_CHAR:
          buffer.append((char)count);
          break;

        case _TAG_QUOTE_CHARS:
          buffer.append(compiledPattern,i,count);
          i+=count;
          break;

        default:
          subFormat(tag,count,buffer);
          break;
      }
    }
    return buffer;
  }

  private void subFormat(
    int patternCharIndex, int count, StringBuffer buffer) 
  {
    int max = Integer.MAX_VALUE;
    String current = null;
    int beginOffset = buffer.length();
    switch (patternCharIndex) {
      case 0: // 'G' - Era
        current = "";
        break;
      case 1: // 'y' - Year
        if (count>=4) buffer.append("Long year");
        else          buffer.append("Short year");
        break;
      case 2: // 'M' - Month
        if (count>=4) {
          buffer.append("Long Month");
        } else if (count==3) {
          buffer.append("Short Month");
        } else {
          buffer.append("Month No");
        }
        break;
      case 4: // 'k'- Hour of the day, 1-based
        buffer.append("Hour of the day");
        break;
      case 9: // 'E' - Day of the week
        buffer.append("Day of the week");
        break;
      case 14: // 'a' - AM/PM
        buffer.append("AM/PM");
        break;
      case 15: // 'h' Hour: 1-based
        buffer.append("Hour");
        break;
      case 17: // 'z' - Zone offset
        buffer.append("Zone offset");
        break;
      case 18: // 'Z' - Zone offset ("-/+hhmm" form)
        buffer.append("Zone offset (\"-/+hhmm\" form)");
        break;
      default:
        if (current==null) {
          buffer.append("Everything Else");
        }
        break;
    }

    if (current!=null) {
      buffer.append(current);
    }
  }

  private char[] compile() {
    int length = _pattern.length();
    boolean inQuote = false;
    StringBuilder compiledPattern = new StringBuilder(length * 2);
    StringBuilder tmpBuffer = null;
    int count = 0;
    int lastTag = -1;
    for (int i = 0; i < length; i++) {
      char c = _pattern.charAt(i);

      if (c == '\'') {
        if ((i + 1) < length) {
          c = _pattern.charAt(i + 1);
          if (c == '\'') {
            i++;
            if (count != 0) {
              encode(lastTag, count, compiledPattern);
              lastTag = -1;
              count = 0;
            }
            if (inQuote) {
              tmpBuffer.append(c);
            } else {
              compiledPattern.append((char)(_TAG_QUOTE_ASCII_CHAR << 8 | c));
            }
            continue;
          }
        }
        if (!inQuote) {
          if (count != 0) {
            encode(lastTag, count, compiledPattern);
            lastTag = -1;
            count = 0;
          }
          if (tmpBuffer == null) {
            tmpBuffer = new StringBuilder(length);
          } else {
            tmpBuffer.setLength(0);
          }
          inQuote = true;
        } else {
          int len = tmpBuffer.length();
          if (len == 1) {
            char ch = tmpBuffer.charAt(0);
            if (ch < 128) {
              compiledPattern.append((char)(_TAG_QUOTE_ASCII_CHAR << 8 | ch));
            } else {
              compiledPattern.append((char)(_TAG_QUOTE_CHARS << 8 | 1));
              compiledPattern.append(ch);
            }
          } else {
            encode(_TAG_QUOTE_CHARS, len, compiledPattern);
            compiledPattern.append(tmpBuffer);
          }
          inQuote = false;
        }
        continue;
      }
      if (inQuote) {
        tmpBuffer.append(c);
        continue;
      }
      if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
        if (count != 0) {
          encode(lastTag, count, compiledPattern);
          lastTag = -1;
          count = 0;
        }
        if (c < 128) {
          compiledPattern.append((char)(_TAG_QUOTE_ASCII_CHAR << 8 | c));
        } else {
          int j;
          for (j = i + 1; j < length; j++) {
            char d = _pattern.charAt(j);
            if (d == '\'' || (d >= 'a' && d <= 'z' || d >= 'A' && d <= 'Z')) {
              break;
            }
          }
          compiledPattern.append((char)(_TAG_QUOTE_CHARS << 8 | (j - i)));
          for (; i < j; i++) {
            compiledPattern.append(_pattern.charAt(i));
          }
          i--;
        }
        continue;
      }

      int tag;
      if ((tag = _patternChars.indexOf(c)) == -1) {
        throw new IllegalArgumentException("Illegal pattern character " + "'" + c + "'");
      }
      if (lastTag == -1 || lastTag == tag) {
        lastTag = tag;
        count++;
        continue;
      }
      encode(lastTag, count, compiledPattern);
      lastTag = tag;
      count = 1;
    }

    if (inQuote) {
      throw new IllegalArgumentException("Unterminated quote");
    }

    if (count != 0) {
      encode(lastTag, count, compiledPattern);
    }

          // Copy the compiled pattern to a char array
    int len = compiledPattern.length();
    char[] r = new char[len];
    compiledPattern.getChars(0, len, r, 0);
    return r;
  }

  private static final void encode(
    int tag, int length, StringBuilder buffer) 
  {
    if (length<255) {
      buffer.append((char)(tag << 8 | length));
    } else {
      buffer.append((char)((tag << 8) | 0xff));
      buffer.append((char)(length >>> 16));
      buffer.append((char)(length & 0xffff));
    }
  }
}
