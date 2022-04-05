package integrator;

import java.util.ArrayList;

import codechicken.nei.api.*;
import got.GOT;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class NEIGOTIntegratorConfig implements IConfigureNEI {
	private ArrayList<ItemStack> hiddenItems = new ArrayList();

	@Override
	public String getName() {
		return GOT.NAME;
	}

	@Override
	public String getVersion() {
		return GOT.VERSION;
	}

	private void hideItem(Block block) {
		this.hideItem(false, block);
	}

	private void hideItem(boolean all, Block block) {
		this.hideItem(new ItemStack(block), all);
	}

	private void hideItem(ItemStack stack, boolean all) {
		int i;
		i = all ? 0 : 8;
		while (i < 16) {
			ItemStack s = new ItemStack(stack.getItem(), 1, i);
			API.hideItem(s);
			getHiddenItems().add(s);
			++i;
		}
	}

	@Override
	public void loadConfig() {
		hideItem(true, GOTRegistry.lionBed);
		hideItem(true, GOTRegistry.strawBed);
		hideItem(true, GOTRegistry.furBed);
		hideItem(true, GOTRegistry.berryPie);
		hideItem(true, GOTRegistry.cherryPie);
		hideItem(true, GOTRegistry.pastry);
		hideItem(true, GOTRegistry.appleCrumble);
		hideItem(true, GOTRegistry.bananaCake);
		hideItem(true, GOTRegistry.lemonCake);
		hideItem(true, GOTRegistry.dateBlock);
		hideItem(true, GOTRegistry.bananaBlock);
		hideItem(true, GOTRegistry.grapevineRed);
		hideItem(true, GOTRegistry.grapevineWhite);
		hideItem(true, GOTRegistry.fuse);
		hideItem(true, GOTRegistry.sothoryosDoubleTorch);
		hideItem(true, GOTRegistry.lettuceCrop);
		hideItem(true, GOTRegistry.pipeweedCrop);
		hideItem(true, GOTRegistry.flaxCrop);
		hideItem(true, GOTRegistry.leekCrop);
		hideItem(true, GOTRegistry.turnipCrop);
		hideItem(true, GOTRegistry.yamCrop);
		hideItem(true, GOTRegistry.spawnerChest);
		hideItem(true, GOTRegistry.spawnerChestStone);
		hideItem(true, GOTRegistry.spawnerChestAncientEssos);
		hideItem(true, GOTRegistry.mugBlock);
		hideItem(true, GOTRegistry.ceramicMugBlock);
		hideItem(true, GOTRegistry.gobletGoldBlock);
		hideItem(true, GOTRegistry.gobletSilverBlock);
		hideItem(true, GOTRegistry.gobletCopperBlock);
		hideItem(true, GOTRegistry.gobletWoodBlock);
		hideItem(true, GOTRegistry.skullCupBlock);
		hideItem(true, GOTRegistry.wineGlassBlock);
		hideItem(true, GOTRegistry.glassBottleBlock);
		hideItem(true, GOTRegistry.aleHornBlock);
		hideItem(true, GOTRegistry.aleHornGoldBlock);
		hideItem(true, GOTRegistry.plateBlock);
		hideItem(true, GOTRegistry.ceramicPlateBlock);
		hideItem(true, GOTRegistry.woodPlateBlock);
		hideItem(true, GOTRegistry.flowerPot);
		hideItem(true, GOTRegistry.armorStand);
		hideItem(true, GOTRegistry.marshLights);
		hideItem(true, GOTRegistry.signCarved);
		hideItem(true, GOTRegistry.signCarvedGlowing);
		hideItem(true, GOTRegistry.bookshelfStorage);
		hideItem(true, GOTRegistry.slabDouble1);
		hideItem(true, GOTRegistry.slabDouble2);
		hideItem(true, GOTRegistry.slabDouble3);
		hideItem(true, GOTRegistry.slabDouble4);
		hideItem(true, GOTRegistry.slabDouble5);
		hideItem(true, GOTRegistry.slabDouble6);
		hideItem(true, GOTRegistry.slabDouble7);
		hideItem(true, GOTRegistry.slabDouble8);
		hideItem(true, GOTRegistry.slabDouble9);
		hideItem(true, GOTRegistry.slabDouble10);
		hideItem(true, GOTRegistry.slabDouble11);
		hideItem(true, GOTRegistry.slabDouble12);
		hideItem(true, GOTRegistry.slabDoubleV);
		hideItem(true, GOTRegistry.slabDoubleThatch);
		hideItem(true, GOTRegistry.slabDoubleDirt);
		hideItem(true, GOTRegistry.slabDoubleSand);
		hideItem(true, GOTRegistry.slabDoubleGravel);
		hideItem(true, GOTRegistry.rottenSlabDouble);
		hideItem(true, GOTRegistry.scorchedSlabDouble);
		hideItem(true, GOTRegistry.slabClayTileDouble);
		hideItem(true, GOTRegistry.slabClayTileDyedDouble1);
		hideItem(true, GOTRegistry.slabClayTileDyedDouble2);
		hideItem(true, GOTRegistry.slabBoneDouble);
		hideItem(true, GOTRegistry.woodSlabDouble1);
		hideItem(true, GOTRegistry.woodSlabDouble2);
		hideItem(true, GOTRegistry.woodSlabDouble3);
		hideItem(true, GOTRegistry.woodSlabDouble4);
		hideItem(true, GOTRegistry.woodSlabDouble5);
		hideItem(GOTRegistry.slabSingle1);
		hideItem(GOTRegistry.slabSingle2);
		hideItem(GOTRegistry.slabSingle3);
		hideItem(GOTRegistry.slabSingle4);
		hideItem(GOTRegistry.slabSingle5);
		hideItem(GOTRegistry.slabSingle6);
		hideItem(GOTRegistry.slabSingle7);
		hideItem(GOTRegistry.slabSingle8);
		hideItem(GOTRegistry.slabSingle9);
		hideItem(GOTRegistry.slabSingle10);
		hideItem(GOTRegistry.slabSingle11);
		hideItem(GOTRegistry.slabSingle12);
		hideItem(GOTRegistry.leavesSnowy);
		hideItem(GOTRegistry.slabSingleV);
		hideItem(GOTRegistry.slabSingleThatch);
		hideItem(GOTRegistry.slabSingleDirt);
		hideItem(GOTRegistry.slabSingleSand);
		hideItem(GOTRegistry.slabSingleGravel);
		hideItem(GOTRegistry.rottenSlabSingle);
		hideItem(GOTRegistry.scorchedSlabSingle);
		hideItem(GOTRegistry.slabClayTileSingle);
		hideItem(GOTRegistry.slabClayTileDyedSingle1);
		hideItem(GOTRegistry.slabClayTileDyedSingle2);
		hideItem(GOTRegistry.slabBoneSingle);
		hideItem(GOTRegistry.woodSlabSingle1);
		hideItem(GOTRegistry.woodSlabSingle2);
		hideItem(GOTRegistry.woodSlabSingle3);
		hideItem(GOTRegistry.woodSlabSingle4);
		hideItem(GOTRegistry.woodSlabSingle5);
	}

	public ArrayList<ItemStack> getHiddenItems() {
		return hiddenItems;
	}

	public void setHiddenItems(ArrayList<ItemStack> hiddenItems) {
		this.hiddenItems = hiddenItems;
	}
}
