package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.*;
import got.common.database.*;
import got.common.entity.other.*;
import got.common.faction.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemConquestHorn extends Item {
	@SideOnly(value = Side.CLIENT)
	public IIcon baseIcon;
	@SideOnly(value = Side.CLIENT)
	public IIcon overlayIcon;

	public GOTItemConquestHorn() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabCombat);
	}

	public boolean canUseHorn(ItemStack itemstack, World world, EntityPlayer entityplayer, boolean sendMessage) {
		GOTInvasions invasionType = GOTItemConquestHorn.getInvasionType(itemstack);
		GOTFaction invasionFaction = invasionType.getInvasionFaction();
		float alignmentRequired = 1000.0f;
		if (GOTLevelData.getData(entityplayer).getAlignment(invasionFaction) >= alignmentRequired) {
			boolean blocked = false;
			if (GOTBannerProtection.isProtected(world, entityplayer, GOTBannerProtection.forFaction(invasionFaction), false)) {
				blocked = true;
			}
			if (GOTEntityNPCRespawner.isSpawnBlocked(entityplayer, invasionFaction)) {
				blocked = true;
			}
			if (blocked) {
				if (sendMessage && !world.isRemote) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.conquestHornProtected", invasionFaction.factionName()));
				}
				return false;
			}
			return true;
		}
		if (sendMessage && !world.isRemote) {
			GOTAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, alignmentRequired, invasionType.getInvasionFaction());
		}
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 0) {
			GOTFaction faction = GOTItemConquestHorn.getInvasionType(itemstack).getInvasionFaction();
			return faction.getFactionColor();
		}
		return 16777215;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int i, int pass) {
		return pass > 0 ? overlayIcon : baseIcon;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		GOTInvasions type = GOTItemConquestHorn.getInvasionType(itemstack);
		if (type != null) {
			return StatCollector.translateToLocal(type.codeNameHorn());
		}
		return super.getItemStackDisplayName(itemstack);
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
		for (GOTInvasions type : GOTInvasions.values()) {
			ItemStack itemstack = new ItemStack(item);
			GOTItemConquestHorn.setInvasionType(itemstack, type);
			list.add(itemstack);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		GOTInvasions invasionType = GOTItemConquestHorn.getInvasionType(itemstack);
		if (canUseHorn(itemstack, world, entityplayer, true)) {
			if (!world.isRemote) {
				GOTEntityInvasionSpawner invasion = new GOTEntityInvasionSpawner(world);
				invasion.setInvasionType(invasionType);
				invasion.isWarhorn = true;
				invasion.spawnsPersistent = true;
				invasion.setLocationAndAngles(entityplayer.posX, entityplayer.posY + 3.0, entityplayer.posZ, 0.0f, 0.0f);
				world.spawnEntityInWorld(invasion);
				invasion.startInvasion(entityplayer);
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		canUseHorn(itemstack, world, entityplayer, false);
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		baseIcon = iconregister.registerIcon(getIconString() + "_base");
		overlayIcon = iconregister.registerIcon(getIconString() + "_overlay");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static ItemStack createHorn(GOTInvasions type) {
		ItemStack itemstack = new ItemStack(GOTRegistry.warhorn);
		GOTItemConquestHorn.setInvasionType(itemstack, type);
		return itemstack;
	}

	public static GOTInvasions getInvasionType(ItemStack itemstack) {
		String s;
		GOTInvasions invasionType = null;
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("InvasionType")) {
			s = itemstack.getTagCompound().getString("InvasionType");
			invasionType = GOTInvasions.forName(s);
		}
		if (invasionType == null && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HornFaction")) {
			s = itemstack.getTagCompound().getString("HornFaction");
			invasionType = GOTInvasions.forName(s);
		}
		if (invasionType == null) {
			invasionType = GOTInvasions.NORTH;
		}
		return invasionType;
	}

	public static void setInvasionType(ItemStack itemstack, GOTInvasions type) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("InvasionType", type.codeName());
	}
}
