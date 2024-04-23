package got.common.entity.other.inanimate;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityLingeringPotion extends EntityPotion implements IEntityAdditionalSpawnData {
	private ItemStack stack;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLingeringPotion(World world) {
		super(world);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLingeringPotion(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
		this.stack = stack;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLingeringPotion(World world, EntityLivingBase thrower, ItemStack stack) {
		super(world, thrower, stack);
		this.stack = stack;
	}

	public ItemStack getStack() {
		return stack;
	}

	@Override
	public void onImpact(MovingObjectPosition mop) {
		if (worldObj.isRemote) {
			return;
		}
		worldObj.spawnEntityInWorld(new GOTEntityLingeringEffect(worldObj, this));
		worldObj.playAuxSFX(2002, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), stack.getItemDamage());
		setDead();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Potion"));
		if (stack == null) {
			setDead();
		}
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		stack = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (stack != null) {
			nbt.setTag("Potion", stack.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeItemStack(buffer, stack);
	}
}