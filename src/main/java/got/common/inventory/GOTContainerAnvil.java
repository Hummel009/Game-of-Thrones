package got.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.database.GOTTradeEntries;
import got.common.enchant.GOTEnchantment;
import got.common.enchant.GOTEnchantmentCombining;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.essos.qohor.GOTEntityQohorBlacksmith;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTTradeEntry;
import got.common.entity.other.GOTTradeable;
import got.common.entity.westeros.GOTEntityLightSkinScrapTrader;
import got.common.item.AnvilNameColorProvider;
import got.common.item.other.*;
import got.common.item.weapon.GOTItemSarbacane;
import got.common.item.weapon.GOTItemThrowingAxe;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class GOTContainerAnvil extends Container {
	private final EntityPlayer thePlayer;
	private final World theWorld;
	private final IInventory invOutput;
	private final IInventory invInput;
	private final boolean isTrader;

	private GOTEntityNPC theNPC;
	private int materialCost;
	private int reforgeCost;
	private int engraveOwnerCost;
	private int clientReforgeTime;
	private boolean isSmithScrollCombine;

	private int xCoord;
	private int yCoord;
	private int zCoord;
	private GOTTradeable theTrader;
	private String repairedItemName;
	private long lastReforgeTime = -1L;
	private boolean doneMischief;

	private GOTContainerAnvil(EntityPlayer entityplayer, boolean trader) {
		thePlayer = entityplayer;
		theWorld = entityplayer.worldObj;
		isTrader = trader;
		invOutput = new InventoryCraftResult();
		invInput = new InventoryBasic("Repair", true, isTrader ? 2 : 3) {

			@Override
			public void markDirty() {
				super.markDirty();
				onCraftMatrixChanged(this);
			}
		};
		addSlotToContainer(new Slot(invInput, 0, 27, 58));
		addSlotToContainer(new Slot(invInput, 1, 76, 47));
		if (!isTrader) {
			addSlotToContainer(new Slot(invInput, 2, 76, 70));
		}
		addSlotToContainer(new GOTSlotAnvilOutput(this, invOutput, 0, 134, 58));
		for (int j1 = 0; j1 < 3; ++j1) {
			for (int i1 = 0; i1 < 9; ++i1) {
				addSlotToContainer(new Slot(entityplayer.inventory, i1 + j1 * 9 + 9, 8 + i1 * 18, 116 + j1 * 18));
			}
		}
		for (int i1 = 0; i1 < 9; ++i1) {
			addSlotToContainer(new Slot(entityplayer.inventory, i1, 8 + i1 * 18, 174));
		}
	}

	public GOTContainerAnvil(EntityPlayer entityplayer, GOTEntityNPC npc) {
		this(entityplayer, true);
		theNPC = npc;
		theTrader = (GOTTradeable) npc;
	}

	public GOTContainerAnvil(EntityPlayer entityplayer, int i, int j, int k) {
		this(entityplayer, false);
		xCoord = i;
		yCoord = j;
		zCoord = k;
	}

	private static String applyFormattingCodes(String name, Iterable<EnumChatFormatting> colors) {
		StringBuilder nameBuilder = new StringBuilder(name);
		for (EnumChatFormatting color : colors) {
			nameBuilder.insert(0, color);
		}
		return nameBuilder.toString();
	}

	private static boolean costsToRename(ItemStack itemstack) {
		Item item = itemstack.getItem();
		return item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemArmor && ((ItemArmor) item).damageReduceAmount > 0 || item instanceof ItemBow || item instanceof GOTItemThrowingAxe || item instanceof GOTItemSarbacane;
	}

	private static List<EnumChatFormatting> getAppliedFormattingCodes(String name) {
		List<EnumChatFormatting> colors = new ArrayList<>();
		for (EnumChatFormatting color : EnumChatFormatting.values()) {
			String formatCode = color.toString();
			if (!name.startsWith(formatCode)) {
				continue;
			}
			colors.add(color);
		}
		return colors;
	}

	public static String stripFormattingCodes(String name) {
		String name1 = name;
		for (EnumChatFormatting color : EnumChatFormatting.values()) {
			String formatCode = color.toString();
			if (!name1.startsWith(formatCode)) {
				continue;
			}
			name1 = name1.substring(formatCode.length());
		}
		return name1;
	}

	private boolean applyMischief(ItemStack itemstack) {
		boolean changed = false;
		Random rand = theWorld.rand;
		if (rand.nextFloat() < 0.8f) {
			String name = itemstack.getDisplayName();
			if ((name = OddmentCollectorNameMischief.garbleName(name, rand)).equals(itemstack.getItem().getItemStackDisplayName(itemstack))) {
				itemstack.func_135074_t();
			} else {
				itemstack.setStackDisplayName(name);
			}
			changed = true;
		}
		if (rand.nextFloat() < 0.2f) {
			GOTEnchantmentHelper.applyRandomEnchantments(itemstack, rand, false, true);
			return true;
		}
		return changed;
	}

	public boolean canEngraveNewOwner(ItemStack itemstack, ICommandSender entityplayer) {
		String currentOwner = GOTItemOwnership.getCurrentOwner(itemstack);
		return currentOwner == null || !currentOwner.equals(entityplayer.getCommandSenderName());
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (isTrader) {
			return theNPC != null && entityplayer.getDistanceToEntity(theNPC) <= 12.0 && theNPC.isEntityAlive() && theNPC.getAttackTarget() == null && theTrader.canTradeWith(entityplayer);
		}
		return theWorld.getBlock(xCoord, yCoord, zCoord) == Blocks.anvil && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : crafters) {
			ICrafting crafting = (ICrafting) crafter;
			crafting.sendProgressBarUpdate(this, 0, materialCost);
			crafting.sendProgressBarUpdate(this, 1, reforgeCost);
			crafting.sendProgressBarUpdate(this, 3, engraveOwnerCost);
		}
	}

	public void engraveOwnership() {
		ItemStack inputItem = invInput.getStackInSlot(0);
		if (inputItem != null && engraveOwnerCost > 0 && hasMaterialOrCoinAmount(engraveOwnerCost)) {
			int cost = engraveOwnerCost;
			GOTItemOwnership.setCurrentOwner(inputItem, thePlayer.getCommandSenderName());
			if (isTrader && theNPC instanceof GOTEntityLightSkinScrapTrader && applyMischief(inputItem)) {
				doneMischief = true;
			}
			invInput.setInventorySlotContents(0, inputItem);
			takeMaterialOrCoinAmount(cost);
			playAnvilSound();
			GOTLevelData.getData(thePlayer).addAchievement(GOTAchievement.engraveOwnership);
		}
	}

	public List<EnumChatFormatting> getActiveItemNameFormatting() {
		ItemStack inputItem = invInput.getStackInSlot(0);
		ItemStack resultItem = invOutput.getStackInSlot(0);
		if (resultItem != null) {
			return getAppliedFormattingCodes(resultItem.getDisplayName());
		}
		if (inputItem != null) {
			return getAppliedFormattingCodes(inputItem.getDisplayName());
		}
		return new ArrayList<>();
	}

	private float getTraderMaterialPrice(ItemStack inputItem) {
		float materialPrice = 0.0f;
		GOTTradeEntry[] sellTrades = theNPC.traderNPCInfo.getSellTrades();
		if (sellTrades != null) {
			for (GOTTradeEntry trade : sellTrades) {
				ItemStack tradeItem = trade.createTradeItem();
				if (!isRepairMaterial(inputItem, tradeItem)) {
					continue;
				}
				materialPrice = (float) trade.getCost() / trade.createTradeItem().stackSize;
				break;
			}
		}
		if (materialPrice <= 0.0f) {
			GOTTradeEntries sellPool = theTrader.getSellPool();
			for (GOTTradeEntry trade : sellPool.getTradeEntries()) {
				ItemStack tradeItem = trade.createTradeItem();
				if (!isRepairMaterial(inputItem, tradeItem)) {
					continue;
				}
				materialPrice = (float) trade.getCost() / trade.createTradeItem().stackSize;
				break;
			}
		}
		if (materialPrice <= 0.0f && isRepairMaterial(inputItem, new ItemStack(GOTItems.valyrianIngot)) && theTrader instanceof GOTEntityQohorBlacksmith) {
			return 200.0f;
		}
		return materialPrice;
	}

	public boolean hasMaterialOrCoinAmount(int cost) {
		if (isTrader) {
			return GOTItemCoin.getInventoryValue(thePlayer, false) >= cost;
		}
		ItemStack inputItem = invInput.getStackInSlot(0);
		ItemStack materialItem = invInput.getStackInSlot(2);
		return materialItem != null && isRepairMaterial(inputItem, materialItem) && materialItem.stackSize >= cost;
	}

	private boolean isRepairMaterial(ItemStack inputItem, ItemStack materialItem) {
		if (inputItem.getItem().getIsRepairable(inputItem, materialItem)) {
			return true;
		}
		Item item = inputItem.getItem();
		if (item == Items.bow && materialItem.getItem() == Items.string || item instanceof ItemFishingRod && materialItem.getItem() == Items.string) {
			return true;
		}
		if (item instanceof ItemShears && materialItem.getItem() == Items.iron_ingot || item instanceof GOTItemChisel && materialItem.getItem() == Items.iron_ingot) {
			return true;
		}
		if (item instanceof ItemEnchantedBook && materialItem.getItem() == Items.paper) {
			return true;
		}
		Item.ToolMaterial material = null;
		if (item instanceof ItemTool) {
			material = Item.ToolMaterial.valueOf(((ItemTool) item).getToolMaterialName());
		} else if (item instanceof ItemSword) {
			material = Item.ToolMaterial.valueOf(((ItemSword) item).getToolMaterialName());
		}
		if (material == Item.ToolMaterial.WOOD) {
			return GOT.isOreNameEqual(materialItem, "plankWood");
		}
		return item instanceof ItemArmor && ((ItemArmor) item).getArmorMaterial() == GOTMaterial.BONE && GOT.isOreNameEqual(materialItem, "bone");
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!theWorld.isRemote) {
			for (int i = 0; i < invInput.getSizeInventory(); ++i) {
				ItemStack itemstack = invInput.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
			if (doneMischief && isTrader && theNPC instanceof GOTEntityLightSkinScrapTrader) {
				theNPC.sendSpeechBank(entityplayer, ((GOTEntityLightSkinScrapTrader) theNPC).getSmithSpeechBank());
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		super.onCraftMatrixChanged(inv);
		if (inv == invInput) {
			updateRepairOutput();
		}
	}

	public void playAnvilSound() {
		if (!theWorld.isRemote) {
			int i;
			int j;
			int k;
			if (isTrader) {
				i = MathHelper.floor_double(theNPC.posX);
				j = MathHelper.floor_double(theNPC.posY);
				k = MathHelper.floor_double(theNPC.posZ);
			} else {
				i = xCoord;
				j = yCoord;
				k = zCoord;
			}
			theWorld.playAuxSFX(1021, i, j, k, 0);
		}
	}

	public void reforgeItem() {
		ItemStack inputItem;
		long curTime = System.currentTimeMillis();
		if ((lastReforgeTime < 0L || curTime - lastReforgeTime >= 2000L) && (inputItem = invInput.getStackInSlot(0)) != null && reforgeCost > 0 && hasMaterialOrCoinAmount(reforgeCost)) {
			int cost = reforgeCost;
			if (inputItem.isItemStackDamageable()) {
				inputItem.setItemDamage(0);
			}
			GOTEnchantmentHelper.applyRandomEnchantments(inputItem, theWorld.rand, true, true);
			GOTEnchantmentHelper.setAnvilCost(inputItem, 0);
			if (isTrader && theNPC instanceof GOTEntityLightSkinScrapTrader && applyMischief(inputItem)) {
				doneMischief = true;
			}
			invInput.setInventorySlotContents(0, inputItem);
			takeMaterialOrCoinAmount(cost);
			playAnvilSound();
			lastReforgeTime = curTime;
			((ICrafting) thePlayer).sendProgressBarUpdate(this, 2, 0);
			if (!isTrader) {
				GOTLevelData.getData(thePlayer).addAchievement(GOTAchievement.reforge);
			}
		}
	}

	@Override
	public ItemStack slotClick(int slotNo, int j, int k, EntityPlayer entityplayer) {
		ItemStack resultCopy;
		ItemStack resultItem = invOutput.getStackInSlot(0);
		resultItem = ItemStack.copyItemStack(resultItem);
		boolean changed = false;
		if (resultItem != null && slotNo == getSlotFromInventory(invOutput, 0).slotNumber && !theWorld.isRemote && isTrader && theNPC instanceof GOTEntityLightSkinScrapTrader && (changed = applyMischief(resultCopy = resultItem.copy()))) {
			invOutput.setInventorySlotContents(0, resultCopy);
		}
		ItemStack slotClickResult = super.slotClick(slotNo, j, k, entityplayer);
		if (changed) {
			doneMischief = true;
			if (invOutput.getStackInSlot(0) != null) {
				invOutput.setInventorySlotContents(0, resultItem.copy());
			}
		}
		return slotClickResult;
	}

	public void takeMaterialOrCoinAmount(int cost) {
		if (isTrader) {
			if (!theWorld.isRemote) {
				GOTItemCoin.takeCoins(cost, thePlayer);
				detectAndSendChanges();
				theNPC.playTradeSound();
			}
		} else {
			ItemStack materialItem = invInput.getStackInSlot(2);
			if (materialItem != null) {
				materialItem.stackSize -= cost;
				if (materialItem.stackSize <= 0) {
					invInput.setInventorySlotContents(2, null);
				} else {
					invInput.setInventorySlotContents(2, materialItem);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int inputSize = invInput.getSizeInventory();
			if (i == inputSize) {
				if (!mergeItemStack(itemstack1, inputSize + 1, inputSize + 37, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (i >= inputSize + 1 ? i >= inputSize + 1 && i < inputSize + 37 && !mergeItemStack(itemstack1, 0, inputSize, false) : !mergeItemStack(itemstack1, inputSize + 1, inputSize + 37, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(entityplayer, itemstack1);
		}
		return itemstack;
	}

	public void updateItemName(String name) {
		List<EnumChatFormatting> colors = getAppliedFormattingCodes(name);
		String name1 = stripFormattingCodes(name);
		repairedItemName = ChatAllowedCharacters.filerAllowedCharacters(name1);
		ItemStack itemstack = invOutput.getStackInSlot(0);
		if (itemstack != null) {
			if (StringUtils.isBlank(repairedItemName)) {
				itemstack.func_135074_t();
			} else {
				itemstack.setStackDisplayName(repairedItemName);
			}
			if (!colors.isEmpty()) {
				itemstack.setStackDisplayName(applyFormattingCodes(itemstack.getDisplayName(), colors));
			}
		}
		updateRepairOutput();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			materialCost = j;
		}
		if (i == 1) {
			reforgeCost = j;
		}
		if (i == 2) {
			clientReforgeTime = 40;
		}
		if (i == 3) {
			engraveOwnerCost = j;
		}
	}

	private void updateRepairOutput() {
		ItemStack inputItem = invInput.getStackInSlot(0);
		materialCost = 0;
		reforgeCost = 0;
		engraveOwnerCost = 0;
		isSmithScrollCombine = false;
		int baseAnvilCost;
		int repairCost = 0;
		int combineCost = 0;
		int renameCost = 0;
		if (inputItem == null) {
			invOutput.setInventorySlotContents(0, null);
			materialCost = 0;
		} else {
			int oneItemRepair;
			GOTEnchantmentCombining.CombineRecipe scrollCombine;
			ItemStack inputCopy = inputItem.copy();
			ItemStack combinerItem = invInput.getStackInSlot(1);
			ItemStack materialItem = isTrader ? null : invInput.getStackInSlot(2);
			Map<Integer, Integer> inputEnchants = EnchantmentHelper.getEnchantments(inputCopy);
			boolean enchantingWithBook = false;
			List<GOTEnchantment> inputModifiers = GOTEnchantmentHelper.getEnchantList(inputCopy);
			baseAnvilCost = GOTEnchantmentHelper.getAnvilCost(inputItem) + (combinerItem == null ? 0 : GOTEnchantmentHelper.getAnvilCost(combinerItem));
			materialCost = 0;
			String previousDisplayName = inputCopy.getDisplayName();
			String defaultItemName = inputCopy.getItem().getItemStackDisplayName(inputCopy);
			String formattedNameToApply = repairedItemName;
			Collection<EnumChatFormatting> colorsToApply = new ArrayList<>(getAppliedFormattingCodes(inputCopy.getDisplayName()));
			boolean alteringNameColor = false;
			if (costsToRename(inputItem) && combinerItem != null) {
				if (combinerItem.getItem() instanceof AnvilNameColorProvider) {
					AnvilNameColorProvider nameColorProvider = (AnvilNameColorProvider) combinerItem.getItem();
					EnumChatFormatting newColor = nameColorProvider.getAnvilNameColor();
					boolean isDifferentColor = !colorsToApply.contains(newColor);
					if (isDifferentColor) {
						for (EnumChatFormatting ecf : EnumChatFormatting.values()) {
							if (!ecf.isColor()) {
								continue;
							}
							while (colorsToApply.contains(ecf)) {
								colorsToApply.remove(ecf);
							}
						}
						colorsToApply.add(newColor);
						alteringNameColor = true;
					}
				} else if (combinerItem.getItem() == Items.flint && !colorsToApply.isEmpty()) {
					colorsToApply.clear();
					alteringNameColor = true;
				}
				if (alteringNameColor) {
					++renameCost;
				}
			}
			if (!colorsToApply.isEmpty()) {
				if (StringUtils.isBlank(formattedNameToApply)) {
					formattedNameToApply = defaultItemName;
				}
				formattedNameToApply = applyFormattingCodes(formattedNameToApply, colorsToApply);
			}
			boolean nameChange = false;
			if (formattedNameToApply != null && !formattedNameToApply.equals(previousDisplayName)) {
				if (StringUtils.isBlank(formattedNameToApply) || formattedNameToApply.equals(defaultItemName)) {
					if (inputCopy.hasDisplayName()) {
						inputCopy.func_135074_t();
						if (!stripFormattingCodes(previousDisplayName).equals(stripFormattingCodes(formattedNameToApply))) {
							nameChange = true;
						}
					}
				} else {
					inputCopy.setStackDisplayName(formattedNameToApply);
					if (!stripFormattingCodes(previousDisplayName).equals(stripFormattingCodes(formattedNameToApply))) {
						nameChange = true;
					}
				}
			}
			if (nameChange && costsToRename(inputItem)) {
				++renameCost;
			}
			if (isTrader && (scrollCombine = GOTEnchantmentCombining.getCombinationResult(inputItem, combinerItem)) != null) {
				invOutput.setInventorySlotContents(0, scrollCombine.createOutputItem());
				materialCost = scrollCombine.getCost();
				reforgeCost = 0;
				engraveOwnerCost = 0;
				isSmithScrollCombine = true;
				return;
			}
			boolean combining = false;
			if (combinerItem != null) {
				enchantingWithBook = combinerItem.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(combinerItem).tagCount() > 0;
				if (enchantingWithBook && !GOTConfig.enchantingVanilla) {
					invOutput.setInventorySlotContents(0, null);
					materialCost = 0;
					return;
				}
				GOTEnchantment combinerItemEnchant = null;
				if (combinerItem.getItem() instanceof GOTItemEnchantment) {
					combinerItemEnchant = ((GOTItemEnchantment) combinerItem.getItem()).getTheEnchant();
				} else if (combinerItem.getItem() instanceof GOTItemModifierTemplate) {
					combinerItemEnchant = GOTItemModifierTemplate.getModifier(combinerItem);
				}
				if (!enchantingWithBook && combinerItemEnchant == null) {
					if (inputCopy.isItemStackDamageable() && inputCopy.getItem() == combinerItem.getItem()) {
						int inputUseLeft = inputItem.getMaxDamage() - inputItem.getItemDamageForDisplay();
						int combinerUseLeft = combinerItem.getMaxDamage() - combinerItem.getItemDamageForDisplay();
						int restoredUses = combinerUseLeft + inputCopy.getMaxDamage() * 12 / 100;
						int newUsesLeft = inputUseLeft + restoredUses;
						int newDamage = inputCopy.getMaxDamage() - newUsesLeft;
						newDamage = Math.max(newDamage, 0);
						if (newDamage < inputCopy.getItemDamage()) {
							inputCopy.setItemDamage(newDamage);
							int restoredUses1 = inputCopy.getMaxDamage() - inputUseLeft;
							int restoredUses2 = inputCopy.getMaxDamage() - combinerUseLeft;
							combineCost += Math.max(0, Math.min(restoredUses1, restoredUses2) / 100);
						}
						combining = true;
					} else if (!alteringNameColor) {
						invOutput.setInventorySlotContents(0, null);
						materialCost = 0;
						return;
					}
				}
				HashMap<Integer, Integer> outputEnchants = new HashMap<>(inputEnchants);
				if (GOTConfig.enchantingVanilla) {
					Map<Integer, Integer> combinerEnchants = EnchantmentHelper.getEnchantments(combinerItem);
					for (Integer obj : combinerEnchants.keySet()) {
						int combinerEnchID = obj;
						Enchantment combinerEnch = Enchantment.enchantmentsList[combinerEnchID];
						int inputEnchLevel = 0;
						if (outputEnchants.containsKey(combinerEnchID)) {
							inputEnchLevel = outputEnchants.get(combinerEnchID);
						}
						int combinedEnchLevel;
						int combinerEnchLevel = combinerEnchants.get(combinerEnchID);
						if (inputEnchLevel == combinerEnchLevel) {
							++combinerEnchLevel;
							combinedEnchLevel = combinerEnchLevel;
						} else {
							combinedEnchLevel = Math.max(combinerEnchLevel, inputEnchLevel);
						}
						combinerEnchLevel = combinedEnchLevel;
						int levelsAdded = combinerEnchLevel - inputEnchLevel;
						boolean canApply = combinerEnch.canApply(inputItem);
						if (thePlayer.capabilities.isCreativeMode || inputItem.getItem() == Items.enchanted_book) {
							canApply = true;
						}
						for (Integer objIn : outputEnchants.keySet()) {
							int inputEnchID = objIn;
							Enchantment inputEnch = Enchantment.enchantmentsList[inputEnchID];
							if (inputEnchID == combinerEnchID || combinerEnch.canApplyTogether(inputEnch) && inputEnch.canApplyTogether(combinerEnch)) {
								continue;
							}
							canApply = false;
							combineCost += levelsAdded;
						}
						if (!canApply) {
							continue;
						}
						combinerEnchLevel = Math.min(combinerEnchLevel, combinerEnch.getMaxLevel());
						outputEnchants.put(combinerEnchID, combinerEnchLevel);
						int costPerLevel = 0;
						int enchWeight = combinerEnch.getWeight();
						switch (enchWeight) {
							case 1:
								costPerLevel = 8;
								break;
							case 2:
								costPerLevel = 4;
								break;
							case 5:
								costPerLevel = 2;
								break;
							case 10:
								costPerLevel = 1;
								break;
							default:
								break;
						}
						combineCost += costPerLevel * levelsAdded;
					}
				} else {
					outputEnchants.clear();
				}
				EnchantmentHelper.setEnchantments(outputEnchants, inputCopy);
				int maxMods = 3;
				Collection<GOTEnchantment> outputMods = new ArrayList<>(inputModifiers);
				List<GOTEnchantment> combinerMods = GOTEnchantmentHelper.getEnchantList(combinerItem);
				if (combinerItemEnchant != null) {
					combinerMods.add(combinerItemEnchant);
				}
				for (GOTEnchantment combinerMod : combinerMods) {
					boolean canApply = combinerMod.canApply(inputItem, false);
					if (canApply) {
						for (GOTEnchantment mod : outputMods) {
							if (mod.isCompatibleWith(combinerMod) && combinerMod.isCompatibleWith(mod)) {
								continue;
							}
							canApply = false;
						}
					}
					int numOutputMods = 0;
					for (GOTEnchantment mod : outputMods) {
						if (mod.getBypassAnvilLimit()) {
							continue;
						}
						++numOutputMods;
					}
					if (!combinerMod.getBypassAnvilLimit() && numOutputMods >= maxMods) {
						canApply = false;
					}
					if (!canApply) {
						continue;
					}
					outputMods.add(combinerMod);
					if (!combinerMod.isBeneficial()) {
						continue;
					}
					combineCost += Math.max(1, (int) combinerMod.getValueModifier());
				}
				GOTEnchantmentHelper.setEnchantList(inputCopy, outputMods);
			}
			if (combineCost > 0) {
				combining = true;
			}
			int numEnchants = 0;
			for (Integer obj : inputEnchants.keySet()) {
				int enchID = obj;
				Enchantment ench = Enchantment.enchantmentsList[enchID];
				int enchLevel = inputEnchants.get(enchID);
				++numEnchants;
				int costPerLevel = 0;
				int enchWeight = ench.getWeight();
				switch (enchWeight) {
					case 1:
						costPerLevel = 8;
						break;
					case 2:
						costPerLevel = 4;
						break;
					case 5:
						costPerLevel = 2;
						break;
					case 10:
						costPerLevel = 1;
						break;
					default:
						break;
				}
				baseAnvilCost += numEnchants + enchLevel * costPerLevel;
			}
			if (enchantingWithBook && !inputCopy.getItem().isBookEnchantable(inputCopy, combinerItem)) {
				inputCopy = null;
			}
			for (GOTEnchantment mod : inputModifiers) {
				if (!mod.isBeneficial()) {
					continue;
				}
				baseAnvilCost += Math.max(1, (int) mod.getValueModifier());
			}
			if (inputCopy.isItemStackDamageable()) {
				boolean canRepair;
				int availableMaterials = 0;
				if (isTrader) {
					canRepair = getTraderMaterialPrice(inputItem) > 0.0f;
					availableMaterials = Integer.MAX_VALUE;
				} else {
					canRepair = materialItem != null && isRepairMaterial(inputItem, materialItem);
					if (materialItem != null) {
						availableMaterials = materialItem.stackSize - combineCost - renameCost;
					}
				}
				oneItemRepair = Math.min(inputCopy.getItemDamageForDisplay(), inputCopy.getMaxDamage() / 4);
				if (canRepair && availableMaterials > 0 && oneItemRepair > 0) {
					availableMaterials -= baseAnvilCost;
					if (availableMaterials > 0) {
						int usedMaterials;
						for (usedMaterials = 0; oneItemRepair > 0 && usedMaterials < availableMaterials; ++usedMaterials) {
							int newDamage = inputCopy.getItemDamageForDisplay() - oneItemRepair;
							inputCopy.setItemDamage(newDamage);
							oneItemRepair = Math.min(inputCopy.getItemDamageForDisplay(), inputCopy.getMaxDamage() / 4);
						}
						repairCost += usedMaterials;
					} else if (!nameChange && !combining) {
						repairCost = 1;
						int newDamage = inputCopy.getItemDamageForDisplay() - oneItemRepair;
						inputCopy.setItemDamage(newDamage);
					}
				}
			}
			boolean repairing = repairCost > 0;
			if (combining || repairing) {
				materialCost = baseAnvilCost;
				materialCost += combineCost + repairCost;
			} else {
				materialCost = 0;
			}
			materialCost += renameCost;
			int nextAnvilCost = GOTEnchantmentHelper.getAnvilCost(inputItem);
			if (combinerItem != null) {
				int combinerAnvilCost = GOTEnchantmentHelper.getAnvilCost(combinerItem);
				nextAnvilCost = Math.max(nextAnvilCost, combinerAnvilCost);
			}
			if (combining) {
				nextAnvilCost += 2;
			} else if (repairing) {
				++nextAnvilCost;
			}
			nextAnvilCost = Math.max(nextAnvilCost, 0);
			if (nextAnvilCost > 0) {
				GOTEnchantmentHelper.setAnvilCost(inputCopy, nextAnvilCost);
			}
			if (GOTEnchantmentHelper.isReforgeable(inputItem)) {
				ItemStack reforgeCopy;
				reforgeCost = 2;
				if (inputItem.getItem() instanceof ItemArmor) {
					reforgeCost = 3;
				}
				if (inputItem.isItemStackDamageable() && (oneItemRepair = Math.min((reforgeCopy = inputItem.copy()).getItemDamageForDisplay(), reforgeCopy.getMaxDamage() / 4)) > 0) {
					int usedMaterials = 0;
					while (oneItemRepair > 0) {
						int newDamage = reforgeCopy.getItemDamageForDisplay() - oneItemRepair;
						reforgeCopy.setItemDamage(newDamage);
						oneItemRepair = Math.min(reforgeCopy.getItemDamageForDisplay(), reforgeCopy.getMaxDamage() / 4);
						++usedMaterials;
					}
					reforgeCost += usedMaterials;
				}
				engraveOwnerCost = 2;
			} else {
				reforgeCost = 0;
				engraveOwnerCost = 0;
			}
			if (isRepairMaterial(inputItem, new ItemStack(Items.string))) {
				int stringFactor = 3;
				materialCost *= stringFactor;
				reforgeCost *= stringFactor;
				engraveOwnerCost *= stringFactor;
			}
			if (isTrader) {
				boolean isCommonRenameOnly = nameChange && materialCost == 0;
				float materialPrice = getTraderMaterialPrice(inputItem);
				if (materialPrice > 0.0f) {
					materialCost = Math.round(materialCost * materialPrice);
					materialCost = Math.max(materialCost, 1);
					reforgeCost = Math.round(reforgeCost * materialPrice);
					reforgeCost = Math.max(reforgeCost, 1);
					engraveOwnerCost = Math.round(engraveOwnerCost * materialPrice);
					engraveOwnerCost = Math.max(engraveOwnerCost, 1);
					if (theTrader instanceof GOTEntityLightSkinScrapTrader) {
						materialCost = MathHelper.ceiling_float_int(materialCost * 0.5f);
						materialCost = Math.max(materialCost, 1);
						reforgeCost = MathHelper.ceiling_float_int(reforgeCost * 0.5f);
						reforgeCost = Math.max(reforgeCost, 1);
						engraveOwnerCost = MathHelper.ceiling_float_int(engraveOwnerCost * 0.5f);
						engraveOwnerCost = Math.max(engraveOwnerCost, 1);
					}
				} else if (!isCommonRenameOnly) {
					invOutput.setInventorySlotContents(0, null);
					materialCost = 0;
					reforgeCost = 0;
					engraveOwnerCost = 0;
					return;
				}
			}
			if (combining || repairing || nameChange || alteringNameColor) {
				invOutput.setInventorySlotContents(0, inputCopy);
			} else {
				invOutput.setInventorySlotContents(0, null);
				materialCost = 0;
			}
			detectAndSendChanges();
		}
	}

	public IInventory getInvOutput() {
		return invOutput;
	}

	public IInventory getInvInput() {
		return invInput;
	}

	public boolean isTrader() {
		return isTrader;
	}

	public GOTEntityNPC getTheNPC() {
		return theNPC;
	}

	public int getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(int materialCost) {
		this.materialCost = materialCost;
	}

	public int getReforgeCost() {
		return reforgeCost;
	}

	public int getEngraveOwnerCost() {
		return engraveOwnerCost;
	}

	public int getClientReforgeTime() {
		return clientReforgeTime;
	}

	public void setClientReforgeTime(int clientReforgeTime) {
		this.clientReforgeTime = clientReforgeTime;
	}

	public boolean isSmithScrollCombine() {
		return isSmithScrollCombine;
	}

	public void setSmithScrollCombine(boolean smithScrollCombine) {
		isSmithScrollCombine = smithScrollCombine;
	}
}