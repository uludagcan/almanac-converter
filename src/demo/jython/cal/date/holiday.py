import sys

from java.awt import *
from java.lang import *
from javax.swing import *

import org.joda.time.DateTime
from com.hm.cal.date import *
from com.hm.cal.util import *
from com.hm.cal.astro.Meeus import *

holidays = [
  Holiday.ASH_WEDNESDAY,
  Holiday.PALM_SUNDAY,
  Holiday.HOLY_THURSDAY,
  Holiday.GOOD_FRIDAY,
  Holiday.EASTER,
  Holiday.ASCENSION,
  Holiday.PENTECOST,
  Holiday.TRINITY_SUNDAY,
  Holiday.ADVENT,
  
  Holiday.ASHURA,
  Holiday.RAMADAN,
  Holiday.EID_AL_FITR,
  Holiday.EID_AL_ADHA,
  
  Holiday.NEW_YEARS_DAY,
  Holiday.MARTIN_LUTHER_KING,
  Holiday.WASHINGTONS_BIRTHDAY,
  Holiday.MEMORIAL_DAY,
  Holiday.INDEPENDENCE_DAY,
  Holiday.LABOR_DAY,
  Holiday.COLUMBUS_DAY,
  Holiday.VETERANS_DAY,
  Holiday.THANKSGIVING,
  Holiday.CHRISTMAS]

def main(args):
  holidayDemo(2015)

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
