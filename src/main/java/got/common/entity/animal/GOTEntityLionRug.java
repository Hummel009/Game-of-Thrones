package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityRugBase;
import got.common.item.other.GOTItemLionRug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityLionRug extends GOTEntityRugBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLionRug(World world) {
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
		return new ItemStack(GOTItems.lionRug, 1, getRugType().getLionID());
	}

	@Override
	public String getRugNoise() {
		return "got:lion.say";
	}

	public GOTItemLionRug.LionRugType getRugType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return GOTItemLionRug.LionRugType.forID(i);
	}

	public void setRugType(GOTItemLionRug.LionRugType t) {
		dataWatcher.updateObject(18, (byte) t.getLionID());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setRugType(GOTItemLionRug.LionRugType.forID(nbt.getByte("RugType")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("RugType", (byte) getRugType().getLionID());
	}
}