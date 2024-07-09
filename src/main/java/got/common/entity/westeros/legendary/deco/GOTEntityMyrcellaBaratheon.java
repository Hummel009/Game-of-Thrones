package got.common.entity.westeros.legendary.deco;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class GOTEntityMyrcellaBaratheon extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMyrcellaBaratheon(World world) {
		super(world);
		setupLegendaryNPC(true);
		setSize(0.6f * 0.9f, 1.8f * 0.9f);
		faction = GOTFaction.CROWNLANDS;
		alignmentBonus = 500.0f;
		killAchievement = GOTAchievement.killMyrcellaBaratheon;
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