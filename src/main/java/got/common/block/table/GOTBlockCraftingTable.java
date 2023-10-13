package got.common.block.table;

import java.util.ArrayList;
import java.util.Collection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GOTBlockCraftingTable extends Block {
	public static Collection<GOTBlockCraftingTable> allCraftingTables = new ArrayList<>();
	public GOTFaction tableFaction;
	public GOTGuiID tableGUIID;
	@SideOnly(Side.CLIENT)
	public IIcon[] tableIcons;

	public GOTBlockCraftingTable(Material material, GOTFaction faction, GOTGuiID guiID) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(2.5f);
		tableFaction = faction;
		tableGUIID = guiID;
		allCraftingTables.add(this);
	}

	@SideOnly(Side.CLIENT)
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
		if (!world.isRemote) {
			entityplayer.openGui(GOT.instance, tableGUIID.ordinal(), world, i, j, k);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		tableIcons = new IIcon[2];
		tableIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		tableIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
	}
}
