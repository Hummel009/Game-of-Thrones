package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAINPCFollowPlayer;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityHodor extends GOTEntityHumanBase {
	public GOTEntityHodor(World world) {
		super(world);
		addTargetTasks(false);
		setupLegendaryNPC(true);
		setSize(0.6f * 1.3f, 1.8f * 1.3f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(2, new GOTEntityAINPCFollowPlayer(this, 12.0f, 1.5));
		tasks.addTask(3, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(3.0);
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.NORTH;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killHodor;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return "legendary/hodor";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}