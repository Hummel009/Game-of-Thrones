package got.common.block.other;

import java.util.Random;

import got.common.GOTLevelData;
import got.common.database.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTBlockOre extends Block {
	public GOTBlockOre() {
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
			int amountXp = 0;

			if (this == GOTRegistry.oreGlowstone) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
			}

			if (this == GOTRegistry.oreSulfur || this == GOTRegistry.oreSaltpeter) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
			}
			dropXpOnBlockBreak(world, i, j, k, amountXp);
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {

		if (this == GOTRegistry.oreGlowstone) {
			return Items.glowstone_dust;
		}
		if (this == GOTRegistry.oreSulfur) {
			return GOTRegistry.sulfur;
		}
		if (this == GOTRegistry.oreSaltpeter) {
			return GOTRegistry.saltpeter;
		}
		return Item.getItemFromBlock(this);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		super.harvestBlock(world, entityplayer, i, j, k, l);
		if (!world.isRemote) {
			if (this == GOTRegistry.oreValyrian) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.mineValyrian);
			}
			if (this == GOTRegistry.oreGlowstone) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.mineGlowstone);
			}
		}
	}

	@Override
	public int quantityDropped(Random random) {

		if (this == GOTRegistry.oreGlowstone) {
			return 2 + random.nextInt(4);
		}
		if (this == GOTRegistry.oreSulfur || this == GOTRegistry.oreSaltpeter) {
			return 1 + random.nextInt(2);
		}
		return 1;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		if (i > 0 && Item.getItemFromBlock(this) != getItemDropped(0, random, i)) {
			int factor = random.nextInt(i + 2) - 1;
			factor = Math.max(factor, 0);
			int drops = this.quantityDropped(random) * (factor + 1);
			if (this == GOTRegistry.oreGlowstone) {
				drops = Math.min(drops, 8);
			}
			return drops;
		}
		return this.quantityDropped(random);
	}
}
