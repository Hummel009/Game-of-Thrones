package got.common;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.client.gui.*;
import got.common.block.other.GOTBlockFlowerPot;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTGuiID;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.*;
import got.common.event.GOTTickHandlerServer;
import got.common.faction.GOTAlignmentBonusMap;
import got.common.faction.GOTFaction;
import got.common.inventory.*;
import got.common.item.other.GOTItemCracker;
import got.common.item.other.GOTItemPouch;
import got.common.network.GOTPacketClientsideGUI;
import got.common.network.GOTPacketFellowshipAcceptInviteResult;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketMenuPrompt;
import got.common.quest.GOTMiniQuest;
import got.common.tileentity.*;
import got.common.util.GOTReflection;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTConquestZone;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GOTCommonProxy implements IGuiHandler {
	public static int packGuiIDWithSlot(int guiID, int slotNo) {
		return guiID | slotNo << 16;
	}

	public static void sendClientsideGUI(EntityPlayerMP entityplayer, int guiID, int x, int y, int z) {
		IMessage packet = new GOTPacketClientsideGUI(guiID, x, y, z);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static boolean testForSlotPackedGuiID(int fullID, int guiID) {
		return (fullID & 0xFFFF) == guiID;
	}

	public static int unpackSlot(int fullID) {
		return fullID >> 16;
	}

	public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
	}

	public void cancelItemHighlight() {
	}

	public void clearMapPlayerLocations() {
	}

	public void clientReceiveSpeech(GOTEntityNPC npc, String speech) {
	}

	public void displayAlignmentSee(String username, Map<GOTFaction, Float> alignments) {
	}

	public void displayBannerGui(GOTEntityBanner banner) {
	}

	public void displayFellowshipAcceptInvitationResult(UUID fellowshipID, String name, GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
	}

	public void displayFTScreen(GOTAbstractWaypoint waypoint, int startX, int startZ) {
	}

	public void displayMenuPrompt(GOTPacketMenuPrompt.Type type) {
	}

	public void displayMessage(GOTGuiMessageTypes message) {
	}

	public void displayMiniquestOffer(GOTMiniQuest quest, GOTEntityNPC npc) {
	}

	public void displayNewDate() {
	}

	public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
		world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) - 1, 3);
	}

	public int getBarrelRenderID() {
		return 0;
	}

	public int getBeaconRenderID() {
		return 0;
	}

	public int getBeamRenderID() {
		return 0;
	}

	public int getBirdCageRenderID() {
		return 0;
	}

	public int getBombRenderID() {
		return 0;
	}

	public int getButterflyJarRenderID() {
		return 0;
	}

	public int getChainRenderID() {
		return 0;
	}

	public int getChestRenderID() {
		return 0;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		GOTEntityNPC npc;
		Entity entity;
		if (testForSlotPackedGuiID(ID, GOTGuiID.POUCH_CHEST.ordinal())) {
			int slot = unpackSlot(ID);
			IInventory chest2 = GOTItemPouch.getChestInvAt(entityplayer, world, i, j, k);
			if (chest2 != null) {
				return new GOTGuiChestWithPouch(entityplayer, slot, chest2);
			}
		}
		if (testForSlotPackedGuiID(ID, GOTGuiID.POUCH_MINECART.ordinal())) {
			int slot = unpackSlot(ID);
			Entity minecart = world.getEntityByID(i);
			if (minecart instanceof EntityMinecartContainer) {
				return new GOTGuiChestWithPouch(entityplayer, slot, (IInventory) minecart);
			}
		}
		GOTGuiID id = GOTGuiID.values()[ID];
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
				TileEntity bookshelf;
				if (world.getBlock(i, j, k) == Blocks.bookshelf) {
					world.setBlock(i, j, k, GOTBlocks.bookshelfStorage, 0, 3);
				}
				bookshelf = world.getTileEntity(i, j, k);
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
				GOTTileEntitySign fake = (GOTTileEntitySign) block.createTileEntity(world, meta);
				fake.setWorldObj(world);
				fake.xCoord = i;
				fake.yCoord = j;
				fake.zCoord = k;
				fake.isFakeGuiSign = true;
				return new GOTGuiEditSign(fake);
			case HIRED_FARMER_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
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
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
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
				if (entity instanceof GOTEntityNPCRideable) {
					GOTEntityNPCRideable npc2 = (GOTEntityNPCRideable) entity;
					if (npc2.getMountInventory() != null) {
						return new GOTGuiNPCMountInventory(entityplayer.inventory, new AnimalChest(npc2.getCommandSenderName(), j), npc2);
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

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public World getClientWorld() {
		return null;
	}

	public int getCloverRenderID() {
		return 0;
	}

	public int getCommandTableRenderID() {
		return 0;
	}

	public int getCoralRenderID() {
		return 0;
	}

	public int getDoorRenderID() {
		return 0;
	}

	public int getDoublePlantRenderID() {
		return 0;
	}

	public int getDoubleTorchRenderID() {
		return 0;
	}

	public int getFallenLeavesRenderID() {
		return 0;
	}

	public int getFenceRenderID() {
		return 0;
	}

	public int getFlowerPotRenderID() {
		return 0;
	}

	public int getFlowerRenderID() {
		return 0;
	}

	public int getGrapevineRenderID() {
		return 0;
	}

	public int getGrassRenderID() {
		return 0;
	}

	public int getLeavesRenderID() {
		return 0;
	}

	public int getPlantainRenderID() {
		return 0;
	}

	public int getPlateRenderID() {
		return 0;
	}

	public int getReedsRenderID() {
		return 0;
	}

	public int getRopeRenderID() {
		return 0;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		GOTEntityNPC npc;
		Entity entity;
		IInventory chest2;
		int slot = unpackSlot(ID);
		if (testForSlotPackedGuiID(ID, GOTGuiID.POUCH_CHEST.ordinal()) && GOTItemPouch.isHoldingPouch(entityplayer, slot) && (chest2 = GOTItemPouch.getChestInvAt(entityplayer, world, i, j, k)) != null) {
			return new GOTContainerChestWithPouch(entityplayer, slot, chest2);
		}
		Entity minecart = world.getEntityByID(i);
		if (testForSlotPackedGuiID(ID, GOTGuiID.POUCH_MINECART.ordinal()) && GOTItemPouch.isHoldingPouch(entityplayer, slot) && minecart instanceof EntityMinecartContainer) {
			return new GOTContainerChestWithPouch(entityplayer, slot, (IInventory) minecart);
		}
		GOTGuiID id = GOTGuiID.values()[ID];
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
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
						return new GOTContainerHiredFarmerInventory(entityplayer.inventory, npc);
					}
				}
				break;
			case HIRED_WARRIOR_INVENTORY:
				entity = world.getEntityByID(i);
				if (entity instanceof GOTEntityNPC) {
					npc = (GOTEntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
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
				if (entity instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) entity).getMountInventory() != null) {
					return new GOTContainerNPCMountInventory(entityplayer.inventory, ((GOTEntityNPCRideable) entity).getMountInventory(), (GOTEntityNPCRideable) entity);
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

	public int getStalactiteRenderID() {
		return 0;
	}

	public int getThatchFloorRenderID() {
		return 0;
	}

	public int getTrapdoorRenderID() {
		return 0;
	}

	public int getTreasureRenderID() {
		return 0;
	}

	public int getUnsmelteryRenderID() {
		return 0;
	}

	public int getVCauldronRenderID() {
		return 0;
	}

	public int getWasteRenderID() {
		return 0;
	}

	public int getWildFireJarRenderID() {
		return 0;
	}

	public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
	}

	public boolean isClient() {
		return false;
	}

	public boolean isSingleplayer() {
		return false;
	}

	public void onInit() {
	}

	public void openHiredNPCGui(GOTEntityNPC npc) {
	}

	public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
		world.setBlock(i, j, k, GOTBlocks.flowerPot, 0, 3);
		GOTBlockFlowerPot.setPlant(world, i, j, k, itemstack);
	}

	public void postInit() {
	}

	public void preInit() {
	}

	public void queueAchievement(GOTAchievement achievement) {
	}

	public void queueConquestNotification(GOTFaction fac, float conq, boolean isCleansing) {
	}

	public void queueFellowshipNotification(IChatComponent message) {
	}

	public void receiveConquestGrid(GOTFaction conqFac, List<GOTConquestZone> allZones) {
	}

	public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
	}

	public void setClientDifficulty(EnumDifficulty difficulty) {
	}

	public void setInPortal(EntityPlayer entityplayer) {
		if (!GOTTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
			GOTTickHandlerServer.playersInPortals.put(entityplayer, 0);
		}
	}

	public void setMapCWPProtectionMessage(IChatComponent message) {
	}

	public void setMapIsOp(boolean isOp) {
	}

	public void setTrackedQuest(GOTMiniQuest quest) {
	}

	public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
	}

	public void showBurnOverlay() {
	}

	public void showFrostOverlay() {
	}

	public void spawnAlignmentBonus(GOTFaction faction, float prevMainAlignment, GOTAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
	}

	public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
	}

	public void testReflection(World world) {
		GOTReflection.testAll(world);
	}

	public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
		entityplayer.openGui(GOT.instance, packGuiIDWithSlot(GOTGuiID.POUCH_CHEST.ordinal(), pouchSlot), world, i, j, k);
	}

	public void validateBannerUsername(GOTEntityBanner banner, int slot, String prevText, boolean valid) {
	}
}