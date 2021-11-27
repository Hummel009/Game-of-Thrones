package got.common.block.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTBlockOreGem extends Block {
	@SideOnly(value = Side.CLIENT)
	public IIcon[] oreIcons;
	public String[] oreNames = { "topaz", "amethyst", "sapphire", "ruby", "amber", "diamond", "opal", "emerald" };

	public GOTBlockOreGem() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(3.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeStone);
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

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= oreNames.length) {
			j = 0;
		}
		return oreIcons[j];
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		switch (i) {
		case 0:
			return GOTRegistry.topaz;
		case 1:
			return GOTRegistry.amethyst;
		case 2:
			return GOTRegistry.sapphire;
		case 3:
			return GOTRegistry.ruby;
		case 4:
			return GOTRegistry.amber;
		case 5:
			return GOTRegistry.diamond;
		case 6:
			return GOTRegistry.opal;
		case 7:
			return GOTRegistry.emerald;
		default:
			break;
		}
		return Item.getItemFromBlock(this);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
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
			int drops = this.quantityDropped(random);
			return drops += random.nextInt(i + 1);
		}
		return this.quantityDropped(random);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		oreIcons = new IIcon[oreNames.length];
		for (int i = 0; i < oreNames.length; ++i) {
			oreIcons[i] = iconregister.registerIcon(getTextureName() + "_" + oreNames[i]);
		}
	}
}
