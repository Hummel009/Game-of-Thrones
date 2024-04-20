package got.common.entity.ai;

import got.GOT;
import got.common.entity.animal.GOTEntityAnimalMF;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTEntityAIMFMate extends EntityAIBase {
	private final GOTEntityAnimalMF theAnimal;
	private final World theWorld;
	private final double moveSpeed;

	private GOTEntityAnimalMF targetMate;
	private int breeding;

	@SuppressWarnings("WeakerAccess")
	public GOTEntityAIMFMate(GOTEntityAnimalMF animal, double d) {
		theAnimal = animal;
		theWorld = animal.worldObj;
		moveSpeed = d;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return targetMate.isEntityAlive() && targetMate.isInLove() && breeding < 60;
	}

	private GOTEntityAnimalMF findMate() {
		float searchRadius = 8.0f;
		Class<? extends GOTEntityAnimalMF> mateClass = theAnimal.getAnimalMFBaseClass();
		List<? extends GOTEntityAnimalMF> entities = theWorld.getEntitiesWithinAABB(mateClass, theAnimal.boundingBox.expand(searchRadius, searchRadius, searchRadius));

		for (GOTEntityAnimalMF entity : entities) {
			if (theAnimal.canMateWith(entity)) {
				return entity;
			}
		}
		return null;
	}

	private void procreate() {
		EntityAgeable babyAnimal = theAnimal.createChild(targetMate);
		if (babyAnimal != null) {
			theAnimal.setGrowingAge(6000);
			targetMate.setGrowingAge(6000);
			theAnimal.resetInLove();
			targetMate.resetInLove();
			babyAnimal.setGrowingAge(-24000);
			babyAnimal.setLocationAndAngles(theAnimal.posX, theAnimal.posY, theAnimal.posZ, 0.0f, 0.0f);
			theWorld.spawnEntityInWorld(babyAnimal);
			Random rand = theAnimal.getRNG();
			for (int i = 0; i < 7; ++i) {
				double d = theAnimal.posX + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * theAnimal.width;
				double d1 = theAnimal.posY + 0.5 + rand.nextFloat() * theAnimal.height;
				double d2 = theAnimal.posZ + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * theAnimal.width;
				double d3 = rand.nextGaussian() * 0.02;
				double d4 = rand.nextGaussian() * 0.02;
				double d5 = rand.nextGaussian() * 0.02;
				theWorld.spawnParticle("heart", d, d1, d2, d3, d4, d5);
			}
			if (GOT.canDropLoot(theWorld)) {
				theWorld.spawnEntityInWorld(new EntityXPOrb(theWorld, theAnimal.posX, theAnimal.posY, theAnimal.posZ, rand.nextInt(4) + 1));
			}
		}
	}

	@Override
	public void resetTask() {
		targetMate = null;
		breeding = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (!theAnimal.isInLove()) {
			return false;
		}
		targetMate = findMate();
		return targetMate != null;
	}

	@Override
	public void updateTask() {
		theAnimal.getLookHelper().setLookPositionWithEntity(targetMate, 10.0f, theAnimal.getVerticalFaceSpeed());
		theAnimal.getNavigator().tryMoveToEntityLiving(targetMate, moveSpeed);
		++breeding;
		if (breeding == 60 && theAnimal.getDistanceSqToEntity(targetMate) < 9.0) {
			procreate();
		}
	}
}