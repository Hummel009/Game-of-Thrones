package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.entity.ai.*;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class GOTEntityHumanBase extends GOTEntityNPC {
	private final boolean canBeMarried;

	protected GOTEntityHumanBase(World world) {
		super(world);
		canBeMarried = GOTEntityUtils.canBeMarried(this);
		if (canBeMarried) {
			tasks.addTask(2, new GOTEntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
			tasks.addTask(5, new GOTEntityAINPCMarry(this, 1.3));
			tasks.addTask(6, new GOTEntityAINPCMate(this, 1.3));
			tasks.addTask(7, new GOTEntityAINPCFollowParent(this, 1.4));
			tasks.addTask(8, new GOTEntityAINPCFollowSpouse(this, 1.1));
			tasks.addTask(7, new GOTEntityAINPCFollowParent(this, 1.4));
			familyInfo.setMarriageEntityClass(getClass());
		}

		boolean canSmoke = GOTEntityUtils.canSmokeDrink(this);
		if (canSmoke) {
			tasks.addTask(6, new GOTEntityAISmoke(this, 8000));
		}
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return isLegendaryNPC ? GOTAchievement.killLegendaryNPC : GOTAchievement.killNPC;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return canBeMarried && familyInfo.interact(entityplayer) || super.interact(entityplayer);
	}

	@Override
	public void onArtificalSpawn() {
		if (canBeMarried && getClass() == familyInfo.getMarriageEntityClass() && rand.nextInt(7) == 0) {
			familyInfo.setChild();
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}