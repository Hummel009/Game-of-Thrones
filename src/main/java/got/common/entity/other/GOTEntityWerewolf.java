package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.faction.GOTFaction;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityWerewolf extends GOTEntityNPC implements GOTBiome.ImmuneToFrost {
	public GOTEntityWerewolf(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		addTargetTasks(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		spawnsInDarkness = true;
		faction = GOTFaction.HOSTILE;
		alignmentBonus = 2.0f;
		killAchievement = GOTAchievement.killWerewolf;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(5.0);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
	}

	@Override
	public String getDeathSound() {
		return "got:direwolf.death";
	}

	@Override
	public String getHurtSound() {
		return "got:direwolf.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:direwolf.say";
	}

	@Override
	public float getSoundVolume() {
		return 0.5f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
	}
}