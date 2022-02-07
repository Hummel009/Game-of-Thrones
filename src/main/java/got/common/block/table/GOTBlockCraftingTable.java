package got.common.block.table;

import java.util.*;

import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.faction.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTBlockCraftingTable extends Block {
	private static List<GOTBlockCraftingTable> allCraftingTables = new ArrayList<>();
	private GOTFaction tableFaction;
	private int tableGUIID;

	public GOTBlockCraftingTable(Material material, GOTFaction faction, int guiID) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(2.5f);
		setTableFaction(faction);
		setTableGUIID(guiID);
		getAllCraftingTables().add(this);
	}

	public GOTFaction getTableFaction() {
		return tableFaction;
	}

	public int getTableGUIID() {
		return tableGUIID;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		boolean hasRequiredAlignment;
		hasRequiredAlignment = true;
		if (hasRequiredAlignment) {
			if (!world.isRemote) {
				entityplayer.openGui(GOT.getInstance(), getTableGUIID(), world, i, j, k);
			}
		} else {
			for (int l = 0; l < 8; ++l) {
				double d = i + world.rand.nextFloat();
				double d1 = j + 1.0;
				double d2 = k + world.rand.nextFloat();
				world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
			}
			if (!world.isRemote) {
				GOTAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, getTableFaction());
			}
		}
		return true;
	}

	public void setTableFaction(GOTFaction tableFaction) {
		this.tableFaction = tableFaction;
	}

	public void setTableGUIID(int tableGUIID) {
		this.tableGUIID = tableGUIID;
	}

	public static List<GOTBlockCraftingTable> getAllCraftingTables() {
		return allCraftingTables;
	}

	public static void setAllCraftingTables(List<GOTBlockCraftingTable> allCraftingTables) {
		GOTBlockCraftingTable.allCraftingTables = allCraftingTables;
	}
}
