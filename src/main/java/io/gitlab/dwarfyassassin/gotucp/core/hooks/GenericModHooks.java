package io.gitlab.dwarfyassassin.gotucp.core.hooks;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.RegistryDelegate;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GenericModHooks {
	public static void removeBlockFromOreDictionary(Block block) {
		removeItemFromOreDictionary(Item.getItemFromBlock(block));
	}

	public static void removeItemFromOreDictionary(Item item) {
		if (item == null) {
			return;
		}
		ItemStack stack = new ItemStack(item, 1, 32767);
		int[] oreIDs = OreDictionary.getOreIDs(stack);
		List<ArrayList<ItemStack>> oreIdToStacks = ReflectionHelper.getPrivateValue(OreDictionary.class, null, "idToStack");
		for (int oreID : oreIDs) {
			ArrayList<ItemStack> oreStacks = oreIdToStacks.get(oreID);
			if (oreStacks != null) {
				HashSet<ItemStack> toRemove = new HashSet<>();
				for (ItemStack oreStack : oreStacks) {
					if (oreStack.getItem() == stack.getItem()) {
						toRemove.add(oreStack);
					}
				}
				oreStacks.removeAll(toRemove);
			}
		}
		String registryName = stack.getItem().delegate.name();
		if (registryName == null) {
			return;
		}
		int stackId = GameData.getItemRegistry().getId(registryName);
		Map<Integer, List<Integer>> stackIdToOreId = ReflectionHelper.getPrivateValue(OreDictionary.class, null, "stackToId");
		stackIdToOreId.remove(stackId);
	}

	public static void setBlockDelagateName(Block block, String name) {
		RegistryDelegate.Delegate<Block> delegate = (RegistryDelegate.Delegate<Block>) block.delegate;
		ReflectionHelper.setPrivateValue(RegistryDelegate.Delegate.class, delegate, name, "name");
	}

	public static void setItemDelagateName(Item item, String name) {
		RegistryDelegate.Delegate<Item> delegate = (RegistryDelegate.Delegate<Item>) item.delegate;
		ReflectionHelper.setPrivateValue(RegistryDelegate.Delegate.class, delegate, name, "name");
	}
}
