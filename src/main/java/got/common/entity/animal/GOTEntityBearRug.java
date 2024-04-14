package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityBearRug extends GOTEntityRugBase {
	@SuppressWarnings("WeakerAccess")
	public GOTEntityBearRug(World world) {
		super(world);
		setSize(1.8f, 0.3f);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
	}

	@Override
	public ItemStack getRugItem() {
		return new ItemStack(GOTItems.bearRug, 1, getRugType().bearID);
	}

	@Override
	public String getRugNoise() {
		return "got:bear.say";
	}

	public GOTEntityBear.BearType getRugType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return GOTEntityBear.BearType.forID(i);
	}

	public void setRugType(GOTEntityBear.BearType t) {
		dataWatcher.updateObject(18, (byte) t.bearID);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setRugType(GOTEntityBear.BearType.forID(nbt.getByte("RugType")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("RugType", (byte) getRugType().bearID);
	}
}