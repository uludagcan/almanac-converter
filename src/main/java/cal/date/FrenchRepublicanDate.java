package cal.date;

import cal.astro.*;

public class FrenchRepublicanDate {

  public int an;
  public int mois;
  public int decade;
  public int jour;

  public FrenchRepublicanDate() {
    this(new JulianDay());
  }

  public FrenchRepublicanDate(GregorianDate date) {
    this(new JulianDay(date));
  }

  public FrenchRepublicanDate(JulianDay jd) {
    double jday = Math.floor(jd.day)+0.5;
    JulianDay[] adr = anneeDeLaRevolution(new JulianDay(jday));
    an = (int)(adr[0].day);
    double equinoxe = adr[1].day;
    mois = (int)(Math.floor((jd.day-equinoxe)/30)+1);
    double djour = (jd.day-equinoxe) % 30;
    decade = (int)(Math.floor(djour/10)+1);
    jour = (int)((djour % 10)+1);
    if (mois>12) jour+=11;
    _adjustForSansCulottides();
  }

  public String getMonthName(int i) { return _mois[i]; }
  public String[] getMonthNames() { return _mois; }
  public String[][] getDayNames() { return _jours; }

  public void print() {
    System.out.println("Année "+an+" de la République");
    System.out.println("  Mois de "+_mois[mois-1]);
    System.out.println("  Décade "+decade+" Jour "+jour);
  }

/////////////////////////////////////////////////////////////////////////////
// private

  private void _adjustForSansCulottides() {
    if (jour>10) {
      jour -= 12;
      decade = 1;
      mois = 13;
    }
    if (mois==13) {
      decade = 1;
      if (jour>6) {
        jour = 1;
      }
    }
  }

  private JulianDay[] anneeDeLaRevolution(JulianDay day) {
    int guess = (new GregorianDate(day)).year-2;
    JulianDay lastEquinox = parisEquinox(guess);
    while (lastEquinox.isAfter(day)) {
      guess--;
      lastEquinox = parisEquinox(guess);
    }
    JulianDay nextEquinox = lastEquinox.minus(1);
    while (!((lastEquinox.isBeforeOrOn(day)) && 
              (day.isBefore(nextEquinox)))) {
      lastEquinox = nextEquinox;
      guess++;
      nextEquinox = parisEquinox(guess);
    }
    JulianDay adr = lastEquinox.minus(FRENCH_REVOLUTION_EPOCH);
    adr.divideEquals(Meeus.TROPICAL_YEAR);
    adr.plusEquals(1);
    return new JulianDay[] {adr, lastEquinox};
  }

  private JulianDay parisEquinox(int year) {
    JulianDay eqJED = new JulianDay(Meeus.equinox(year,Season.AUTUMN));
    JulianDay eqJD = eqJED.minus(Meeus.deltat(year)/(24*60*60));
    JulianDay eqApp = eqJD.plusEquals(Meeus.equationOfTime(eqJED.day));
    double dtParis = (2+(20/60.0)+(15/(60*60.0)))/360.0;
    JulianDay eqParis = eqApp.plus(dtParis);
    eqParis.setToMidnight();
    return eqParis;
  }

  private JulianDay FRENCH_REVOLUTION_EPOCH = new JulianDay(2375839.5);

  private String[] _mois = 
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
  
  private String[][] _jours =
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
     }


  };
}
