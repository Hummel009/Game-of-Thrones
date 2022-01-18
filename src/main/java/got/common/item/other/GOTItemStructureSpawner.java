package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.GOTLevelData;
import got.common.database.GOTCreativeTabs;
import got.common.world.structure.other.GOTStructureRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemStructureSpawner extends Item {
	public static int lastStructureSpawnTick = 0;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconBase;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconOverlay;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconVillageBase;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconVillageOverlay;

	public GOTItemStructureSpawner() {
		setHasSubtypes(true);
		setCreativeTab(GOTCreativeTabs.tabSpawn);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		GOTStructureRegistry.StructureColorInfo info = GOTStructureRegistry.structureItemSpawners.get(itemstack.getItemDamage());
		if (info != null) {
			if (pass == 0) {
				return info.colorBackground;
			}
			return info.colorForeground;
		}
		return 16777215;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int i, int pass) {
		GOTStructureRegistry.StructureColorInfo info = GOTStructureRegistry.structureItemSpawners.get(i);
		if (info != null) {
			if (info.isVillage) {
				if (pass == 0) {
					return iconVillageBase;
				}
				return iconVillageOverlay;
			}
			if (pass == 0) {
				return iconBase;
			}
			return iconOverlay;
		}
		return iconBase;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		StringBuilder s = new StringBuilder(("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim());
		String structureName = GOTStructureRegistry.getNameFromID(itemstack.getItemDamage());
		if (structureName != null) {
			s.append(" ").append(StatCollector.translateToLocal("got.structure." + structureName + ".name"));
		}
		return s.toString();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (GOTStructureRegistry.StructureColorInfo info : GOTStructureRegistry.structureItemSpawners.values()) {
			if (info.isHidden) {
				continue;
			}
			list.add(new ItemStack(item, 1, info.spawnedID));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (world.isRemote) {
			return true;
		}
		if (GOTLevelData.structuresBanned()) {
			entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.spawnStructure.disabled"));
			return false;
		}
		if (GOTLevelData.isPlayerBannedForStructures(entityplayer)) {
			entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.spawnStructure.banned"));
			return false;
		}
		if (lastStructureSpawnTick > 0) {
			entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.spawnStructure.wait", lastStructureSpawnTick / 20.0));
			return false;
		}
		if (spawnStructure(entityplayer, world, itemstack.getItemDamage(), i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side]) && !entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}
		return true;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		iconBase = iconregister.registerIcon(getIconString() + "_base");
		iconOverlay = iconregister.registerIcon(getIconString() + "_overlay");
		iconVillageBase = iconregister.registerIcon(getIconString() + "_village_base");
		iconVillageOverlay = iconregister.registerIcon(getIconString() + "_village_overlay");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public boolean spawnStructure(EntityPlayer entityplayer, World world, int id, int i, int j, int k) {
		if (!GOTStructureRegistry.structureItemSpawners.containsKey(id)) {
			return false;
		}
		GOTStructureRegistry.IStructureProvider strProvider = GOTStructureRegistry.getStructureForID(id);
		if (strProvider != null) {
			boolean generated = strProvider.generateStructure(world, entityplayer, i, j, k);
			if (generated) {
				lastStructureSpawnTick = 20;
				world.playSoundAtEntity(entityplayer, "got:item.structureSpawner", 1.0f, 1.0f);
			}
			return generated;
		}
		return false;
	}
}
