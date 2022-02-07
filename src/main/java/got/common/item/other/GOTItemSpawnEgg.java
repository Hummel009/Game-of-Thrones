package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseSpawnEgg;
import got.common.entity.other.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemSpawnEgg extends Item {
	public GOTItemSpawnEgg() {
		setHasSubtypes(true);
		setCreativeTab(GOTCreativeTabs.tabSpawn);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseSpawnEgg());
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int i) {
		GOTEntityRegistry.SpawnEggInfo info = GOTEntityRegistry.spawnEggs.get(itemstack.getItemDamage());
		return info != null ? i == 0 ? info.primaryColor : info.secondaryColor : 16777215;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int i, int j) {
		return Items.spawn_egg.getIconFromDamageForRenderPass(i, j);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		StringBuilder itemName = new StringBuilder(("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim());
		String entityName = GOTEntityRegistry.getStringFromID(itemstack.getItemDamage());
		if (entityName != null) {
			itemName.append(" ").append(StatCollector.translateToLocal("entity." + entityName + ".name"));
		}
		return itemName.toString();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (GOTEntityRegistry.SpawnEggInfo info : GOTEntityRegistry.spawnEggs.values()) {
			list.add(new ItemStack(item, 1, info.spawnedID));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		Entity entity;
		if (world.isRemote) {
			return true;
		}
		Block block = world.getBlock(i, j, k);
		i += Facing.offsetsXForSide[l];
		j += Facing.offsetsYForSide[l];
		k += Facing.offsetsZForSide[l];
		double d = 0.0;
		if (l == 1 && block != null && block.getRenderType() == 11) {
			d = 0.5;
		}
		entity = GOTItemSpawnEgg.spawnCreature(world, itemstack.getItemDamage(), i + 0.5, j + d, k + 0.5);
		if (entity != null) {
			if (entity instanceof EntityLiving && itemstack.hasDisplayName()) {
				((EntityLiving) entity).setCustomNameTag(itemstack.getDisplayName());
			}
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).isNPCPersistent = true;
				((GOTEntityNPC) entity).onArtificalSpawn();
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return true;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static Entity spawnCreature(World world, int id, double d, double d1, double d2) {
		if (!GOTEntityRegistry.spawnEggs.containsKey(id)) {
			return null;
		}
		String entityName = GOTEntityRegistry.getStringFromID(id);
		Entity entity = EntityList.createEntityByName(entityName, world);
		if (entity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving) entity;
			entityliving.setLocationAndAngles(d, d1, d2, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0f), 0.0f);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			entityliving.onSpawnWithEgg(null);
			world.spawnEntityInWorld(entityliving);
			entityliving.playLivingSound();
		}
		return entity;
	}
}
