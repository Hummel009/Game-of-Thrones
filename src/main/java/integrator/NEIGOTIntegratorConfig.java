package integrator;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import got.GOT;
import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class NEIGOTIntegratorConfig implements IConfigureNEI {
	public Collection<ItemStack> hiddenItems = new ArrayList<>();

	@Override
	public String getName() {
		return "Game of Thrones";
	}

	@Override
	public String getVersion() {
		return GOT.VERSION;
	}

	public void hideItem(Block block) {
		hideItem(false, block);
	}

	public void hideItem(boolean all, Block block) {
		hideItem(new ItemStack(block), all);
	}

	public void hideItem(ItemStack stack, boolean all) {
		int i = all ? 0 : 8;
		while (i < 16) {
			ItemStack s = new ItemStack(stack.getItem(), 1, i);
			API.hideItem(s);
			hiddenItems.add(s);
			i++;
		}
	}

	@Override
	public void loadConfig() {
		hideItem(true, GOTBlocks.lionBed);
		hideItem(true, GOTBlocks.strawBed);
		hideItem(true, GOTBlocks.furBed);
		hideItem(true, GOTBlocks.berryPie);
		hideItem(true, GOTBlocks.cherryPie);
		hideItem(true, GOTBlocks.pastry);
		hideItem(true, GOTBlocks.appleCrumble);
		hideItem(true, GOTBlocks.bananaCake);
		hideItem(true, GOTBlocks.lemonCake);
		hideItem(true, GOTBlocks.date);
		hideItem(true, GOTBlocks.banana);
		hideItem(true, GOTBlocks.grapevineRed);
		hideItem(true, GOTBlocks.grapevineWhite);
		hideItem(true, GOTBlocks.fuse);
		hideItem(true, GOTBlocks.sothoryosDoubleTorch);
		hideItem(true, GOTBlocks.lettuceCrop);
		hideItem(true, GOTBlocks.pipeweedCrop);
		hideItem(true, GOTBlocks.flaxCrop);
		hideItem(true, GOTBlocks.leekCrop);
		hideItem(true, GOTBlocks.turnipCrop);
		hideItem(true, GOTBlocks.yamCrop);
		hideItem(true, GOTBlocks.spawnerChest);
		hideItem(true, GOTBlocks.spawnerChestStone);
		hideItem(true, GOTBlocks.spawnerChestAncientEssos);
		hideItem(true, GOTBlocks.mug);
		hideItem(true, GOTBlocks.ceramicMug);
		hideItem(true, GOTBlocks.gobletGold);
		hideItem(true, GOTBlocks.gobletSilver);
		hideItem(true, GOTBlocks.gobletCopper);
		hideItem(true, GOTBlocks.gobletWood);
		hideItem(true, GOTBlocks.skullCup);
		hideItem(true, GOTBlocks.wineGlass);
		hideItem(true, GOTBlocks.glassBottle);
		hideItem(true, GOTBlocks.aleHorn);
		hideItem(true, GOTBlocks.aleHornGold);
		hideItem(true, GOTBlocks.plate);
		hideItem(true, GOTBlocks.ceramicPlate);
		hideItem(true, GOTBlocks.woodPlate);
		hideItem(true, GOTBlocks.flowerPot);
		hideItem(true, GOTBlocks.armorStand);
		hideItem(true, GOTBlocks.marshLights);
		hideItem(true, GOTBlocks.signCarved);
		hideItem(true, GOTBlocks.signCarvedGlowing);
		hideItem(true, GOTBlocks.bookshelfStorage);
		hideItem(true, GOTBlocks.slabDouble1);
		hideItem(true, GOTBlocks.slabDouble2);
		hideItem(true, GOTBlocks.slabDouble3);
		hideItem(true, GOTBlocks.slabDouble4);
		hideItem(true, GOTBlocks.slabDouble5);
		hideItem(true, GOTBlocks.slabDouble6);
		hideItem(true, GOTBlocks.slabDouble7);
		hideItem(true, GOTBlocks.slabDouble8);
		hideItem(true, GOTBlocks.slabDouble9);
		hideItem(true, GOTBlocks.slabDouble10);
		hideItem(true, GOTBlocks.slabDouble11);
		hideItem(true, GOTBlocks.slabDouble12);
		hideItem(true, GOTBlocks.slabDoubleV);
		hideItem(true, GOTBlocks.slabDoubleThatch);
		hideItem(true, GOTBlocks.slabDoubleDirt);
		hideItem(true, GOTBlocks.slabDoubleSand);
		hideItem(true, GOTBlocks.slabDoubleGravel);
		hideItem(true, GOTBlocks.rottenSlabDouble);
		hideItem(true, GOTBlocks.scorchedSlabDouble);
		hideItem(true, GOTBlocks.slabClayTileDouble);
		hideItem(true, GOTBlocks.slabClayTileDyedDouble1);
		hideItem(true, GOTBlocks.slabClayTileDyedDouble2);
		hideItem(true, GOTBlocks.slabBoneDouble);
		hideItem(true, GOTBlocks.woodSlabDouble1);
		hideItem(true, GOTBlocks.woodSlabDouble2);
		hideItem(true, GOTBlocks.woodSlabDouble3);
		hideItem(true, GOTBlocks.woodSlabDouble4);
		hideItem(true, GOTBlocks.woodSlabDouble5);
		hideItem(GOTBlocks.slabSingle1);
		hideItem(GOTBlocks.slabSingle2);
		hideItem(GOTBlocks.slabSingle3);
		hideItem(GOTBlocks.slabSingle4);
		hideItem(GOTBlocks.slabSingle5);
		hideItem(GOTBlocks.slabSingle6);
		hideItem(GOTBlocks.slabSingle7);
		hideItem(GOTBlocks.slabSingle8);
		hideItem(GOTBlocks.slabSingle9);
		hideItem(GOTBlocks.slabSingle10);
		hideItem(GOTBlocks.slabSingle11);
		hideItem(GOTBlocks.slabSingle12);
		hideItem(GOTBlocks.leavesSnowy);
		hideItem(GOTBlocks.slabSingleV);
		hideItem(GOTBlocks.slabSingleThatch);
		hideItem(GOTBlocks.slabSingleDirt);
		hideItem(GOTBlocks.slabSingleSand);
		hideItem(GOTBlocks.slabSingleGravel);
		hideItem(GOTBlocks.rottenSlabSingle);
		hideItem(GOTBlocks.scorchedSlabSingle);
		hideItem(GOTBlocks.slabClayTileSingle);
		hideItem(GOTBlocks.slabClayTileDyedSingle1);
		hideItem(GOTBlocks.slabClayTileDyedSingle2);
		hideItem(GOTBlocks.slabBoneSingle);
		hideItem(GOTBlocks.woodSlabSingle1);
		hideItem(GOTBlocks.woodSlabSingle2);
		hideItem(GOTBlocks.woodSlabSingle3);
		hideItem(GOTBlocks.woodSlabSingle4);
		hideItem(GOTBlocks.woodSlabSingle5);
	}
}
