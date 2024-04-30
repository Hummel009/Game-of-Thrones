package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIDrink;
import got.common.entity.ai.GOTEntityAIEat;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityHummel009 extends GOTEntityNPC implements GOTBiome.ImmuneToFrost, GOTBiome.ImmuneToHeat {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHummel009(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		addTargetTasks(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIOpenDoor(this, true));
		tasks.addTask(3, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new GOTEntityAIEat(this, GOTFoods.WESTEROS, 8000));
		tasks.addTask(5, new GOTEntityAIDrink(this, GOTFoods.WESTEROS_DRINK, 8000));
		tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		isImmuneToFire = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		return super.attackEntityFrom(damagesource, 0);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropItem(GOTItems.crowbar, 64);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return "legendary/hummel";
	}

	@Override
	public boolean speakTo(EntityPlayer entityplayer) {
		String speechBank = getSpeechBank(entityplayer);
		if (speechBank != null) {
			sendSpeechBank(entityplayer, speechBank);
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.freeman);
			return true;
		}
		return false;
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.crowbar));
		npcItemsInv.setIdleItem(null);
		return entityData;
	}
}