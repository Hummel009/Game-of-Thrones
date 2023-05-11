package got.common.block.table;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GOTBlockCraftingTable extends Block {
	public static List<GOTBlockCraftingTable> allCraftingTables = new ArrayList<>();
	public GOTFaction tableFaction;
	public int tableGUIID;
	@SideOnly(value = Side.CLIENT)
	public IIcon[] tableIcons;

	public GOTBlockCraftingTable(Material material, GOTFaction faction, int guiID) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(2.5f);
		tableFaction = faction;
		tableGUIID = guiID;
		allCraftingTables.add(this);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return tableIcons[1];
		}
		if (i == 0) {
			return Blocks.planks.getIcon(0, 0);
		}
		return tableIcons[0];
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		boolean hasRequiredAlignment;
		hasRequiredAlignment = true;
		if (hasRequiredAlignment) {
			if (!world.isRemote) {
				entityplayer.openGui(GOT.instance, tableGUIID, world, i, j, k);
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

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		tableIcons = new IIcon[2];
		tableIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		tableIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
	}
}
