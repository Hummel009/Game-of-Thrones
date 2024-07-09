package got.common.entity.essos.legendary;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityMissandei extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMissandei(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.GHISCAR;
		killAchievement = GOTAchievement.killMissandei;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/missandei_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}