package got.common.entity.westeros.legendary;

import got.common.database.GOTNames;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityCrasterWife extends GOTEntityHumanBase implements GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrasterWife(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WILDLING;
	}

	@Override
	public boolean isNotAttackable() {
		return true;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/cwife_friendly";
		}
		return "legendary/cwife_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getWildName(rand, familyInfo.isMale()));
	}
}