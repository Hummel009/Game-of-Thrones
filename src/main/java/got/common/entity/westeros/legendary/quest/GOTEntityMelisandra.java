package got.common.entity.westeros.legendary.quest;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityMelisandra extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMelisandra(World world) {
		super(world);
		setupLegendaryNPC(true);
		isImmuneToFire = true;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DRAGONSTONE;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.MELISANDRA;
	}

	@Override
	public float getReputationBonus() {
		return 300.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killMelisandra;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.ruby, 1);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/melisandra_friendly";
		}
		return "special/father_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}