package got.common.entity.westeros.legendary.deco;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;

public class GOTEntityRobinArryn extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRobinArryn(World world) {
		super(world);
		setupLegendaryNPC(true);
		setSize(0.6f * 0.75f, 1.8f * 0.75f);
		faction = GOTFaction.ARRYN;
		alignmentBonus = 100.0f;
		killAchievement = GOTAchievement.killRobinArryn;
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