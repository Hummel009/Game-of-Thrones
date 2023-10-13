package got.common.block.other;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import got.common.util.GOTReflection;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.*;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GOTBlockReplacement {
	public static boolean initForgeHooks;

	public static void injectReplacementItem(ItemStack itemstack, Item newItem) {
		NBTTagCompound nbt = new NBTTagCompound();
		itemstack.writeToNBT(nbt);
		itemstack.readFromNBT(nbt);
	}

	public static void replaceBlockStats(int id, Block newBlock, ItemBlock itemblock) {
		replaceStat(id, StatList.mineBlockStatArray, new StatCrafting("stat.mineBlock." + id, new ChatComponentTranslation("stat.mineBlock", new ItemStack(newBlock).func_151000_E()), itemblock));
		replaceStat(id, StatList.objectUseStats, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(itemblock).func_151000_E()), itemblock));
		replaceStat(id, StatList.objectCraftStats, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(itemblock).func_151000_E()), itemblock));
	}

	public static void replaceItemStats(int id, Item newItem) {
		replaceStat(id, StatList.objectUseStats, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(newItem).func_151000_E()), newItem));
		replaceStat(id, StatList.objectCraftStats, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(newItem).func_151000_E()), newItem));
		if (newItem.isDamageable()) {
			replaceStat(id, StatList.objectBreakStats, new StatCrafting("stat.breakItem." + id, new ChatComponentTranslation("stat.breakItem", new ItemStack(newItem).func_151000_E()), newItem));
		}
	}

	public static void replaceRecipesEtc(Item newItem) {
		String newItemName = newItem.getUnlocalizedName();
		List<IRecipe> craftingRecipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipe obj : craftingRecipes) {
			if (obj instanceof ShapedRecipes) {
				ItemStack output = obj.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
			if (obj instanceof ShapelessRecipes) {
				ItemStack output = obj.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
			if (obj instanceof ShapedOreRecipe) {
				ItemStack output = obj.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
			if (obj instanceof ShapelessOreRecipe) {
				ItemStack output = obj.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
		}
		for (Object obj : AchievementList.achievementList) {
			Achievement a = (Achievement) obj;
			ItemStack icon = a.theItemStack;
			if (icon.getItem().getUnlocalizedName().equals(newItem.getUnlocalizedName())) {
				injectReplacementItem(icon, newItem);
			}
		}
	}

	public static void replaceStat(int id, StatBase[] stats, StatBase newStat) {
		StatBase oldStat = stats[id];
		if (oldStat != null && oldStat.statId.equals(newStat.statId)) {
			for (int i = 0; i < stats.length; i++) {
				StatBase otherOldStat = stats[i];
				if (otherOldStat != null && otherOldStat.statId.equals(oldStat.statId)) {
					StatList.allStats.remove(otherOldStat);
					StatList.objectMineStats.remove(otherOldStat);
					StatList.itemStats.remove(otherOldStat);
					StatList.generalStats.remove(otherOldStat);
					Reflect.getOneShotStats().remove(otherOldStat.statId);
					stats[i] = newStat;
				}
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
			for (int meta = 0; meta <= 15; meta++) {
				newBlock.setHarvestLevel(oldBlock.getHarvestTool(meta), oldBlock.getHarvestLevel(meta), meta);
			}
			if (itemClass != null) {
				Constructor<ItemBlock> itemCtor = null;
				Constructor<ItemBlock>[] itemCtors = (Constructor<ItemBlock>[]) itemClass.getConstructors();
				for (Constructor<ItemBlock> ct : itemCtors) {
					Class<?>[] params = ct.getParameterTypes();
					if (params.length == 1 && Block.class.isAssignableFrom(params[0])) {
						itemCtor = ct;
						break;
					}
				}
				ItemBlock itemblock = itemCtor.newInstance(newBlock).setUnlocalizedName(itemblockName);
				Reflect.getUnderlyingIntMap(Item.itemRegistry).func_148746_a(itemblock, id);
				Reflect.getUnderlyingObjMap(Item.itemRegistry).put(registryName, itemblock);
				replaceBlockStats(id, newBlock, itemblock);
				replaceRecipesEtc(itemblock);
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
			replaceItemStats(id, newItem);
			replaceRecipesEtc(newItem);
		} catch (Exception e) {
			FMLLog.severe("Failed to replace vanilla item %s", oldItem.getUnlocalizedName());
			throw new RuntimeException(e);
		}
	}

	public static class Reflect {
		public static String getBlockName(Block block) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(Block.class, block, "unlocalizedName", "field_149770_b");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static String getItemName(Item item) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "unlocalizedName", "field_77774_bZ");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static Map<String, StatBase> getOneShotStats() {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(StatList.class, null, "oneShotStats", "field_75942_a");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static ObjectIntIdentityMap getUnderlyingIntMap(RegistryNamespaced registry) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(RegistryNamespaced.class, registry, "underlyingIntegerMap", "field_148759_a");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static Map<Object, Object> getUnderlyingObjMap(RegistryNamespaced registry) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(RegistrySimple.class, registry, "registryObjects", "field_82596_a");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static void overwriteBlockList(Block oldBlock, Block newBlock) {
			try {
				Field field = null;
				Field[] declaredFields = Blocks.class.getDeclaredFields();
				for (Field f : declaredFields) {
					GOTReflection.unlockFinalField(f);
					if (f.get(null) == oldBlock) {
						field = f;
						break;
					}
				}
				GOTReflection.setFinalField(Blocks.class, null, newBlock, field);
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}

		public static void overwriteItemList(Item oldItem, Item newItem) {
			try {
				Field field = null;
				Field[] declaredFields = Items.class.getDeclaredFields();
				for (Field f : declaredFields) {
					GOTReflection.unlockFinalField(f);
					if (f.get(null) == oldItem) {
						field = f;
						break;
					}
				}
				GOTReflection.setFinalField(Items.class, null, newItem, field);
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}
	}
}