package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import got.common.world.biome.essos.GOTBiomeShadowLand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockAsshaiFlower extends GOTBlockFlower {
	public GOTBlockAsshaiFlower() {
		float f = 0.125f;
		setFlowerBounds(f, 0.0f, f, 1.0f - f, 0.8f, 1.0f - f);
		setTickRandomly(true);
	}

	private static boolean isEntityVulnerable(EntityLivingBase entity) {
		if (GOT.getNPCFaction(entity) == GOTFaction.ASSHAI) {
			return false;
		}
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			if (entityplayer.capabilities.isCreativeMode) {
				return false;
			}
			float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.ASSHAI);
			float max = 100.0f;
			if (alignment >= max) {
				return false;
			}
			if (alignment > 0.0f) {
				float f = alignment / max;
				f = 1.0f - f;
				return entity.getRNG().nextFloat() < f;
			}
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return super.canBlockStay(world, i, j, k) || GOTBiomeShadowLand.isBlackSurface(world, i, j - 1, k);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		EntityLivingBase living;
		super.onEntityCollidedWithBlock(world, i, j, k, entity);
		if (!world.isRemote && entity instanceof EntityLivingBase && isEntityVulnerable(living = (EntityLivingBase) entity)) {
			int dur = 100;
			living.addPotionEffect(new PotionEffect(Potion.poison.id, dur));
			living.addPotionEffect(new PotionEffect(Potion.blindness.id, dur * 2));
			living.addPotionEffect(new PotionEffect(Potion.confusion.id, dur));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(4) == 0) {
			double d = i + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
			double d1 = j + MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
			double d2 = k + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
			if (random.nextBoolean()) {
				GOT.proxy.spawnParticle("asshaiWater", d, d1, d2, 0.0, 0.0, 0.0);
			} else {
				GOT.proxy.spawnParticle("whiteSmoke", d, d1, d2, 0.0, 0.0, 0.0);
			}
		}
	}
}