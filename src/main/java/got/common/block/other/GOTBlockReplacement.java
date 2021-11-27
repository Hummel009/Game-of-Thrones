package got.common.block.other;

import java.lang.reflect.*;
import java.util.*;

import cpw.mods.fml.common.*;
import got.common.util.GOTReflection;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.*;

public class GOTBlockReplacement {
	public static boolean initForgeHooks = false;

	public static void injectReplacementItem(ItemStack itemstack, Item newItem) {
		NBTTagCompound nbt = new NBTTagCompound();
		itemstack.writeToNBT(nbt);
		itemstack.readFromNBT(nbt);
	}

	public static void replaceBlockStats(int id, Block newBlock, ItemBlock itemblock) {
		GOTBlockReplacement.replaceStat(id, StatList.mineBlockStatArray, new StatCrafting("stat.mineBlock." + id, new ChatComponentTranslation("stat.mineBlock", new ItemStack(newBlock).func_151000_E()), itemblock));
		GOTBlockReplacement.replaceStat(id, StatList.objectUseStats, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(itemblock).func_151000_E()), itemblock));
		GOTBlockReplacement.replaceStat(id, StatList.objectCraftStats, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(itemblock).func_151000_E()), itemblock));
	}

	public static void replaceItemStats(int id, Item newItem) {
		GOTBlockReplacement.replaceStat(id, StatList.objectUseStats, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(newItem).func_151000_E()), newItem));
		GOTBlockReplacement.replaceStat(id, StatList.objectCraftStats, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(newItem).func_151000_E()), newItem));
		if (newItem.isDamageable()) {
			GOTBlockReplacement.replaceStat(id, StatList.objectBreakStats, new StatCrafting("stat.breakItem." + id, new ChatComponentTranslation("stat.breakItem", new ItemStack(newItem).func_151000_E()), newItem));
		}
	}

	public static void replaceRecipesEtc(Item newItem) {
		String newItemName = newItem.getUnlocalizedName();
		List craftingRecipes = CraftingManager.getInstance().getRecipeList();
		for (Object obj : craftingRecipes) {
			ItemStack output;
			if (obj instanceof ShapedRecipes && (output = ((ShapedRecipes) obj).getRecipeOutput()) != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
				GOTBlockReplacement.injectReplacementItem(output, newItem);
			}
			if (obj instanceof ShapelessRecipes && (output = ((ShapelessRecipes) obj).getRecipeOutput()) != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
				GOTBlockReplacement.injectReplacementItem(output, newItem);
			}
			if (obj instanceof ShapedOreRecipe && (output = ((ShapedOreRecipe) obj).getRecipeOutput()) != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
				GOTBlockReplacement.injectReplacementItem(output, newItem);
			}
			if (!(obj instanceof ShapelessOreRecipe) || (output = ((ShapelessOreRecipe) obj).getRecipeOutput()) == null || output.getItem() == null || !output.getItem().getUnlocalizedName().equals(newItemName)) {
				continue;
			}
			GOTBlockReplacement.injectReplacementItem(output, newItem);
		}
		for (Object obj : AchievementList.achievementList) {
			Achievement a = (Achievement) obj;
			ItemStack icon = a.theItemStack;
			if (!icon.getItem().getUnlocalizedName().equals(newItem.getUnlocalizedName())) {
				continue;
			}
			GOTBlockReplacement.injectReplacementItem(icon, newItem);
		}
	}

	public static void replaceStat(int id, StatBase[] stats, StatBase newStat) {
		StatBase oldStat = stats[id];
		if (oldStat != null && oldStat.statId.equals(newStat.statId)) {
			for (int i = 0; i < stats.length; ++i) {
				StatBase otherOldStat = stats[i];
				if (otherOldStat == null || !otherOldStat.statId.equals(oldStat.statId)) {
					continue;
				}
				StatList.allStats.remove(otherOldStat);
				StatList.objectMineStats.remove(otherOldStat);
				StatList.itemStats.remove(otherOldStat);
				StatList.generalStats.remove(otherOldStat);
				Reflect.getOneShotStats().remove(otherOldStat.statId);
				stats[i] = newStat;
			}
			newStat.registerStat();
		}
	}

	public static void replaceVanillaBlock(Block oldBlock, Block newBlock, Class<? extends ItemBlock> itemClass) {
		try {
			Item oldItem = Item.getItemFromBlock(oldBlock);
			int id = Block.blockRegistry.getIDForObject(oldBlock);
			String blockName = Reflect.getBlockName(oldBlock);
			String registryName = Block.blockRegistry.getNameForObject(oldBlock);
			String itemblockName = blockName;
			if (oldItem != null) {
				itemblockName = Reflect.getItemName(oldItem);
			}
			newBlock.setBlockName(blockName);
			Reflect.overwriteBlockList(oldBlock, newBlock);
			Reflect.getUnderlyingIntMap(Block.blockRegistry).func_148746_a(newBlock, id);
			Reflect.getUnderlyingObjMap(Block.blockRegistry).put(registryName, newBlock);
			if (!initForgeHooks) {
				ForgeHooks.isToolEffective(new ItemStack(Items.iron_shovel), Blocks.dirt, 0);
				initForgeHooks = true;
			}
			for (int meta = 0; meta <= 15; ++meta) {
				newBlock.setHarvestLevel(oldBlock.getHarvestTool(meta), oldBlock.getHarvestLevel(meta), meta);
			}
			if (itemClass != null) {
				Constructor<?> itemCtor = null;
				for (Constructor<?> ct : itemClass.getConstructors()) {
					Class<?>[] params = ct.getParameterTypes();
					if (params.length != 1 || !Block.class.isAssignableFrom(params[0])) {
						continue;
					}
					itemCtor = ct;
					break;
				}
				ItemBlock itemblock = ((ItemBlock) itemCtor.newInstance(newBlock)).setUnlocalizedName(itemblockName);
				Reflect.getUnderlyingIntMap(Item.itemRegistry).func_148746_a(itemblock, id);
				Reflect.getUnderlyingObjMap(Item.itemRegistry).put(registryName, itemblock);
				GOTBlockReplacement.replaceBlockStats(id, newBlock, itemblock);
				GOTBlockReplacement.replaceRecipesEtc(itemblock);
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to replace vanilla block %s", oldBlock.getUnlocalizedName());
			throw new RuntimeException(e);
		}
	}

	public static void replaceVanillaItem(Item oldItem, Item newItem) {
		try {
			int id = Item.itemRegistry.getIDForObject(oldItem);
			String itemName = Reflect.getItemName(oldItem);
			String registryName = Item.itemRegistry.getNameForObject(oldItem);
			newItem.setUnlocalizedName(itemName);
			Reflect.overwriteItemList(oldItem, newItem);
			Reflect.getUnderlyingIntMap(Item.itemRegistry).func_148746_a(newItem, id);
			Reflect.getUnderlyingObjMap(Item.itemRegistry).put(registryName, newItem);
			GOTBlockReplacement.replaceItemStats(id, newItem);
			GOTBlockReplacement.replaceRecipesEtc(newItem);
		} catch (Exception e) {
			FMLLog.severe("Failed to replace vanilla item %s", oldItem.getUnlocalizedName());
			throw new RuntimeException(e);
		}
	}

	public static class Reflect {
		public Reflect() {
		}

		public static String getBlockName(Block block) {
			try {
				return (String) ObfuscationReflectionHelper.getPrivateValue(Block.class, block, "unlocalizedName", "field_149770_b");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static String getItemName(Item item) {
			try {
				return (String) ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "unlocalizedName", "field_77774_bZ");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static Map getOneShotStats() {
			try {
				return (Map) ObfuscationReflectionHelper.getPrivateValue(StatList.class, null, "oneShotStats", "field_75942_a");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static ObjectIntIdentityMap getUnderlyingIntMap(RegistryNamespaced registry) {
			try {
				return (ObjectIntIdentityMap) ObfuscationReflectionHelper.getPrivateValue(RegistryNamespaced.class, registry, "underlyingIntegerMap", "field_148759_a");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static Map getUnderlyingObjMap(RegistryNamespaced registry) {
			try {
				return (Map) ObfuscationReflectionHelper.getPrivateValue(RegistrySimple.class, registry, "registryObjects", "field_82596_a");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static void overwriteBlockList(Block oldBlock, Block newBlock) {
			try {
				Field field = null;
				for (Field f : Blocks.class.getDeclaredFields()) {
					GOTReflection.unlockFinalField(f);
					if (f.get(null) != oldBlock) {
						continue;
					}
					field = f;
					break;
				}
				GOTReflection.setFinalField(Blocks.class, null, newBlock, field);
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}

		public static void overwriteItemList(Item oldItem, Item newItem) {
			try {
				Field field = null;
				for (Field f : Items.class.getDeclaredFields()) {
					GOTReflection.unlockFinalField(f);
					if (f.get(null) != oldItem) {
						continue;
					}
					field = f;
					break;
				}
				GOTReflection.setFinalField(Items.class, null, newItem, field);
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}
	}

}
