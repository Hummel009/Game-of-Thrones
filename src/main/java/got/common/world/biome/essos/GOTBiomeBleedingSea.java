package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

import java.awt.*;

public class GOTBiomeBleedingSea extends GOTBiomeEssosBase {
	public GOTBiomeBleedingSea(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		biomeColors.setWater(new Color(0x640a0a));

		biomeWaypoints = GOTWaypoint.Region.MOSSOVY;
		biomeAchievement = GOTAchievement.enterBleedingSea;
		enableRiver = false;
		banditChance = GOTEventSpawner.EventChance.NEVER;
		wallBlock = GOTBezierType.WALL_YI_TI;
	}
}