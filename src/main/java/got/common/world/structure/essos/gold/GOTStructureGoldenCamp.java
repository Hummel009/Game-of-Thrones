package got.common.world.structure.essos.gold;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.essos.gold.*;
import got.common.entity.other.*;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureGoldenCamp extends GOTStructureCampBase {
	public GOTStructureGoldenCamp(boolean flag) {
		super(flag);
	}

	@Override
	public GOTStructureBase createTent(boolean flag, Random random) {
		return new GOTStructureGoldenTent(false);
	}

	@Override
	public boolean generateFarm() {
		return false;
	}

	@Override
	public GOTEntityNPC getCampCaptain(World world, Random random) {
		return new GOTEntityGoldenCaptain(world);
	}

	@Override
	public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityGoldenWarrior.class);
		respawner.setCheckRanges(24, -12, 12, 12);
		respawner.setSpawnRanges(8, -4, 4, 16);
		this.placeNPCRespawner(respawner, world, i, j, k);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tableBlock = GOTRegistry.commandTable;
	}
}