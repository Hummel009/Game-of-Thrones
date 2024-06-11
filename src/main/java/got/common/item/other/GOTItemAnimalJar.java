package got.common.item.other;

import got.common.GOTLevelData;
import got.common.block.other.GOTBlockAnimalJar;
import got.common.database.GOTAchievement;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.animal.GOTEntityButterfly;
import got.common.tileentity.GOTTileEntityAnimalJar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class GOTItemAnimalJar extends GOTItemBlockMetadata {
	public GOTItemAnimalJar(Block block) {
		super(block);
		setMaxStackSize(1);
	}

	private static NBTTagCompound getEntityData(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound nbt;
			if (itemstack.getTagCompound().hasKey("GOTButterfly")) {
				nbt = itemstack.getTagCompound().getCompoundTag("GOTButterfly");
				if (!nbt.hasNoTags()) {
					nbt.setString("id", GOTEntityRegistry.getStringFromClass(GOTEntityButterfly.class));
					setEntityData(itemstack, (NBTTagCompound) nbt.copy());
				}
				itemstack.getTagCompound().removeTag("GOTButterfly");
			}
			if (itemstack.getTagCompound().hasKey("JarEntity") && !(nbt = itemstack.getTagCompound().getCompoundTag("JarEntity")).hasNoTags()) {
				return nbt;
			}
		}
		return null;
	}

	public static Entity getItemJarEntity(ItemStack itemstack, World world) {
		NBTTagCompound nbt = getEntityData(itemstack);
		if (nbt != null) {
			return EntityList.createEntityFromNBT(nbt, world);
		}
		return null;
	}

	public static void setEntityData(ItemStack itemstack, NBTTagCompound nbt) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		if (nbt == null) {
			itemstack.getTagCompound().removeTag("JarEntity");
		} else {
			itemstack.getTagCompound().setTag("JarEntity", nbt);
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
		ItemStack itemstack1 = entityplayer.getCurrentEquippedItem();
		World world = entityplayer.worldObj;
		GOTBlockAnimalJar jarBlock = (GOTBlockAnimalJar) field_150939_a;
		if (jarBlock.canCapture(entity) && getEntityData(itemstack1) == null) {
			NBTTagCompound nbt;
			if (!world.isRemote && entity.writeToNBTOptional(nbt = new NBTTagCompound())) {
				setEntityData(itemstack1, nbt);
				entity.playSound("random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
				entity.setDead();
				if (entity instanceof GOTEntityButterfly) {
					GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.useButterflyJar);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		Entity jarEntity;
		if (!world.isRemote && (jarEntity = getItemJarEntity(itemstack, world)) != null) {
			double x = entityplayer.posX;
			double y = entityplayer.boundingBox.minY + entityplayer.getEyeHeight();
			double z = entityplayer.posZ;
			Vec3 look = entityplayer.getLookVec();
			float length = 2.0f;
			jarEntity.setLocationAndAngles(x + look.xCoord * length, y + look.yCoord * length, z + look.zCoord * length, world.rand.nextFloat(), 0.0f);
			world.spawnEntityInWorld(jarEntity);
			jarEntity.playSound("random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
			setEntityData(itemstack, null);
		}
		return itemstack;
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
		if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
			TileEntity tileentity = world.getTileEntity(i, j, k);
			if (tileentity instanceof GOTTileEntityAnimalJar) {
				GOTTileEntityAnimalJar jar = (GOTTileEntityAnimalJar) tileentity;
				jar.setEntityData(getEntityData(itemstack));
			}
			return true;
		}
		return false;
	}
}