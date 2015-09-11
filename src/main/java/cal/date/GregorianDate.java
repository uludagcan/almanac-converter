package cal.util;

import cal.date.*;

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
  private final static int _TAG_QUOTE_CHAR = 101;
  private char[] _charPattern = null;

  private StringBuffer parseDate(Almanac almanac, StringBuffer buffer) {
    _charPattern = compile();

    for (int i=0; i<_charPattern.length; ) {
      int tag   = _charPattern[i] >>> 8;
      int count = _charPattern[i++] & 0xff;
      if (count==255) {
        count = _charPattern[i++] << 16;
        count |= _charPattern[i++];
      }
      switch(tag) {
        case _TAG_QUOTE_ASCII_CHAR:
          buffer.append((char)count);
          break;

        case _TAG_QUOTE_CHAR:
          buffer.append(_charPattern,i,count);
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
      case 1: // 'G' - Era
        current = "";
        break;
      case 1: // 'y' - Year
        if (count>=4) buffer.append("Long year");
        else          buffer.append("Short year");
        break;
      case 2: // 'M' - Month
        if (count>=4) {
          current = "Long Month";
        } else if (count==3) {
          current = "Short Month";
        } else {
          if (count<3) current = null;
        }
        if (current==null) {
          buffer.append("Month Number");
        }
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
    StringBuilder builder = new StringBuilder(length*2);
    StringBuilder tmp = null;
    int count = 0, lastTag = -1, tag;
    boolean inQuote = false;

    for (int i=0; i<length; ++i) {
      char c = _pattern.charAt(i);
      if (c=='\'') {
        if ((i+1)<length) {
          c = _pattern.charAt(i+1);
          if (c=='\'') {
            i++;
            if (count!=0) {
              encode(lastTag,count,builder);
              lastTag = -1;
              count = 0;
            }
            if (inQuote) {
              tmp.append(c);
            } else {
              builder.append((char)(_TAG_QUOTE_ASCII_CHAR << 8 | c));
            }
            continue;
          }
        }
        if (!inQuote) {
          if (count!=0) {
            encode(lastTag,count,builder);
            lastTag = -1;
            count = 0;
          }
          if (tmp==null) {
            tmp = new StringBuilder(length);
          } else {
            tmp.setLength(0);
          }
          inQuote = true;
        } else {
          int len = tmp.length();
          if (len==1) {
            char ch = tmp.charAt(0);
            if (ch<128) {
              builder.append((char)(_TAG_QUOTE_ASCII_CHAR << 8 | ch));
            } else {
              builder.append((char)(_TAG_QUOTE_CHAR << 8 | 1));
              builder.append(ch);
            }
          } else {
            encode(_TAG_QUOTE_CHAR,len,builder);
            builder.append(tmp);
          }
          inQuote = false;
        }
        continue;
      } 

      if (inQuote) {
        tmp.append(c);
        continue;
      }

      if (!((c>='a' && c<='z') || (c>='A' && c<='Z'))) {
        if (count!=0) {
          encode(lastTag,count,builder);
          lastTag = -1;
          count = 0;
        }
        if (c<128) {
          builder.append((char)(_TAG_QUOTE_ASCII_CHAR << 8 | c));
        } else {
          int j;
          for (j=i+1; j<length; ++j) {
            char d = _pattern.charAt(j);
            if (d=='\'' || ((d>='a' && d<='z') || (d>='A' && d<='Z')))
              break;
          }
          builder.append((char)(_TAG_QUOTE_CHAR << 8 | (j-i)));
          for (; i<j; i++) builder.append(_pattern.charAt(i));
          --i;
        }
        continue;
      }

      if ((tag = _patternChars.indexOf(c))==-1) {
        throw new IllegalArgumentException(c+": not an acceptable format");
      }

      if (lastTag==-1 || lastTag==tag) {
        lastTag = tag;
        count++;
        continue;
      }

      encode(lastTag,count,builder);
      lastTag = tag;
      count = 1;
    }

    if (inQuote) {
      throw new IllegalArgumentException("Unterminated quote");
    }

    if (count!=0) {
      encode(lastTag,count,builder);
    }

    int len = builder.length();
    char[] r = new char[len];
    builder.getChars(0,len,r,0);
    return r;
  }

  private static final void encode(
    int tag, int length, StringBuilder buffer) 
  {
    if (length<255) {
      buffer.append((char)tag << 8 | length);
    } else {
      buffer.append((char)((tag << 8) | 0xff));
      buffer.append((char)(length >>> 16));
      buffer.append((char)(length & 0xffff));
    }
  }
}
