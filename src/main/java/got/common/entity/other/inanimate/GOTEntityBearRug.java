package got.common.entity.other.inanimate;

import got.common.database.GOTItems;
import got.common.entity.animal.GOTEntityBear;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityBearRug extends GOTEntityRugBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
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
		return new ItemStack(GOTItems.bearRug, 1, getRugType().getBearID());
	}

	public GOTEntityBear.BearType getRugType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return GOTEntityBear.BearType.forID(i);
	}

	public void setRugType(GOTEntityBear.BearType t) {
		dataWatcher.updateObject(18, (byte) t.getBearID());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setRugType(GOTEntityBear.BearType.forID(nbt.getByte("RugType")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("RugType", (byte) getRugType().getBearID());
	}
}