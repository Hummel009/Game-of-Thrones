package got.common.entity.animal;

import java.util.UUID;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAvoidWithChance;
import got.common.entity.other.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityDikDik extends EntityCreature implements GOTAmbientCreature, GOTRandomSkinEntity {
	public GOTEntityDikDik(World world) {
		super(world);
		setSize(0.6f, 1.0f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAvoidEntity(this, GOTEntityLionBase.class, 12.0f, 1.5, 2.0));
		tasks.addTask(1, new GOTEntityAIAvoidWithChance(this, EntityPlayer.class, 12.0f, 1.5, 2.0, 0.1f));
		tasks.addTask(2, new EntityAIPanic(this, 2.0));
		tasks.addTask(3, new EntityAIWander(this, 1.2));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
		tasks.addTask(5, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		Block block = worldObj.getBlock(i, j - 1, k);
		if (block == Blocks.grass) {
			return 10.0f;
		}
		return worldObj.getLightBrightness(i, j, k) - 0.5f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			return GOTAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "got:deer.death";
	}

	@Override
	public String getHurtSound() {
		return "got:deer.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:deer.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTRegistry.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 1.3f;
	}

	@Override
	public int getTalkInterval() {
		return 300;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
