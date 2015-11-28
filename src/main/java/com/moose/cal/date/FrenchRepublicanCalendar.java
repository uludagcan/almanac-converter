/*****************************************************************************
Copyright 2015 Hypotemoose, Inc.

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
package com.moose.cal.date;

import com.moose.cal.astro.*;
import static com.moose.cal.util.RomanNumeralGenerator.*;
import static com.moose.cal.util.Converter.*;
import static com.moose.cal.util.Util.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTime;

/**
 * A date in the French Republican Calendar.
 * <p>
 * The French Republican Calendar (FRC) was a calendar created and used
 * during the French Revolution. It was only used in practice for 12 years 
 * starting in late 1793 until it was abolished by Napoleon Bonaparte as an
 * effort to reinstate the catholic church within France. This calendar was 
 * later picked up, albeit briefly, during the Paris Commune of 1871.
 * <p>
 * Each year is divided into 12 months (mois), with each month being an 
 * equal 30 days long, divided out further into 3 weeks (décades) 10 days 
 * long. Every year begins on the autumnal equinox as observed in Paris.
 * The slight variation in seaons required the use of 5-6 additional
 * "Sans-culottides" days. While the calendar was adopted on October 24, 1793
 * (3 Brumaire, An II), the official epoch was set to September 22, 1792 
 * (1 Vendemiaire, An I) to commemorate the founding of the republic.
 * <p>
 * To further reduce the influence of the Church, a Rural Calendar was 
 * introduced, naming each day of the year after various crops, minerals, 
 * animals and work tools to reflect the changing of the seasons. 
 * @author Chris Engelsma
 * @version 2015.11.09
 */
public final class FrenchRepublicanCalendar extends Almanac {

  public static final String CALENDAR_NAME = "French Republican Calendar";
  public static final JulianDay EPOCH = new JulianDay(2375839.5);

  /**
   * Constructs a French Republican Date for today's date.
   */
  public FrenchRepublicanCalendar() {
    this(new JulianDay());
  }
  
  /**
   * Constructs a French Republican Date with given year, month and day.
   * @param year the year
   * @param month the month
   * @param day the day
   */
  public FrenchRepublicanCalendar(int year, int month, int day) {
    this(year,month,(day/10)+1,(day%10));
  }

  /**
   * Constructs a French Republican Date with given year, month,
   * week and day.
   * @param year the year
   * @param month the month
   * @param week the week
   * @param day the day
   */ 
  public FrenchRepublicanCalendar(int year, int month, int week, int day) {
    super();
    this.year = year;
    this.month = month;
    this.day = day;
    _week = week;
  }

  /**
   * Constructs a French Republican Date fromr a given Gregorian
   * Calendar date.
   * @param date a Gregorian Calendar Date.
   */
  public FrenchRepublicanCalendar(GregorianCalendar date) {
    this(new JulianDay(date));
  }

  /**
   * Constructs a French Republican Date from a given Julian Day.
   * @param jd a Julian Day.
   */
  public FrenchRepublicanCalendar(JulianDay jd) {
    this(toFrenchRepublicanCalendar(jd));
  }

  /**
   * Constructs a French Republican date from another French Republican date.
   * @param date a French Republican date.
   */
  public FrenchRepublicanCalendar(FrenchRepublicanCalendar date) {
    this(date.getYear(),date.getMonth(),date.getWeek(),date.getDay());
  }

  /**
   * Returns today's date as a string.
   * Convenience static method.
   * @return today's date.
   */
  public static String asToday() {
    return (new FrenchRepublicanCalendar()).toString();
  }

  /**
   * Gets the 10-day week (décade).
   * @return the décade.
   */
  public int getWeek() {
    return _week;
  }

  /**
   * Sets this calendar.
   * @param a an almanac.
   */
  @Override
  public void set(Almanac a) {
    FrenchRepublicanCalendar cal = toFrenchRepublicanCalendar(a);
    this.year = cal.getYear();
    this.month = cal.getMonth();
    this.day = cal.getDay();
    _week = cal.getWeek();
  }
 
  /**
   * Returns this calendar's name.
   * @return this calendar's name.
   */
  @Override
  public String getName() {
    return CALENDAR_NAME;
  }

  /**
   * Gets the month names.
   * @return the month names.
   */
  @Override
  public String[] getMonths() {
    return _monthNames;
  }

  /**
   * Gets the number of months in the year.
   * @return the number of months in the year.
   */
  @Override
  public int getNumberOfMonthsInYear() {
    return 13;
  }

  /**
   * Sets the day.
   * @param day the day.
   */
  @Override
  public void setDay(int day) {
    this.day = (day%10);
    _week = day/10+1;
  }

  /**
   * Gets the weekday name.
   * @return the weekday.
   */
  @Override 
  public String getWeekDay() {
    return (this.year>=1) ? _weekDayNames[getDay(false)-1] : "";
  }

  /**
   * Gets the days of the week.
   * @return the days of the week.
   */
  @Override
  public String[] getWeekDays() {
    return _weekDayNames;
  }

  /**
   * Sets this date to the next day.
   */
  @Override
  public void nextDay() {
    day = getDay(true);
    if (day==getNumberOfDaysInMonth()) {
      if (month==getNumberOfMonthsInYear()) {
        month = 1;
        year++;
      } else month++;
      day = 1;
    } else day++;

    _week = (day/10)+1; 
    this.day = (day%10);
  }

  /**
   * Sets this date to the previous day.
   */
  @Override
  public void prevDay() {
    day = getDay(true);

    if (day==1) {
      if (month==1) {
        month = getNumberOfMonthsInYear();
        year--;
      } else month--;
      day = getNumberOfDaysInMonth();
    } else day--;

    _week = (day/10)+1; 
    this.day = (day%10);
  }

  /**
   * Gets the day number.
   * @param longForm true, if using long form (no decades); false, otherwise.
   * @return the day.
   */
  public int getDay(boolean longForm) {
    if (longForm) return ((_week-1)*10)+this.day;
    else return this.day;
  }

  /**
   * Gets the month name.
   * @return the month name
   */
  public String getMonthName() { 
    return getMonthName(this.month);
  }

  /**
   * Gets the number of days in this month and year.
   * @return the number of days in this month and year.
   */
  @Override
  public int getNumberOfDaysInMonth() {
    return FrenchRepublicanCalendar.getNumberOfDaysInMonth(month,year);
  }

  /**
   * Gets the number of days in a given month for this year.
   * @param a month in this year.
   * @return the number of days in a month of this year.
   */
  public int getNumberOfDaysInMonth(int month) {
    return FrenchRepublicanCalendar.getNumberOfDaysInMonth(month,this.year);
  }

  /**
   * Returns the number of days in a given month and year.
   * @param month a month.
   * @param year a year.
   * @return the number of days in the month and year.
   */
  public static int getNumberOfDaysInMonth(int month, int year) {
    return (month==13) ? (isLeapYear(year) ? 6 : 5) : 30;
  }

  /**
   * Gets a month name.
   * @param month a month number [1-12].
   * @throws IndexOutOfBoundsException
   * @return a month name
   */
  public static String getMonthName(int month) 
    throws IndexOutOfBoundsException
  {
    return _monthNames[month-1];
  }

  /**
   * Gets the Rural calendar name of the day.
   * @return the Rural calendar name of the day.
   */
  public String getDayName() {
    int iday = getDay(true);
    return _dayNames[this.month-1][iday-1];
  }


  /**
   * Prints this date in long form.
   */
  public void printLong() {
    String out = "French Republican Date: ";
    out += "Année "+its(getYear())+" de la République\n";
    out += "  Mois de "+getMonthName() + "\n";
    out += "  Décade "+itr(getWeek());
    out += " Jour "+itr(getDay(false));
    out += " - \""+getDayName()+"\"";

    System.out.println(out);
  }

  /**
   * Determines if this year is a leap year.
   * @return true, if a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return FrenchRepublicanCalendar.isLeapYear(this.year);
  }

  /**
   * Determines if a given year is a leap year.
   * A leap year is determined by the day of the autumnal solstice.
   * @param year a year.
   * @return true, if a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    JulianDay jday1 = new JulianDay(new FrenchRepublicanCalendar(year,1,1));
    JulianDay jday2 = new JulianDay(new FrenchRepublicanCalendar(year+1,1,1));
    int val1 = (int)jday1.atNoon().getValue();
    int val2 = (int)jday2.atNoon().getValue();
    return (val2-val1>365);
  }

  /**
   * Gets the number of days in a week.
   * @return the number of days in a week.
   */
  @Override
  public int getNumberOfDaysInWeek() { 
    return 10; 
  }

  /**
   * Gets the date as a string.
   * @return the date as a string.
   */
  @Override
  public String getDate() {
    return (this.year>=1) ? new String(getDay(true)+" "+
                                       getMonthName()+", "+
                                       toRoman(getYear())) : "";
  }

  @Override
  public String toString() {
    return new String(CALENDAR_NAME+": "+getDate());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FrenchRepublicanCalendar))
      return false;
    if (obj == this)
      return true;
      
    final FrenchRepublicanCalendar date = (FrenchRepublicanCalendar) obj;

    return new EqualsBuilder()
      .append(this.year,date.getYear())
      .append(this.month,date.getMonth())
      .append(_week,date.getWeek())
      .append(this.day,date.getDay(false))
      .isEquals();
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(this.year)
      .append(this.month)
      .append(_week)
      .append(this.day)
      .toHashCode();
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private int _week;

  final static String[] _monthNames = 
  {
    "Vendémiaire",
    "Brumaire",
    "Frimaire",
    "Nivôse",
    "Pluviôse",
    "Ventôse",
    "Germinal",
    "Floréal",
    "Prairial",
    "Messidor",
    "Thermidor",
    "Fructidor",
    "Sans-culottides"
  };

  final static String[] _weekDayNames =
  {
    "Primidi",
    "Duodi",
    "Tridi",
    "Quartidi",
    "Quintidi",
    "Sextidi",
    "Septidi",
    "Octidi",
    "Nonidi",
    "Décadi"
  };
  
  final static String[][] _dayNames =
  {
    // Vendémiaire
    {
      "Raisin","Safran","Châtaigne","Colchique","Cheval",
      "Balsamine","Carotte","Amaranthe","Panais","Cuve",
      "Pomme de terre","Immortelle","Potiron","Réséda","Âne",
      "Belle de nuit","Citrouille","Sarrasin","Tournesol","Pressoir",
      "Chanvre","Pêche","Navet","Amaryllis","Bœuf",
      "Aubergine","Piment","Tomate","Orge","Tonneau"
     },
     // Brumaire
     {
       "Pomme","Céleri","Poire","Betterave","Oie",
       "Héliotrope","Figue","Scorsonère","Alisier","Charrue",
       "Salsifis","Mâcre","Topinambour","Endive","Dindon",
       "Chervis","Cresson","Dentelaire","Grenade","Herse",
       "Bacchante","Azerole","Garance","Orange","Faisan",
       "Pistache","Macjonc","Coing","Cormier","Rouleau"
     },
     // Frimaire
     {
       "Raiponce","Turneps","Chicorée","Nèfle","Cochon",
       "Mâche","Chou-fleur","Miel","Genièvre","Pioche",
       "Cire","Raifort","Cèdre","Sapin","Chevreuil",
       "Ajonc","Cyprès","Lierre","Sabine","Hoyau",
       "Érable à sucre","Bruyère","Roseau","Oseille","Grillon",
       "Pignon","Liège","Truffe","Olive","Pelle"
     },
     // Nivôse
     {
       "Tourbe","Houille","Bitume","Soufre","Chien",
       "Lave","Terre végétale","Fumier","Salpêtre","Fléau",
       "Granit","Argile","Ardoise","Grès","Lapin",
       "Silex","Marne","Pierre à chaux","Marbre","Van",
       "Pierre à plâtre","Sel","Fer","Cuivre","Chat",
       "Étain","Plomb","Zinc","Mercure","Crible"
    },
    // Pluviôse
    {
      "Lauréole","Mousse","Fragon","Perce-neige","Taureau",
      "Laurier-thym","Amadouvier","Mézéréon","Peuplier","Coignée",
      "Ellébore","Brocoli","Laurier","Avelinier","Vache",
      "Buis","Lichen","If","Pulmonaire","Serpette",
      "Thlaspi","Thimelé","Chiendent","Trainasse","Lièvre",
      "Guède","Noisetier","Cyclamen","Chélidoine","Traîneau"
    },
    // Ventôse
    {
      "Tussilage","Cornouiller","Violier","Troène","Bouc",
      "Asaret","Alaterne","Violette","Marceau","Bêche",
      "Narcisse","Orme","Fumeterre","Vélar","Chêvre",
      "Épinard","Doronic","Mouron","Cerfeuil","Cordeau",
      "Mandragore","Persil","Cochléaria","Pâquerette","Thon",
      "Pissenlit","Sylvie","Capillaire","Frêne","Plantoir"
    },
    // Germinal
    {
      "Primevère","Platane","Asperge","Tulipe","Poule",
      "Bette","Bouleau","Jonquille","Aulne","Couvoir",
      "Pervenche","Charme","Morille","Hêtre","Abeille",
      "Laitue","Mélèze","Ciguë","Radis","Ruche",
      "Gainier","Romaine","Marronnier","Roquette","Pigeon",
      "Lilas","Anémone","Pensée","Myrtille","Greffoir"
    },
    // Floréal
    {
      "Rose","Chêne","Fougère","Aubépine","Rossignol",
      "Ancolie","Muguet","Champignon","Hyacinthe","Râteau",
      "Rhubarbe","Sainfoin","Bâton d'or","Charmerisier","Ver à soie",
      "Consoude","Pimprenelle","Corbeille d'or","Arroche","Sarcloir",
      "Statice","Fritillaire","Bourrache","Valériane","Carpe",
      "Fusain","Civette","Buglosse","Sénevé","Houlette"
    },
    // Prairial
    {
      "Luzerne","Hémérocalle","Trèfle","Angélique","Canard",
      "Mélisse","Fromental","Martagon","Serpolet","Faux",
      "Fraise","Bétoine","Pois","Acacia","Caille",
      "Œillet","Sureau","Pavot","Tilleul","Fourche",
      "Barbeau","Camomille","Chèvrefeuille","Caille-lait","Tanche",
      "Jasmin","Verveine","Thym","Pivoine","Chariot"
    },
    // Messidor
    {
      "Seigle","Avoine","Oignon","Véronique","Mulet",
      "Romarin","Concombre","Échalote","Absinthe","Faucille",
      "Coriandre","Artichaut","Girofle","Lavande","Chamois",
      "Tabac","Groseille","Gesse","Cerise","Parc",
      "Menthe","Cumin","Haricot","Haricot","Orcanète","Pintade",
      "Sauge","Ail","Vesce","Blé","Chalémie"
    },
    // Thermidor
    {
      "Épeautre","Bouillon blanc","Melon","Ivraie","Bélier",
      "Prêle","Armoise","Carthame","Mûre","Arrosoir",
      "Panic","Salicorne","Abricot","Basilic","Brebis",
      "Guimauve","Lin","Amande","Gentiane","Écluse",
      "Carline","Câprier","Lentille","Aunée","Loutre",
      "Myrte","Colza","Lupin","Coton","Moulin"
    },
    // Fructidor
    {
      "Prune","Millet","Lycoperdon","Escourgeon","Saumon",
      "Tubéreuse","Sucrion","Apocyn","Réglisse","Échelle",
      "Pastèque","Fenouil","Épine vinette","Noix","Truite",
      "Citron","Cardère","Nerprun","Tagette","Hotte",
      "Églantier","Noisette","Houblon","Sorgho","Écrevisse",
      "Bigarade","Verge d'or","Maïs","Marron","Panier"
    },
    // Sans-culottides
    {
      "La Fête de la Vertu",
      "La Fête du Génie",
      "La Fête di Travail",
      "La Fête de l'Opinion",
      "La Fête des Récompenses",
      "La Fête de la Révolution"
    }
  };
}
