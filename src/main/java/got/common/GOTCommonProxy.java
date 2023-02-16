package got.common;

import java.util.*;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.IGuiHandler;
import got.GOT;
import got.client.gui.*;
import got.common.block.other.GOTBlockFlowerPot;
import got.common.database.*;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.*;
import got.common.faction.*;
import got.common.inventory.*;
import got.common.item.other.*;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import got.common.tileentity.*;
import got.common.util.GOTReflection;
import got.common.world.map.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.*;

public class GOTCommonProxy implements IGuiHandler {
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

	public Object openGuiOven(EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntity oven = world.getTileEntity(i, j, k);
		if (oven instanceof GOTTileEntityOven) {
			return new GOTGuiOven(entityplayer.inventory, (GOTTileEntityOven) oven);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		HashMap<Integer, Runnable> interfaces = new HashMap<>();
		interfaces.put(0, () -> openGuiOven(entityplayer, world, i, j, k));
		interfaces.put(1, () -> openGuiHiredInteractNoFunc(entityplayer, world, i, j, k));
		interfaces.put(2, () -> new GOTGuiCraftingTable.Ibben(entityplayer.inventory, world, i, j, k));
		interfaces.put(3, () -> openGuiTrade(entityplayer, world, i, j, k));
		interfaces.put(4, GOTGuiIronBank::new);
		interfaces.put(5, () -> openGuiAlloyForge(entityplayer, world, i, j, k));
		interfaces.put(7, () -> openGuiUnitTrade(entityplayer, world, i, j, k));
		interfaces.put(9, GOTGuiHornSelect::new);
		interfaces.put(11, () -> GOTGuiMenu.openMenu(entityplayer));
		interfaces.put(13, () -> new GOTGuiCraftingTable.North(entityplayer.inventory, world, i, j, k));
		interfaces.put(15, () -> new GOTGuiPouch(entityplayer, i));
		interfaces.put(16, () -> openGuiBarrel(entityplayer, world, i, j, k));
		interfaces.put(17, () -> openGuiArmorStand(entityplayer, world, i, j, k));
		interfaces.put(18, () -> new GOTGuiCraftingTable.Hillmen(entityplayer.inventory, world, i, j, k));
		interfaces.put(19, () -> openGuiTradeInteract(entityplayer, world, i, j, k));
		interfaces.put(20, () -> openGuiUnitTradeInteract(entityplayer, world, i, j, k));
		interfaces.put(21, () -> openGuiHiredInteract(entityplayer, world, i, j, k));
		interfaces.put(22, () -> openGuiHiredFarmerInventory(entityplayer, world, i, j, k));
		interfaces.put(23, () -> new GOTGuiCraftingTable.Wildling(entityplayer.inventory, world, i, j, k));
		interfaces.put(24, () -> openGuiTradeUnitTradeInteract(entityplayer, world, i, j, k));
		interfaces.put(25, () -> new GOTGuiCraftingTable.Summer(entityplayer.inventory, world, i, j, k));
		interfaces.put(28, () -> new GOTGuiCraftingTable.Gift(entityplayer.inventory, world, i, j, k));
		interfaces.put(29, () -> openGuiMountInventory(entityplayer, world, i, j, k));
		interfaces.put(32, GOTGuiQuestBook::new);
		interfaces.put(33, GOTGuiSquadronItem::new);
		interfaces.put(37, () -> new GOTGuiCraftingTable.Lhazar(entityplayer.inventory, world, i, j, k));
		interfaces.put(39, () -> new GOTGuiCraftingTable.Sothoryos(entityplayer.inventory, world, i, j, k));
		interfaces.put(49, () -> new GOTGuiCraftingTable.YiTi(entityplayer.inventory, world, i, j, k));
		interfaces.put(53, () -> new GOTGuiAnvil(entityplayer, i, j, k));

		interfaces.put(60, () -> new GOTGuiMap().setConquestGrid());
		interfaces.put(61, GOTGuiBrandingIron::new);
		interfaces.put(62, () -> new GOTGuiCraftingTable.Arryn(entityplayer.inventory, world, i, j, k));
		interfaces.put(65, () -> new GOTGuiCraftingTable.Crownlands(entityplayer.inventory, world, i, j, k));
		interfaces.put(66, () -> new GOTGuiCraftingTable.Dorne(entityplayer.inventory, world, i, j, k));
		interfaces.put(67, () -> new GOTGuiCraftingTable.Dragonstone(entityplayer.inventory, world, i, j, k));
		interfaces.put(68, () -> new GOTGuiCraftingTable.Ghiscar(entityplayer.inventory, world, i, j, k));
		interfaces.put(69, () -> new GOTGuiCraftingTable.Ironborn(entityplayer.inventory, world, i, j, k));
		interfaces.put(70, () -> new GOTGuiCraftingTable.Lorath(entityplayer.inventory, world, i, j, k));
		interfaces.put(71, () -> new GOTGuiCraftingTable.Lys(entityplayer.inventory, world, i, j, k));
		interfaces.put(72, () -> new GOTGuiCraftingTable.Myr(entityplayer.inventory, world, i, j, k));
		interfaces.put(73, () -> new GOTGuiCraftingTable.Norvos(entityplayer.inventory, world, i, j, k));
		interfaces.put(74, () -> new GOTGuiCraftingTable.Pentos(entityplayer.inventory, world, i, j, k));
		interfaces.put(75, () -> new GOTGuiCraftingTable.Qarth(entityplayer.inventory, world, i, j, k));
		interfaces.put(76, () -> new GOTGuiCraftingTable.Qohor(entityplayer.inventory, world, i, j, k));
		interfaces.put(77, () -> new GOTGuiCraftingTable.Reach(entityplayer.inventory, world, i, j, k));
		interfaces.put(78, () -> new GOTGuiCraftingTable.Riverlands(entityplayer.inventory, world, i, j, k));
		interfaces.put(79, () -> new GOTGuiCraftingTable.Stormlands(entityplayer.inventory, world, i, j, k));
		interfaces.put(80, () -> new GOTGuiCraftingTable.Tyrosh(entityplayer.inventory, world, i, j, k));
		interfaces.put(81, () -> new GOTGuiCraftingTable.Volantis(entityplayer.inventory, world, i, j, k));
		interfaces.put(82, () -> new GOTGuiCraftingTable.Westerlands(entityplayer.inventory, world, i, j, k));
		interfaces.put(84, () -> new GOTGuiCraftingTable.Asshai(entityplayer.inventory, world, i, j, k));
		interfaces.put(85, () -> new GOTGuiCraftingTable.Braavos(entityplayer.inventory, world, i, j, k));
		interfaces.put(86, () -> new GOTGuiCraftingTable.Dothraki(entityplayer.inventory, world, i, j, k));
		interfaces.put(87, () -> new GOTGuiCraftingTable.Jogos(entityplayer.inventory, world, i, j, k));
		interfaces.put(88, () -> new GOTGuiCraftingTable.Mossovy(entityplayer.inventory, world, i, j, k));

		interfaces.put(35, () -> openGuiCoinExchange(entityplayer, world, i, j, k));


		if (interfaces.containsKey(ID)) {
			interfaces.get(ID).run();
		}

		switch (ID) {
		case 38:
			TileEntity unsmeltery = world.getTileEntity(i, j, k);
			if (unsmeltery instanceof GOTTileEntityUnsmeltery) {
				return new GOTGuiUnsmeltery(entityplayer.inventory, (GOTTileEntityUnsmeltery) unsmeltery);
			}
			break;
		case 40:
			TileEntity trap = world.getTileEntity(i, j, k);
			if (trap instanceof GOTTileEntitySarbacaneTrap) {
				return new GuiDispenser(entityplayer.inventory, (GOTTileEntitySarbacaneTrap) trap);
			}
			break;
		case 41:
			TileEntity chest = world.getTileEntity(i, j, k);
			if (chest instanceof GOTTileEntityChest) {
				return new GuiChest(entityplayer.inventory, (GOTTileEntityChest) chest);
			}
			break;
		case 45:
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPCRespawner) {
				return new GOTGuiNPCRespawner((GOTEntityNPCRespawner) entity);
			}
			break;
		case 46:
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
					return new GOTGuiHiredWarriorInventory(entityplayer.inventory, npc);
				}
			}
			break;
		case 47:
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			GOTTileEntitySign fake = (GOTTileEntitySign) block.createTileEntity(world, meta);
			fake.setWorldObj(world);
			fake.xCoord = i;
			fake.yCoord = j;
			fake.zCoord = k;
			fake.isFakeGuiSign = true;
			return new GOTGuiEditSign(fake);
		case 48:
			if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof GOTItemCracker) {
				return new GOTGuiCracker(entityplayer);
			}
			break;
		case 52:
			TileEntity millstone = world.getTileEntity(i, j, k);
			if (millstone instanceof GOTTileEntityMillstone) {
				return new GOTGuiMillstone(entityplayer.inventory, (GOTTileEntityMillstone) millstone);
			}
			break;
		case 54:
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTGuiAnvil(entityplayer, (GOTEntityNPC) entity);
			}
			break;
		case 55:
			TileEntity bookshelf;
			if (world.getBlock(i, j, k) == Blocks.bookshelf) {
				world.setBlock(i, j, k, GOTRegistry.bookshelfStorage, 0, 3);
			}
			bookshelf = world.getTileEntity(i, j, k);
			if (bookshelf instanceof GOTTileEntityBookshelf) {
				return new GOTGuiBookshelf(entityplayer.inventory, (GOTTileEntityBookshelf) bookshelf);
			}
			break;
		case 58:
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTMercenary) {
				return new GOTGuiMercenaryInteract((GOTEntityNPC) entity);
			}
			break;
		case 59:
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTMercenary) {
				return new GOTGuiMercenaryHire(entityplayer, (GOTMercenary) entity, world);
			}
			break;
		}
		if (GOTCommonProxy.testForSlotPackedGuiID(ID, 63)) {
			int slot = GOTCommonProxy.unpackSlot(ID);
			IInventory chest2 = GOTItemPouch.getChestInvAt(entityplayer, world, i, j, k);
			if (chest2 != null) {
				return new GOTGuiChestWithPouch(entityplayer, slot, chest2);
			}
		}
		if (GOTCommonProxy.testForSlotPackedGuiID(ID, 64)) {
			int slot = GOTCommonProxy.unpackSlot(ID);
			Entity minecart = world.getEntityByID(i);
			if (minecart instanceof EntityMinecartContainer) {
				return new GOTGuiChestWithPouch(entityplayer, slot, (EntityMinecartContainer) minecart);
			}
		}
		return null;
	}

	private Object openGuiCoinExchange(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTEntityNPC) {
			return new GOTGuiCoinExchange(entityplayer, (GOTEntityNPC) entity);
		}
		return null;
	}

	private Object openGuiMountInventory(EntityPlayer entityplayer, World world, int i, int j, int k) {
		GOTEntityNPCRideable npcRideable;
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTEntityHorse) {
			GOTEntityHorse horse = (GOTEntityHorse) entity;
			return new GOTGuiMountInventory(entityplayer.inventory, new AnimalChest(horse.getCommandSenderName(), j), horse);
		}
		npcRideable = (GOTEntityNPCRideable) entity;
		if (entity instanceof GOTEntityNPCRideable && npcRideable.getMountInventory() != null) {
			return new GOTGuiNPCMountInventory(entityplayer.inventory, new AnimalChest(npcRideable.getCommandSenderName(), j), npcRideable);
		}
		return null;
	}

	private Object openGuiTradeUnitTradeInteract(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTTradeable) {
			return new GOTGuiTradeUnitTradeInteract((GOTEntityNPC) entity);
		}
		return null;
	}

	private Object openGuiHiredFarmerInventory(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) entity;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
				return new GOTGuiHiredFarmerInventory(entityplayer.inventory, npc);
			}
		}
		return null;
	}

	private Object openGuiHiredInteract(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTEntityNPC) {
			return new GOTGuiHiredInteract((GOTEntityNPC) entity);
		}
		return null;
	}

	private Object openGuiUnitTradeInteract(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTUnitTradeable) {
			return new GOTGuiUnitTradeInteract((GOTEntityNPC) entity);
		}
		return null;
	}

	private Object openGuiTradeInteract(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTTradeable) {
			return new GOTGuiTradeInteract((GOTEntityNPC) entity);
		}
		return null;
	}

	private Object openGuiArmorStand(EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntity stand = world.getTileEntity(i, j, k);
		if (stand instanceof GOTTileEntityArmorStand) {
			return new GOTGuiArmorStand(entityplayer.inventory, (GOTTileEntityArmorStand) stand);
		}
		return null;
	}

	private Object openGuiBarrel(EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntity barrel = world.getTileEntity(i, j, k);
		if (barrel instanceof GOTTileEntityBarrel) {
			return new GOTGuiBarrel(entityplayer.inventory, (GOTTileEntityBarrel) barrel);
		}
		return null;
	}

	private Object openGuiUnitTrade(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTUnitTradeable) {
			return new GOTGuiUnitTrade(entityplayer, (GOTUnitTradeable) entity, world);
		}
		return null;
	}

	private Object openGuiAlloyForge(EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntity forge = world.getTileEntity(i, j, k);
		if (forge instanceof GOTTileEntityAlloyForge) {
			return new GOTGuiAlloyForge(entityplayer.inventory, (GOTTileEntityAlloyForge) forge);
		}
		return null;
	}

	private Object openGuiTrade(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTTradeable) {
			return new GOTGuiTrade(entityplayer.inventory, (GOTTradeable) entity, world);
		}
		return null;
	}

	private Object openGuiHiredInteractNoFunc(EntityPlayer entityplayer, World world, int i, int j, int k) {
		Entity entity = world.getEntityByID(i);
		if (entity instanceof GOTEntityNPC) {
			return new GOTGuiHiredInteractNoFunc((GOTEntityNPC) entity);
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
		TileEntity stand;
		TileEntity unsmeltery;
		TileEntity forge;
		TileEntity chest;
		GOTEntityNPC npc;
		TileEntity oven;
		Entity entity;
		TileEntity trap;
		TileEntity millstone;
		TileEntity barrel;
		TileEntity bookshelf;
		switch (ID) {
		case 0:
			oven = world.getTileEntity(i, j, k);
			if (oven instanceof GOTTileEntityOven) {
				return new GOTContainerOven(entityplayer.inventory, (GOTTileEntityOven) oven);
			}
			break;
		case 2:
			return new GOTContainerCraftingTable.Ibben(entityplayer.inventory, world, i, j, k);
		case 3:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTTradeable) {
				return new GOTContainerTrade(entityplayer.inventory, (GOTTradeable) entity, world);
			}
			break;
		case 4:
			return new GOTGuiIronBank();
		case 5:
			forge = world.getTileEntity(i, j, k);
			if (forge instanceof GOTTileEntityAlloyForge) {
				return new GOTContainerAlloyForge(entityplayer.inventory, (GOTTileEntityAlloyForge) forge);
			}
			break;
		case 7:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTUnitTradeable) {
				return new GOTContainerUnitTrade(entityplayer, (GOTUnitTradeable) entity, world);
			}
			break;
		case 13:
			return new GOTContainerCraftingTable.North(entityplayer.inventory, world, i, j, k);
		case 15:
			if (GOTItemPouch.isHoldingPouch(entityplayer, i)) {
				return new GOTContainerPouch(entityplayer, i);
			}
			break;
		case 16:
			barrel = world.getTileEntity(i, j, k);
			if (barrel instanceof GOTTileEntityBarrel) {
				return new GOTContainerBarrel(entityplayer.inventory, (GOTTileEntityBarrel) barrel);
			}
			break;
		case 17:
			stand = world.getTileEntity(i, j, k);
			if (stand instanceof GOTTileEntityArmorStand) {
				return new GOTContainerArmorStand(entityplayer.inventory, (GOTTileEntityArmorStand) stand);
			}
			break;
		case 18:
			return new GOTContainerCraftingTable.Hillmen(entityplayer.inventory, world, i, j, k);
		case 22:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
					return new GOTContainerHiredFarmerInventory(entityplayer.inventory, npc);
				}
			}
			break;
		case 23:
			return new GOTContainerCraftingTable.Wildling(entityplayer.inventory, world, i, j, k);
		case 25:
			return new GOTContainerCraftingTable.Summer(entityplayer.inventory, world, i, j, k);
		case 28:
			return new GOTContainerCraftingTable.Gift(entityplayer.inventory, world, i, j, k);
		case 29:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityHorse) {
				GOTEntityHorse horse = (GOTEntityHorse) entity;
				return new GOTContainerMountInventory(entityplayer.inventory, GOTReflection.getHorseInv(horse), horse);
			}
			if (entity instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) entity).getMountInventory() != null) {
				return new GOTContainerNPCMountInventory(entityplayer.inventory, ((GOTEntityNPCRideable) entity).getMountInventory(), (GOTEntityNPCRideable) entity);
			}
			break;
		case 35:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				npc = (GOTEntityNPC) entity;
				return new GOTContainerCoinExchange(entityplayer, npc);
			}
			break;
		case 37:
			return new GOTContainerCraftingTable.Lhazar(entityplayer.inventory, world, i, j, k);
		case 38:
			unsmeltery = world.getTileEntity(i, j, k);
			if (unsmeltery instanceof GOTTileEntityUnsmeltery) {
				return new GOTContainerUnsmeltery(entityplayer.inventory, (GOTTileEntityUnsmeltery) unsmeltery);
			}
			break;
		case 39:
			return new GOTContainerCraftingTable.Sothoryos(entityplayer.inventory, world, i, j, k);
		case 40:
			trap = world.getTileEntity(i, j, k);
			if (trap instanceof GOTTileEntitySarbacaneTrap) {
				return new ContainerDispenser(entityplayer.inventory, (GOTTileEntitySarbacaneTrap) trap);
			}
			break;
		case 41:
			chest = world.getTileEntity(i, j, k);
			if (chest instanceof GOTTileEntityChest) {
				return new ContainerChest(entityplayer.inventory, (GOTTileEntityChest) chest);
			}
			break;
		case 46:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
					return new GOTContainerHiredWarriorInventory(entityplayer.inventory, npc);
				}
			}
			break;
		case 48:
			if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof GOTItemCracker) {
				return new GOTContainerCracker(entityplayer);
			}
			break;
		case 49:
			return new GOTContainerCraftingTable.YiTi(entityplayer.inventory, world, i, j, k);
		case 52:
			millstone = world.getTileEntity(i, j, k);
			if (millstone instanceof GOTTileEntityMillstone) {
				return new GOTContainerMillstone(entityplayer.inventory, (GOTTileEntityMillstone) millstone);
			}
			break;
		case 53:
			return new GOTContainerAnvil(entityplayer, i, j, k);
		case 54:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTContainerAnvil(entityplayer, (GOTEntityNPC) entity);
			}
			break;
		case 55:
			bookshelf = world.getTileEntity(i, j, k);
			if (bookshelf instanceof GOTTileEntityBookshelf) {
				return new GOTContainerBookshelf(entityplayer.inventory, (GOTTileEntityBookshelf) bookshelf);
			}
			break;
		case 59:
			entity = world.getEntityByID(i);
			if (entity instanceof GOTMercenary) {
				return new GOTContainerUnitTrade(entityplayer, (GOTMercenary) entity, world);
			}
			break;
		case 62:
			return new GOTContainerCraftingTable.Arryn(entityplayer.inventory, world, i, j, k);
		case 65:
			return new GOTContainerCraftingTable.Crownlands(entityplayer.inventory, world, i, j, k);
		case 66:
			return new GOTContainerCraftingTable.Dorne(entityplayer.inventory, world, i, j, k);
		case 67:
			return new GOTContainerCraftingTable.Dragonstone(entityplayer.inventory, world, i, j, k);
		case 68:
			return new GOTContainerCraftingTable.Ghiscar(entityplayer.inventory, world, i, j, k);
		case 69:
			return new GOTContainerCraftingTable.Ironborn(entityplayer.inventory, world, i, j, k);
		case 70:
			return new GOTContainerCraftingTable.Lorath(entityplayer.inventory, world, i, j, k);
		case 71:
			return new GOTContainerCraftingTable.Lys(entityplayer.inventory, world, i, j, k);
		case 72:
			return new GOTContainerCraftingTable.Myr(entityplayer.inventory, world, i, j, k);
		case 73:
			return new GOTContainerCraftingTable.Norvos(entityplayer.inventory, world, i, j, k);
		case 74:
			return new GOTContainerCraftingTable.Pentos(entityplayer.inventory, world, i, j, k);
		case 75:
			return new GOTContainerCraftingTable.Qarth(entityplayer.inventory, world, i, j, k);
		case 76:
			return new GOTContainerCraftingTable.Qohor(entityplayer.inventory, world, i, j, k);
		case 77:
			return new GOTContainerCraftingTable.Reach(entityplayer.inventory, world, i, j, k);
		case 78:
			return new GOTContainerCraftingTable.Riverlands(entityplayer.inventory, world, i, j, k);
		case 79:
			return new GOTContainerCraftingTable.Stormlands(entityplayer.inventory, world, i, j, k);
		case 80:
			return new GOTContainerCraftingTable.Tyrosh(entityplayer.inventory, world, i, j, k);
		case 81:
			return new GOTContainerCraftingTable.Volantis(entityplayer.inventory, world, i, j, k);
		case 82:
			return new GOTContainerCraftingTable.Westerlands(entityplayer.inventory, world, i, j, k);
		case 84:
			return new GOTContainerCraftingTable.Asshai(entityplayer.inventory, world, i, j, k);
		case 85:
			return new GOTContainerCraftingTable.Braavos(entityplayer.inventory, world, i, j, k);
		case 86:
			return new GOTContainerCraftingTable.Dothraki(entityplayer.inventory, world, i, j, k);
		case 87:
			return new GOTContainerCraftingTable.Jogos(entityplayer.inventory, world, i, j, k);
		case 88:
			return new GOTContainerCraftingTable.Mossovy(entityplayer.inventory, world, i, j, k);
		default:
			break;
		}
		IInventory chest2;
		int slot = GOTCommonProxy.unpackSlot(ID);
		if (GOTCommonProxy.testForSlotPackedGuiID(ID, 63) && GOTItemPouch.isHoldingPouch(entityplayer, slot) && (chest2 = GOTItemPouch.getChestInvAt(entityplayer, world, i, j, k)) != null) {
			return new GOTContainerChestWithPouch(entityplayer, slot, chest2);
		}
		Entity minecart = world.getEntityByID(i);
		if (GOTCommonProxy.testForSlotPackedGuiID(ID, 64) && GOTItemPouch.isHoldingPouch(entityplayer, slot) && minecart instanceof EntityMinecartContainer) {
			return new GOTContainerChestWithPouch(entityplayer, slot, (EntityMinecartContainer) minecart);
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

	public boolean isPaused() {
		return false;
	}

	public boolean isSingleplayer() {
		return false;
	}

	public void onLoad() {
	}

	public void onPostload() {
	}

	public void onPreload() {
	}

	public void openHiredNPCGui(GOTEntityNPC npc) {
	}

	public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
		world.setBlock(i, j, k, GOTRegistry.flowerPot, 0, 3);
		GOTBlockFlowerPot.setPlant(world, i, j, k, itemstack);
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
		entityplayer.openGui(GOT.instance, packGuiIDWithSlot(63, pouchSlot), world, i, j, k);
	}

	public void validateBannerUsername(GOTEntityBanner banner, int slot, String prevText, boolean valid) {
	}

	public static int packGuiIDWithSlot(int guiID, int slotNo) {
		return guiID | slotNo << 16;
	}

	public static void sendClientsideGUI(EntityPlayerMP entityplayer, int guiID, int x, int y, int z) {
		GOTPacketClientsideGUI packet = new GOTPacketClientsideGUI(guiID, x, y, z);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static boolean testForSlotPackedGuiID(int fullID, int guiID) {
		return (fullID & 0xFFFF) == guiID;
	}

	public static int unpackSlot(int fullID) {
		return fullID >> 16;
	}
}