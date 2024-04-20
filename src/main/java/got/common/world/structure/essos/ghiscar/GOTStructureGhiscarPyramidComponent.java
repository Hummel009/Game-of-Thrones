package got.common.world.structure.essos.ghiscar;

import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.Random;

public class GOTStructureGhiscarPyramidComponent extends StructureComponent {
	private static final GOTStructureBase PYRAMID_GEN = new GOTStructureGhiscarPyramid(false).setRestrictions(false);
	private static final Random PYRAMID_RAND = new Random();
	private int posX;
	private int posY = -1;
	private int posZ;
	private int direction;
	private long pyramidSeed = -1L;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureGhiscarPyramidComponent() {
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureGhiscarPyramidComponent(int l, Random random, int i, int k) {
		super(l);
		int r = GOTStructureGhiscarPyramid.RADIUS + 5;
		boundingBox = new StructureBoundingBox(i - r, 0, k - r, i + r, 255, k + r);
		posX = i;
		posZ = k;
		direction = random.nextInt(4);
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
		if (posY == -1) {
			posY = world.getTopSolidOrLiquidBlock(structureBoundingBox.getCenterX(), structureBoundingBox.getCenterZ());
		}
		if (pyramidSeed == -1L) {
			pyramidSeed = random.nextLong();
		}
		PYRAMID_GEN.setStructureBB(structureBoundingBox);
		PYRAMID_RAND.setSeed(pyramidSeed);
		PYRAMID_GEN.generate(world, PYRAMID_RAND, posX, posY, posZ, direction);
		return true;
	}

	@Override
	public void func_143011_b(NBTTagCompound nbt) {
		posX = nbt.getInteger("PyrX");
		posY = nbt.getInteger("PyrY");
		posZ = nbt.getInteger("PyrZ");
		direction = nbt.getInteger("Direction");
		pyramidSeed = nbt.getLong("Seed");
	}

	@Override
	public void func_143012_a(NBTTagCompound nbt) {
		nbt.setInteger("PyrX", posX);
		nbt.setInteger("PyrY", posY);
		nbt.setInteger("PyrZ", posZ);
		nbt.setInteger("Direction", direction);
		nbt.setLong("Seed", pyramidSeed);
	}
}