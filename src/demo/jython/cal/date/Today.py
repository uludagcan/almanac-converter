import sys

from java.awt import *
from java.lang import *
from javax.swing import *

from com.hm.cal.date import *
from com.hm.cal.astro.Meeus import *

DEG = u'\N{DEGREE SIGN}'

def main(args):
  basic_demo()
  lunar_demo()

def basic_demo():
  am = [
    GregorianCalendar(),
    JulianCalendar(),
    FrenchRepublicanCalendar(),
    MayaCalendar(),
    IslamicCalendar(),
    HebrewCalendar(),
    PersianCalendar(),
    IndianCivilCalendar()
  ]

  print "Today is... "
  for date in am:
    print date
  print ""

def lunar_demo():
  jd = JulianDay()
  phase = getLunarPhase(jd.getValue())
  illum = getLunarIlluminationFromPhase(phase)
  print "The lunar phase is %d%s, or %d%% illuminated\n" % (phase,DEG,illum)

  gd = GregorianCalendar()
  year = gd.getYear()
  month = gd.getMonth()
  day = gd.getDay()

  quarters = getMoonQuarters(year,month,day)

  moon0 = JulianDay(quarters[0])
  moon1 = JulianDay(quarters[1])
  moon2 = JulianDay(quarters[2])
  moon3 = JulianDay(quarters[3])

  print "New Moon is ",GregorianCalendar(moon0).getDate()
  print "First Quarter moon is ",GregorianCalendar(moon1).getDate()
  print "Full Moon is ",GregorianCalendar(moon2).getDate()
  print "Last Quarter moon is ",GregorianCalendar(moon3).getDate()

#############################################################################
class RunMain(Runnable):
  def run(self):
    main(sys.argv)
SwingUtilities.invokeLater(RunMain())
