package got.common;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import got.common.network.GOTPacketDate;
import got.common.network.GOTPacketHandler;
import got.common.world.GOTWorldInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOTDate {
	public static int ticksInDay = GOTTime.DAY_LENGTH;
	public static long prevWorldTime = -1L;

	public static void loadDates(NBTTagCompound levelData) {
		if (levelData.hasKey("Dates")) {
			NBTTagCompound nbt = levelData.getCompoundTag("Dates");
			AegonCalendar.currentDay = nbt.getInteger("AegonDate");
		} else {
			AegonCalendar.currentDay = 0;
		}
	}

	public static void resetWorldTimeInMenu() {
		prevWorldTime = -1L;
	}

	public static void saveDates(NBTTagCompound levelData) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("AegonDate", AegonCalendar.currentDay);
		levelData.setTag("Dates", nbt);
	}

	public static void sendUpdatePacket(EntityPlayerMP entityplayer, boolean update) {
		NBTTagCompound nbt = new NBTTagCompound();
		saveDates(nbt);
		GOTPacketDate packet = new GOTPacketDate(nbt, update);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void setDate(int date) {
		AegonCalendar.currentDay = date;
		GOTLevelData.markDirty();
		FMLLog.info("Updating GOT day: " + AegonCalendar.getDate().getDateName(false));
		for (EntityPlayerMP entityplayer : (List<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			sendUpdatePacket(entityplayer, true);
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
			setDate(AegonCalendar.currentDay + 1);
		}
		prevWorldTime = worldTime;
	}

	public enum Season {
		SPRING("spring", 0), SUMMER("summer", 1), AUTUMN("autumn", 2), WINTER("winter", 3);

		public static Season[] allSeasons = {SPRING, SUMMER, AUTUMN, WINTER};
		public String name;
		public int seasonID;

		Season(String s, int i) {
			name = s;
			seasonID = i;
		}

	}

	public static class AegonCalendar {
		public static Date startDate = new Date(298, Month.JULY, 10);
		public static int currentDay;
		public static Map<Integer, Date> cachedDates = new HashMap<>();

		public static Date getDate() {
			return getDate(currentDay);
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
			return getDate().month.season;
		}

		public static boolean isLeapYear(int year) {
			return year % 4 == 0 && year % 100 != 0;
		}

		public enum Day {
			SUNDAY("sunday"), MONDAY("monday"), TUESDAY("tuesday"), WEDNESDAY("wednesday"), THIRSDAY("thirsday"), FRIDAY("friday"), SATURDAY("saturday");

			public String name;

			Day(String s) {
				name = s;
			}

			public String getDayName() {
				return StatCollector.translateToLocal("got.date.day." + name);
			}
		}

		public enum Month {
			JANUARY("january", 31, Season.WINTER), FEBRUARY("february", 28, Season.WINTER), MARCH("march", 31, Season.SPRING), APRIL("april", 30, Season.SPRING), MAY("may", 31, Season.SPRING), JUNE("june", 30, Season.SUMMER), JULY("july", 31, Season.SUMMER), AUGUST("august", 31, Season.SUMMER), SEPTEMBER("september", 30, Season.AUTUMN), OCTOBER("october", 31, Season.AUTUMN), NOVEMBER("november", 30, Season.AUTUMN), DECEMBER("december", 31, Season.WINTER);

			public String name;
			public int days;
			public boolean hasWeekdayName;
			public boolean isLeapYear;
			public Season season;

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

			public String getMonthName() {
				return StatCollector.translateToLocal("got.date.month." + name);
			}

			public boolean isSingleDay() {
				return days == 1;
			}
		}

		public static class Date {
			public int year;
			public Month month;
			public int monthDate;
			public Day day;

			public Date(int y, Month m, int d) {
				year = y;
				month = m;
				monthDate = d;
			}

			public Date copy() {
				return new Date(year, month, monthDate);
			}

			public Date decrement() {
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
					if (newMonth.isLeapYear && !isLeapYear(newYear)) {
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

			public Day getDay() {
				if (!month.hasWeekdayName) {
					return null;
				}
				if (day == null) {
					int yearDay = 0;
					int monthID = month.ordinal();
					for (int i = 0; i < monthID; ++i) {
						Month m = Month.values()[i];
						if (m.hasWeekdayName) {
							yearDay += m.days;
						}
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
				return new String[]{dateName, yearName};
			}

			public Date increment() {
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
					if (newMonth.isLeapYear && !isLeapYear(newYear)) {
						++monthID;
						newMonth = Month.values()[monthID];
					}
				}
				return new Date(newYear, newMonth, newDate);
			}
		}

	}

}
