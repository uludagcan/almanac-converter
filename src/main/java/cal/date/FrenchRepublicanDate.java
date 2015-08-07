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

  public String getMois() { 
    return _mois[mois-1]; 
  }

  public String getDecade() {
    return (decade>1) ? (decade==3) ? "III" : "II" : "I";
  }

  public String getJour() {
    return _jours[mois-1][(decade-1)*10+(jour-1)];
  }

//  public String[] getMonthNames() { return _mois; }
//  public String[][] getDayNames() { return _jours; }

  public void print() {
    System.out.println("Année "+an+" de la République");
    System.out.println("  Mois de "+getMois());
    System.out.print("  Décade "+getDecade()+" Jour "+jour);
    System.out.println(" - \""+getJour()+"\"");
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
