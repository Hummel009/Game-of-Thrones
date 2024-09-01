package got.common.world.map;

import got.common.faction.GOTFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GOTConquestZone {
	private static List<GOTFaction> allPlayableFacs;

	private final int gridX;
	private final int gridZ;

	private boolean isDummyZone;
	private boolean isLoaded = true;
	private boolean clientSide;
	private float[] conquestStrengths;
	private long lastChangeTime;
	private long isEmptyKey;

	public GOTConquestZone(int i, int k) {
		gridX = i;
		gridZ = k;
		if (allPlayableFacs == null && (allPlayableFacs = GOTFaction.getPlayableReputationFactions()).size() >= 62) {
			throw new RuntimeException("Too many factions! Need to upgrade GOTConquestZone data format.");
		}
		conquestStrengths = new float[allPlayableFacs.size()];
	}

	public static GOTConquestZone readFromNBT(NBTTagCompound nbt) {
		short x = nbt.getShort("X");
		short z = nbt.getShort("Z");
		long time = nbt.getLong("Time");
		GOTConquestZone zone = new GOTConquestZone(x, z);
		zone.isLoaded = false;
		zone.lastChangeTime = time;
		block0:
		for (GOTFaction fac : allPlayableFacs) {
			Collection<String> names = new ArrayList<>();
			names.add(fac.codeName());
			for (String alias : names) {
				String facKey = alias + "_str";
				if (!nbt.hasKey(facKey)) {
					continue;
				}
				float str = nbt.getFloat(facKey);
				zone.setConquestStrengthRaw(fac, str);
				continue block0;
			}
		}
		zone.isLoaded = true;
		return zone;
	}

	public void addConquestStrength(GOTFaction fac, float add, World world) {
		float str = getConquestStrength(fac, world);
		setConquestStrength(fac, str + add, world);
	}

	private float calcTimeStrReduction(long worldTime) {
		int dl = (int) (worldTime - lastChangeTime);
		float s = dl / 20.0f;
		float graceCap = 3600.0f;
		if (s > graceCap) {
			float decayRate = 3600.0f;
			return (s - graceCap) / decayRate;
		}
		return 0.0f;
	}

	public void checkForEmptiness(World world) {
		boolean emptyCheck = true;
		for (GOTFaction fac : allPlayableFacs) {
			float str = getConquestStrength(fac, world);
			if (str == 0.0f) {
				continue;
			}
			emptyCheck = false;
			break;
		}
		if (emptyCheck) {
			conquestStrengths = new float[allPlayableFacs.size()];
			isEmptyKey = 0L;
			markDirty();
		}
	}

	public void clearAllFactions(World world) {
		for (GOTFaction fac : allPlayableFacs) {
			setConquestStrengthRaw(fac, 0.0f);
		}
		lastChangeTime = world.getTotalWorldTime();
		markDirty();
	}

	public float getConquestStrength(GOTFaction fac, long worldTime) {
		float str = getConquestStrengthRaw(fac);
		str -= calcTimeStrReduction(worldTime);
		return Math.max(str, 0.0f);
	}

	public float getConquestStrength(GOTFaction fac, World world) {
		return getConquestStrength(fac, world.getTotalWorldTime());
	}

	public float getConquestStrengthRaw(GOTFaction fac) {
		if (!fac.isPlayableReputationFaction()) {
			return 0.0f;
		}
		int index = allPlayableFacs.indexOf(fac);
		return conquestStrengths[index];
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridZ() {
		return gridZ;
	}

	public long getLastChangeTime() {
		return lastChangeTime;
	}

	public void setLastChangeTime(long l) {
		lastChangeTime = l;
		markDirty();
	}

	public boolean isDummyZone() {
		return isDummyZone;
	}

	public boolean isEmpty() {
		return isEmptyKey == 0L;
	}

	private void markDirty() {
		if (isLoaded && !clientSide) {
			GOTConquestGrid.markZoneDirty(this);
		}
	}

	public void setClientSide() {
		clientSide = true;
	}

	public void setConquestStrength(GOTFaction fac, float str, World world) {
		setConquestStrengthRaw(fac, str);
		updateAllOtherFactions(fac, world);
		lastChangeTime = world.getTotalWorldTime();
		markDirty();
	}

	public void setConquestStrengthRaw(GOTFaction fac, float str) {
		float str1 = str;
		if (!fac.isPlayableReputationFaction()) {
			return;
		}
		if (str1 < 0.0f) {
			str1 = 0.0f;
		}
		int index = allPlayableFacs.indexOf(fac);
		conquestStrengths[index] = str1;
		if (str1 == 0.0f) {
			isEmptyKey &= ~(1L << index);
		} else {
			isEmptyKey |= 1L << index;
		}
		markDirty();
	}

	public GOTConquestZone setDummyZone() {
		isDummyZone = true;
		return this;
	}

	@Override
	public String toString() {
		return "GOTConquestZone: " + gridX + ", " + gridZ;
	}

	private void updateAllOtherFactions(GOTFaction fac, World world) {
		for (int i = 0; i < conquestStrengths.length; ++i) {
			GOTFaction otherFac = allPlayableFacs.get(i);
			if (otherFac == fac || conquestStrengths[i] <= 0.0f) {
				continue;
			}
			float otherStr = getConquestStrength(otherFac, world);
			setConquestStrengthRaw(otherFac, otherStr);
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setShort("X", (short) gridX);
		nbt.setShort("Z", (short) gridZ);
		nbt.setLong("Time", lastChangeTime);
		for (int i = 0; i < conquestStrengths.length; ++i) {
			GOTFaction fac = allPlayableFacs.get(i);
			String facKey = fac.codeName() + "_str";
			float str = conquestStrengths[i];
			if (str == 0.0f) {
				continue;
			}
			nbt.setFloat(facKey, str);
		}
	}
}