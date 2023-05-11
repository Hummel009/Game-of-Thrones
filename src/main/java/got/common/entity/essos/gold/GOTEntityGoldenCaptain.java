package got.common.entity.essos.gold;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityGoldenCaptain extends GOTEntityGoldenWarrior implements GOTUnitTradeable {
	public GOTEntityGoldenCaptain(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "standart/civilized/usual_friendly";
			}
			return "standart/civilized/usual_neutral";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.GOLDENCOMPANY;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}
