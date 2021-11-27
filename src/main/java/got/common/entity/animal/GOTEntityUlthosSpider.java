package got.common.entity.animal;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntitySpiderBase;
import got.common.faction.GOTFaction;
import net.minecraft.world.World;

public class GOTEntityUlthosSpider extends GOTEntitySpiderBase {
	public GOTEntityUlthosSpider(World world) {
		super(world);
		spawnsInDarkness = false;
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.ULTHOS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.KILL_ULTHOS;
	}

	@Override
	public int getRandomSpiderScale() {
		return rand.nextInt(3);
	}

	@Override
	public int getRandomSpiderType() {
		return rand.nextBoolean() ? 0 : 1 + rand.nextInt(2);
	}
}
