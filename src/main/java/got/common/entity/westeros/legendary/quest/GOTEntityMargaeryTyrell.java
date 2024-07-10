package got.common.entity.westeros.legendary.quest;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityMargaeryTyrell extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMargaeryTyrell(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.REACH;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.MARGAERY;
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killMargaeryTyrell;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/margaery_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}