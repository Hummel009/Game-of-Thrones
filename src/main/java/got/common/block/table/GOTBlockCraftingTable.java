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
	public static List<GOTBlockCraftingTable> allCraftingTables = new ArrayList<>();
	public GOTFaction tableFaction;
	public int tableGUIID;

	public GOTBlockCraftingTable(Material material, GOTFaction faction, int guiID) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(2.5f);
		tableFaction = faction;
		tableGUIID = guiID;
		allCraftingTables.add(this);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		boolean hasRequiredAlignment;
		hasRequiredAlignment = true;
		if (hasRequiredAlignment) {
			if (!world.isRemote) {
				entityplayer.openGui(GOT.getInstance(), tableGUIID, world, i, j, k);
			}
		} else {
			for (int l = 0; l < 8; ++l) {
				double d = i + world.rand.nextFloat();
				double d1 = j + 1.0;
				double d2 = k + world.rand.nextFloat();
				world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
			}
			if (!world.isRemote) {
				GOTAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, tableFaction);
			}
		}
		return true;
	}
}
