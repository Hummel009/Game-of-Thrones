package got.common.entity.essos.legendary.quest;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityDaenerysTargaryen extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDaenerysTargaryen(World world) {
		super(world);
		setupLegendaryNPC(true);
		isImmuneToFire = true;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DOTHRAKI;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.DAENERYS;
	}

	@Override
	public float getAlignmentBonus() {
		return 500.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killDaenerysTargaryen;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.targaryenBoots, 1);
		dropItem(GOTItems.targaryenLeggings, 1);
		dropItem(GOTItems.targaryenChestplate, 1);
		dropItem(GOTItems.targaryenHelmet, 1);

		dropItem(GOTItems.bloodOfTrueKings, 1);
		dropItem(GOTItems.blackArakh, 1);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/daenerys_friendly";
		}
		return "legendary/daenerys_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}