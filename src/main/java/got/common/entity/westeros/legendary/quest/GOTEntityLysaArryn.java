package got.common.entity.westeros.legendary.quest;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.world.World;

public class GOTEntityLysaArryn extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLysaArryn(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.ARRYN;
		miniQuestFactory = GOTMiniQuestFactory.LYSA;
		alignmentBonus = 100.0f;
		killAchievement = GOTAchievement.killLysaArryn;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}