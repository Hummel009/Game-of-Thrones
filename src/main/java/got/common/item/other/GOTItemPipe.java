package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import got.common.entity.other.GOTEntitySmokeRing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GOTItemPipe extends Item {
	public static int MAGIC_COLOR = 16;

	public GOTItemPipe() {
		setMaxDamage(300);
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabMisc);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int color = GOTItemPipe.getSmokeColor(itemstack);
		list.add(StatCollector.translateToLocal(this.getUnlocalizedName() + ".subtitle." + color));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 16; ++i) {
			ItemStack itemstack = new ItemStack(this);
			GOTItemPipe.setSmokeColor(itemstack, i);
			list.add(itemstack);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.inventory.hasItem(GOTRegistry.pipeweed) || entityplayer.capabilities.isCreativeMode) {
			itemstack.damageItem(1, entityplayer);
			if (!entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.consumeInventoryItem(GOTRegistry.pipeweed);
			}
			if (entityplayer.canEat(false)) {
				entityplayer.getFoodStats().addStats(2, 0.3f);
			}
			if (!world.isRemote) {
				GOTEntitySmokeRing smoke = new GOTEntitySmokeRing(world, entityplayer);
				int color = GOTItemPipe.getSmokeColor(itemstack);
				smoke.setSmokeColour(color);
				world.spawnEntityInWorld(smoke);
			}
			world.playSoundAtEntity(entityplayer, "got:textures/model.puff", 1.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.inventory.hasItem(GOTRegistry.pipeweed) || entityplayer.capabilities.isCreativeMode) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	public static int getSmokeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SmokeColour")) {
			return itemstack.getTagCompound().getInteger("SmokeColour");
		}
		return 0;
	}

	public static boolean isPipeDyed(ItemStack itemstack) {
		int color = GOTItemPipe.getSmokeColor(itemstack);
		return color != 0 && color != 16;
	}

	public static void removePipeDye(ItemStack itemstack) {
		GOTItemPipe.setSmokeColor(itemstack, 0);
	}

	public static void setSmokeColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("SmokeColour", i);
	}
}
