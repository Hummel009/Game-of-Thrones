package got.common.entity.ai;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTAlignmentValues;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityAINPCMarry extends EntityAIBase {
	private final GOTEntityNPC theNPC;
	private final World theWorld;
	private final double moveSpeed;

	private GOTEntityNPC theSpouse;
	private int marryDelay;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAINPCMarry(GOTEntityNPC npc, double d) {
		theNPC = npc;
		theWorld = npc.worldObj;
		moveSpeed = d;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return theSpouse != null && theSpouse.isEntityAlive() && theNPC.getFamilyInfo().canMarryNPC(theSpouse) && theSpouse.getFamilyInfo().canMarryNPC(theNPC);
	}

	private void marry() {
		int maxChildren = theNPC.getFamilyInfo().getRandomMaxChildren();
		theNPC.getFamilyInfo().setSpouseUniqueID(theSpouse.getUniqueID());
		theSpouse.getFamilyInfo().setSpouseUniqueID(theNPC.getUniqueID());
		theNPC.setCurrentItemOrArmor(0, null);
		theNPC.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		theSpouse.setCurrentItemOrArmor(0, null);
		theSpouse.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		theNPC.getFamilyInfo().setMaxChildren(maxChildren);
		theSpouse.getFamilyInfo().setMaxChildren(maxChildren);
		theNPC.getFamilyInfo().setMaxBreedingDelay();
		theSpouse.getFamilyInfo().setMaxBreedingDelay();
		theNPC.spawnHearts();
		theSpouse.spawnHearts();
		EntityPlayer ringPlayer = theNPC.getFamilyInfo().getRingGivingPlayer();
		if (ringPlayer != null) {
			GOTLevelData.getData(ringPlayer).addAlignment(ringPlayer, GOTAlignmentValues.MARRIAGE_BONUS, theNPC.getFaction(), theNPC);

		}
		EntityPlayer ringPlayerSpouse = theSpouse.getFamilyInfo().getRingGivingPlayer();
		if (ringPlayerSpouse != null) {
			GOTLevelData.getData(ringPlayerSpouse).addAlignment(ringPlayerSpouse, GOTAlignmentValues.MARRIAGE_BONUS, theSpouse.getFaction(), theSpouse);
			GOTLevelData.getData(ringPlayer).addAchievement(GOTAchievement.marry);
		}
		theWorld.spawnEntityInWorld(new EntityXPOrb(theWorld, theNPC.posX, theNPC.posY, theNPC.posZ, theNPC.getRNG().nextInt(8) + 2));
	}

	@Override
	public void resetTask() {
		theSpouse = null;
		marryDelay = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (theNPC.getClass() != theNPC.getFamilyInfo().getMarriageEntityClass() || theNPC.getFamilyInfo().getSpouseUniqueID() != null || theNPC.getFamilyInfo().getAge() != 0 || theNPC.getEquipmentInSlot(4) != null || theNPC.getEquipmentInSlot(0) == null) {
			return false;
		}
		List<GOTEntityNPC> list = theNPC.worldObj.getEntitiesWithinAABB(theNPC.getFamilyInfo().getMarriageEntityClass(), theNPC.boundingBox.expand(16.0, 4.0, 16.0));
		GOTEntityNPC spouse = null;
		double distanceSq = Double.MAX_VALUE;
		for (GOTEntityNPC candidate : list) {
			double d;
			if (!theNPC.getFamilyInfo().canMarryNPC(candidate) || !candidate.getFamilyInfo().canMarryNPC(theNPC) || (d = theNPC.getDistanceSqToEntity(candidate)) > distanceSq) {
				continue;
			}
			distanceSq = d;
			spouse = candidate;
		}
		if (spouse == null) {
			return false;
		}
		theSpouse = spouse;
		return true;
	}

	@Override
	public void updateTask() {
		theNPC.getLookHelper().setLookPositionWithEntity(theSpouse, 10.0f, theNPC.getVerticalFaceSpeed());
		theNPC.getNavigator().tryMoveToEntityLiving(theSpouse, moveSpeed);
		++marryDelay;
		if (marryDelay % 20 == 0) {
			theNPC.spawnHearts();
		}
		if (marryDelay >= 60 && theNPC.getDistanceSqToEntity(theSpouse) < 9.0) {
			marry();
		}
	}
}