import sys

from java.awt import *
from java.lang import *
from javax.swing import *

import org.joda.time.DateTime
from com.moose.cal.date import *
from com.moose.cal.astro.Meeus import *

holidays = [
  Holiday.ASH_WEDNESDAY,
  Holiday.PALM_SUNDAY,
  Holiday.HOLY_THURSDAY,
  Holiday.GOOD_FRIDAY,
  Holiday.EASTER ]
#  Holiday.ASCENSION,
#  Holiday.PENTECOST,
#  Holiday.TRINITY_SUNDAY,
#  Holiday.ADVENT]

def main(args):
  holidayDemo(2000)

def holidayDemo(year):
  for holiday in holidays:
    date = HolidayCalculator.get(holiday,year)
    gc = GregorianCalendar(date)
    print holiday.name(),gc.getDate()

#############################################################################
class RunMain(Runnable):
  def run(self):
    main(sys.argv)
SwingUtilities.invokeLater(RunMain())
