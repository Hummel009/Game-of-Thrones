package got.common.tileentity;

import got.common.entity.GOTEntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;

public class GOTTileEntitySpawnerChest extends TileEntityChest {
	private String entityClassName = "";

	public Entity createMob() {
		return EntityList.createEntityByName(entityClassName, worldObj);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		entityClassName = nbt.getString("MobID");
	}

	public void setMobID(Class<? extends Entity> entityClass) {
		entityClassName = GOTEntityRegistry.getStringFromClass(entityClass);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("MobID", entityClassName);
	}
}