package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityHodor extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHodor(World world) {
		super(world);
		addTargetTasks(false);
		setupLegendaryNPC(true);
		setSize(0.6f * 1.3f, 1.8f * 1.3f);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.NORTH;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killHodor;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return "legendary/hodor";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}