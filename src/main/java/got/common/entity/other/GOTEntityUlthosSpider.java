package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityUlthosSpider extends GOTEntitySpiderBase implements GOTBiome.ImmuneToHeat, GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityUlthosSpider(World world) {
		super(world);
		spawnsInDarkness = true;
		faction = GOTFaction.ULTHOS;
		alignmentBonus = 2.0f;
		killAchievement = GOTAchievement.killUlthosSpider;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && riddenByEntity instanceof EntityPlayer && isMountSaddled()) {
			GOTLevelData.getData((EntityPlayer) riddenByEntity).addAchievement(GOTAchievement.rideUlthosSpider);
		}
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