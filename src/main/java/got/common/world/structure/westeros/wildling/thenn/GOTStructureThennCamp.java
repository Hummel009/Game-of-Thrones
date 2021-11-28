package got.common.world.structure.westeros.wildling.thenn;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.other.*;
import got.common.entity.westeros.wildling.thenn.*;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureThennCamp extends GOTStructureCampBase {
	public GOTStructureThennCamp(boolean flag) {
		super(flag);
	}

	@Override
	public GOTStructureBase createTent(boolean flag, Random random) {
		return new GOTStructureThennTent(false);
	}

	@Override
	public boolean generateFarm() {
		return false;
	}

	@Override
	public GOTEntityNPC getCampCaptain(World world, Random random) {
		return new GOTEntityThennMagnar(world);
	}

	@Override
	public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityThenn.class);
		respawner.setCheckRanges(24, -12, 12, 12);
		respawner.setSpawnRanges(8, -4, 4, 16);
		this.placeNPCRespawner(respawner, world, i, j, k);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tableBlock = GOTRegistry.commandTable;
	}

	@Override
	public boolean spawnSmith() {
		return true;
	}
}