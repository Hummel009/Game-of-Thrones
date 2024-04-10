package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTBlockOreGem extends Block {
	private final String[] oreNames = {"topaz", "amethyst", "sapphire", "ruby", "amber", "diamond", "opal", "emerald"};

	@SideOnly(Side.CLIENT)
	private IIcon[] oreIcons;

	public GOTBlockOreGem() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(3.0f);
		setResistance(5.0f);
		setStepSound(soundTypeStone);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
		super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
		if (getItemDropped(meta, world.rand, fortune) != Item.getItemFromBlock(this)) {
			int amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
			dropXpOnBlockBreak(world, i, j, k, amountXp);
		}
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= oreNames.length) {
			j1 = 0;
		}
		return oreIcons[j1];
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		switch (i) {
			case 0:
				return GOTItems.topaz;
			case 1:
				return GOTItems.amethyst;
			case 2:
				return GOTItems.sapphire;
			case 3:
				return GOTItems.ruby;
			case 4:
				return GOTItems.amber;
			case 5:
				return GOTItems.diamond;
			case 6:
				return GOTItems.opal;
			case 7:
				return GOTItems.emerald;
			default:
				break;
		}
		return Item.getItemFromBlock(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < oreNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 1 + random.nextInt(2);
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		if (i > 0 && Item.getItemFromBlock(this) != getItemDropped(0, random, i)) {
			int drops = quantityDropped(random);
			return drops + random.nextInt(i + 1);
		}
		return quantityDropped(random);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		oreIcons = new IIcon[oreNames.length];
		for (int i = 0; i < oreNames.length; ++i) {
			oreIcons[i] = iconregister.registerIcon(getTextureName() + '_' + oreNames[i]);
		}
	}
}
