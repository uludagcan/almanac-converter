package cal.date;

import cal.astro.*;
import cal.util.*;

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
public class FrenchRepublicanDate implements Almanac {

  public int year;
  public int month;
  public int week;
  public int day;

  /**
   * Constructs a French Republican Date for today's date.
   */
  public FrenchRepublicanDate() {
    this(new JulianDay());
  }
  
  /**
   * Constructs a French Republican Date with given year, month,
   * week and day.
   * @param year The year.
   * @param month The month.
   * @param week The week.
   * @param day The day.
   */ 
  public FrenchRepublicanDate(int year, int month, int week, int day) {
    this.year = year;
    this.month = month;
    this.week = week;
    this.day = day;
  }

  /**
   * Constructs a French Republican Date fromr a given Gregorian
   * Calendar date.
   * @param date A Gregorian Calendar Date.
   */
  public FrenchRepublicanDate(GregorianDate date) {
    this(new JulianDay(date));
  }

  /**
   * Constructs a French Republican Date from a given Julian Day.
   * @param jd A Julian Day.
   */
  public FrenchRepublicanDate(JulianDay jd) {
    double jday = Math.floor(jd.day)+0.5;
    double[] adr = anneeDeLaRevolution(new JulianDay(jday));
    year = (int)adr[0];
    double equinoxe = adr[1];
    month = (int)(Math.floor((jd.day-equinoxe)/30)+1);
    double djour = (jd.day-equinoxe) % 30;
    week = (int)(Math.floor(djour/10)+1);
    day = (int)((djour % 10)+1);
    if (month>12) day+=11;
    _adjustForSansCulottides();
  }

  /**
   * Gets the month name.
   * @return The month name.
   */
  public String getMois() { 
    return _month[month-1]; 
  }

  /**
   * Gets the year in traditional Roman numerals.
   * @return The year in Roman numerals.
   */
  public String getYear() {
    return (RomanNumeralGenerator.toRoman(year));
  }

  /**
   * Gets the 10-day week (decade) in traditional Roman numerals.
   * @return The decade in Roman numerals.
   */
  public String getDecade() {
    return (RomanNumeralGenerator.toRoman(week));
  }

  /**
   * Gets the Rural calendar name of the day.
   * @return The Rural calendar name of the day.
   */
  public String getJour() {
    return _days[month-1][(week-1)*10+(day-1)];
  }

  /**
   * Prints this date in long form.
   */
  public void printLong() {
    System.out.print("French Republican Date: ");
    System.out.println("Année "+getYear()+" de la République");
    System.out.println("  Mois de "+getMois());
    System.out.print("  Décade "+getDecade()+" Jour "+day);
    System.out.println(" - \""+getJour()+"\"");
  }

  /**
   * Prints this date in short form.
   */
  public void print() {
    System.out.print("French Republican Date: ");
    System.out.print(((week-1)*10)+day);
    System.out.print(" "+getMois()+", L'an "+getYear());
  }

/////////////////////////////////////////////////////////////////////////////
// private

private void _adjustForSansCulottides() {
    if (day>10) {
      day -= 12;
      week = 1;
      month = 13;
    }
    if (month==13) {
      week = 1;
      if (day>6) {
        day = 1;
      }
    }
  }

  private double[] anneeDeLaRevolution(JulianDay julday) {
    int guess = (new GregorianDate(julday)).year-2;
    double nexteq,lasteq,jd;
    jd = julday.day;
    lasteq = parisEquinox(guess);
    while (lasteq>jd) {
      guess--;
      lasteq = parisEquinox(guess);
    }
    nexteq = lasteq-1;
    while (!((lasteq <= jd) && (jd < nexteq))) {
      lasteq = nexteq;
      guess++;
      nexteq = parisEquinox(guess);
    }
    double adr = (lasteq - FRENCH_REVOLUTION_EPOCH.day);
    adr /= Meeus.TROPICAL_YEAR;
    adr += 1;
    return new double[] {Math.round(adr), lasteq};
  }

  private double parisEquinox(int year) {
    double eqJED = Meeus.equinox(year,Season.AUTUMN);
    double eqJD = eqJED - Meeus.deltat(year)/(24.0*60.0*60.0);
    double eqAPP = eqJD + Meeus.equationOfTime(eqJED);
    double dtParis = (2.0+(20.0/60.0)+(15.0/(60.0*60.0)))/360.0;
    double eqParis = eqAPP + dtParis;
    eqParis = Math.floor(eqParis-0.5)+0.5;
    return eqParis;
  }


  private JulianDay FRENCH_REVOLUTION_EPOCH = new JulianDay(2375839.5);

  private String[] _month = 
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
  
  private String[][] _days =
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
