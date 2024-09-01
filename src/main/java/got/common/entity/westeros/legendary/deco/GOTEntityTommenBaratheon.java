package got.common.entity.westeros.legendary.deco;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class GOTEntityTommenBaratheon extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTommenBaratheon(World world) {
		super(world);
		setupLegendaryNPC(true);
		setSize(0.6f * 0.75f, 1.8f * 0.75f);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getReputationBonus() {
		return 500.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killTommenBaratheon;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}