package got.common;

import java.util.*;

import com.google.common.math.IntMath;

import cpw.mods.fml.common.FMLLog;
import got.common.network.*;
import got.common.world.GOTWorldInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GOTDate {
	private static int ticksInDay = GOTTime.getDayLength();
	private static long prevWorldTime = -1L;

	public static void loadDates(NBTTagCompound levelData) {
		if (levelData.hasKey("Dates")) {
			NBTTagCompound nbt = levelData.getCompoundTag("Dates");
			AegonCalendar.setCurrentDay(nbt.getInteger("AegonDate"));
		} else {
			AegonCalendar.setCurrentDay(0);
		}
	}

	public static void resetWorldTimeInMenu() {
		prevWorldTime = -1L;
	}

	public static void saveDates(NBTTagCompound levelData) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("AegonDate", AegonCalendar.getCurrentDay());
		levelData.setTag("Dates", nbt);
	}

	public static void sendUpdatePacket(EntityPlayerMP entityplayer, boolean update) {
		NBTTagCompound nbt = new NBTTagCompound();
		GOTDate.saveDates(nbt);
		GOTPacketDate packet = new GOTPacketDate(nbt, update);
		GOTPacketHandler.getNetworkWrapper().sendTo(packet, entityplayer);
	}

	public static void setDate(int date) {
		AegonCalendar.setCurrentDay(date);
		GOTLevelData.markDirty();
		FMLLog.info("Updating GOT day: " + AegonCalendar.getDate().getDateName(false));
		for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
			GOTDate.sendUpdatePacket(entityplayer, true);
		}
	}

	public static void update(World world) {
		if (!(world.getWorldInfo() instanceof GOTWorldInfo)) {
			return;
		}
		long worldTime = world.getWorldTime();
		if (prevWorldTime == -1L) {
			prevWorldTime = worldTime;
		}
		if (worldTime / ticksInDay != prevWorldTime / ticksInDay) {
			GOTDate.setDate(AegonCalendar.getCurrentDay() + 1);
		}
		prevWorldTime = worldTime;
	}

	public static class AegonCalendar {
		private static Date startDate = new Date(298, Month.JULY, 10);
		private static int currentDay;
		private static Map<Integer, Date> cachedDates = new HashMap<>();

		public static int getCurrentDay() {
			return currentDay;
		}

		public static Date getDate() {
			return AegonCalendar.getDate(getCurrentDay());
		}

		public static Date getDate(int day) {
			Date date = cachedDates.get(day);
			if (date == null) {
				date = startDate.copy();
				if (day < 0) {
					for (int i = 0; i < -day; ++i) {
						date = date.decrement();
					}
				} else {
					for (int i = 0; i < day; ++i) {
						date = date.increment();
					}
				}
				cachedDates.put(day, date);
			}
			return date;
		}

		public static Season getSeason() {
			return AegonCalendar.getDate().month.season;
		}

		private static boolean isLeapYear(int year) {
			return year % 4 == 0 && year % 100 != 0;
		}

		public static void setCurrentDay(int currentDay) {
			AegonCalendar.currentDay = currentDay;
		}

		public static class Date {
			private int year;
			private Month month;
			private int monthDate;
			private Day day;

			private Date(int y, Month m, int d) {
				year = y;
				month = m;
				monthDate = d;
			}

			private Date copy() {
				return new Date(year, month, monthDate);
			}

			private Date decrement() {
				int newYear = year;
				Month newMonth = month;
				int newDate = monthDate;
				--newDate;
				if (newDate < 0) {
					int monthID = newMonth.ordinal();
					--monthID;
					if (monthID < 0) {
						monthID = Month.values().length - 1;
						--newYear;
					}
					newMonth = Month.values()[monthID];
					if (newMonth.isLeapYear && !AegonCalendar.isLeapYear(newYear)) {
						--monthID;
						newMonth = Month.values()[monthID];
					}
					newDate = newMonth.days;
				}
				return new Date(newYear, newMonth, newDate);
			}

			public String getDateName(boolean longName) {
				String[] dayYear = getDayAndYearNames(longName);
				return dayYear[0] + ", " + dayYear[1];
			}

			private Day getDay() {
				if (!month.hasWeekdayName) {
					return null;
				}
				if (day == null) {
					int yearDay = 0;
					int monthID = month.ordinal();
					for (int i = 0; i < monthID; ++i) {
						Month m = Month.values()[i];
						if (!m.hasWeekdayName) {
							continue;
						}
						yearDay += m.days;
					}
					yearDay += monthDate;
					int dayID = IntMath.mod(yearDay - 1, Day.values().length);
					day = Day.values()[dayID];
				}
				return day;
			}

			public String[] getDayAndYearNames(boolean longName) {
				StringBuilder builder = new StringBuilder();
				if (month.hasWeekdayName) {
					builder.append(getDay().getDayName());
				}
				builder.append(" ");
				if (!month.isSingleDay()) {
					builder.append(monthDate);
				}
				builder.append(" ");
				builder.append(month.getMonthName());
				String dateName = builder.toString();
				builder = new StringBuilder();
				builder.append(year);
				builder.append(" ");
				if (longName) {
					builder.append(StatCollector.translateToLocal("got.date.long"));
				} else {
					builder.append(StatCollector.translateToLocal("got.date"));
				}
				String yearName = builder.toString();
				return new String[] { dateName, yearName };
			}

			private Date increment() {
				int newYear = year;
				Month newMonth = month;
				int newDate = monthDate;
				++newDate;
				if (newDate > newMonth.days) {
					newDate = 1;
					int monthID = newMonth.ordinal();
					++monthID;
					if (monthID >= Month.values().length) {
						monthID = 0;
						++newYear;
					}
					newMonth = Month.values()[monthID];
					if (newMonth.isLeapYear && !AegonCalendar.isLeapYear(newYear)) {
						++monthID;
						newMonth = Month.values()[monthID];
					}
				}
				return new Date(newYear, newMonth, newDate);
			}
		}

		private enum Day {
			SUNDAY("sunday"), MONDAY("monday"), TUESDAY("tuesday"), WEDNESDAY("wednesday"), THIRSDAY("thirsday"), FRIDAY("friday"), SATURDAY("saturday");

			private String name;

			Day(String s) {
				name = s;
			}

			private String getDayName() {
				return StatCollector.translateToLocal("got.date.day." + name);
			}
		}

		private enum Month {
			JANUARY("january", 31, Season.WINTER), FEBRUARY("february", 28, Season.WINTER), MARCH("march", 31, Season.SPRING), APRIL("april", 30, Season.SPRING), MAY("may", 31, Season.SPRING), JUNE("june", 30, Season.SUMMER), JULY("july", 31, Season.SUMMER), AUGUST("august", 31, Season.SUMMER), SEPTEMBER("september", 30, Season.AUTUMN), OCTOBER("october", 31, Season.AUTUMN), NOVEMBER("november", 30, Season.AUTUMN), DECEMBER("december", 31, Season.WINTER);

			private String name;
			private int days;
			private boolean hasWeekdayName;
			private boolean isLeapYear;
			private Season season;

			Month(String s, int i, Season se) {
				this(s, i, se, true, false);
			}

			Month(String s, int i, Season se, boolean flag, boolean flag1) {
				name = s;
				days = i;
				hasWeekdayName = flag;
				isLeapYear = flag1;
				season = se;
			}

			private String getMonthName() {
				return StatCollector.translateToLocal("got.date.month." + name);
			}

			private boolean isSingleDay() {
				return days == 1;
			}
		}

	}

	public enum Season {
		SPRING("spring", 0), SUMMER("summer", 1), AUTUMN("autumn", 2), WINTER("winter", 3);

		static {
		}

		Season(String s, int i) {
		}
	}

}
