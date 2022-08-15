package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import net.minecraft.block.Block;

public class GOTBiomeRedSea extends GOTBiomeJogosNhai {
	public GOTBiomeRedSea(int i, boolean major) {
		super(i, major);
		decorator.clearRandomStructures();
		decorator.clearVillages();
		npcSpawnList.clear();
		biomeColors.setWater(0x640a0a);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterRedSea;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	public GOTBiomeRedSea setLakeBlock(Block block) {
		topBlock = block;
		fillerBlock = block;
		return this;
	}
}
