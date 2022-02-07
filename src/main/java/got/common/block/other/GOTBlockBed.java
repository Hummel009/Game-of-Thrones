package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class GOTBlockBed extends BlockBed {
	private Item bedItem;
	private Block bedBottomBlock;
	private int bedBottomMetadata;
	@SideOnly(value = Side.CLIENT)
	private IIcon[] bedIconsEnd;
	@SideOnly(value = Side.CLIENT)
	private IIcon[] bedIconsSide;
	@SideOnly(value = Side.CLIENT)
	private IIcon[] bedIconsTop;

	public GOTBlockBed(Block block, int k) {
		bedBottomBlock = block;
		bedBottomMetadata = k;
		setHardness(0.2f);
		setStepSound(Block.soundTypeWood);
	}

	public Item getBedItem() {
		return bedItem;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int i1;
		if (i == 0) {
			return bedBottomBlock.getIcon(0, bedBottomMetadata);
		}
		int k = BlockDirectional.getDirection(j);
		int l = Direction.bedDirection[k][i];
		i1 = BlockBed.isBlockHeadOfBed(j) ? 1 : 0;
		return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? l != 5 && l != 4 ? bedIconsTop[i1] : bedIconsSide[i1] : bedIconsEnd[i1];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return getBedItem();
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return BlockBed.isBlockHeadOfBed(i) ? null : getBedItem();
	}

	@Override
	public boolean isBed(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
		return true;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		bedIconsTop = new IIcon[] { iconregister.registerIcon(getTextureName() + "_feet_top"), iconregister.registerIcon(getTextureName() + "_head_top") };
		bedIconsEnd = new IIcon[] { iconregister.registerIcon(getTextureName() + "_feet_end"), iconregister.registerIcon(getTextureName() + "_head_end") };
		bedIconsSide = new IIcon[] { iconregister.registerIcon(getTextureName() + "_feet_side"), iconregister.registerIcon(getTextureName() + "_head_side") };
	}

	public void setBedItem(Item bedItem) {
		this.bedItem = bedItem;
	}
}
