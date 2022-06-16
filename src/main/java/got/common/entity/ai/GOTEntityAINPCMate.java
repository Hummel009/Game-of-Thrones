package got.common.entity.ai;

import got.common.entity.other.*;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class GOTEntityAINPCMate extends EntityAIBase {
	public GOTEntityNPC theNPC;
	public World theWorld;
	public GOTEntityNPC theSpouse;
	public int spawnBabyDelay = 0;
	public double moveSpeed;

	public GOTEntityAINPCMate(GOTEntityNPC npc, double d) {
		theNPC = npc;
		theWorld = npc.worldObj;
		moveSpeed = d;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return theSpouse.isEntityAlive() && spawnBabyDelay < 60 && theNPC.familyInfo.getAge() == 0 && theSpouse.familyInfo.getAge() == 0;
	}

	@Override
	public void resetTask() {
		theSpouse = null;
		spawnBabyDelay = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (theNPC.getClass() != theNPC.familyInfo.marriageEntityClass || theNPC.familyInfo.spouseUniqueID == null || theNPC.familyInfo.children >= 3 || theNPC.familyInfo.getAge() != 0) {
			return false;
		}
		theSpouse = theNPC.familyInfo.getSpouse();
		return theSpouse != null && theNPC.getDistanceToEntity(theSpouse) < 16.0 && theSpouse.familyInfo.children < 3 && theSpouse.familyInfo.getAge() == 0;
	}

	public void spawnBaby() {
		GOTEntityNPC baby = (GOTEntityNPC) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(theNPC.familyInfo.marriageEntityClass), theWorld);
		GOTEntityNPC maleParent = theNPC.familyInfo.isMale() ? theNPC : theSpouse;
		GOTEntityNPC femaleParent = theNPC.familyInfo.isMale() ? theSpouse : theNPC;
		baby.familyInfo.setChild();
		baby.familyInfo.setMale(baby.getRNG().nextBoolean());
		baby.familyInfo.maleParentID = maleParent.getUniqueID();
		baby.familyInfo.femaleParentID = femaleParent.getUniqueID();
		baby.createNPCChildName(maleParent, femaleParent);
		baby.onSpawnWithEgg(null);
		baby.setLocationAndAngles(theNPC.posX, theNPC.posY, theNPC.posZ, 0.0f, 0.0f);
		baby.isNPCPersistent = true;
		theWorld.spawnEntityInWorld(baby);
		theNPC.familyInfo.setMaxBreedingDelay();
		theSpouse.familyInfo.setMaxBreedingDelay();
		++theNPC.familyInfo.children;
		++theSpouse.familyInfo.children;
		theNPC.spawnHearts();
		theSpouse.spawnHearts();
	}

	@Override
	public void updateTask() {
		theNPC.getLookHelper().setLookPositionWithEntity(theSpouse, 10.0f, theNPC.getVerticalFaceSpeed());
		theNPC.getNavigator().tryMoveToEntityLiving(theSpouse, moveSpeed);
		++spawnBabyDelay;
		if (spawnBabyDelay % 20 == 0) {
			theNPC.spawnHearts();
		}
		if (spawnBabyDelay >= 60 && theNPC.getDistanceSqToEntity(theSpouse) < 9.0) {
			spawnBaby();
		}
	}
}
