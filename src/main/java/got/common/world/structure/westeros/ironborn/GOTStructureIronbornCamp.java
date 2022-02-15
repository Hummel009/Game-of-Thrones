package got.common.world.structure.westeros.ironborn;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.legendary.warrior.GOTEntityVictarionGreyjoy;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureIronbornCamp extends GOTStructureCampBase {
	public GOTStructureIronbornCamp(boolean flag) {
		super(flag);
	}

	@Override
	public GOTStructureBase createTent(boolean flag, Random random) {
		return new GOTStructureIronbornTent(false);
	}

	@Override
	public boolean generateFarm() {
		return false;
	}

	@Override
	public GOTEntityNPC getCampCaptain(World world, Random random) {
		return new GOTEntityVictarionGreyjoy(world);
	}

	@Override
	public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 20; ++l) {
			spawnNPCAndSetHome(new GOTEntityIronbornSoldier(world), world, 0, 1, 0, 16);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tableBlock = GOTRegistry.commandTable;
	}
}