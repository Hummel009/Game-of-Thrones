package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockDoor extends BlockDoor {
	public GOTBlockDoor() {
		this(Material.wood);
		setStepSound(Block.soundTypeWood);
		setHardness(3.0f);
	}

	public GOTBlockDoor(Material material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabUtil);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return (i & 8) != 0 ? null : Item.getItemFromBlock(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getDoorRenderID();
	}
}
