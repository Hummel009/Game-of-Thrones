package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public abstract class GOTEntityHumanBase extends GOTEntityNPC {
	public GOTEntityHumanBase(World world) {
		super(world);
		canBeMarried = false;
		if (familyInfo.isMale() && familyInfo.age >= 0 && !isLegendaryNPC && !isNotHuman) {
			tasks.addTask(6, new GOTEntityAISmoke(this, 8000));
		}
		tasks.addTask(2, new GOTEntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
		tasks.addTask(5, new GOTEntityAINPCMarry(this, 1.3));
		tasks.addTask(6, new GOTEntityAINPCMate(this, 1.3));
		tasks.addTask(7, new GOTEntityAINPCFollowParent(this, 1.4));
		familyInfo.marriageEntityClass = this.getClass();
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killer;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (canBeMarried && familyInfo.interact(entityplayer)) {
			return true;
		}
		return super.interact(entityplayer);
	}

	@Override
	public void onArtificalSpawn() {
		if (canBeMarried && this.getClass() == familyInfo.marriageEntityClass && rand.nextInt(7) == 0) {
			familyInfo.setChild();
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public boolean speakTo(EntityPlayer entityplayer) {
		boolean flag = super.speakTo(entityplayer);
		if (flag && isDrunkard() && entityplayer.isPotionActive(Potion.confusion.id)) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.speakToDrunkard);
		}
		return flag;
	}
}
