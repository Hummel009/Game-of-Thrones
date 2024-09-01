package got.common.entity.westeros.legendary.deco;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class GOTEntityShae extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityShae(World world) {
		super(world);
		addTargetTasks(false);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getReputationBonus() {
		return 1.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killShae;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}