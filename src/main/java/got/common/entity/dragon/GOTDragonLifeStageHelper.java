package got.common.entity.dragon;

import got.common.util.GOTLog;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GOTDragonLifeStageHelper extends GOTDragonHelper {
	public GOTDragonLifeStage lifeStagePrev;
	public GOTDragonScaleModifier scaleModifier = new GOTDragonScaleModifier();
	public int eggWiggleX;
	public int eggWiggleZ;

	public GOTDragonLifeStageHelper(GOTEntityDragon dragon) {
		super(dragon);
	}

	@Override
	public void applyEntityAttributes() {
		scaleModifier.setScale(getScale());
		dragon.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(scaleModifier);
		dragon.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(scaleModifier);
	}

	public int getEggWiggleX() {
		return eggWiggleX;
	}

	public int getEggWiggleZ() {
		return eggWiggleZ;
	}

	public GOTDragonLifeStage getLifeStage() {
		int age = dragon.getGrowingAge();
		if (age >= GOTDragonLifeStage.ADULT.ageLimit) {
			return GOTDragonLifeStage.ADULT;
		}
		if (age >= GOTDragonLifeStage.JUVENILE.ageLimit) {
			return GOTDragonLifeStage.JUVENILE;
		}
		if (age >= GOTDragonLifeStage.HATCHLING.ageLimit) {
			return GOTDragonLifeStage.HATCHLING;
		}
		return GOTDragonLifeStage.EGG;
	}

	public void setLifeStage(GOTDragonLifeStage lifeStage) {
		GOTLog.getLogger().trace("setLifeStage({})", lifeStage);
		dragon.setGrowingAge(lifeStage.ageLimit);
		updateLifeStage();
	}

	public float getScale() {

		if (isEgg()) {
			return 0.9f / GOTEntityDragon.BASE_WIDTH;
		}

		return 1 - dragon.getGrowingAge() / (float) GOTDragonLifeStage.EGG.ageLimit;
	}

	public boolean isAdult() {
		return getLifeStage() == GOTDragonLifeStage.ADULT;
	}

	public boolean isEgg() {
		return getLifeStage() == GOTDragonLifeStage.EGG;
	}

	public boolean isHatchling() {
		return getLifeStage() == GOTDragonLifeStage.HATCHLING;
	}

	public boolean isJuvenile() {
		return getLifeStage() == GOTDragonLifeStage.JUVENILE;
	}

	@Override
	public void onDeath() {
		if (dragon.isClient() && isEgg()) {
			playEggCrackEffect();
		}
	}

	@Override
	public void onLivingUpdate() {
		updateLifeStage();
		updateEgg();
		updateScale();
	}

	public void onNewLifeStage(GOTDragonLifeStage lifeStage, GOTDragonLifeStage prevLifeStage) {
		GOTLog.getLogger().trace("onNewLifeStage({},{})", prevLifeStage, lifeStage);

		if (dragon.isClient()) {
			if (prevLifeStage == GOTDragonLifeStage.EGG && lifeStage == GOTDragonLifeStage.HATCHLING) {
				playEggCrackEffect();
			}
		} else {

			dragon.setCanFly(lifeStage != GOTDragonLifeStage.EGG && lifeStage != GOTDragonLifeStage.HATCHLING);

			dragon.getNavigator().setEnterDoors(lifeStage == GOTDragonLifeStage.HATCHLING);

			if (lifeStage == GOTDragonLifeStage.EGG) {
				dragon.setPathToEntity(null);
				dragon.setAttackTarget(null);
			}

			IAttributeInstance healthAttrib = dragon.getEntityAttribute(SharedMonsterAttributes.maxHealth);
			IAttributeInstance damageAttrib = dragon.getEntityAttribute(SharedMonsterAttributes.attackDamage);

			healthAttrib.removeModifier(scaleModifier);
			damageAttrib.removeModifier(scaleModifier);

			scaleModifier.setScale(getScale());

			healthAttrib.applyModifier(scaleModifier);
			damageAttrib.applyModifier(scaleModifier);

			dragon.setHealth(dragon.getMaxHealth());
		}
	}

	public void playEggCrackEffect() {
		int bx = (int) Math.round(dragon.posX - 0.5);
		int by = (int) Math.round(dragon.posY);
		int bz = (int) Math.round(dragon.posZ - 0.5);
		dragon.worldObj.playAuxSFX(2001, bx, by, bz, Block.getIdFromBlock(Blocks.dragon_egg));
	}

	public void transformToEgg() {
		if (dragon.getHealth() <= 0) {

			return;
		}

		GOTLog.getLogger().debug("transforming to egg");
		float volume = 1;
		float pitch = 0.5f + (0.5f - rand.nextFloat()) * 0.1f;
		dragon.worldObj.playSoundAtEntity(dragon, "mob.endermen.portal", volume, pitch);

		if (dragon.isSaddled()) {
			dragon.dropItem(Items.saddle, 1);
		}

		dragon.entityDropItem(new ItemStack(Blocks.dragon_egg), 0);
		dragon.setDead();
	}

	public void updateEgg() {
		if (!isEgg()) {
			return;
		}

		int age = dragon.getGrowingAge();

		int eggAge = GOTDragonLifeStage.EGG.ageLimit;
		int hatchAge = GOTDragonLifeStage.HATCHLING.ageLimit;
		float chance = (age - eggAge) / (float) (hatchAge - eggAge);

		if (chance > 0.66f) {

			chance /= 60;
			if (eggWiggleX > 0) {
				eggWiggleX--;
			} else if (rand.nextFloat() < chance) {
				eggWiggleX = rand.nextBoolean() ? 10 : 20;
				playEggCrackEffect();
			}
			if (eggWiggleZ > 0) {
				eggWiggleZ--;
			} else if (rand.nextFloat() < chance) {
				eggWiggleZ = rand.nextBoolean() ? 10 : 20;
				playEggCrackEffect();
			}
		}

		rand.nextDouble();
		rand.nextDouble();
		rand.nextDouble();
		rand.nextDouble();
		rand.nextDouble();
		rand.nextDouble();
	}

	public void updateLifeStage() {

		GOTDragonLifeStage lifeStage = getLifeStage();
		if (lifeStagePrev != lifeStage) {
			onNewLifeStage(lifeStage, lifeStagePrev);
			lifeStagePrev = lifeStage;
		}
	}

	public void updateScale() {
		dragon.setScalePublic(getScale());
	}
}
