package got.common;

import cpw.mods.fml.common.network.IGuiHandler;
import got.GOT;
import got.client.gui.*;
import got.common.database.GOTBlocks;
import got.common.database.GOTGuiId;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.*;
import got.common.entity.other.info.GOTHireableInfo;
import got.common.inventory.*;
import got.common.item.other.GOTItemCracker;
import got.common.item.other.GOTItemPouch;
import got.common.tileentity.*;
import got.common.util.GOTReflection;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.world.World;

public class GOTGuiHandler implements IGuiHandler {
	public static final GOTGuiHandler INSTANCE = new GOTGuiHandler();

	private GOTGuiHandler() {
	}

	private static boolean testForSlotPackedGuiID(int fullID, int guiID) {
		return (fullID & 0xFFFF) == guiID;
	}

	private static int unpackSlot(int fullID) {
		return fullID >> 16;
	}

	public static int packGuiIDWithSlot(int guiID, int slotNo) {
		return guiID | slotNo << 16;
	}

	@Override
	@SuppressWarnings("CastConflictsWithInstanceof")
	public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		GOTEntityNPC npc;
		Entity entity;
		if (testForSlotPackedGuiID(ID, GOTGuiId.POUCH_CHEST.ordinal())) {
			int slot = unpackSlot(ID);
			IInventory chest2 = GOTItemPouch.getChestInvAt(entityplayer, world, i, j, k);
			if (chest2 != null) {
				return new GOTGuiChestWithPouch(entityplayer, slot, chest2);
			}
		}
		if (testForSlotPackedGuiID(ID, GOTGuiId.POUCH_MINECART.ordinal())) {
			int slot = unpackSlot(ID);
			Entity minecart = world.getEntityByID(i);
			if (minecart instanceof EntityMinecartContainer) {
				return new GOTGuiChestWithPouch(entityplayer, slot, (IInventory) minecart);
			}
		}
		GOTGuiId id = GOTGuiId.values()[ID];
		switch (id) {
			case ALLOY_FORGE:
				TileEntity forge = world.getTileEntity(i, j, k);
				if (forge instanceof GOTTileEntityAlloyForge) {
					return new GOTGuiAlloyForge(entityplayer.inventory, (GOTTileEntityAlloyForge) forge);
				}
				break;
			case ANVIL:
				return new GOTGuiAnvil(entityplayer, i, j, k);
			case ANVIL_NPC:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					return new GOTGuiAnvil(entityplayer, (GOTEntityNPC) entity);
				}
				break;
			case ARMOR_STAND:
				TileEntity stand = world.getTileEntity(i, j, k);
				if (stand instanceof GOTTileEntityArmorStand) {
					return new GOTGuiArmorStand(entityplayer.inventory, (GOTTileEntityArmorStand) stand);
				}
				break;
			case BARREL:
				TileEntity barrel = world.getTileEntity(i, j, k);
				if (barrel instanceof GOTTileEntityBarrel) {
					return new GOTGuiBarrel(entityplayer.inventory, (GOTTileEntityBarrel) barrel);
				}
				break;
			case BOOKSHELF:
				if (world.getBlock(i, j, k) == Blocks.bookshelf) {
					world.setBlock(i, j, k, GOTBlocks.bookshelfStorage, 0, 3);
				}
				TileEntity bookshelf = world.getTileEntity(i, j, k);
				if (bookshelf instanceof GOTTileEntityBookshelf) {
					return new GOTGuiBookshelf(entityplayer.inventory, (GOTTileEntityBookshelf) bookshelf);
				}
				break;
			case BRANDING_IRON:
				return new GOTGuiBrandingIron();
			case CHEST:
				TileEntity chest = world.getTileEntity(i, j, k);
				if (chest instanceof GOTTileEntityChest) {
					return new GuiChest(entityplayer.inventory, (IInventory) chest);
				}
				break;
			case COIN_EXCHANGE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					return new GOTGuiCoinExchange(entityplayer, (GOTEntityNPC) entity);
				}
				break;
			case CONQUEST:
				return new GOTGuiMap().setConquestGrid();
			case CRACKER:
				if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof GOTItemCracker) {
					return new GOTGuiCracker(entityplayer);
				}
				break;
			case DISPENSER:
				TileEntity trap = world.getTileEntity(i, j, k);
				if (trap instanceof GOTTileEntitySarbacaneTrap) {
					return new GuiDispenser(entityplayer.inventory, (TileEntityDispenser) trap);
				}
				break;
			case EDIT_SIGN:
				Block block = world.getBlock(i, j, k);
				int meta = world.getBlockMetadata(i, j, k);
				GOTTileEntitySignCarved fake = (GOTTileEntitySignCarved) block.createTileEntity(world, meta);
				fake.setWorldObj(world);
				fake.xCoord = i;
				fake.yCoord = j;
				fake.zCoord = k;
				fake.setFakeGuiSign(true);
				return new GOTGuiEditSign(fake);
			case HIRED_FARMER_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer().equals(entityplayer) && npc.getHireableInfo().getHiredTask() == GOTHireableInfo.Task.FARMER) {
						return new GOTGuiHiredFarmerInventory(entityplayer.inventory, npc);
					}
				}
				break;
			case HIRED_INTERACT:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					return new GOTGuiHiredInteract((GOTEntityNPC) entity);
				}
				break;
			case HIRED_INTERACT_NO_FUNC:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					return new GOTGuiHiredInteractNoFunc((GOTEntityNPC) entity);
				}
				break;
			case HIRED_WARRIOR_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer().equals(entityplayer) && npc.getHireableInfo().getHiredTask() == GOTHireableInfo.Task.WARRIOR) {
						return new GOTGuiHiredWarriorInventory(entityplayer.inventory, npc);
					}
				}
				break;
			case HORN_SELECT:
				return new GOTGuiHornSelect();
			case IRON_BANK:
				return new GOTGuiIronBank();
			case MENU:
				return GOTGuiMenu.openMenu(entityplayer);
			case MERCENARY_HIRE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTMercenary) {
					return new GOTGuiMercenaryHire(entityplayer, (GOTMercenary) entity, world);
				}
				break;
			case MERCENARY_INTERACT:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTMercenary) {
					return new GOTGuiMercenaryInteract((GOTEntityNPC) entity);
				}
				break;
			case MILLSTONE:
				TileEntity millstone = world.getTileEntity(i, j, k);
				if (millstone instanceof GOTTileEntityMillstone) {
					return new GOTGuiMillstone(entityplayer.inventory, (GOTTileEntityMillstone) millstone);
				}
				break;
			case MOUNT_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityHorse) {
					GOTEntityHorse horse = (GOTEntityHorse) entity;
					return new GOTGuiMountInventory(entityplayer.inventory, new AnimalChest(horse.getCommandSenderName(), j), horse);
				}
				if (entity instanceof GOTEntitySpiderBase) {
					GOTEntitySpiderBase npc2 = (GOTEntitySpiderBase) entity;
					if (npc2.getMountInventory() != null) {
						return new GOTGuiSpiderInventory(entityplayer.inventory, new AnimalChest(npc2.getCommandSenderName(), j), npc2);
					}
				}
				break;
			case NPC_RESPAWNER:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPCRespawner) {
					return new GOTGuiNPCRespawner((GOTEntityNPCRespawner) entity);
				}
				break;
			case OVEN:
				TileEntity oven = world.getTileEntity(i, j, k);
				if (oven instanceof GOTTileEntityOven) {
					return new GOTGuiOven(entityplayer.inventory, (GOTTileEntityOven) oven);
				}
				break;
			case POUCH:
				return new GOTGuiPouch(entityplayer, i);
			case QUEST_BOOK:
				return new GOTGuiQuestBook();
			case SQUADRON_ITEM:
				return new GOTGuiSquadronItem();
			case TABLE_ARRYN:
				return new GOTGuiCraftingTable.Arryn(entityplayer.inventory, world, i, j, k);
			case TABLE_ASSHAI:
				return new GOTGuiCraftingTable.Asshai(entityplayer.inventory, world, i, j, k);
			case TABLE_BRAAVOS:
				return new GOTGuiCraftingTable.Braavos(entityplayer.inventory, world, i, j, k);
			case TABLE_CROWNLANDS:
				return new GOTGuiCraftingTable.Crownlands(entityplayer.inventory, world, i, j, k);
			case TABLE_DORNE:
				return new GOTGuiCraftingTable.Dorne(entityplayer.inventory, world, i, j, k);
			case TABLE_DOTHRAKI:
				return new GOTGuiCraftingTable.Dothraki(entityplayer.inventory, world, i, j, k);
			case TABLE_DRAGONSTONE:
				return new GOTGuiCraftingTable.Dragonstone(entityplayer.inventory, world, i, j, k);
			case TABLE_GHISCAR:
				return new GOTGuiCraftingTable.Ghiscar(entityplayer.inventory, world, i, j, k);
			case TABLE_GIFT:
				return new GOTGuiCraftingTable.Gift(entityplayer.inventory, world, i, j, k);
			case TABLE_HILLMEN:
				return new GOTGuiCraftingTable.Hillmen(entityplayer.inventory, world, i, j, k);
			case TABLE_IBBEN:
				return new GOTGuiCraftingTable.Ibben(entityplayer.inventory, world, i, j, k);
			case TABLE_IRONBORN:
				return new GOTGuiCraftingTable.Ironborn(entityplayer.inventory, world, i, j, k);
			case TABLE_JOGOS:
				return new GOTGuiCraftingTable.Jogos(entityplayer.inventory, world, i, j, k);
			case TABLE_LHAZAR:
				return new GOTGuiCraftingTable.Lhazar(entityplayer.inventory, world, i, j, k);
			case TABLE_LORATH:
				return new GOTGuiCraftingTable.Lorath(entityplayer.inventory, world, i, j, k);
			case TABLE_LYS:
				return new GOTGuiCraftingTable.Lys(entityplayer.inventory, world, i, j, k);
			case TABLE_MOSSOVY:
				return new GOTGuiCraftingTable.Mossovy(entityplayer.inventory, world, i, j, k);
			case TABLE_MYR:
				return new GOTGuiCraftingTable.Myr(entityplayer.inventory, world, i, j, k);
			case TABLE_NORTH:
				return new GOTGuiCraftingTable.North(entityplayer.inventory, world, i, j, k);
			case TABLE_NORVOS:
				return new GOTGuiCraftingTable.Norvos(entityplayer.inventory, world, i, j, k);
			case TABLE_PENTOS:
				return new GOTGuiCraftingTable.Pentos(entityplayer.inventory, world, i, j, k);
			case TABLE_QARTH:
				return new GOTGuiCraftingTable.Qarth(entityplayer.inventory, world, i, j, k);
			case TABLE_QOHOR:
				return new GOTGuiCraftingTable.Qohor(entityplayer.inventory, world, i, j, k);
			case TABLE_REACH:
				return new GOTGuiCraftingTable.Reach(entityplayer.inventory, world, i, j, k);
			case TABLE_RIVERLANDS:
				return new GOTGuiCraftingTable.Riverlands(entityplayer.inventory, world, i, j, k);
			case TABLE_SOTHORYOS:
				return new GOTGuiCraftingTable.Sothoryos(entityplayer.inventory, world, i, j, k);
			case TABLE_STORMLANDS:
				return new GOTGuiCraftingTable.Stormlands(entityplayer.inventory, world, i, j, k);
			case TABLE_SUMMER:
				return new GOTGuiCraftingTable.Summer(entityplayer.inventory, world, i, j, k);
			case TABLE_TYROSH:
				return new GOTGuiCraftingTable.Tyrosh(entityplayer.inventory, world, i, j, k);
			case TABLE_VOLANTIS:
				return new GOTGuiCraftingTable.Volantis(entityplayer.inventory, world, i, j, k);
			case TABLE_WESTERLANDS:
				return new GOTGuiCraftingTable.Westerlands(entityplayer.inventory, world, i, j, k);
			case TABLE_WILDLING:
				return new GOTGuiCraftingTable.Wildling(entityplayer.inventory, world, i, j, k);
			case TABLE_YI_TI:
				return new GOTGuiCraftingTable.YiTi(entityplayer.inventory, world, i, j, k);
			case TRADE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTTradeable) {
					return new GOTGuiTrade(entityplayer.inventory, (GOTTradeable) entity, world);
				}
				break;
			case TRADE_INTERACT:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTTradeable) {
					return new GOTGuiTradeInteract((GOTEntityNPC) entity);
				}
				break;
			case TRADE_UNIT_TRADE_INTERACT:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTTradeable) {
					return new GOTGuiTradeUnitTradeInteract((GOTEntityNPC) entity);
				}
				break;
			case UNIT_TRADE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTUnitTradeable) {
					return new GOTGuiUnitTrade(entityplayer, (GOTUnitTradeable) entity, world);
				}
				break;
			case UNIT_TRADE_INTERACT:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTUnitTradeable) {
					return new GOTGuiUnitTradeInteract((GOTEntityNPC) entity);
				}
				break;
			case UNSMELTERY:
				TileEntity unsmeltery = world.getTileEntity(i, j, k);
				if (unsmeltery instanceof GOTTileEntityUnsmeltery) {
					return new GOTGuiUnsmeltery(entityplayer.inventory, (GOTTileEntityUnsmeltery) unsmeltery);
				}
				break;
			default:
				break;
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		GOTEntityNPC npc;
		Entity entity;
		IInventory chest2;
		int slot = unpackSlot(ID);
		if (testForSlotPackedGuiID(ID, GOTGuiId.POUCH_CHEST.ordinal()) && GOTItemPouch.isHoldingPouch(entityplayer, slot) && (chest2 = GOTItemPouch.getChestInvAt(entityplayer, world, i, j, k)) != null) {
			return new GOTContainerChestWithPouch(entityplayer, slot, chest2);
		}
		Entity minecart = world.getEntityByID(i);
		if (testForSlotPackedGuiID(ID, GOTGuiId.POUCH_MINECART.ordinal()) && GOTItemPouch.isHoldingPouch(entityplayer, slot) && minecart instanceof EntityMinecartContainer) {
			return new GOTContainerChestWithPouch(entityplayer, slot, (IInventory) minecart);
		}
		GOTGuiId id = GOTGuiId.values()[ID];
		switch (id) {
			case ALLOY_FORGE:
				TileEntity forge = world.getTileEntity(i, j, k);
				if (forge instanceof GOTTileEntityAlloyForge) {
					return new GOTContainerAlloyForge(entityplayer.inventory, (GOTTileEntityAlloyForge) forge);
				}
				break;
			case ANVIL:
				return new GOTContainerAnvil(entityplayer, i, j, k);
			case ANVIL_NPC:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					return new GOTContainerAnvil(entityplayer, (GOTEntityNPC) entity);
				}
				break;
			case ARMOR_STAND:
				TileEntity stand = world.getTileEntity(i, j, k);
				if (stand instanceof GOTTileEntityArmorStand) {
					return new GOTContainerArmorStand(entityplayer.inventory, (GOTTileEntityArmorStand) stand);
				}
				break;
			case BARREL:
				TileEntity barrel = world.getTileEntity(i, j, k);
				if (barrel instanceof GOTTileEntityBarrel) {
					return new GOTContainerBarrel(entityplayer.inventory, (GOTTileEntityBarrel) barrel);
				}
				break;
			case BOOKSHELF:
				TileEntity bookshelf = world.getTileEntity(i, j, k);
				if (bookshelf instanceof GOTTileEntityBookshelf) {
					return new GOTContainerBookshelf(entityplayer.inventory, (GOTTileEntityBookshelf) bookshelf);
				}
				break;
			case CHEST:
				TileEntity chest = world.getTileEntity(i, j, k);
				if (chest instanceof GOTTileEntityChest) {
					return new ContainerChest(entityplayer.inventory, (IInventory) chest);
				}
				break;
			case COIN_EXCHANGE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					return new GOTContainerCoinExchange(entityplayer, npc);
				}
				break;
			case CRACKER:
				if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof GOTItemCracker) {
					return new GOTContainerCracker(entityplayer);
				}
				break;
			case DISPENSER:
				TileEntity trap = world.getTileEntity(i, j, k);
				if (trap instanceof GOTTileEntitySarbacaneTrap) {
					return new ContainerDispenser(entityplayer.inventory, (TileEntityDispenser) trap);
				}
				break;
			case HIRED_FARMER_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer().equals(entityplayer) && npc.getHireableInfo().getHiredTask() == GOTHireableInfo.Task.FARMER) {
						return new GOTContainerHiredFarmerInventory(entityplayer.inventory, npc);
					}
				}
				break;
			case HIRED_WARRIOR_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer().equals(entityplayer) && npc.getHireableInfo().getHiredTask() == GOTHireableInfo.Task.WARRIOR) {
						return new GOTContainerHiredWarriorInventory(entityplayer.inventory, npc);
					}
				}
				break;
			case IRON_BANK:
				return new GOTGuiIronBank();
			case MERCENARY_HIRE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTMercenary) {
					return new GOTContainerUnitTrade(entityplayer, (GOTHireableBase) entity, world);
				}
				break;
			case MILLSTONE:
				TileEntity millstone = world.getTileEntity(i, j, k);
				if (millstone instanceof GOTTileEntityMillstone) {
					return new GOTContainerMillstone(entityplayer.inventory, (GOTTileEntityMillstone) millstone);
				}
				break;
			case MOUNT_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityHorse) {
					GOTEntityHorse horse = (GOTEntityHorse) entity;
					return new GOTContainerMountInventory(entityplayer.inventory, GOTReflection.getHorseInv(horse), horse);
				}
				if (entity instanceof GOTEntitySpiderBase && ((GOTEntitySpiderBase) entity).getMountInventory() != null) {
					return new GOTContainerSpiderInventory(entityplayer.inventory, ((GOTEntitySpiderBase) entity).getMountInventory(), (GOTEntitySpiderBase) entity);
				}
				break;
			case OVEN:
				TileEntity oven = world.getTileEntity(i, j, k);
				if (oven instanceof GOTTileEntityOven) {
					return new GOTContainerOven(entityplayer.inventory, (GOTTileEntityOven) oven);
				}
				break;
			case POUCH:
				if (GOTItemPouch.isHoldingPouch(entityplayer, i)) {
					return new GOTContainerPouch(entityplayer, i);
				}
				break;
			case TABLE_ARRYN:
				return new GOTContainerCraftingTable.Arryn(entityplayer.inventory, world, i, j, k);
			case TABLE_ASSHAI:
				return new GOTContainerCraftingTable.Asshai(entityplayer.inventory, world, i, j, k);
			case TABLE_BRAAVOS:
				return new GOTContainerCraftingTable.Braavos(entityplayer.inventory, world, i, j, k);
			case TABLE_CROWNLANDS:
				return new GOTContainerCraftingTable.Crownlands(entityplayer.inventory, world, i, j, k);
			case TABLE_DORNE:
				return new GOTContainerCraftingTable.Dorne(entityplayer.inventory, world, i, j, k);
			case TABLE_DOTHRAKI:
				return new GOTContainerCraftingTable.Dothraki(entityplayer.inventory, world, i, j, k);
			case TABLE_DRAGONSTONE:
				return new GOTContainerCraftingTable.Dragonstone(entityplayer.inventory, world, i, j, k);
			case TABLE_GHISCAR:
				return new GOTContainerCraftingTable.Ghiscar(entityplayer.inventory, world, i, j, k);
			case TABLE_GIFT:
				return new GOTContainerCraftingTable.Gift(entityplayer.inventory, world, i, j, k);
			case TABLE_HILLMEN:
				return new GOTContainerCraftingTable.Hillmen(entityplayer.inventory, world, i, j, k);
			case TABLE_IBBEN:
				return new GOTContainerCraftingTable.Ibben(entityplayer.inventory, world, i, j, k);
			case TABLE_IRONBORN:
				return new GOTContainerCraftingTable.Ironborn(entityplayer.inventory, world, i, j, k);
			case TABLE_JOGOS:
				return new GOTContainerCraftingTable.Jogos(entityplayer.inventory, world, i, j, k);
			case TABLE_LHAZAR:
				return new GOTContainerCraftingTable.Lhazar(entityplayer.inventory, world, i, j, k);
			case TABLE_LORATH:
				return new GOTContainerCraftingTable.Lorath(entityplayer.inventory, world, i, j, k);
			case TABLE_LYS:
				return new GOTContainerCraftingTable.Lys(entityplayer.inventory, world, i, j, k);
			case TABLE_MYR:
				return new GOTContainerCraftingTable.Myr(entityplayer.inventory, world, i, j, k);
			case TABLE_MOSSOVY:
				return new GOTContainerCraftingTable.Mossovy(entityplayer.inventory, world, i, j, k);
			case TABLE_NORTH:
				return new GOTContainerCraftingTable.North(entityplayer.inventory, world, i, j, k);
			case TABLE_NORVOS:
				return new GOTContainerCraftingTable.Norvos(entityplayer.inventory, world, i, j, k);
			case TABLE_PENTOS:
				return new GOTContainerCraftingTable.Pentos(entityplayer.inventory, world, i, j, k);
			case TABLE_QARTH:
				return new GOTContainerCraftingTable.Qarth(entityplayer.inventory, world, i, j, k);
			case TABLE_QOHOR:
				return new GOTContainerCraftingTable.Qohor(entityplayer.inventory, world, i, j, k);
			case TABLE_REACH:
				return new GOTContainerCraftingTable.Reach(entityplayer.inventory, world, i, j, k);
			case TABLE_RIVERLANDS:
				return new GOTContainerCraftingTable.Riverlands(entityplayer.inventory, world, i, j, k);
			case TABLE_SOTHORYOS:
				return new GOTContainerCraftingTable.Sothoryos(entityplayer.inventory, world, i, j, k);
			case TABLE_STORMLANDS:
				return new GOTContainerCraftingTable.Stormlands(entityplayer.inventory, world, i, j, k);
			case TABLE_SUMMER:
				return new GOTContainerCraftingTable.Summer(entityplayer.inventory, world, i, j, k);
			case TABLE_TYROSH:
				return new GOTContainerCraftingTable.Tyrosh(entityplayer.inventory, world, i, j, k);
			case TABLE_VOLANTIS:
				return new GOTContainerCraftingTable.Volantis(entityplayer.inventory, world, i, j, k);
			case TABLE_WESTERLANDS:
				return new GOTContainerCraftingTable.Westerlands(entityplayer.inventory, world, i, j, k);
			case TABLE_WILDLING:
				return new GOTContainerCraftingTable.Wildling(entityplayer.inventory, world, i, j, k);
			case TABLE_YI_TI:
				return new GOTContainerCraftingTable.YiTi(entityplayer.inventory, world, i, j, k);
			case TRADE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTTradeable) {
					return new GOTContainerTrade(entityplayer.inventory, (GOTTradeable) entity, world);
				}
				break;
			case UNIT_TRADE:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTUnitTradeable) {
					return new GOTContainerUnitTrade(entityplayer, (GOTHireableBase) entity, world);
				}
				break;
			case UNSMELTERY:
				TileEntity unsmeltery = world.getTileEntity(i, j, k);
				if (unsmeltery instanceof GOTTileEntityUnsmeltery) {
					return new GOTContainerUnsmeltery(entityplayer.inventory, (GOTTileEntityUnsmeltery) unsmeltery);
				}
				break;
			default:
				break;
		}
		return null;
	}

	public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
		if (world.isRemote && GOT.proxy.isClient()) {
			((EntityClientPlayerMP) entityplayer).sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0F, 0.0F, 0.0F));
		} else {
			entityplayer.openGui(GOT.instance, packGuiIDWithSlot(GOTGuiId.POUCH_CHEST.ordinal(), pouchSlot), world, i, j, k);
		}
	}
}