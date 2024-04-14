package got.common.entity.dragon;

import net.minecraft.entity.DataWatcher;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class GOTDragonHelper {
	protected GOTEntityDragon dragon;
	protected DataWatcher dataWatcher;
	protected Random rand;

	protected GOTDragonHelper(GOTEntityDragon dragon) {
		this.dragon = dragon;
		dataWatcher = dragon.getDataWatcher();
		rand = dragon.getRNG();
	}

	public void applyEntityAttributes() {
	}

	public void onDeath() {
	}

	public void onDeathUpdate() {
	}

	public void onLivingUpdate() {
	}

	public void readFromNBT(NBTTagCompound nbt) {
	}

	public void writeToNBT(NBTTagCompound nbt) {
	}
}
