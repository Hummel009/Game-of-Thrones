package got.common.entity.dragon.helper;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.DataWatcher;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public abstract class GOTDragonHelper {
	protected final GOTEntityDragon dragon;
	protected final DataWatcher dataWatcher;
	protected final Random rand;

	protected GOTDragonHelper(GOTEntityDragon dragon) {
		this.dragon = dragon;
		dataWatcher = dragon.getDataWatcher();
		rand = dragon.getRNG();
	}

	public abstract void applyEntityAttributes();

	public abstract void onDeath();

	public abstract void onDeathUpdate();

	public abstract void onLivingUpdate();

	public abstract void readFromNBT(NBTTagCompound nbt);

	public abstract void writeToNBT(NBTTagCompound nbt);
}