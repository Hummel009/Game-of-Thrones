package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockCoralReef extends Block {
	private static final String[] PLANT_NAMES = {"purple", "yellow", "blue", "red", "green"};
	private static final Random ICON_RAND = new Random();

	private IIcon[] plantIcons;

	public GOTBlockCoralReef() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(1.0f);
		setResistance(5.0f);
		setStepSound(soundTypeStone);
		setLightLevel(1.0f);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
		super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
		int amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
		dropXpOnBlockBreak(world, i, j, k, amountXp);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return super.getIcon(i, j);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return GOTItems.coral;
	}

	public IIcon getRandomPlantIcon(int i, int j, int k) {
		int hash = i * 25799626 ^ k * 6879038 ^ j;
		ICON_RAND.setSeed(hash);
		ICON_RAND.setSeed(ICON_RAND.nextLong());
		return plantIcons[ICON_RAND.nextInt(plantIcons.length)];
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getCoralRenderID();
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob)) {
			entity.attackEntityFrom(DamageSource.cactus, 0.5f);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 1 + random.nextInt(2);
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		int drops = quantityDropped(random);
		if (i > 0) {
			int factor = random.nextInt(i + 2) - 1;
			factor = Math.max(factor, 0);
			drops *= factor + 1;
		}
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
		plantIcons = new IIcon[PLANT_NAMES.length];
		for (int i = 0; i < PLANT_NAMES.length; ++i) {
			plantIcons[i] = iconregister.registerIcon(getTextureName() + '_' + PLANT_NAMES[i]);
		}
	}
}
