package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

public class GOTEntityUlthosSpider extends GOTEntitySpiderBase implements GOTBiome.ImmuneToHeat, GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityUlthosSpider(World world) {
		super(world);
		spawnsInDarkness = true;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.ULTHOS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killUlthos;
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