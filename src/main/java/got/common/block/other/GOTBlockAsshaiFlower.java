package got.common.block.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import got.common.world.biome.essos.GOTBiomeShadowLand;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTBlockAsshaiFlower extends GOTBlockFlower {
	public GOTBlockAsshaiFlower() {
		float f = 0.125f;
		setFlowerBounds(f, 0.0f, f, 1.0f - f, 0.8f, 1.0f - f);
		setTickRandomly(true);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return super.canBlockStay(world, i, j, k) || GOTBiomeShadowLand.isBlackSurface(world, i, j - 1, k);
	}

	public boolean isEntityVulnerable(EntityLivingBase entity) {
		if (GOT.getNPCFaction(entity) == GOTFaction.ASSHAI) {
			return false;
		}
		if (entity instanceof EntityPlayer) {
			float max;
			EntityPlayer entityplayer = (EntityPlayer) entity;
			if (entityplayer.capabilities.isCreativeMode) {
				return false;
			}
			float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.ASSHAI);
			if (alignment >= (max = 100.0f)) {
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
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		EntityLivingBase living;
		super.onEntityCollidedWithBlock(world, i, j, k, entity);
		if (!world.isRemote && entity instanceof EntityLivingBase && isEntityVulnerable(living = (EntityLivingBase) entity)) {
			int dur = 100;
			living.addPotionEffect(new PotionEffect(Potion.poison.id, dur));
			living.addPotionEffect(new PotionEffect(Potion.blindness.id, dur * 2));
		}
	}

	@SideOnly(value = Side.CLIENT)
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

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		super.updateTick(world, i, j, k, random);
		if (!world.isRemote && world.getBiomeGenForCoords(i, k) instanceof GOTBiomeShadowLand) {
			double range = 5.0;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
			for (Object obj : entities) {
				EntityLivingBase entity = (EntityLivingBase) obj;
				if (!isEntityVulnerable(entity)) {
					continue;
				}
				int dur = 200;
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, dur));
			}
		}
	}
}
