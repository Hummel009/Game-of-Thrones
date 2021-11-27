package got.common.world.map;

import java.util.*;

import got.common.faction.GOTFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTConquestZone {
	public static List<GOTFaction> allPlayableFacs = null;
	public int gridX;
	public int gridZ;
	public boolean isDummyZone = false;
	public float[] conquestStrengths;
	public long lastChangeTime;
	public long isEmptyKey = 0L;
	public boolean isLoaded = true;
	public boolean clientSide = false;

	public GOTConquestZone(int i, int k) {
		gridX = i;
		gridZ = k;
		if (allPlayableFacs == null && (allPlayableFacs = GOTFaction.getPlayableAlignmentFactions()).size() >= 62) {
			throw new RuntimeException("Too many factions! Need to upgrade GOTConquestZone data format.");
		}
		conquestStrengths = new float[allPlayableFacs.size()];
	}

	public void addConquestStrength(GOTFaction fac, float add, World world) {
		float str = this.getConquestStrength(fac, world);
		setConquestStrength(fac, str += add, world);
	}

	public float calcTimeStrReduction(long worldTime) {
		int dl = (int) (worldTime - lastChangeTime);
		float s = dl / 20.0f;
		float graceCap = 3600.0f;
		if (s > graceCap) {
			float decayRate = 3600.0f;
			return (s -= graceCap) / decayRate;
		}
		return 0.0f;
	}

	public void checkForEmptiness(World world) {
		boolean emptyCheck = true;
		for (GOTFaction fac : allPlayableFacs) {
			float str = this.getConquestStrength(fac, world);
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
		return this.getConquestStrength(fac, world.getTotalWorldTime());
	}

	public float getConquestStrengthRaw(GOTFaction fac) {
		if (!fac.isPlayableAlignmentFaction()) {
			return 0.0f;
		}
		int index = allPlayableFacs.indexOf(fac);
		return conquestStrengths[index];
	}

	public long getLastChangeTime() {
		return lastChangeTime;
	}

	public boolean isEmpty() {
		return isEmptyKey == 0L;
	}

	public void markDirty() {
		if (isLoaded && !clientSide) {
			GOTConquestGrid.markZoneDirty(this);
		}
	}

	public GOTConquestZone setClientSide() {
		clientSide = true;
		return this;
	}

	public void setConquestStrength(GOTFaction fac, float str, World world) {
		setConquestStrengthRaw(fac, str);
		updateAllOtherFactions(fac, world);
		lastChangeTime = world.getTotalWorldTime();
		markDirty();
	}

	public void setConquestStrengthRaw(GOTFaction fac, float str) {
		if (!fac.isPlayableAlignmentFaction()) {
			return;
		}
		if (str < 0.0f) {
			str = 0.0f;
		}
		int index = allPlayableFacs.indexOf(fac);
		conquestStrengths[index] = str;
		isEmptyKey = str == 0.0f ? (isEmptyKey &= 1L << index ^ 0xFFFFFFFFFFFFFFFFL) : (isEmptyKey |= 1L << index);
		markDirty();
	}

	public GOTConquestZone setDummyZone() {
		isDummyZone = true;
		return this;
	}

	public void setLastChangeTime(long l) {
		lastChangeTime = l;
		markDirty();
	}

	@Override
	public String toString() {
		return "GOTConquestZone: " + gridX + ", " + gridZ;
	}

	public void updateAllOtherFactions(GOTFaction fac, World world) {
		for (int i = 0; i < conquestStrengths.length; ++i) {
			GOTFaction otherFac = allPlayableFacs.get(i);
			if (otherFac == fac || (conquestStrengths[i] <= 0.0f)) {
				continue;
			}
			float otherStr = this.getConquestStrength(otherFac, world);
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

	public static GOTConquestZone readFromNBT(NBTTagCompound nbt) {
		short x = nbt.getShort("X");
		short z = nbt.getShort("Z");
		long time = nbt.getLong("Time");
		GOTConquestZone zone = new GOTConquestZone(x, z);
		zone.isLoaded = false;
		zone.lastChangeTime = time;
		block0: for (GOTFaction fac : allPlayableFacs) {
			ArrayList<String> nameAndAliases = new ArrayList<>();
			nameAndAliases.add(fac.codeName());
			nameAndAliases.addAll(fac.listAliases());
			for (String alias : nameAndAliases) {
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
}
