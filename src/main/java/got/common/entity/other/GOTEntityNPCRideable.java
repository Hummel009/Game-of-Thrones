package got.common.entity.other;

import got.GOT;
import got.common.item.other.GOTItemMountArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class GOTEntityNPCRideable extends GOTEntityNPC implements GOTNPCMount {
	public UUID tamingPlayer;
	public int npcTemper;

	protected GOTEntityNPCRideable(World world) {
		super(world);
	}

	public void angerNPC() {
		playSound(getHurtSound(), getSoundVolume(), getSoundPitch() * 1.5f);
	}

	@Override
	public boolean canDespawn() {
		return super.canDespawn() && !isNPCTamed();
	}

	@Override
	public boolean canRenameNPC() {
		return isNPCTamed() || super.canRenameNPC();
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	public double getBaseMountedYOffset() {
		return height * 0.5;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	public int getMaxNPCTemper() {
		return 100;
	}

	@Override
	public double getMountedYOffset() {
		double d = getBaseMountedYOffset();
		if (riddenByEntity != null) {
			d += riddenByEntity.yOffset - riddenByEntity.getYOffset();
		}
		return d;
	}

	public IInventory getMountInventory() {
		return null;
	}

	public int getNPCTemper() {
		return npcTemper;
	}

	public void setNPCTemper(int i) {
		npcTemper = i;
	}

	@Override
	public float getStepHeightWhileRiddenByPlayer() {
		return 1.0f;
	}

	public EntityPlayer getTamingPlayer() {
		return worldObj.func_152378_a(tamingPlayer);
	}

	public int increaseNPCTemper(int i) {
		npcTemper = MathHelper.clamp_int(npcTemper + i, 0, getMaxNPCTemper());
		return npcTemper;
	}

	@Override
	public boolean isMountArmorValid(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof GOTItemMountArmor) {
			GOTItemMountArmor armor = (GOTItemMountArmor) itemstack.getItem();
			return armor.isValid(this);
		}
		return false;
	}

	public boolean isNPCTamed() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setNPCTamed(boolean flag) {
		dataWatcher.updateObject(17, (byte) (flag ? 1 : 0));
	}

	@Override
	public void moveEntityWithHeading(float strafe, float forward) {
		GOTMountFunctions.move(this, strafe, forward);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		GOTMountFunctions.update(this);
	}

	public void openGUI(EntityPlayer entityplayer) {
		IInventory inv = getMountInventory();
		if (inv != null && !worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer) && isNPCTamed()) {
			entityplayer.openGui(GOT.instance, 29, worldObj, getEntityId(), inv.getSizeInventory(), 0);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setNPCTamed(nbt.getBoolean("NPCTamed"));
		if (nbt.hasKey("NPCTamer")) {
			tamingPlayer = UUID.fromString(nbt.getString("NPCTamer"));
		}
		npcTemper = nbt.getInteger("NPCTemper");
	}

	@Override
	public void super_moveEntityWithHeading(float strafe, float forward) {
		super.moveEntityWithHeading(strafe, forward);
	}

	public void tameNPC(EntityPlayer entityplayer) {
		setNPCTamed(true);
		tamingPlayer = entityplayer.getUniqueID();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("NPCTamed", isNPCTamed());
		if (tamingPlayer != null) {
			nbt.setString("NPCTamer", tamingPlayer.toString());
		}
		nbt.setInteger("NPCTemper", npcTemper);
	}
}
