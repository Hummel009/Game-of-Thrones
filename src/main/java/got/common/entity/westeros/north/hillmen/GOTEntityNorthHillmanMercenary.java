package got.common.entity.westeros.north.hillmen;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.entity.other.GOTMercenary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanMercenary extends GOTEntityNorthHillmanWarrior implements GOTMercenary {
	public GOTEntityNorthHillmanMercenary(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public float getMercAlignmentRequired() {
		return 10.0f;
	}

	@Override
	public int getMercBaseCost() {
		return 5;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standart/wild/hired_soldier";
			}
			return "standart/wild/usual_friendly";
		}
		return "standart/wild/usual_hostile";
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}
}
