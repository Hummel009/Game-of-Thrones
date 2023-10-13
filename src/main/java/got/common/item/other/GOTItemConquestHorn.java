package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTBannerProtection;
import got.common.GOTLevelData;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityInvasionSpawner;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GOTItemConquestHorn extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon baseIcon;
	@SideOnly(Side.CLIENT)
	public IIcon overlayIcon;

	public GOTItemConquestHorn() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabCombat);
	}

	public boolean canUseHorn(ItemStack itemstack, World world, EntityPlayer entityplayer, boolean sendMessage) {
		GOTInvasions invasionType = getInvasionType(itemstack);
		GOTFaction invasionFaction = invasionType.invasionFaction;
		float alignmentRequired = 1000.0f;
		if (GOTLevelData.getData(entityplayer).getAlignment(invasionFaction) >= alignmentRequired) {
			boolean blocked = GOTBannerProtection.isProtected(world, entityplayer, GOTBannerProtection.forFaction(invasionFaction), false);
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
			GOTAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, alignmentRequired, invasionType.invasionFaction);
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 0) {
			GOTFaction faction = getInvasionType(itemstack).invasionFaction;
			return faction.getFactionColor();
		}
		return 16777215;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int i, int pass) {
		return pass > 0 ? overlayIcon : baseIcon;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		GOTInvasions type = getInvasionType(itemstack);
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

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (GOTInvasions type : GOTInvasions.values()) {
			ItemStack itemstack = new ItemStack(item);
			setInvasionType(itemstack, type);
			list.add(itemstack);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		GOTInvasions invasionType = getInvasionType(itemstack);
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		baseIcon = iconregister.registerIcon(getIconString() + "_base");
		overlayIcon = iconregister.registerIcon(getIconString() + "_overlay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static ItemStack createHorn(GOTInvasions type) {
		ItemStack itemstack = new ItemStack(GOTItems.conquestHorn);
		setInvasionType(itemstack, type);
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
