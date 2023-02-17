package got.common;

import java.util.*;
import java.util.function.Supplier;

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

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
		HashMap<Integer, Supplier<Object>> guiMap = new HashMap<>();
		guiMap.put(0, () -> {
			TileEntity oven = world.getTileEntity(i, j, k);
			if (oven instanceof GOTTileEntityOven) {
				return new GOTGuiOven(entityplayer.inventory, (GOTTileEntityOven) oven);
			}
			return null;
		});
		guiMap.put(1, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTGuiHiredInteractNoFunc((GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(2, () -> new GOTGuiCraftingTable.Ibben(entityplayer.inventory, world, i, j, k));
		guiMap.put(3, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTTradeable) {
				return new GOTGuiTrade(entityplayer.inventory, (GOTTradeable) entity, world);
			}
			return null;
		});
		guiMap.put(4, GOTGuiIronBank::new);
		guiMap.put(5, () -> {
			TileEntity forge = world.getTileEntity(i, j, k);
			if (forge instanceof GOTTileEntityAlloyForge) {
				return new GOTGuiAlloyForge(entityplayer.inventory, (GOTTileEntityAlloyForge) forge);
			}
			return null;
		});
		guiMap.put(7, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTUnitTradeable) {
				return new GOTGuiUnitTrade(entityplayer, (GOTUnitTradeable) entity, world);
			}
			return null;
		});
		guiMap.put(9, GOTGuiHornSelect::new);
		guiMap.put(11, () -> GOTGuiMenu.openMenu(entityplayer));
		guiMap.put(13, () -> new GOTGuiCraftingTable.North(entityplayer.inventory, world, i, j, k));
		guiMap.put(16, () -> {
			TileEntity barrel = world.getTileEntity(i, j, k);
			if (barrel instanceof GOTTileEntityBarrel) {
				return new GOTGuiBarrel(entityplayer.inventory, (GOTTileEntityBarrel) barrel);
			}
			return null;
		});
		guiMap.put(15, () -> new GOTGuiPouch(entityplayer, i));
		guiMap.put(17, () -> {
			TileEntity stand = world.getTileEntity(i, j, k);
			if (stand instanceof GOTTileEntityArmorStand) {
				return new GOTGuiArmorStand(entityplayer.inventory, (GOTTileEntityArmorStand) stand);
			}
			return null;
		});
		guiMap.put(18, () -> new GOTGuiCraftingTable.Hillmen(entityplayer.inventory, world, i, j, k));
		guiMap.put(19, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTTradeable) {
				return new GOTGuiTradeInteract((GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(20, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTUnitTradeable) {
				return new GOTGuiUnitTradeInteract((GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(21, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTGuiHiredInteract((GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(22, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
					return new GOTGuiHiredFarmerInventory(entityplayer.inventory, npc);
				}
			}
			return null;
		});
		guiMap.put(23, () -> new GOTGuiCraftingTable.Wildling(entityplayer.inventory, world, i, j, k));
		guiMap.put(24, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTTradeable) {
				return new GOTGuiTradeUnitTradeInteract((GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(25, () -> new GOTGuiCraftingTable.Summer(entityplayer.inventory, world, i, j, k));
		guiMap.put(28, () -> new GOTGuiCraftingTable.Gift(entityplayer.inventory, world, i, j, k));
		guiMap.put(29, () -> {
			GOTEntityNPCRideable npc2;
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityHorse) {
				GOTEntityHorse horse = (GOTEntityHorse) entity;
				return new GOTGuiMountInventory(entityplayer.inventory, new AnimalChest(horse.getCommandSenderName(), j), horse);
			}
			npc2 = (GOTEntityNPCRideable) entity;
			if (entity instanceof GOTEntityNPCRideable && npc2.getMountInventory() != null) {
				return new GOTGuiNPCMountInventory(entityplayer.inventory, new AnimalChest(npc2.getCommandSenderName(), j), npc2);
			}
			return null;
		});
		guiMap.put(32, GOTGuiQuestBook::new);
		guiMap.put(33, GOTGuiSquadronItem::new);
		guiMap.put(35, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTGuiCoinExchange(entityplayer, (GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(37, () -> new GOTGuiCraftingTable.Lhazar(entityplayer.inventory, world, i, j, k));
		guiMap.put(38, () -> {
			TileEntity unsmeltery = world.getTileEntity(i, j, k);
			if (unsmeltery instanceof GOTTileEntityUnsmeltery) {
				return new GOTGuiUnsmeltery(entityplayer.inventory, (GOTTileEntityUnsmeltery) unsmeltery);
			}
			return null;
		});
		guiMap.put(39, () -> new GOTGuiCraftingTable.Sothoryos(entityplayer.inventory, world, i, j, k));
		guiMap.put(40, () -> {
			TileEntity trap = world.getTileEntity(i, j, k);
			if (trap instanceof GOTTileEntitySarbacaneTrap) {
				return new GuiDispenser(entityplayer.inventory, (GOTTileEntitySarbacaneTrap) trap);
			}
			return null;
		});
		guiMap.put(41, () -> {
			TileEntity chest = world.getTileEntity(i, j, k);
			if (chest instanceof GOTTileEntityChest) {
				return new GuiChest(entityplayer.inventory, (GOTTileEntityChest) chest);
			}
			return null;
		});
		guiMap.put(45, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPCRespawner) {
				return new GOTGuiNPCRespawner((GOTEntityNPCRespawner) entity);
			}
			return null;
		});
		guiMap.put(46, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
					return new GOTGuiHiredWarriorInventory(entityplayer.inventory, npc);
				}
			}
			return null;
		});
		guiMap.put(47, () -> {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			GOTTileEntitySign fake = (GOTTileEntitySign) block.createTileEntity(world, meta);
			fake.setWorldObj(world);
			fake.xCoord = i;
			fake.yCoord = j;
			fake.zCoord = k;
			fake.isFakeGuiSign = true;
			return new GOTGuiEditSign(fake);
		});
		guiMap.put(48, () -> {
			if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof GOTItemCracker) {
				return new GOTGuiCracker(entityplayer);
			}
			return null;
		});
		guiMap.put(49, () -> new GOTGuiCraftingTable.YiTi(entityplayer.inventory, world, i, j, k));
		guiMap.put(52, () -> {
			TileEntity millstone = world.getTileEntity(i, j, k);
			if (millstone instanceof GOTTileEntityMillstone) {
				return new GOTGuiMillstone(entityplayer.inventory, (GOTTileEntityMillstone) millstone);
			}
			return null;
		});
		guiMap.put(53, () -> new GOTGuiAnvil(entityplayer, i, j, k));
		guiMap.put(54, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTGuiAnvil(entityplayer, (GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(55, () -> {
			TileEntity bookshelf;
			if (world.getBlock(i, j, k) == Blocks.bookshelf) {
				world.setBlock(i, j, k, GOTRegistry.bookshelfStorage, 0, 3);
			}
			bookshelf = world.getTileEntity(i, j, k);
			if (bookshelf instanceof GOTTileEntityBookshelf) {
				return new GOTGuiBookshelf(entityplayer.inventory, (GOTTileEntityBookshelf) bookshelf);
			}
			return null;
		});
		guiMap.put(58, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTMercenary) {
				return new GOTGuiMercenaryInteract((GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(59, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTMercenary) {
				return new GOTGuiMercenaryHire(entityplayer, (GOTMercenary) entity, world);
			}
			return null;
		});
		guiMap.put(60, () -> new GOTGuiMap().setConquestGrid());
		guiMap.put(61, GOTGuiBrandingIron::new);
		guiMap.put(62, () -> new GOTGuiCraftingTable.Arryn(entityplayer.inventory, world, i, j, k));
		guiMap.put(65, () -> new GOTGuiCraftingTable.Crownlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(66, () -> new GOTGuiCraftingTable.Dorne(entityplayer.inventory, world, i, j, k));
		guiMap.put(67, () -> new GOTGuiCraftingTable.Dragonstone(entityplayer.inventory, world, i, j, k));
		guiMap.put(68, () -> new GOTGuiCraftingTable.Ghiscar(entityplayer.inventory, world, i, j, k));
		guiMap.put(69, () -> new GOTGuiCraftingTable.Ironborn(entityplayer.inventory, world, i, j, k));
		guiMap.put(70, () -> new GOTGuiCraftingTable.Lorath(entityplayer.inventory, world, i, j, k));
		guiMap.put(71, () -> new GOTGuiCraftingTable.Lys(entityplayer.inventory, world, i, j, k));
		guiMap.put(72, () -> new GOTGuiCraftingTable.Myr(entityplayer.inventory, world, i, j, k));
		guiMap.put(73, () -> new GOTGuiCraftingTable.Norvos(entityplayer.inventory, world, i, j, k));
		guiMap.put(74, () -> new GOTGuiCraftingTable.Pentos(entityplayer.inventory, world, i, j, k));
		guiMap.put(75, () -> new GOTGuiCraftingTable.Qarth(entityplayer.inventory, world, i, j, k));
		guiMap.put(76, () -> new GOTGuiCraftingTable.Qohor(entityplayer.inventory, world, i, j, k));
		guiMap.put(77, () -> new GOTGuiCraftingTable.Reach(entityplayer.inventory, world, i, j, k));
		guiMap.put(78, () -> new GOTGuiCraftingTable.Riverlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(79, () -> new GOTGuiCraftingTable.Stormlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(80, () -> new GOTGuiCraftingTable.Tyrosh(entityplayer.inventory, world, i, j, k));
		guiMap.put(81, () -> new GOTGuiCraftingTable.Volantis(entityplayer.inventory, world, i, j, k));
		guiMap.put(82, () -> new GOTGuiCraftingTable.Westerlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(84, () -> new GOTGuiCraftingTable.Asshai(entityplayer.inventory, world, i, j, k));
		guiMap.put(85, () -> new GOTGuiCraftingTable.Braavos(entityplayer.inventory, world, i, j, k));
		guiMap.put(86, () -> new GOTGuiCraftingTable.Dothraki(entityplayer.inventory, world, i, j, k));
		guiMap.put(87, () -> new GOTGuiCraftingTable.Jogos(entityplayer.inventory, world, i, j, k));
		guiMap.put(88, () -> new GOTGuiCraftingTable.Mossovy(entityplayer.inventory, world, i, j, k));
		Supplier<Object> guiSupplier = guiMap.get(ID);
		if (guiSupplier != null) {
			return guiSupplier.get();
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
		IInventory chest2;
		HashMap<Integer, Supplier<Object>> guiMap = new HashMap<>();
		guiMap.put(0, () ->

		{
			TileEntity oven = world.getTileEntity(i, j, k);
			if (oven instanceof GOTTileEntityOven) {
				return new GOTContainerOven(entityplayer.inventory, (GOTTileEntityOven) oven);
			}
			return null;
		});
		guiMap.put(2, () -> new GOTContainerCraftingTable.Ibben(entityplayer.inventory, world, i, j, k));
		guiMap.put(3, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTTradeable) {
				return new GOTContainerTrade(entityplayer.inventory, (GOTTradeable) entity, world);
			}
			return null;
		});
		guiMap.put(4, GOTGuiIronBank::new);
		guiMap.put(5, () -> {
			TileEntity forge = world.getTileEntity(i, j, k);
			if (forge instanceof GOTTileEntityAlloyForge) {
				return new GOTContainerAlloyForge(entityplayer.inventory, (GOTTileEntityAlloyForge) forge);
			}
			return null;
		});
		guiMap.put(7, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTUnitTradeable) {
				return new GOTContainerUnitTrade(entityplayer, (GOTUnitTradeable) entity, world);
			}
			return null;
		});
		guiMap.put(13, () -> new GOTContainerCraftingTable.North(entityplayer.inventory, world, i, j, k));
		guiMap.put(15, () -> {
			if (GOTItemPouch.isHoldingPouch(entityplayer, i)) {
				return new GOTContainerPouch(entityplayer, i);
			}
			return null;
		});
		guiMap.put(16, () -> {
			TileEntity barrel = world.getTileEntity(i, j, k);
			if (barrel instanceof GOTTileEntityBarrel) {
				return new GOTContainerBarrel(entityplayer.inventory, (GOTTileEntityBarrel) barrel);
			}
			return null;
		});
		guiMap.put(17, () -> {
			TileEntity stand = world.getTileEntity(i, j, k);
			if (stand instanceof GOTTileEntityArmorStand) {
				return new GOTContainerArmorStand(entityplayer.inventory, (GOTTileEntityArmorStand) stand);
			}
			return null;
		});
		guiMap.put(18, () -> new GOTContainerCraftingTable.Hillmen(entityplayer.inventory, world, i, j, k));
		guiMap.put(22, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
					return new GOTContainerHiredFarmerInventory(entityplayer.inventory, npc);
				}
			}
			return null;
		});
		guiMap.put(23, () -> new GOTContainerCraftingTable.Wildling(entityplayer.inventory, world, i, j, k));
		guiMap.put(25, () -> new GOTContainerCraftingTable.Summer(entityplayer.inventory, world, i, j, k));
		guiMap.put(28, () -> new GOTContainerCraftingTable.Gift(entityplayer.inventory, world, i, j, k));
		guiMap.put(29, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityHorse) {
				GOTEntityHorse horse = (GOTEntityHorse) entity;
				return new GOTContainerMountInventory(entityplayer.inventory, GOTReflection.getHorseInv(horse), horse);
			}
			if (entity instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) entity).getMountInventory() != null) {
				return new GOTContainerNPCMountInventory(entityplayer.inventory, ((GOTEntityNPCRideable) entity).getMountInventory(), (GOTEntityNPCRideable) entity);
			}
			return null;
		});
		guiMap.put(35, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				return new GOTContainerCoinExchange(entityplayer, npc);
			}
			return null;
		});
		guiMap.put(37, () -> new GOTContainerCraftingTable.Lhazar(entityplayer.inventory, world, i, j, k));
		guiMap.put(38, () -> {
			TileEntity unsmeltery = world.getTileEntity(i, j, k);
			if (unsmeltery instanceof GOTTileEntityUnsmeltery) {
				return new GOTContainerUnsmeltery(entityplayer.inventory, (GOTTileEntityUnsmeltery) unsmeltery);
			}
			return null;
		});
		guiMap.put(39, () -> new GOTContainerCraftingTable.Sothoryos(entityplayer.inventory, world, i, j, k));
		guiMap.put(40, () -> {
			TileEntity trap = world.getTileEntity(i, j, k);
			if (trap instanceof GOTTileEntitySarbacaneTrap) {
				return new ContainerDispenser(entityplayer.inventory, (GOTTileEntitySarbacaneTrap) trap);
			}
			return null;
		});
		guiMap.put(41, () -> {
			TileEntity chest = world.getTileEntity(i, j, k);
			if (chest instanceof GOTTileEntityChest) {
				return new ContainerChest(entityplayer.inventory, (GOTTileEntityChest) chest);
			}
			return null;
		});
		guiMap.put(46, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer().equals(entityplayer) && npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
					return new GOTContainerHiredWarriorInventory(entityplayer.inventory, npc);
				}
			}
			return null;
		});
		guiMap.put(48, () -> {
			if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() instanceof GOTItemCracker) {
				return new GOTContainerCracker(entityplayer);
			}
			return null;
		});
		guiMap.put(49, () -> new GOTContainerCraftingTable.YiTi(entityplayer.inventory, world, i, j, k));
		guiMap.put(52, () -> {
			TileEntity millstone = world.getTileEntity(i, j, k);
			if (millstone instanceof GOTTileEntityMillstone) {
				return new GOTContainerMillstone(entityplayer.inventory, (GOTTileEntityMillstone) millstone);
			}
			return null;
		});
		guiMap.put(53, () -> new GOTContainerAnvil(entityplayer, i, j, k));
		guiMap.put(54, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTEntityNPC) {
				return new GOTContainerAnvil(entityplayer, (GOTEntityNPC) entity);
			}
			return null;
		});
		guiMap.put(55, () -> {
			TileEntity bookshelf = world.getTileEntity(i, j, k);
			if (bookshelf instanceof GOTTileEntityBookshelf) {
				return new GOTContainerBookshelf(entityplayer.inventory, (GOTTileEntityBookshelf) bookshelf);
			}
			return null;
		});
		guiMap.put(59, () -> {
			Entity entity = world.getEntityByID(i);
			if (entity instanceof GOTMercenary) {
				return new GOTContainerUnitTrade(entityplayer, (GOTMercenary) entity, world);
			}
			return null;
		});
		guiMap.put(62, () -> new GOTContainerCraftingTable.Arryn(entityplayer.inventory, world, i, j, k));
		guiMap.put(65, () -> new GOTContainerCraftingTable.Crownlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(66, () -> new GOTContainerCraftingTable.Dorne(entityplayer.inventory, world, i, j, k));
		guiMap.put(67, () -> new GOTContainerCraftingTable.Dragonstone(entityplayer.inventory, world, i, j, k));
		guiMap.put(68, () -> new GOTContainerCraftingTable.Ghiscar(entityplayer.inventory, world, i, j, k));
		guiMap.put(69, () -> new GOTContainerCraftingTable.Ironborn(entityplayer.inventory, world, i, j, k));
		guiMap.put(70, () -> new GOTContainerCraftingTable.Lorath(entityplayer.inventory, world, i, j, k));
		guiMap.put(71, () -> new GOTContainerCraftingTable.Lys(entityplayer.inventory, world, i, j, k));
		guiMap.put(72, () -> new GOTContainerCraftingTable.Myr(entityplayer.inventory, world, i, j, k));
		guiMap.put(73, () -> new GOTContainerCraftingTable.Norvos(entityplayer.inventory, world, i, j, k));
		guiMap.put(74, () -> new GOTContainerCraftingTable.Pentos(entityplayer.inventory, world, i, j, k));
		guiMap.put(75, () -> new GOTContainerCraftingTable.Qarth(entityplayer.inventory, world, i, j, k));
		guiMap.put(76, () -> new GOTContainerCraftingTable.Qohor(entityplayer.inventory, world, i, j, k));
		guiMap.put(77, () -> new GOTContainerCraftingTable.Reach(entityplayer.inventory, world, i, j, k));
		guiMap.put(78, () -> new GOTContainerCraftingTable.Riverlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(79, () -> new GOTContainerCraftingTable.Stormlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(80, () -> new GOTContainerCraftingTable.Tyrosh(entityplayer.inventory, world, i, j, k));
		guiMap.put(81, () -> new GOTContainerCraftingTable.Volantis(entityplayer.inventory, world, i, j, k));
		guiMap.put(82, () -> new GOTContainerCraftingTable.Westerlands(entityplayer.inventory, world, i, j, k));
		guiMap.put(84, () -> new GOTContainerCraftingTable.Asshai(entityplayer.inventory, world, i, j, k));
		guiMap.put(85, () -> new GOTContainerCraftingTable.Braavos(entityplayer.inventory, world, i, j, k));
		guiMap.put(86, () -> new GOTContainerCraftingTable.Dothraki(entityplayer.inventory, world, i, j, k));
		guiMap.put(87, () -> new GOTContainerCraftingTable.Jogos(entityplayer.inventory, world, i, j, k));
		guiMap.put(88, () -> new GOTContainerCraftingTable.Mossovy(entityplayer.inventory, world, i, j, k));
		Supplier<Object> guiSupplier = guiMap.get(ID);
		if (guiSupplier != null) {
			return guiSupplier.get();
		}
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