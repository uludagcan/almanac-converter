import sys

from java.awt import *
from java.lang import *
from javax.swing import *

from cal.date import *

def main(args):
  demo1()

def demo1():
  gd = GregorianDate()
  pattern = "MM/DD/yyyy a"
  date = gd.getDate(pattern)
  print date


#############################################################################
class RunMain(Runnable):
  def run(self):
    main(sys.argv)
SwingUtilities.invokeLater(RunMain())
