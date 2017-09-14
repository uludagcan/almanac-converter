Almanac Converter 
-----------------

An easy-to-use Java-based calendar converter - able to convert between various known calendars.

[![Build Status](https://travis-ci.org/chrisengelsma/almanac-converter.svg?branch=master)](https://travis-ci.org/chrisengelsma/almanac-converter) [![Codecov](https://img.shields.io/codecov/c/github/chrisengelsma/almanac-converter.svg)](https://codecov.io/gh/chrisengelsma/almanac-converter) [![license](https://img.shields.io/github/license/chrisengelsma/almanac-converter.svg)](https://github.com/chrisengelsma/almanac-converter/blob/master/LICENSE) [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

## Table of Contents
- [Requirements](#requirements)
- [Installation](#installation)
  - [Option 1: Build the source](#option-1-build-the-source)
  - [Option 2: Use Release Jar](#option-2-use-release-jar)
- [Supported Calendars](#supported-calendars)
  - [Gregorian Calendar](#gregorian-calendar)
  - [Julian Calendar](#julian-calendar)
  - [French Republican Calendar](#french-republican-calendar)
  - [Maya Calendar](#maya-calendar)
  - [Islamic Calendar](#islamic-calendar)
  - [Persian Calendar](#persian-calendar)
  - [Indian Civil Calendar](#indian-civil-calendar)
  - [Julian Day](#julian-day)
- [Development Roadmap](#development-roadmap)
- [Credits](#credits)

## Requirements

* Java Development Kit (JDK) 1.8 +
* Gradle 3.3 +

## Installation

Include the compiled jar file in the classpath of your java compiler:

```bash
$ javac -cp path/to/almanac-converter-X.X.X.jar ...
```

### Option 1 Build the source

After downloading the source, run

```bash
$ gradle build
```

which will build a jar and place it in `build/lib`.

### Option 2 Use release jar

Download the latest release jar from here: [Releases](https://github.com/chrisengelsma/almanac-converter/releases)

## Supported Calendars

### Gregorian Calendar
The Gregorian Calendar is internationally the most widely used civil calendar. It was named for Pope Gregory XIII who introduced it on October 15, 1582. The calendar was a refinement to the Julian Calendar, with the motivation of setting the Easter holiday to a specific date instead of the spring equinox, which naturally drifted dates.

Each year is divided into 12 months, with a varied number of days per month. To account for the drift in seasons, a leap year occurs which introduces an additional day in February. These leap years happen every year that's divisible by 4, except years that are divisible by 100 but not divisible by 400. For example: 1700, 1800 and 1900 are NOT leap years, but 2000 is a leap year.

### Julian Calendar
The Julian Calendar is a calendar introduced under Julius Caesar in 46 BC. It very closely resembled the Gregorian Calendar in that it was comprised of years of 365 days long divided into 12 months. The main difference is that leap years are simply computed every 4 years (no 100-400 year rule). This calendar is still in use by some Orthodox churches (although it is primarily referred to as the "Old Style").

### French Republican Calendar
The French Republican Calendar (or, erroneously, French Revolutionary Calendar) was a calendar created and used during the French Revolution. It was only used in practice for 12 years starting in late 1793 until it was abolished by Napoleon Bonaparte as an effort to reinstate the catholic church within France. This calendar was later picked up, albeit briefly, during the Paris Commune of 1871.

Each year is divided into 12 months (mois), with each month being an equal 30 days long, divided out further into 3 weeks (décades) 10 days long. Every year begins on the autumnal equinox as observed in Paris. The slight variation in seaons required the use of 5-6 additional "Sans-culottides" days. While the calendar was adopted on October 24, 1793 (3 Brumaire, An II), the official epoch was set to September 22, 1792 (1 Vendemiaire, An I) to commemorate the founding of the republic.
 
To further reduce the influence of the Church, a Rural Calendar was introduced, naming each day of the year after various crops, minerals, animals and work tools to reflect the changing of the seasons. 

### Maya Calendar
The Maya calendar is not one calendar per se, but rather a system of calendars employed by pre-Columbian Mesoamerica. In many modern communities in Guatemala and Mexico, this calendar is still used today.

The Maya calendar consists of several cycles (or counts) of different lengths. The 260-day calendar, or <em>Tzolk'in</em>, was combined with the 365-day calendar, or <em>Haab'</em> to form a synchronized cycle lasting for 52 Haab' called the Calendar Round. To measure periods longer than this, the Maya used the Long Count calendar.

### Islamic Calendar
The Islamic calendar, or Hijri calendar, is a lunar calendar currently used in many Muslim countries and also used by Muslims to determine the proper days on which to observing the annual fasting, to attend Hajj, and to celebrate other Islamic holidays and festivals. The calendar consists of 12 months with a year of 354 days. The lengths of the months are determined by the birth of a new lunar cycle, which historically resulted in each month being 29 or 30 days depending on the visibility of the moon. However, certain sects and groups now use a tabular Islamic calendar, in which odd-numbered months have thirty days, and even months have 29.

### Hebrew Calendar
The Hebrew (Jewish) calendar is a calendar used predominantly for Jewish religious observances. It determines the dates for Jewish holidays and the appropriate public reading of Torah portions. In Israel, it is used for religious purposes, provides a time frame for agriculture and is an official calendar used for civil purposes.

### Persian Calendar
The Persian calendar was officially adopted in 1925. Each year consists of 12 months. The beginning of the calendar is determined by the day on which the March equinox occurs at or after noon at with reference to Iran standard time. Because of this, leap years have no set rule, however an additional day can be added to the last month on years where there is discrepancy.

### Indian Civil Calendar
While a plethora of calendars exist in India, the government adopted an official calendar in 1957 to be used for civil purposes. The calendar is comprised of 12 months. The first month, Chaitra, contains 30 days in a normal year and 31 days in a leap year, followed by five 30-day months and then six 31-day months. Leap years occur in the same year as a Gregorian year, so the two calendars are remain synchronized. The calendar begins counting from the start of the Saka era, which occurs on March 22, 79 in the Gregorian calendar.
 
### Julian Day
The Julian Day is the continuous count of days since the beginning of the Julian Period used primarily by astronomers. The Julian Period is a chronological interval of 7980 years beginning in 4713 BC, and has been used since 1583 to convert between different calendars. The next Julian Period begins in the year 3268 AD.

## Development Roadmap
```
* Julian Day                 [100%] DONE
* Gregorian Calendar         [100%] DONE
* Julian Calendar            [100%] DONE
* French Republican Calendar [100%] DONE
* Mayan Calendar             [100%] DONE
* Islamic Calendar           [100%] DONE
* Hebrew Calendar            [100%] DONE
* Persian Calendar           [100%] DONE
* Indian Civil Calendar      [100%] DONE
* Coptic Calendar            [  0%] IN PROGRESS
* Chinese Calendar           [  0%] PLANNING
* Soviet Calendar            [  0%] PLANNING
* Dangun Calendar            [  0%] PLANNING
* Juche Calendar             [  0%] PLANNING
* More...
```

## Credits

Inspired by Fourmilab's javascript-based calendar converter and astronomical equations by Jean Meeus.
