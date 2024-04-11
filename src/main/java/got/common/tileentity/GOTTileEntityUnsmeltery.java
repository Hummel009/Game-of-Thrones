package got.common.tileentity;

import com.mojang.authlib.GameProfile;
import got.GOT;
import got.common.block.table.GOTBlockCraftingTable;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.inventory.GOTContainerCraftingTable;
import got.common.item.other.GOTItemMountArmor;
import got.common.item.weapon.GOTItemCrossbow;
import got.common.item.weapon.GOTItemThrowingAxe;
import got.common.recipe.GOTRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GOTTileEntityUnsmeltery extends GOTTileEntityAlloyForge {
	public static Random unsmeltingRand = new Random();
	public static Map<Pair<Item, Integer>, Integer> unsmeltableCraftingCounts = new HashMap<>();
	public float prevRocking;
	public float rocking;
	public float prevRockingPhase;
	public float rockingPhase = unsmeltingRand.nextFloat() * 3.1415927F * 2.0F;
	public boolean prevServerActive;
	public boolean serverActive;
	public boolean clientActive;

	public static ItemStack getEquipmentMaterial(ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}
		Item item = itemstack.getItem();
		ItemStack material = null;
		if (item instanceof ItemTool) {
			material = ((ItemTool) item).func_150913_i().getRepairItemStack();
		} else if (item instanceof ItemSword) {
			material = GOTMaterial.getToolMaterialByName(((ItemSword) item).getToolMaterialName()).getRepairItemStack();
		} else if (item instanceof ItemHoe) {
			material = GOTMaterial.getToolMaterialByName(((ItemHoe) item).getToolMaterialName()).getRepairItemStack();
		} else if (item instanceof GOTItemCrossbow) {
			material = ((GOTItemCrossbow) item).getCrossbowMaterial().getRepairItemStack();
		} else if (item instanceof GOTItemThrowingAxe) {
			material = ((GOTItemThrowingAxe) item).getAxeMaterial().getRepairItemStack();
		} else if (item instanceof ItemArmor) {
			material = new ItemStack(((ItemArmor) item).getArmorMaterial().func_151685_b());
		} else if (item instanceof GOTItemMountArmor) {
			material = new ItemStack(((GOTItemMountArmor) item).getMountArmorMaterial().func_151685_b());
		}
		if (material != null) {
			if (item.getIsRepairable(itemstack, material)) {
				return material;
			}
		} else {
			if (item == Items.bucket) {
				return new ItemStack(Items.iron_ingot);
			}
			if (item == GOTItems.silverRing) {
				return new ItemStack(GOTItems.silverNugget);
			}
			if (item == GOTItems.copperRing) {
				return new ItemStack(GOTItems.copperNugget);
			}
			if (item == GOTItems.bronzeRing) {
				return new ItemStack(GOTItems.bronzeNugget);
			}
			if (item == GOTItems.goldRing) {
				return new ItemStack(Items.gold_nugget);
			}
			if (item == GOTItems.valyrianRing) {
				return new ItemStack(GOTItems.valyrianNugget);
			}
			if (item == GOTItems.gobletGold) {
				return new ItemStack(Items.gold_ingot);
			}
			if (item == GOTItems.gobletSilver) {
				return new ItemStack(GOTItems.silverIngot);
			}
			if (item == GOTItems.gobletCopper) {
				return new ItemStack(GOTItems.copperIngot);
			}
		}
		return null;
	}

	public boolean canBeUnsmelted(ItemStack itemstack) {
		if (itemstack == null) {
			return false;
		}
		ItemStack material = getEquipmentMaterial(itemstack);
		return material != null && TileEntityFurnace.getItemBurnTime(material) == 0 && (!(itemstack.getItem() instanceof ItemBlock) || !Block.getBlockFromItem(itemstack.getItem()).getMaterial().getCanBurn()) && determineResourcesUsed(itemstack, material) > 0;
	}

	@Override
	public boolean canDoSmelting() {
		ItemStack input = inventory[inputSlots[0]];
		if (input == null) {
			return false;
		}
		ItemStack result = getLargestUnsmeltingResult(input);
		if (result == null) {
			return false;
		}
		ItemStack output = inventory[outputSlots[0]];
		if (output == null) {
			return true;
		}
		if (!output.isItemEqual(result)) {
			return false;
		}
		int resultSize = output.stackSize + result.stackSize;
		return resultSize <= getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
	}

	@Override
	public boolean canMachineInsertInput(ItemStack itemstack) {
		return itemstack != null && getLargestUnsmeltingResult(itemstack) != null;
	}

	public int countMatchingIngredients(ItemStack material, Iterable<?> ingredientList, List<IRecipe> recursiveCheckedRecipes) {
		int i = 0;
		for (Object obj : ingredientList) {
			if (obj instanceof ItemStack) {
				ItemStack ingredient = (ItemStack) obj;
				if (OreDictionary.itemMatches(material, ingredient, false)) {
					i++;
					continue;
				}
				int sub = determineResourcesUsed(ingredient, material, recursiveCheckedRecipes);
				if (sub > 0) {
					i += sub;
				}
				continue;
			}
			if (obj instanceof List) {
				Iterable<ItemStack> oreIngredients = (Iterable<ItemStack>) obj;
				boolean matched = false;
				for (ItemStack ingredient : oreIngredients) {
					if (OreDictionary.itemMatches(material, ingredient, false)) {
						matched = true;
						break;
					}
				}
				if (matched) {
					i++;
					continue;
				}
				for (ItemStack ingredient : oreIngredients) {
					int sub = determineResourcesUsed(ingredient, material, recursiveCheckedRecipes);
					if (sub > 0) {
						i += sub;
					}
				}
			}
		}
		return i;
	}

	public int determineResourcesUsed(ItemStack itemstack, ItemStack material) {
		return determineResourcesUsed(itemstack, material, null);
	}

	public int determineResourcesUsed(ItemStack itemstack, ItemStack material, List<IRecipe> recursiveCheckedRecipes) {
		List<IRecipe> recursiveCheckedRecipes1 = recursiveCheckedRecipes;
		if (itemstack == null) {
			return 0;
		}
		Pair<Item, Integer> key = Pair.of(itemstack.getItem(), itemstack.getItemDamage());
		if (unsmeltableCraftingCounts.containsKey(key)) {
			return unsmeltableCraftingCounts.get(key);
		}
		int count = 0;
		Collection<List<IRecipe>> allRecipeLists = new ArrayList<>();
		allRecipeLists.add(CraftingManager.getInstance().getRecipeList());
		EntityPlayer player = getProxyPlayer();
		for (GOTBlockCraftingTable table : GOTBlockCraftingTable.ALL_CRAFTING_TABLES) {
			Object container = GOT.proxy.getServerGuiElement(table.getGuiId().ordinal(), player, worldObj, 0, 0, 0);
			if (container instanceof GOTContainerCraftingTable) {
				GOTContainerCraftingTable containerTable = (GOTContainerCraftingTable) container;
				allRecipeLists.add(containerTable.getRecipeList());
			}
		}
		allRecipeLists.add(GOTRecipe.UNSMELT);
		if (recursiveCheckedRecipes1 == null) {
			recursiveCheckedRecipes1 = new ArrayList<>();
		}
		label63:
		for (List<IRecipe> recipes : allRecipeLists) {
			for (IRecipe recipesObj : recipes) {
				if (!recursiveCheckedRecipes1.contains(recipesObj)) {
					ItemStack result = recipesObj.getRecipeOutput();
					if (result != null && result.getItem() == itemstack.getItem() && (itemstack.isItemStackDamageable() || result.getItemDamage() == itemstack.getItemDamage())) {
						recursiveCheckedRecipes1.add(recipesObj);
						if (recipesObj instanceof ShapedRecipes) {
							ShapedRecipes shaped = (ShapedRecipes) recipesObj;
							ItemStack[] ingredients = shaped.recipeItems;
							int i = countMatchingIngredients(material, Arrays.asList(ingredients), recursiveCheckedRecipes1);
							i /= result.stackSize;
							if (i > 0) {
								count = i;
								break label63;
							}
						}
						if (recipesObj instanceof ShapelessRecipes) {
							ShapelessRecipes shapeless = (ShapelessRecipes) recipesObj;
							List<?> ingredients = shapeless.recipeItems;
							int i = countMatchingIngredients(material, ingredients, recursiveCheckedRecipes1);
							i /= result.stackSize;
							if (i > 0) {
								count = i;
								break label63;
							}
						}
						if (recipesObj instanceof ShapedOreRecipe) {
							ShapedOreRecipe shaped = (ShapedOreRecipe) recipesObj;
							Object[] ingredients = shaped.getInput();
							int i = countMatchingIngredients(material, Arrays.asList(ingredients), recursiveCheckedRecipes1);
							i /= result.stackSize;
							if (i > 0) {
								count = i;
								break label63;
							}
						}
						if (recipesObj instanceof ShapelessOreRecipe) {
							ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipesObj;
							List<Object> ingredients = shapeless.getInput();
							int i = countMatchingIngredients(material, ingredients, recursiveCheckedRecipes1);
							i /= result.stackSize;
							if (i > 0) {
								count = i;
								break label63;
							}
						}
					}
				}
			}
		}
		unsmeltableCraftingCounts.put(key, count);
		return count;
	}

	@Override
	public void doSmelt() {
		if (canDoSmelting()) {
			ItemStack input = inventory[inputSlots[0]];
			ItemStack result = getRandomUnsmeltingResult(input);
			if (result != null) {
				if (inventory[outputSlots[0]] == null) {
					inventory[outputSlots[0]] = result.copy();
				} else if (inventory[outputSlots[0]].isItemEqual(result)) {
					inventory[outputSlots[0]].stackSize += result.stackSize;
				}
			}
			inventory[inputSlots[0]].stackSize--;
			if (inventory[inputSlots[0]].stackSize <= 0) {
				inventory[inputSlots[0]] = null;
			}
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("Active", serverActive);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	@Override
	public int getForgeInvSize() {
		return 3;
	}

	@Override
	public String getForgeName() {
		return StatCollector.translateToLocal("got.container.unsmeltery");
	}

	public ItemStack getLargestUnsmeltingResult(ItemStack itemstack) {
		if (itemstack == null || !canBeUnsmelted(itemstack)) {
			return null;
		}
		ItemStack material = getEquipmentMaterial(itemstack);
		int items = determineResourcesUsed(itemstack, material);
		int meta = material.getItemDamage();
		if (meta == 32767) {
			meta = 0;
		}
		return new ItemStack(material.getItem(), items, meta);
	}

	public EntityPlayer getProxyPlayer() {
		if (!worldObj.isRemote) {
			return FakePlayerFactory.get((WorldServer) worldObj, new GameProfile(null, "GOTUnsmeltery"));
		}
		return GOT.proxy.getClientPlayer();
	}

	public ItemStack getRandomUnsmeltingResult(ItemStack itemstack) {
		ItemStack result = getLargestUnsmeltingResult(itemstack);
		if (result == null) {
			return null;
		}
		float items = result.stackSize;
		items *= 0.8f;
		if (itemstack.isItemStackDamageable()) {
			items *= (float) (itemstack.getMaxDamage() - itemstack.getItemDamage()) / itemstack.getMaxDamage();
		}
		int items_int = Math.round(items * MathHelper.randomFloatClamp(unsmeltingRand, 0.7f, 1.0f));
		if (items_int <= 0) {
			return null;
		}
		return new ItemStack(result.getItem(), items_int, result.getItemDamage());
	}

	public float getRockingAmount(float tick) {
		float mag = prevRocking + (rocking - prevRocking) * tick;
		float phase = prevRockingPhase + (rockingPhase - prevRockingPhase) * tick;
		return mag * MathHelper.sin(phase);
	}

	@Override
	public int getSmeltingDuration() {
		return 400;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		clientActive = data.getBoolean("Active");
	}

	@Override
	public void setupForgeSlots() {
		inputSlots = new int[]{0};
		fuelSlot = 1;
		outputSlots = new int[]{2};
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote) {
			prevRocking = rocking;
			prevRockingPhase = rockingPhase;
			rockingPhase += 0.1F;
			if (clientActive) {
				rocking += 0.05F;
			} else {
				rocking -= 0.01F;
			}
			rocking = MathHelper.clamp_float(rocking, 0.0F, 1.0F);
		} else {
			prevServerActive = serverActive;
			serverActive = isSmelting();
			if (serverActive != prevServerActive) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}
}