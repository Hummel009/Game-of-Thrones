package got.common.entity.dragon.helper;

import got.common.entity.dragon.GOTEntityDragon;
import got.common.util.GOTLog;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Locale;

public class GOTDragonReproductionHelper extends GOTDragonHelper {
	private static final String NBT_BREEDER = "HatchedBy";
	private static final String NBT_REPRO_COUNT = "ReproductionCount";

	private final int dataIndexBreeder;
	private final int dataIndexReproduced;

	public GOTDragonReproductionHelper(GOTEntityDragon dragon, int dataIndexBreeder, int dataIndexReproCount) {
		super(dragon);

		this.dataIndexBreeder = dataIndexBreeder;
		dataIndexReproduced = dataIndexReproCount;

		dataWatcher.addObject(dataIndexBreeder, "");
		dataWatcher.addObject(dataIndexReproCount, 0);
	}

	private void addReproduced() {
		setReproCount(getReproCount() + 1);
	}

	public boolean canMateWith(EntityAnimal mate) {
		if (mate == dragon || !dragon.isTamed() || !(mate instanceof GOTEntityDragon) || !canReproduce()) {

			return false;
		}
		GOTEntityDragon dragonMate = (GOTEntityDragon) mate;

		return dragonMate.isTamed() && dragonMate.getReproductionHelper().canReproduce() && dragon.isInLove() && dragonMate.isInLove();
	}

	public boolean canReproduce() {
		byte REPRO_LIMIT = 2;
		return getReproCount() < REPRO_LIMIT;
	}

	public EntityAgeable createChild(EntityAgeable mate) {
		if (!(mate instanceof GOTEntityDragon)) {
			throw new IllegalArgumentException("The mate isn't a dragon");
		}
		GOTEntityDragon parent1 = dragon;
		GOTEntityDragon parent2 = (GOTEntityDragon) mate;
		GOTEntityDragon baby = new GOTEntityDragon(dragon.worldObj);

		if (parent1.hasCustomNameTag() && parent2.hasCustomNameTag()) {
			String p1Name = parent1.getCustomNameTag();
			String p2Name = parent2.getCustomNameTag();
			String babyName;
			if (p1Name.contains(" ") || p2Name.contains(" ")) {

				String[] p1Names = p1Name.split(" ");
				String[] p2Names = p2Name.split(" ");
				p1Name = fixChildName(p1Names[rand.nextInt(p1Names.length)]);
				p2Name = fixChildName(p2Names[rand.nextInt(p2Names.length)]);
				babyName = rand.nextBoolean() ? p1Name + ' ' + p2Name : p2Name + ' ' + p1Name;
			} else {

				if (rand.nextBoolean()) {
					p1Name = p1Name.substring(0, (p1Name.length() - 1) / 2);
				} else {
					p1Name = p1Name.substring((p1Name.length() - 1) / 2);
				}
				if (rand.nextBoolean()) {
					p2Name = p2Name.substring(0, (p2Name.length() - 1) / 2);
				} else {
					p2Name = p2Name.substring((p2Name.length() - 1) / 2);
				}
				p2Name = fixChildName(p2Name);
				babyName = rand.nextBoolean() ? p1Name + p2Name : p2Name + p1Name;
			}
			baby.setCustomNameTag(babyName);
		}
		parent1.getReproductionHelper().addReproduced();
		parent2.getReproductionHelper().addReproduced();
		return baby;
	}

	private String fixChildName(String nameOld) {
		if (nameOld == null || nameOld.isEmpty()) {
			return nameOld;
		}
		char[] chars = nameOld.toLowerCase(Locale.ROOT).toCharArray();

		chars[0] = Character.toUpperCase(chars[0]);

		String nameNew = new String(chars);

		if (!nameOld.equals(nameNew)) {
			GOTLog.getLogger().debug("Fixed child name {} -> {}");
		}
		return nameNew;
	}

	private String getBreederName() {
		return dataWatcher.getWatchableObjectString(dataIndexBreeder);
	}

	public void setBreederName(String breederName) {
		GOTLog.getLogger().trace("setBreederName({})", breederName);
		dataWatcher.updateObject(dataIndexBreeder, breederName);
	}

	private int getReproCount() {
		return dataWatcher.getWatchableObjectInt(dataIndexReproduced);
	}

	private void setReproCount(int reproCount) {
		GOTLog.getLogger().trace("setReproCount({})", reproCount);
		dataWatcher.updateObject(dataIndexReproduced, reproCount);
	}

	@Override
	public void applyEntityAttributes() {
	}

	@Override
	public void onDeath() {
	}

	@Override
	public void onDeathUpdate() {
	}

	@Override
	public void onLivingUpdate() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		setBreederName(nbt.getString(NBT_BREEDER));

		int reproCount = 0;
		String NBT_REPRODUCED = "HasReproduced";
		if (nbt.hasKey(NBT_REPRO_COUNT)) {
			reproCount = nbt.getInteger(NBT_REPRO_COUNT);
		} else if (nbt.getBoolean(NBT_REPRODUCED)) {
			reproCount++;
		}
		setReproCount(reproCount);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString(NBT_BREEDER, getBreederName() == null ? "" : getBreederName());
		nbt.setInteger(NBT_REPRO_COUNT, getReproCount());
	}
}