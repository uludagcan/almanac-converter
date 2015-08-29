package cal.date;

import cal.astro.*;
import static cal.util.RomanNumeralGenerator.*;
import static cal.util.Converter.*;
import static cal.util.Util.*;

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
 * @version 1.0
 * @since 2015-08-07
 */
public final class FrenchRepublicanDate implements Almanac {
  public static final String CALENDAR_NAME = "French Republican Calendar";
  public static final JulianDay EPOCH = new JulianDay(2375839.5);

  /**
   * Constructs a French Republican Date for today's date.
   */
  public FrenchRepublicanDate() {
    this(new JulianDay());
  }
  
  /**
   * Constructs a French Republican Date with given year, month,
   * week and day.
   * @param year the year.
   * @param month the month.
   * @param week the week.
   * @param day the day.
   */ 
  public FrenchRepublicanDate(int year, int month, int week, int day) {
    _year = year;
    _month = month;
    _week = week;
    _day = day;
  }

  /**
   * Constructs a French Republican Date fromr a given Gregorian
   * Calendar date.
   * @param date a Gregorian Calendar Date.
   */
  public FrenchRepublicanDate(GregorianDate date) {
    this(new JulianDay(date));
  }

  /**
   * Constructs a French Republican Date from a given Julian Day.
   * @param jd a Julian Day.
   */
  public FrenchRepublicanDate(JulianDay jd) {
    this(toFrenchRepublicanDate(jd));
  }

  /**
   * Constructs a French Republican date from another French Republican date.
   * @param date a French Republican date.
   */
  public FrenchRepublicanDate(FrenchRepublicanDate date) {
    this(date.getYear(),date.getMonth(),date.getWeek(),date.getDay());
  }

  /**
   * Returns today's date as a string.
   * Convenience static method.
   * @return today's date.
   */
  public static String asToday() {
    return (new FrenchRepublicanDate()).getDate();
  }

  /**
   * Gets the month.
   * @return the month.
   */
  public int getMonth() {
    return _month;
  }

  /**
   * Gets the year.
   * @return the year.
   */
  public int getYear() {
    return _year;
  }

  /**
   * Gets the 10-day week (décade).
   * @return the décade.
   */
  public int getWeek() {
    return _week;
  }

  /**
   * Gets the long-form day number (assumes no use of decades).
   * @return the day.
   */
  public int getDay() {
    return getDay(false);
  }

  /**
   * Gets the day number.
   * @param useDecade true, if using decades; false, otherwise.
   * @return the day.
   */
  public int getDay(boolean useDecade) {
    if (useDecade) return _day;
    else return ((_week-1)*10)+_day;
  }

  /**
   * Gets the month name.
   * @return the month name.
   */
  public String getMonthName() { 
    return _monthNames[_month-1]; 
  }

  /**
   * Gets the Rural calendar name of the day.
   * @return the Rural calendar name of the day.
   */
  public String getDayName() {
    return _dayNames[_month-1][(_week-1)*10+(_day-1)];
  }


  /**
   * Prints this date in long form.
   */
  public void printLong() {
    String out = "French Republican Date: ";
    out += "Année "+its(getYear())+" de la République\n";
    out += "  Mois de "+getMonthName() + "\n";
    out += "  Décade "+itr(getWeek());
    out += " Jour "+itr(getDay(true));
    out += " - \""+getDayName()+"\"";

    System.out.println(out);
  }

  /**
   * Gets this date.
   * @return the date.
   */
  @Override
  public String getDate() {
    String out = itr(getDay());
    out += " "+its(getMonth());
    out += " "+getYear();
    return out;
  }

  /**
   * Prints this date in short form.
   */
  @Override
  public void print() {
    System.out.print("French Republican Date: ");
    System.out.print(getDay()+" "+getMonth()+", L'an "+getYear());
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private int _year;
  private int _month;
  private int _week;
  private int _day;

  private String[] _monthNames = 
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
  
  private String[][] _dayNames =
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
