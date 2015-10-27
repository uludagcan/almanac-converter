import sys

from java.awt import *
from java.lang import *
from javax.swing import *

import org.joda.time.DateTime
from com.moose.cal.date import *

def main(args):
  demo1()

def demo1():
  gc = GregorianCalendar()
  jc = JulianCalendar(gc)
  frc = FrenchRepublicanCalendar(gc)
  mc = MayaCalendar(gc)
  ic = IslamicCalendar(gc)

  print "## TODAY ##"
  print gc
  print jc
  print frc
  print mc
  print ic


#############################################################################
class RunMain(Runnable):
  def run(self):
    main(sys.argv)
SwingUtilities.invokeLater(RunMain())
