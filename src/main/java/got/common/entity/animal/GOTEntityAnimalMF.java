package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityRegistry;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class GOTEntityAnimalMF extends EntityAnimal implements GOTBiome.ImmuneToFrost {
	protected GOTEntityAnimalMF(World world) {
		super(world);
	}

	@Override
	public boolean canMateWith(EntityAnimal mate) {
		GOTEntityAnimalMF mfMate = (GOTEntityAnimalMF) mate;
		if (mate == this) {
			return false;
		}
		if (getAnimalMFBaseClass().equals(mfMate.getAnimalMFBaseClass()) && isInLove() && mate.isInLove()) {
			boolean thisMale = isMale();
			return thisMale != mfMate.isMale();
		}
		return false;
	}

	public abstract Class<? extends GOTEntityAnimalMF> getAnimalMFBaseClass();

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTRegistry.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	public abstract boolean isMale();
}
