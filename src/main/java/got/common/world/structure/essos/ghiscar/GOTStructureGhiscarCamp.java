package got.common.world.structure.essos.ghiscar;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.other.*;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureGhiscarCamp extends GOTStructureCampBase {
	public GOTStructureGhiscarCamp(boolean flag) {
		super(flag);
	}

	@Override
	public GOTStructureBase createTent(boolean flag, Random random) {
		return new GOTStructureGhiscarTent(false);
	}

	@Override
	public GOTEntityNPC getCampCaptain(World world, Random random) {
		return new GOTEntityGhiscarAdmiral(world);
	}

	@Override
	public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityGhiscarCorsair.class);
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
