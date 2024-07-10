package got.common.entity.westeros.legendary.deco;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntitySansaStark extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySansaStark(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killSansaStark;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/sansa_friendly";
		}
		return "legendary/sansa_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}